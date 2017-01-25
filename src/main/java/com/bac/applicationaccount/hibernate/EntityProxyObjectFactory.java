package com.bac.applicationaccount.hibernate;

import com.bac.applicationaccount.AccessLevel;
import com.bac.applicationaccount.Account;
import com.bac.applicationaccount.AccountUser;
import com.bac.applicationaccount.Application;
import com.bac.applicationaccount.User;

public interface EntityProxyObjectFactory {

	ApplicationAccountEntityProxy getObject(AccessLevel delegate);

	ApplicationAccountEntityProxy getObject(Account delegate);

	ApplicationAccountEntityProxy getObject(AccountUser delegate);

	ApplicationAccountEntityProxy getObject(Application delegate);

	ApplicationAccountEntityProxy getObject(User delegate);

	/*
	 * Non-populated object instances
	 */
	ApplicationAccountEntityProxy getAccessLevelObject();

	ApplicationAccountEntityProxy getAccountObject();

	ApplicationAccountEntityProxy getAccountUserObject();

	ApplicationAccountEntityProxy getApplicationObject();

	ApplicationAccountEntityProxy getUserObject();

	/*
	 * Object class instances
	 * 
	 */
	Class<? extends AccessLevel> getAccessLevelClass();

	Class<? extends Account> getAccountClass();

	Class<? extends AccountUser> getAccountUserClass();

	Class<? extends Application> getApplicationClass();

	Class<? extends User> getUserClass();

	/*
	 * Secondary Key object instances
	 * 
	 */
	ProxyHasSecondaryKey getSecondaryKeyObject(Application delegate);

	ProxyHasSecondaryKey getSecondaryKeyObject(AccountUser delegate);

	ProxyHasSecondaryKey getSecondaryKeyObject(User delegate);

}
