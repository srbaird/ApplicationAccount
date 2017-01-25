
package com.bac.applicationaccount;

import java.util.List;

/**
 * A Class to provide the standard data access and persistence methods for the
 * Application access data.
 * 
 * @author Simon Baird
 */
public interface ApplicationAccount {

	/*
	 * Account
	 */
	Account getAccount(Integer id);

	List<? extends Account> getAccounts();

	Account createAccount(Account account);

	Account updateAccount(Account account);

	void deleteAccount(Account account);

	/*
	 * Account User
	 */
	AccountUser getAccountUser(AccountUser accountUser);

	List<? extends AccountUser> getAccountUsers();

	AccountUser createAccountUser(AccountUser accountUser);

	AccountUser updateAccountUser(AccountUser accountUser);

	void deleteAccountUser(AccountUser accountUser);

	AccountUser getAccountUserBySecondaryKey(AccountUser accountUser);

	/*
	 * User
	 */

	User getUser(Integer id);

	User reloadUser(Integer id);

	List<? extends User> getUsers();

	User createUser(User user);

	User updateUser(User user);

	void deleteUser(User user);

	User getUserBySecondaryKey(User user);

	/*
	 * Access Level
	 */

	AccessLevel getAccessLevel(Integer id);

	List<? extends AccessLevel> getAccessLevels();

	AccessLevel createAccessLevel(AccessLevel accessLevel);

	AccessLevel updateAccessLevel(AccessLevel accessLevel);

	void deleteAccessLevel(AccessLevel accessLevel);

	/*
	 * Application
	 */
	Application getApplication(Integer id);

	List<? extends Application> getApplications();

	Application createApplication(Application application);

	Application updateApplication(Application application);

	void deleteApplication(Application application);

	Application getApplicationBySecondaryKey(Application application);
}
