package com.bac.applicationaccount.impl;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.atMost;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import javax.annotation.Resource;
import javax.security.auth.Subject;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.UnsupportedCallbackException;
import javax.security.auth.login.Configuration;
import javax.security.auth.login.LoginContext;
import javax.security.auth.login.LoginException;
import javax.security.auth.spi.LoginModule;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bac.applicationaccount.AccessLevel;
import com.bac.applicationaccount.Account;
import com.bac.applicationaccount.AccountUser;
import com.bac.applicationaccount.Application;
import com.bac.applicationaccount.User;
import com.bac.applicationaccount.impl.ApplicationAccountLogin;
import com.bac.applicationaccount.impl.PasswordAuthentication;
import com.bac.applicationaccount.impl.SimpleComponentFactory;

public class LoginTests extends AbstractHibernateApplicationTestCase {

	private final String LOGIN_HARNESS_CONFIG_FILE_LOCATION = "src/test/resources/test_mock_harness_jaas.config";
	private final String LOGIN_CONFIG_PROPERTY_NAME = "java.security.auth.login.config";
	private final String APPLICATION_ACCOUNT_LOGIN_CONFIG_FILE_LOCATION = "src/test/resources/test_application_jaas.config";

	private static final Logger logger = LoggerFactory.getLogger(LoginTests.class);

	@Resource(name = "login")
	ApplicationAccountLogin instance;

	@Before
	public void setTheEnvironment() {

		System.setProperty(LOGIN_CONFIG_PROPERTY_NAME, APPLICATION_ACCOUNT_LOGIN_CONFIG_FILE_LOCATION);
		Configuration.setConfiguration(null);
	}

	/*
	 * Override the normal config file to direct call to a Mock object.
	 * sequence. When a login is valid then the commit() method should also be
	 * called.
	 */
	@Test
	public void set_The_Config_File_With_Valid_Login()
			throws IOException, UnsupportedCallbackException, LoginException {

		logger.info("set_The_Config_File_With_Valid_Login");

		final String applicationName = "ApplicationAccount";
		final String callbackUser = "user@Email.address";
		final char[] callbackPassword = "user@Email.address's password".toCharArray();
		final CallbackHandler callbackHandler = this.getCallbackHandler(callbackUser, callbackPassword);

		System.setProperty(LOGIN_CONFIG_PROPERTY_NAME, LOGIN_HARNESS_CONFIG_FILE_LOCATION);

		final LoginModule mockLoginModule = Mockito.mock(LoginModule.class);
		Mockito.when(mockLoginModule.login()).thenReturn(true);
		Mockito.when(mockLoginModule.commit()).thenReturn(true);
		// Set the delegate to the mock object
		LoginModuleHarness.setDelegate(mockLoginModule);

		LoginContext lc = new LoginContext(applicationName, callbackHandler);
		//
		// Login Context initialises the Login Module
		//
		//
		lc.login();
		//
		assertNotNull(lc.getSubject());
		verify(mockLoginModule, atMost(1)).initialize(anyObject(), anyObject(), anyObject(), anyObject());
		verify(mockLoginModule, atMost(1)).login();
		verify(mockLoginModule, atMost(1)).commit();
		verify(mockLoginModule, never()).abort();

		verify(callbackHandler, atMost(1)).handle(anyObject());
	}

	/*
	 * Override the test config file and provide a Mock object instead. The
	 * login should throw an Exception
	 */
	@Test(expected = LoginException.class)
	public void set_The_Config_File_With_Invalid_Login()
			throws IOException, UnsupportedCallbackException, LoginException {

		logger.info("set_The_Config_File_With_Invalid_Login");

		System.setProperty(LOGIN_CONFIG_PROPERTY_NAME, LOGIN_HARNESS_CONFIG_FILE_LOCATION);

		final String applicationName = "ApplicationAccount";
		final CallbackHandler callbackHandler = this.getCallbackHandler("any", "any".toCharArray());

		final LoginModule mockLoginModule = Mockito.mock(LoginModule.class);
		Mockito.when(mockLoginModule.login()).thenReturn(false);
		// Set the delegate to the mock object
		LoginModuleHarness.setDelegate(mockLoginModule);

		LoginContext lc = new LoginContext(applicationName, callbackHandler);
		//
		// Login Context initialises the Login Module
		//
		lc.login();
	}

	/*
	 * Override the test config file and provide a Mock object instead.
	 * Attempting to authenticate on an unknown application should throw an
	 * Exception
	 * 
	 */
	@Test(expected = LoginException.class)
	public void set_The_Config_File_For_Invalid_Application()
			throws IOException, UnsupportedCallbackException, LoginException {

		logger.info("set_The_Config_File_For_Invalid_Application");

		System.setProperty(LOGIN_CONFIG_PROPERTY_NAME, LOGIN_HARNESS_CONFIG_FILE_LOCATION);

		final CallbackHandler callbackHandler = this.getCallbackHandler("any", "any".toCharArray());
		new LoginContext("any", callbackHandler);
	}

