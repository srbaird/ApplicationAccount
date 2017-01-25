package com.bac.applicationaccount.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Collections;
import java.util.Map;

import javax.annotation.Resource;
import javax.security.auth.Subject;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.UnsupportedCallbackException;
import javax.security.auth.login.LoginException;
import javax.security.auth.spi.LoginModule;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bac.applicationaccount.User;
import com.bac.applicationaccount.impl.PasswordAuthentication;
import com.bac.applicationaccount.impl.SimpleComponentFactory;

public class AccountLoginModuleTests extends AbstractHibernateApplicationTestCase {

	private static final Logger logger = LoggerFactory.getLogger(AccountLoginModuleTests.class);

	@Resource(name = "loginModule")
	LoginModule instance;

	/*
	 * The login process should return true when a valid key/password is
	 * provided
	 */
	@Test
	public void loginWithAuthenticUserShouldBeValid() throws IOException, UnsupportedCallbackException,
			NoSuchAlgorithmException, InvalidKeySpecException, LoginException {

		logger.info("loginWithAuthenticUserShouldBeValid");
		//
		// Login details
		//
		final String userKey = "someone@email.format";
		final char[] passwordArray = "someone@email.format's valid password".toCharArray();
		//
		// Create a User with the above login details
		//
		final User user = SimpleComponentFactory.getUser();
		user.setUserEmail(userKey);
		user.setPasswordSalt(PasswordAuthentication.generateSalt());
		user.setUserPassword(PasswordAuthentication.getEncryptedPassword(passwordArray, user.getPasswordSalt()));
		dao.createUser(user);
		//
		// Initialize with a callback handler supplying the login credentials
		//
		Subject subject = new Subject();
		CallbackHandler callbackHandler = getCallbackHandler(userKey, passwordArray);
		Map<String, ?> sharedState = Collections.emptyMap();
		Map<String, ?> options = Collections.emptyMap();
		instance.initialize(subject, callbackHandler, sharedState, options);
		//
		// Login module should query the data source for the user specified in
		// the callback handler
		//
		final boolean authenticated = true;
		assertEquals(authenticated, instance.login());
	}

	/*
	 * The login process should return fail when an invalid key/password is
	 * provided
	 */
	@Test(expected = LoginException.class)
	public void loginWithIncorrectCredentialsShouldBeInvalid() throws NoSuchAlgorithmException, InvalidKeySpecException,
			IOException, UnsupportedCallbackException, LoginException {

		logger.info("loginWithIncorrectCredentialsShouldBeInvalid");
		//
		// Login details
		//
		final String userKey = "someone@email.format";
		final char[] usePasswordArray = "someone@email.format's valid password".toCharArray();
		final char[] invalidPasswordAttempt = "some other password attempt".toCharArray();
		//
		// Create a User with the above login details
		//
		final User user = SimpleComponentFactory.getUser();
		user.setUserEmail(userKey);
		user.setPasswordSalt(PasswordAuthentication.generateSalt());
		user.setUserPassword(PasswordAuthentication.getEncryptedPassword(usePasswordArray, user.getPasswordSalt()));
		dao.createUser(user);
		//
		// Initialize with a callback handler supplying the invalid password
		//
		Subject subject = new Subject();
		CallbackHandler callbackHandler = getCallbackHandler(userKey, invalidPasswordAttempt);
		Map<String, ?> sharedState = Collections.emptyMap();
		Map<String, ?> options = Collections.emptyMap();
		instance.initialize(subject, callbackHandler, sharedState, options);
		//
		// Login using the callback handler
		//
		instance.login();
	}

	/*
	 * The login process should fail when an against an empty user database
	 */
	@Test(expected = LoginException.class)
	public void loginWithEmptyUSerDatabaseShouldBeInvalid() throws NoSuchAlgorithmException, InvalidKeySpecException,
			IOException, UnsupportedCallbackException, LoginException {

		logger.info("loginWithEmptyUSerDatabaseShouldBeInvalid");
		//
		// Login details
		//
		final String userKey = "someone@email.format";
		final char[] usePasswordArray = "someone@email.format's valid password".toCharArray();
		//
		// Initialize with a callback handler supplying the login attempt
		//
		Subject subject = new Subject();
		CallbackHandler callbackHandler = getCallbackHandler(userKey, usePasswordArray);
		Map<String, ?> sharedState = Collections.emptyMap();
		Map<String, ?> options = Collections.emptyMap();
		instance.initialize(subject, callbackHandler, sharedState, options);
		//
		// Login using the callback handler
		//
		instance.login();
	}

	/*
	 * Logging in without initializing should result in a Login Exception
	 */
	@Test(expected = LoginException.class)
	public void loginWithoutInitializingShouldThrowLoginError() throws LoginException {

		logger.info("loginWithoutInitializingShouldThrowLoginError");
		//
		// Login using the callback handler
		//
		instance.login();

	}

