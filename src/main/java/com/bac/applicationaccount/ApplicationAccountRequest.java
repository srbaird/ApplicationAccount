package com.bac.applicationaccount;

/*
 * Class to identify the User and Application request
 */
public class ApplicationAccountRequest {

	private final String applicationName;
	private final String userIdentifier;

	public ApplicationAccountRequest(String applicationName, String userIdentifier) {
		super();
		this.applicationName = applicationName;
		this.userIdentifier = userIdentifier;
	}

	public String getApplicationName() {
		return applicationName;
	}

	public String getUserIdentifier() {
		return userIdentifier;
	}

}
