package com.bac.applicationaccount.data.hibernate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.hibernate.NonUniqueObjectException;
import org.hibernate.ObjectNotFoundException;
import org.hibernate.exception.ConstraintViolationException;
import org.junit.Test;

import com.bac.applicationaccount.data.AccessLevel;
import com.bac.applicationaccount.data.Account;
import com.bac.applicationaccount.data.AccountUser;
import com.bac.applicationaccount.data.Application;
import com.bac.applicationaccount.data.SimpleComponentFactory;
import com.bac.applicationaccount.data.User;

public class HibernateApplicationAccountTestAccountUser extends AbstractHibernateTestCase {

	// logger
	// private static final Logger logger =
	// LoggerFactory.getLogger(HibernateApplicationAccountTestAccountUser.class);

	/*
	 * Creating an AccountUser with default values should fail as null
	 * application or user id must fail the integrity constraint
	 */

	@Test(expected = ConstraintViolationException.class)
	public void defaultAccountUserShouldFail() {

		instance.createAccountUser(SimpleComponentFactory.getAccountUser());
	}

	/*
	 * Creating an AccountUser with an invalid user id will fail the integrity
	 * constraint
	 */

	@Test(expected = ConstraintViolationException.class)
	public void createdAccountUserWithInvalidAccountIdShouldFail() {

		//
		// Persist a simple Account and test
		//
		AccountUser accountUser = SimpleComponentFactory.getAccountUser();
		accountUser.setUserId(getValidUserId());
		accountUser.setAccessLevelId(getValidAccessLevelId());
		instance.createAccountUser(accountUser);
	}

	/*
	 * Creating an AccountUser with an invalid account id will fail the
	 * integrity constraint
	 */

	@Test(expected = ConstraintViolationException.class)
	public void createdAccountUserWithInvalidUserIdShouldFail() {

		//
		// Persist a simple Account and test
		//
		AccountUser accountUser = SimpleComponentFactory.getAccountUser();
		accountUser.setAccountId(getValidAccountId());
		accountUser.setAccessLevelId(getValidAccessLevelId());
		instance.createAccountUser(accountUser);
	}

	/*
	 * Creating an AccountUser with an invalid access level id will fail the
	 * integrity constraint
	 */

	@Test(expected = ConstraintViolationException.class)
	public void createdAccountUserWithInvalidAccessLevelIdShouldFail() {

		//
		// Persist a simple Account and test
		//
		AccountUser accountUser = SimpleComponentFactory.getAccountUser();
		accountUser.setUserId(getValidUserId());
		accountUser.setAccountId(getValidAccountId());
		instance.createAccountUser(accountUser);
	}

	/*
	 * Updating the account id to a non-existent value should result in an the
	 * entity being not found as the account id is one of the key values
	 * 
	 */
	@Test(expected = ObjectNotFoundException.class)
	public void updatingAccountUserWithInvalidAccountIdShouldFail() {

		//
		// Persist a simple AccountUser and test
		//
		AccountUser accountUser = createDefaultValidAccountUser();
		accountUser.setAccountId(-99);
		instance.updateAccountUser(accountUser);
	}

	/*
	 * Updating the user id to a non-existent value should result in an the
	 * entity being not found as the user id is one of the key values
	 * 
	 */
	@Test(expected = ObjectNotFoundException.class)
	public void updatingAccountUserWithInvalidUserIdShouldFail() {

		//
		// Persist a simple AccountUser and test
		//
		AccountUser accountUser = createDefaultValidAccountUser();
		accountUser.setUserId(-99);
		instance.updateAccountUser(accountUser);
	}

	/*
	 * Updating the access level id to a non-existent value should result in an
	 * integrity violation
	 * 
	 */
	@Test(expected = ConstraintViolationException.class)
	public void updatingAccountUserWithInvalidAccessLevelIdShouldFail() {

		//
		// Persist a simple AccountUser and test
		//
		AccountUser accountUser = createDefaultValidAccountUser();
		accountUser.setAccessLevelId(-99);
		instance.updateAccountUser(accountUser);
	}

	/*
	 * Read an empty AccountUser should result in a null value
	 */
	@Test
	public void getAccountUserWithEmptyValues() {

		assertNull(instance.getAccountUser(SimpleComponentFactory.getAccountUser()));
	}

