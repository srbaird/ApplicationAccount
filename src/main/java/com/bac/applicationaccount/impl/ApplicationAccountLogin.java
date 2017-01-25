
package com.bac.applicationaccount.impl;

import java.util.Objects;
import java.util.Set;

import javax.security.auth.Subject;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.login.LoginContext;
import javax.security.auth.login.LoginException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bac.applicationaccount.ApplicationAccount;
import com.bac.applicationaccount.SubjectDecorator;
import com.bac.applicationaccount.User;

/**
 *
 * @author Simon Baird
 */
public class ApplicationAccountLogin {

	// Use the class name to find the JAAS authentication entry
	private final String JAAS_APPLICATION_NAME = getClass().getSimpleName();
	// TODO: move these to a resource bundle
	private final String NO_APPLICATION_NAME_MSG = "No application name supplied";
	private final String NO_CALLBACK_MSG = "No call back handler supplied";
	private final String NO_CONFIG_MSG = "No config file name supplied";
	private final String INCOMPLETE_LOGIN_MSG = "Login has not supplied correct credentials";
	//
	private final String LOGIN_CONFIG_PROPERTY_NAME = "java.security.auth.login.config";
	private String loginConfigName;
	//
	private ApplicationAccount applicationAccount;
	//
	private SubjectDecorator subjectDecorator;
	// logger
	private static final Logger logger = LoggerFactory.getLogger(ApplicationAccountLogin.class);

	public String getLoginConfigName() {
		return loginConfigName;
	}

	/**
	 * Use the supplied login configuration file name to set the System property
	 * and refresh the default Configuration.
	 * 
	 * @param loginConfigName the value to replace the current System property.
	 */
	public void setLoginConfigName(String loginConfigName) {

		Objects.requireNonNull(loginConfigName, NO_CONFIG_MSG);
		this.loginConfigName = loginConfigName;
		System.setProperty(LOGIN_CONFIG_PROPERTY_NAME, loginConfigName);
	}

	public ApplicationAccount getApplicationAccount() {
		return applicationAccount;
	}

	public void setApplicationAccount(ApplicationAccount applicationAccount) {
		this.applicationAccount = applicationAccount;
	}

	public SubjectDecorator getSubjectDecorator() {
		return subjectDecorator;
	}

	public void setSubjectDecorator(SubjectDecorator subjectDecorator) {
		this.subjectDecorator = subjectDecorator;
	}

	public Subject login(String applicationName, CallbackHandler callbackHandler)
			throws LoginException, SecurityException {

		logger.debug("login to '{}'", applicationName);

		Objects.requireNonNull(applicationName, NO_APPLICATION_NAME_MSG);
		Objects.requireNonNull(callbackHandler, NO_CALLBACK_MSG);
		//
		//
		//
		LoginContext lc = new LoginContext(JAAS_APPLICATION_NAME, callbackHandler);
		//
		lc.login();
		//
		Subject subject = lc.getSubject();
		//
		// Get the User from the Subject credentials. If there is not a single
		// User then throw an exception
		//
		Set<User> publicUserCredentials = subject.getPublicCredentials(User.class);
		if (publicUserCredentials.size() != 1) {
			throw new LoginException(INCOMPLETE_LOGIN_MSG);
		}
		if (subjectDecorator == null) {
			// Nothing further required
			return subject;
		}
		//
		// A decorator has been supplied so add a request credential and
		// decorate the Subject
		//
		User user = publicUserCredentials.stream().findFirst().get();
		subject.getPrivateCredentials().add(new ApplicationAccountRequest(applicationName, user.getUserKey()));
		return subjectDecorator.addSubjectDetail(subject);
	}
}