	/*
	 * Committing after a valid login should return a true value
	 */
	@Test
	public void commitAfterValidLoginShouldReturnTrue() throws IOException, UnsupportedCallbackException,
			NoSuchAlgorithmException, InvalidKeySpecException, LoginException {

		logger.info("commitAfterValidLoginShouldReturnTrue");
		//
		// Login details
		//
		final String userKey = "someone@email.format";
		final char[] passwordArray = "someone@email.format's valid password".toCharArray();
		//
		// Create a User with the above login details
		//
		final User user = SimpleComponentFactory.getUser();
		user.setUserEmail(userKey);
		user.setPasswordSalt(PasswordAuthentication.generateSalt());
		user.setUserPassword(PasswordAuthentication.getEncryptedPassword(passwordArray, user.getPasswordSalt()));
		dao.createUser(user);
		//
		// Initialize with a callback handler supplying the login credentials
		//
		Subject subject = new Subject();
		CallbackHandler callbackHandler = getCallbackHandler(userKey, passwordArray);
		Map<String, ?> sharedState = Collections.emptyMap();
		Map<String, ?> options = Collections.emptyMap();
		instance.initialize(subject, callbackHandler, sharedState, options);
		//
		// Login module should query the data source for the user specified in
		// the callback handler
		//
		final boolean authenticated = true;
		assertEquals(authenticated, instance.login());
		//
		final boolean committed = true;
		assertEquals(committed, instance.commit());
		assertFalse(subject.getPrincipals().isEmpty());
		assertTrue(subject.getPublicCredentials().contains(user));
	}

	/*
	 * Committing without logging in should return a false value
	 */
	@Test
	public void commitWithoutLoginShouldReturnFalse() throws IOException, UnsupportedCallbackException,
			NoSuchAlgorithmException, InvalidKeySpecException, LoginException {

		logger.info("commitWithoutLoginShouldReturnFalse");
		//
		final boolean committed = false;
		assertEquals(committed, instance.commit());
	}

	/*
	 * Aborting the authentication process without logging in should return a
	 * false value
	 */
	@Test
	public void abortWithoutLoginShouldReturnFalse() throws LoginException {

		logger.info("abortWithoutLoginShouldReturnFalse");
		//
		final boolean aborted = false;
		assertEquals(aborted, instance.abort());
	}

	/*
	 * Aborting after logging in should return a true value and clear the
	 * Principal and Credential collections
	 */
	@Test
	public void abortAfterLoginShouldReturnTrue() throws LoginException, NoSuchAlgorithmException,
			InvalidKeySpecException, IOException, UnsupportedCallbackException {

		logger.info("abortWithoutLoginShouldReturnFalse");
		//
		// Login details
		//
		final String userKey = "someone@email.format";
		final char[] passwordArray = "someone@email.format's valid password".toCharArray();
		//
		// Create a User with the above login details
		//
		final User user = SimpleComponentFactory.getUser();
		user.setUserEmail(userKey);
		user.setPasswordSalt(PasswordAuthentication.generateSalt());
		user.setUserPassword(PasswordAuthentication.getEncryptedPassword(passwordArray, user.getPasswordSalt()));
		dao.createUser(user);
		//
		// Initialize with a callback handler supplying the login credentials
		//
		Subject subject = new Subject();
		CallbackHandler callbackHandler = getCallbackHandler(userKey, passwordArray);
		Map<String, ?> sharedState = Collections.emptyMap();
		Map<String, ?> options = Collections.emptyMap();
		instance.initialize(subject, callbackHandler, sharedState, options);
		//
		// Login using the callback handler
		//
		instance.login();
		instance.commit();
		//
		// Test
		//
		final boolean aborted = true;
		assertEquals(aborted, instance.abort());
		assertTrue(subjectIsLoggedOut(subject));
	}

	/*
	 * Logging out should clear the subject after a successfull login
	 */
	public void logoutShouldClearTheSubjectState() throws NoSuchAlgorithmException, InvalidKeySpecException,
			IOException, UnsupportedCallbackException, LoginException {

		logger.info("abortWithoutLoginShouldReturnFalse");
		//
		// Login details
		//
		final String userKey = "someone@email.format";
		final char[] passwordArray = "someone@email.format's valid password".toCharArray();
		//
		// Create a User with the above login details
		//
		final User user = SimpleComponentFactory.getUser();
		user.setUserEmail(userKey);
		user.setPasswordSalt(PasswordAuthentication.generateSalt());
		user.setUserPassword(PasswordAuthentication.getEncryptedPassword(passwordArray, user.getPasswordSalt()));
		dao.createUser(user);
		//
		// Initialize with a callback handler supplying the login credentials
		//
		Subject subject = new Subject();
		CallbackHandler callbackHandler = getCallbackHandler(userKey, passwordArray);
		Map<String, ?> sharedState = Collections.emptyMap();
		Map<String, ?> options = Collections.emptyMap();
		instance.initialize(subject, callbackHandler, sharedState, options);
		//
		// Login using the callback handler
		//
		instance.login();
		boolean isLoggedOut = true;
		assertEquals(isLoggedOut, instance.logout());
		assertTrue(subjectIsLoggedOut(subject));
	}

	//
	//
	//
	private boolean subjectIsLoggedOut(Subject subject) {

		boolean principalsIsEmpty = subject.getPrincipals().isEmpty();
		boolean credentialsIsEmpty = subject.getPublicCredentials(User.class).isEmpty();
		return principalsIsEmpty && credentialsIsEmpty;
	}
}