	/*
	 * An empty database should contain no Accounts
	 */
	@Test
	public void accountUsersShouldNotExistInAnEmptyDatabase() {

		assertTrue(instance.getAccountUsers().isEmpty());
	}

	/*
	 * Adding an Account should result in it being present in the list of all
	 * Accounts
	 */
	@Test
	public void createdAccountUserInListOfAllAccountUsers() {

		final AccountUser accountUser = createDefaultValidAccountUser();

		List<? extends AccountUser> result = instance.getAccountUsers();
		assertTrue(result.size() == 1);
		assertTrue(result.contains(accountUser));
	}

	/*
	 * Adding multiple AccountUsers should result in them all being present in
	 * the list of all AccountUsers. No other AccountUsers should exist in the
	 * list.
	 */
	@Test
	public void createdAccountUsersInListOfAllAccountUsers() {

		List<AccountUser> accounts = Arrays.asList(new AccountUser[] { createDefaultValidAccountUser(),
				createDefaultValidAccountUser(), createDefaultValidAccountUser() });
		List<? extends AccountUser> result = instance.getAccountUsers();
		assertTrue(result.size() == accounts.size());
		assertTrue(result.containsAll(accounts));
	}

	/*
	 * A newly created account user should have null or default values and valid
	 * account and user ids
	 */
	@Test
	public void createdAccountUserHasNullMembers() {

		//
		// Persist a simple Account and test
		//
		AccountUser result = createDefaultValidAccountUser();
		assertNotNull(result);
		assertNotNull(result.getAccountId());
		assertNotNull(result.getAccessLevelId());
		assertNotNull(result.getUserId());
		//
		// Active defaults to 'N'
		//
		final Character DEFAULT_ACTIVE = 'N';
		assertEquals(result.getActive(), DEFAULT_ACTIVE);
		//
		// The remaining members should have null values
		//
		assertNull(result.getCreateDate());
		assertNull(result.getAccountMessage());
		assertNull(result.getLastAccessDate());
	}

	/*
	 * AccountUser secondary key access is used to locate an entity as it does
	 * not have a unique id. The keys are account id and user id.
	 */
	@Test
	public void locateAccountUserUsingSecondaryKey() {

		Integer accountId = null;
		Integer userId = null;
		Integer accessLevelId = null;
		{
			//
			// Create the AccountUser
			//
			AccountUser result = createDefaultValidAccountUser();
			accountId = result.getAccountId();
			userId = result.getUserId();
			accessLevelId = result.getAccessLevelId();
		}
		//
		// Read back using the secondary key values
		//
		AccountUser secondaryAccountUser = SimpleComponentFactory.getAccountUser();
		secondaryAccountUser.setAccountId(accountId);
		secondaryAccountUser.setUserId(userId);
		secondaryAccountUser = instance.getAccountUserBySecondaryKey(secondaryAccountUser);
		assertEquals(accessLevelId, secondaryAccountUser.getAccessLevelId());
	}

	/*
	 * Secondary keys must be unique for AccountUsers. Creating more than one
	 * AccountUser with the same secondary key (account_id, user_id) should
	 * result in an exception
	 */
	@Test(expected = NonUniqueObjectException.class)
	public void duplicate_Secondary_Keys_Should_Cause_An_Exception() {

		AccountUser initial = createDefaultValidAccountUser();
		AccountUser duplicate = SimpleComponentFactory.getAccountUser();

		duplicate.setAccessLevelId(initial.getAccessLevelId());
		duplicate.setAccountId(initial.getAccountId());
		duplicate.setUserId(initial.getUserId());

		instance.createAccountUser(duplicate);
	}

