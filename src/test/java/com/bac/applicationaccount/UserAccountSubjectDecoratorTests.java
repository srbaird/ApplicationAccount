package com.bac.applicationaccount;

import static org.junit.Assert.*;

import java.util.Set;

import javax.annotation.Resource;
import javax.security.auth.Subject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.bac.applicationaccount.data.AccessLevel;
import com.bac.applicationaccount.data.Account;
import com.bac.applicationaccount.data.AccountUser;
import com.bac.applicationaccount.data.Application;
import com.bac.applicationaccount.data.SimpleComponentFactory;
import com.bac.applicationaccount.data.User;

/*
 * 
 * Testing the Subject Decorator involves setting a request credential and 
 * checking to see if the supplied user name has an account with the application. 
 * If the user or application are invalid, or the user has no account then the 
 * subject credentials should be unchanged
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/applicationContextHSQL.xml" })
@Transactional
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
public class UserAccountSubjectDecoratorTests extends AbstractHibernateApplicationTestCase {

	@Resource(name = "userAccountSubjectDecorator")
	UserAccountSubjectDecorator instance;

	private static final Logger logger = LoggerFactory.getLogger(UserAccountSubjectDecoratorTests.class);

	/*
	 * Decorating a null Subject should result in an IllegalArgumentException
	 */
	@Test(expected = IllegalArgumentException.class)
	public void decorating_Null_Subject_Should_Result_In_An_Exception() {

		logger.info("decorating_Null_Subject_Should_Result_In_An_Exception");
		instance.decorate(null);
	}

	/*
	 * Decorating an empty subject should result in no credentials being added
	 */
	@Test
	public void decorating_An_Empty_Subject_Should_Return_Empty_Credentials() {

		logger.info("decorating_An_Empty_Subject_Should_Return_Empty_Credentials");
		assertTrue(instance.decorate(new Subject()).getPublicCredentials().isEmpty());
	}

	/*
	 * Decorating a Subject with non-existent User should result in no Account
	 * credentials being added
	 */
	@Test
	public void decorating_Subject_With_Unknown_User_Should_Return_Empty_Credentials() {

		logger.info("decorating_Subject_With_Unknown_User_Should_Return_Empty_Credentials");
		final Subject subject = new Subject();
		final ApplicationAccountRequest request = new ApplicationAccountRequest("application", "user");
		subject.getPrivateCredentials().add(request);
		assertTrue(instance.decorate(subject).getPublicCredentials(Account.class).isEmpty());
	}

	/*
	 * Decorating a Subject with a valid User but an unknown Application should
	 * result in no Account credentials being added
	 */
	@Test
	public void decorating_Subject_With_Valid_User_And_Invalid_Application_Should_Return_Empty_Credentials() {

		logger.info("decorating_Subject_With_Valid_User_And_Invalid_Application_Should_Return_Empty_Credentials");
		//
		// Add a User using the DAO retrieved from the instance
		//
		final String userKey = "user@email.address";
		final User user = SimpleComponentFactory.getUser();
		user.setUserEmail(userKey);
		final ApplicationAccount dao = instance.getApplicationAccount();
		dao.createUser(user);
		//
		// Set the Subject with a request
		//
		final Subject subject = new Subject();
		final ApplicationAccountRequest request = new ApplicationAccountRequest("application", userKey);
		subject.getPrivateCredentials().add(request);
		//
		//
		//
		assertTrue(instance.decorate(subject).getPublicCredentials(Account.class).isEmpty());
	}

	/*
	 * Decorating a Subject with a valid User and Application without adding an
	 * AccountUser association should result in no Account credentials being
	 * added
	 */
	@Test
	public void decorating_Subject_Without_Valid_UserAccount_Should_Return_Empty_Credentials() {

		logger.info("decorating_Subject_Without_Valid_UserAccount_Should_Return_Empty_Credentials");
		//
		// Add a User and application using the DAO retrieved from the instance
		//
		final ApplicationAccount dao = instance.getApplicationAccount();
		//
		final String userKey = "user@email.address";
		final String applicationName = "ApplicationAccount";
		//
		final User user = SimpleComponentFactory.getUser();
		user.setUserEmail(userKey);
		dao.createUser(user);
		//
		final Application application = SimpleComponentFactory.getApplication();
		application.setName(applicationName);
		dao.createApplication(application);
		//
		// Set the Subject with a request
		//
		final Subject subject = new Subject();
		final ApplicationAccountRequest request = new ApplicationAccountRequest(applicationName, userKey);
		subject.getPrivateCredentials().add(request);
		//
		//
		//
		assertTrue(instance.decorate(subject).getPublicCredentials(Account.class).isEmpty());
	}

	/*
	 * A User with a valid account for the application should be reflected in
	 * the Subject credentials.
	 */
	@Test
	public void decorating_With_Valid_Account_Should_Be_Reflected_In_Credentials() {

		logger.info("decorating_With_Valid_Account_Should_Be_Reflected_In_Credentials");
		//
		// Add a User, Application and Account using the DAO retrieved from the
		// instance
		//
		final ApplicationAccount dao = instance.getApplicationAccount();
		//
		final String userKey = "user@email.address";
		final String applicationName = "ApplicationAccount";
		//
		final User user = SimpleComponentFactory.getUser();
		user.setUserEmail(userKey);
		dao.createUser(user);
		//
		final Application application = SimpleComponentFactory.getApplication();
		application.setName(applicationName);
		dao.createApplication(application);
		//
		final AccessLevel accessLevel = dao.createAccessLevel(SimpleComponentFactory.getAccessLevel());
		//
		Account account = SimpleComponentFactory.getAccount();
		account.setApplicationId(application.getId());
		account = dao.createAccount(account);
		//
		final AccountUser accountUser = SimpleComponentFactory.getAccountUser();
		accountUser.setUserId(user.getId());
		accountUser.setAccountId(account.getId());
		accountUser.setAccessLevelId(accessLevel.getId());
		dao.createAccountUser(accountUser);
		//
		assertTrue(dao.reloadUser(user.getId()).getAccounts().size() > 0);
		//
		// Set the Subject with a request
		//
		final Subject subject = new Subject();
		final ApplicationAccountRequest request = new ApplicationAccountRequest(applicationName, userKey);
		subject.getPrivateCredentials().add(request);
		//
		//
		//
		Subject result = instance.decorate(subject);
		Set<Account> credentials = result.getPublicCredentials(Account.class);
		assertFalse(credentials.isEmpty());
		Account credential = credentials.stream().findFirst().get();
		assertEquals(application.getId(), credential.getApplicationId());
	}

}
