/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bac.applicationaccount;

import java.util.List;

import com.bac.applicationaccount.data.AccessLevel;
import com.bac.applicationaccount.data.Account;
import com.bac.applicationaccount.data.AccountUser;
import com.bac.applicationaccount.data.Application;
import com.bac.applicationaccount.data.User;

/**
 *
 * @author user0001
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