	/*
	 * When a newly created AccountUser is read back it should contain the
	 * appropriate member values
	 */
	@Test
	public void createAccountUserAndReadBack() {

		Integer accountId = null;
		Integer userId = null;
		final Integer accessLevelId = getValidAccessLevelId();
		final Date createDate = getDateWithoutMillis();
		final Date lastAccessDate = getDateWithoutMillis();
		final Character active = 'Y';
		final String accountMessage = "Account message";
		{
			//
			// Insert a Application with the above values
			//
			AccountUser accountUser = SimpleComponentFactory.getAccountUser();
			accountUser.setAccountId(getValidAccountId());
			accountUser.setUserId(getValidUserId());
			accountUser.setAccessLevelId(accessLevelId);
			accountUser.setActive(active);
			accountUser.setCreateDate(createDate);
			accountUser.setLastAccessDate(lastAccessDate);
			accountUser.setAccountMessage(accountMessage);
			accountUser = instance.createAccountUser(accountUser);
			accountId = accountUser.getAccountId();
			userId = accountUser.getUserId();
		}
		final AccountUser secondaryAccountUser = createDefaultValidAccountUser();
		secondaryAccountUser.setAccountId(accountId);
		secondaryAccountUser.setUserId(userId);
		userId = secondaryAccountUser.getUserId();
		final AccountUser result = instance.getAccountUserBySecondaryKey(secondaryAccountUser);
		assertEquals(result.getAccessLevelId(), accessLevelId);
		assertEquals(result.getCreateDate(), createDate);
		assertEquals(result.getLastAccessDate(), lastAccessDate);
		assertEquals(result.getActive(), active);
		assertEquals(result.getAccountMessage(), accountMessage);
	}

	/*
	 * When a newly created AccountUser is updated and read back it should
	 * contain the appropriate member values
	 */
	@Test
	public void updatedAccountUserHasCorrectValues() {
		Integer accountId = null;
		Integer userId = null;
		final Date createDate = getDateWithoutMillis();
		final Date lastAccessDate = getDateWithoutMillis();
		final Character active = 'Y';
		final String accountMessage = "Account message";
		{
			//
			// Insert a Application ...
			//
			final AccountUser accountUser = createDefaultValidAccountUser();
			accountId = accountUser.getAccountId();
			userId = accountUser.getUserId();
		}
		{
			//
			// ... and update with the above values
			//
			AccountUser accountUser = createDefaultValidAccountUser();
			accountUser.setAccountId(accountId);
			accountUser.setUserId(userId);
			accountUser = instance.getAccountUserBySecondaryKey(accountUser);
			accountUser.setActive(active);
			accountUser.setCreateDate(createDate);
			accountUser.setLastAccessDate(lastAccessDate);
			accountUser.setAccountMessage(accountMessage);
			instance.updateAccountUser(accountUser);
		}
		final AccountUser secondaryAccountUser = createDefaultValidAccountUser();
		secondaryAccountUser.setAccountId(accountId);
		secondaryAccountUser.setUserId(userId);
		userId = secondaryAccountUser.getUserId();
		final AccountUser result = instance.getAccountUserBySecondaryKey(secondaryAccountUser);
		assertEquals(result.getCreateDate(), createDate);
		assertEquals(result.getLastAccessDate(), lastAccessDate);
		assertEquals(result.getActive(), active);
		assertEquals(result.getAccountMessage(), accountMessage);
	}

	/*
	 * Convenience methods for setting up test environment
	 * ***************************************************
	 */

	private AccountUser createDefaultValidAccountUser() {

		AccountUser accountUser = SimpleComponentFactory.getAccountUser();

		accountUser.setAccountId(getValidAccountId());
		accountUser.setUserId(getValidUserId());
		accountUser.setAccessLevelId(getValidAccessLevelId());

		AccountUser result = instance.createAccountUser(accountUser);
		return result;
	}

	private Integer getValidAccountId() {

		Integer validApplicationId = getValidApplicationId();
		Account account = SimpleComponentFactory.getAccount();

		account.setApplicationId(validApplicationId);
		Account valid = instance.createAccount(account);
		return valid.getId();
	}

	private Integer getValidApplicationId() {

		Application valid = instance.createApplication(SimpleComponentFactory.getApplication());
		return valid.getId();
	}

	private Integer getValidUserId() {

		User valid = instance.createUser(SimpleComponentFactory.getUser());
		return valid.getId();
	}

	private Integer getValidAccessLevelId() {

		AccessLevel valid = instance.createAccessLevel(SimpleComponentFactory.getAccessLevel());
		return valid.getId();
	}
}
