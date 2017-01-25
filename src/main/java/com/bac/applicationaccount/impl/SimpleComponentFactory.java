package com.bac.applicationaccount.impl;

import com.bac.applicationaccount.AccessLevel;
import com.bac.applicationaccount.Account;
import com.bac.applicationaccount.AccountUser;
import com.bac.applicationaccount.Application;
import com.bac.applicationaccount.User;

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
