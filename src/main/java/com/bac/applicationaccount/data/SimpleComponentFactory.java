package com.bac.applicationaccount.data;

public final class SimpleComponentFactory {

	public static AccessLevel getAccessLevel() {
		return new SimpleAccessLevel();
	}

	public static Account getAccount() {
		return new SimpleAccount();
	}
	//
	// Restrict access to this package
	//
	static Account getAccount(Account account) {
		return new SimpleAccount(account);
	}

	public static AccountUser getAccountUser() {
		return new SimpleAccountUser();
	}

	public static Application getApplication() {
		return new SimpleApplication();
	}

	public static User getUser() {
		return new SimpleUser();
	}
}