	/*
	 * Attempting to authenticate on an unknown application should throw an
	 * Exception
	 * 
	 */
	@Test(expected = LoginException.class)
	public void invalid_Application_Should_Throw_exception()
			throws IOException, UnsupportedCallbackException, LoginException {

		logger.info("invalid_Application_Should_Throw_exception");

		final CallbackHandler callbackHandler = this.getCallbackHandler("any", "any".toCharArray());

		instance.login("any", callbackHandler);
	}

	/*
	 * Attempting to login on an valid application with an unknown User should
	 * throw an Exception
	 * 
	 */
	@Test(expected = LoginException.class)
	public void valid_Application_Invalid_User_Should_Throw_exception()
			throws IOException, UnsupportedCallbackException, LoginException {

		logger.info("valid_Application_Invalid_User_Should_Throw_exception");

		final String applicationName = "ApplicationAccount";

		final CallbackHandler callbackHandler = this.getCallbackHandler("any", "any".toCharArray());

		instance.login(applicationName, callbackHandler);
	}

	/*
	 * Login on an valid application with an valid User only should return a
	 * valid User in the public credentials but without any Account credentials
	 * 
	 */
	@Test
	public void valid_Application_With_Valid_User_Only() 
			throws IOException, UnsupportedCallbackException,
			LoginException, NoSuchAlgorithmException, InvalidKeySpecException {

		logger.info("valid_Application_With_Valid_User_Only");

		final String userKey = "user@Email.address";
		final String userPassword = "user@Email.address's password";
		final String applicationName = "ApplicationAccount";

		createUser(userKey, userPassword);

		final CallbackHandler callbackHandler = getCallbackHandler(userKey, userPassword.toCharArray());

		Subject result = instance.login(applicationName, callbackHandler);
		assertNotNull(result);
		assertFalse(result.getPublicCredentials(User.class).isEmpty());
		assertTrue(result.getPublicCredentials(Account.class).isEmpty());
	}

	/*
	 * Login with a valid User and Account/Application should result in User and
	 * Account credentials *assuming that the appropriate Subject decorator has been set*.
	 * 
	 */
	@Test
	public void valid_Application_With_Valid_User_Association() 
			throws NoSuchAlgorithmException, InvalidKeySpecException, IOException
			, UnsupportedCallbackException, LoginException, SecurityException {
		
		logger.info("valid_Application_With_Valid_User_Only");		

		final String userKey = "user@Email.address";
		final String userPassword = "user@Email.address's password";
		final String applicationName = "ApplicationAccount";
		createUserApplicationAssociation(userKey, userPassword, applicationName );
		
		final CallbackHandler callbackHandler = getCallbackHandler(userKey, userPassword.toCharArray());
		
		Subject result = instance.login(applicationName, callbackHandler);
		assertNotNull(result);
		assertFalse(result.getPublicCredentials(User.class).isEmpty());
		assertFalse(result.getPublicCredentials(Account.class).isEmpty());
	}
	
	

	// ************************************************************************************************
	// Convenience Methods
	// ************************************************************************************************
	private void createUserApplicationAssociation(String userKey, String password, String applicationName) 
			throws NoSuchAlgorithmException, InvalidKeySpecException {
		
		
		createAccountUser(
				createAccount(createApplication(applicationName))
				, createUser(userKey, password) 
				, createAccessLevel());
	}
	
	private Application createApplication(String applicationName) {
		
		Application application = SimpleComponentFactory.getApplication();
		application.setName(applicationName);
		return dao.createApplication(application);
	}
	
	private AccountUser createAccountUser(Account account, User user, AccessLevel accessLevel) {
		
		AccountUser accountUser =  SimpleComponentFactory.getAccountUser();
		accountUser.setAccessLevelId(accessLevel.getId());
		accountUser.setUserId(user.getId());
		accountUser.setAccountId(account.getId());
		return dao.createAccountUser(accountUser);
	}
	
	private AccessLevel createAccessLevel() {

		return dao.createAccessLevel(SimpleComponentFactory.getAccessLevel());
	}
	
	private Account createAccount(Application application) {
		
		Account account = SimpleComponentFactory.getAccount();
		account.setApplicationId(application.getId());
		return dao.createAccount(account);
	}
	
	private User createUser(String userKey, String password)
			throws NoSuchAlgorithmException, InvalidKeySpecException {

		//
		// Create a User with supplied details
		//
		final User user = SimpleComponentFactory.getUser();
		user.setUserKey(userKey);
		user.setPasswordSalt(PasswordAuthentication.generateSalt());
		user.setUserPassword(
				PasswordAuthentication.getEncryptedPassword(password.toCharArray(), user.getPasswordSalt()));
		return dao.createUser(user);
	}

}
