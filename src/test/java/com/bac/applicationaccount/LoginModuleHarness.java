package com.bac.applicationaccount;

import java.util.Map;

import javax.security.auth.Subject;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.login.LoginException;
import javax.security.auth.spi.LoginModule;

/*
 * Harness for mocked test LoginModules. 
 * The concrete implementation of this can be supplied using Configuration files.
 */
public final class LoginModuleHarness implements LoginModule {


	private static LoginModule delegate;

	public static void setDelegate(LoginModule that) {

		delegate = that;
	}

	public LoginModuleHarness() {

	}

	@Override
	public void initialize(Subject subject, CallbackHandler callbackHandler, Map<String, ?> sharedState,
			Map<String, ?> options) {
		delegate.initialize(subject, callbackHandler, sharedState, options);
	}

	@Override
	public boolean login() throws LoginException {

		return delegate.login();
	}

	@Override
	public boolean commit() throws LoginException {

		return delegate.commit();
	}

	@Override
	public boolean abort() throws LoginException {

		return delegate.abort();
	}

	@Override
	public boolean logout() throws LoginException {
		// TODO Auto-generated method stub
		return false;
	}

}
