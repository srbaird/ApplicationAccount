package com.bac.applicationaccount.impl;

import java.util.Set;

import javax.security.auth.Subject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bac.applicationaccount.Application;
import com.bac.applicationaccount.ApplicationAccount;
import com.bac.applicationaccount.User;

public class UserAccountSubjectDecorator extends AbstractSubjectDecorator {

	private static final Logger logger = LoggerFactory.getLogger(UserAccountSubjectDecorator.class);

	// DAO to get further subject information
	private ApplicationAccount applicationAccount;
	//
	private final String NULL_SUBJECT_MSG = "A null subject was  provided to the decorator";

	public ApplicationAccount getApplicationAccount() {
		return applicationAccount;
	}

	public void setApplicationAccount(ApplicationAccount applicationAccount) {
		this.applicationAccount = applicationAccount;
	}

	@Override
	public Subject decorate(Subject subject) {

		logger.debug("Decorate the Subject");
		
		if (subject == null) {
			throw new IllegalArgumentException(NULL_SUBJECT_MSG);
		}
		Set<ApplicationAccountRequest> requests = subject.getPrivateCredentials(ApplicationAccountRequest.class);
		if (requests.size() != 1) {
			return subject;
		}
		// A valid request exists so load it from the data store
		ApplicationAccountRequest request = requests.stream().findFirst().get();
		
		logger.debug("A valid request exists for '{}', '{}'", request.getApplicationName(), request.getUserIdentifier());
		
		User user = SimpleComponentFactory.getUser();
		user.setUserEmail(request.getUserIdentifier());
		user = applicationAccount.getUserBySecondaryKey(user);
		if (user == null) {
			logger.debug("Request user was not found");
			return subject;
		}
		//
		// Read the accounts for the User
		// Need the key to the Application
		//
		logger.debug("User was found, get application");

		Application application = SimpleComponentFactory.getApplication();
		application.setName(request.getApplicationName());
		final Application newApplication = applicationAccount.getApplicationBySecondaryKey(application);
		if (newApplication == null) {
			logger.debug("Request application was not found");
			return subject;
		}
		
		logger.debug("Application was found, get the accounts");

		// Reload to get the up to date User (particularly for testing)
		user = applicationAccount.reloadUser(user.getId());
		user.getAccounts().stream().filter(a -> a.getApplicationId() == newApplication.getId())
				.forEach(a -> subject.getPublicCredentials().add(a));

		return subject;
	}

}
