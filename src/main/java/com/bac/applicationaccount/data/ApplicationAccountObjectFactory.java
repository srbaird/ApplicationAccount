package com.bac.applicationaccount.data;

public interface ApplicationAccountObjectFactory {

	AccessLevel getObject(AccessLevel delegate);

	Account getObject(Account delegate);

	AccountUser getObject(AccountUser delegate);

	Application getObject(Application delegate);

	Object getObject(User delegate);
	
	Object getObject(String objectType);

}
