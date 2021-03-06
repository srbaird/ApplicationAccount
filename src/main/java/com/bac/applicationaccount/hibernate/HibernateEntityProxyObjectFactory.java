package com.bac.applicationaccount.hibernate;

import com.bac.applicationaccount.AccessLevel;
import com.bac.applicationaccount.Account;
import com.bac.applicationaccount.AccountUser;
import com.bac.applicationaccount.Application;
import com.bac.applicationaccount.User;

public class HibernateEntityProxyObjectFactory implements EntityProxyObjectFactory {

	@Override
	public ApplicationAccountEntityProxy getObject(AccessLevel delegate) {

		return delegate instanceof HibernateAccessLevel ? (HibernateAccessLevel) delegate
				: new HibernateAccessLevel(delegate);
	}

	@Override
	public ApplicationAccountEntityProxy getObject(Account delegate) {

		return delegate instanceof HibernateAccount ? (HibernateUser) delegate : new HibernateAccount(delegate);
	}

	@Override
	public ApplicationAccountEntityProxy getObject(AccountUser delegate) {

		return delegate instanceof HibernateAccountUser ? (HibernateAccountUser) delegate
				: new HibernateAccountUser(delegate);
	}

	@Override
	public ApplicationAccountEntityProxy getObject(Application delegate) {

		return delegate instanceof HibernateApplication ? (HibernateApplication) delegate
				: new HibernateApplication(delegate);
	}

	@Override
	public ApplicationAccountEntityProxy getObject(User delegate) {

		return delegate instanceof HibernateUser ? (HibernateUser) delegate : new HibernateUser(delegate);
	}

	/*
	 * Non-populated object instances
	 */
	@Override
	public ApplicationAccountEntityProxy getAccessLevelObject() {
		return new HibernateAccessLevel();
	}

	@Override
	public ApplicationAccountEntityProxy getAccountObject() {
		return new HibernateAccount();
	}

	@Override
	public ApplicationAccountEntityProxy getAccountUserObject() {
		return new HibernateAccountUser();
	}

	@Override
	public ApplicationAccountEntityProxy getApplicationObject() {
		return new HibernateApplication();
	}

	@Override
	public ApplicationAccountEntityProxy getUserObject() {
		return new HibernateUser();
	}

	/*
	 * Object class instances
	 * 
	 */
	@Override
	public Class<? extends AccessLevel> getAccessLevelClass() {

		return HibernateAccessLevel.class;
	}

	@Override
	public Class<? extends Account> getAccountClass() {

		return HibernateAccount.class;
	}

	@Override
	public Class<? extends AccountUser> getAccountUserClass() {

		return HibernateAccountUser.class;
	}

	@Override
	public Class<? extends Application> getApplicationClass() {

		return HibernateApplication.class;
	}

	@Override
	public Class<? extends User> getUserClass() {

		return HibernateUser.class;
	}

	/*
	 * Secondary Key object instances
	 * 
	 */

	@Override
	public ProxyHasSecondaryKey getSecondaryKeyObject(AccountUser delegate) {

		return (ProxyHasSecondaryKey) getObject(delegate);
	}

	@Override
	public ProxyHasSecondaryKey getSecondaryKeyObject(Application delegate) {

		return (ProxyHasSecondaryKey) getObject(delegate);
	}

	@Override
	public ProxyHasSecondaryKey getSecondaryKeyObject(User delegate) {

		return (ProxyHasSecondaryKey) getObject(delegate);
	}

}
