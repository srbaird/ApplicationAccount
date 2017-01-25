/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bac.applicationaccount.impl;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Map;

import javax.annotation.Resource;
import javax.security.auth.Subject;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.PasswordCallback;
import javax.security.auth.callback.UnsupportedCallbackException;
import javax.security.auth.login.LoginException;
import javax.security.auth.spi.LoginModule;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bac.applicationaccount.ApplicationAccount;
import com.bac.applicationaccount.User;

/**
 *
 * @author user0001
 */
public class AccountLoginModule implements LoginModule {

	// initial state
	private Subject subject;
	private CallbackHandler callbackHandler;
	// the authentication status
	private boolean succeeded = false;
	private boolean commitSucceeded = false;
	//
	private String username;
	private char[] password;
	private AccountPrincipal accountPrincipal;
	private User user;
	//
	private final String LOGIN_FAILED_MSG = "Invalid login attempt for user '{}'";
	private final String LOGIN_MODULE_FAILED = "Login module incomplete";
	//
	// Inject the ApplicationAccount DAO
	//
	@Resource(name = "applicationAccount")
	private ApplicationAccount applicationAccount;
	// logger
	private static final Logger logger = LoggerFactory.getLogger(AccountLoginModule.class);

	public AccountLoginModule() {
		//
		// Obtain the DAO when the LoginModule is created by the LoginContext
		//
		applicationAccount = ApplicationAccounts.getInstance();
		logger.info("Instantiated");
	}

	public ApplicationAccount getApplicationAccount() {
		return applicationAccount;
	}

	public void setApplicationAccount(ApplicationAccount applicationAccount) {
		this.applicationAccount = applicationAccount;
	}

	@Override
	public void initialize(Subject subject, CallbackHandler callbackHandler, Map<String, ?> sharedState,
			Map<String, ?> options) {

		this.subject = subject;
		this.callbackHandler = callbackHandler;
		// Shared state and Options not supported in this implementation
	}

	@Override
	public boolean login() throws LoginException {

		logger.debug("Perform login()");
		
		if (callbackHandler == null) {
			throw new LoginException(LOGIN_MODULE_FAILED);
		}
		//
		// Obtain the login details from the callback
		//
		Callback[] callbacks = new Callback[2];
		callbacks[0] = new NameCallback("User name");
		callbacks[1] = new PasswordCallback("Password", false);

		try {
			callbackHandler.handle(callbacks);
			username = ((NameCallback) callbacks[0]).getName();
			char[] callbackPassword = ((PasswordCallback) callbacks[1]).getPassword();
			if (callbackPassword == null) {
				// treat a NULL password as an empty password
				callbackPassword = new char[0];
			}
			password = new char[callbackPassword.length];
			System.arraycopy(callbackPassword, 0, password, 0, callbackPassword.length);
			((PasswordCallback) callbacks[1]).clearPassword();

		} catch (java.io.IOException ioe) {
			throw new LoginException(ioe.toString());
		} catch (UnsupportedCallbackException uce) {
			throw new LoginException(uce.getCallback().toString() + "not available");
		}
		//
		// Authenticate the login details
		//
		try {
			authenticateAccount(username, password);
		} catch (LoginException ex) {
			logger.error(LOGIN_FAILED_MSG, username);
			clearState();
			throw ex;
		}
		succeeded = true;
		return succeeded;
	}

	@Override
	public boolean commit() throws LoginException {

		logger.debug("Perform commit()");
		
		if (succeeded == false) {
			return false;
		} else {
			// add a Principal (authenticated identity) to the Subject
			// assume the user we authenticated is the AccountPrincipal
			accountPrincipal = new AccountPrincipal(username);
			subject.getPrincipals().add(accountPrincipal);
			//
			// Add account details for the principal
			//
			subject.getPublicCredentials().add(user);
			clearState();
			commitSucceeded = true;
			return commitSucceeded;
		}
	}

	@Override
	public boolean abort() throws LoginException {

		logger.debug("Perform abort()");
		
		if (succeeded == false) {
			return false;
		} else if (succeeded == true && commitSucceeded == false) {
			// login succeeded but overall authentication failed
			clearState();
			accountPrincipal = null;
			user = null;
			succeeded = false;
		} else {
			// overall authentication succeeded and commit succeeded,
			// but someone else's commit failed
			logout();
		}
		return true;
	}

	@Override
	public boolean logout() throws LoginException {
		
		logger.debug("Perform logout()");

		if (subject != null) {
			subject.getPrincipals().remove(accountPrincipal);
			subject.getPublicCredentials().remove(user);
		}
		clearState();
		accountPrincipal = null;
		user = null;
		succeeded = false;
		commitSucceeded = false;
		return true;
	}

	private void authenticateAccount(String userKey, char[] password) throws LoginException {

		// Look up account details in persistent store
		user = new SimpleUser();
		user.setUserEmail(userKey);

		user = applicationAccount.getUserBySecondaryKey(user);
		if (user == null) {
			logger.warn(" User '{}' does not exist", userKey);
			throw new LoginException(LOGIN_FAILED_MSG);
		}
		// Authenticate the password
		boolean authentic;
		try {
			authentic = PasswordAuthentication.authenticate(password, user.getUserPassword(), user.getPasswordSalt());
		} catch (NoSuchAlgorithmException | InvalidKeySpecException ex) {
			logger.error("Password authentication error", ex);
			throw new LoginException(LOGIN_FAILED_MSG);
		}
		if (!authentic) {
			logger.warn("Password supplied for '{}' failed authentication", userKey);
			throw new LoginException(LOGIN_FAILED_MSG);
		}
	}

	private void clearState() {

		username = null;
		if (password != null) {
			for (int i = 0; i < password.length; i++) {
				password[i] = ' ';
			}
			password = null;
		}
	}
}
