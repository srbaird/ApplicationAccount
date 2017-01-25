package com.bac.applicationaccount.hibernate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.hibernate.exception.ConstraintViolationException;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bac.applicationaccount.Account;
import com.bac.applicationaccount.Application;
import com.bac.applicationaccount.impl.SimpleComponentFactory;

public class HibernateApplicationAccountTestAccount extends AbstractHibernateTestCase {

	// logger
	private static final Logger logger = LoggerFactory.getLogger(HibernateApplicationAccountTestAccount.class);

	/*
	 * Creating an Account with default values should fail as a null application
	 * id must fail the integrity constraint
	 */

	@Test(expected = ConstraintViolationException.class)
	public void defaultAccountShouldFail() {

		instance.createAccountUser(SimpleComponentFactory.getAccountUser());
	}

	/*
	 * Creating an Account with an unknown application id will fail the
	 * integrity constraint
	 */

	@Test(expected = ConstraintViolationException.class)
	public void createdAccountWithInvalidApplicationIdShouldFail() {

		//
		// Persist a simple Account and test
		//
		Account account = SimpleComponentFactory.getAccount();
		account.setApplicationId(0);
		instance.createAccount(account);
	}

	/*
	 * Updating the application id to a non-existent value should result in an
	 * integrity violation
	 * 
	 */
	@Test(expected = ConstraintViolationException.class)
	public void updatingAccountWithInvalidApplicationIdShouldFail() {

		//
		// Persist a simple Account and test
		//
		Account account = createDefaultValidAccount();
		logger.info(account + " created. Application id is " + account.getApplicationId());
		account.setApplicationId(-99);
		account = instance.updateAccount(account);
		logger.info(account + " updated. Application id is now " + account.getApplicationId());
	}

	/**
	 * Once the Account is created then any attempt to change the id should
	 * result in an exceptions
	 */
	// TODO: Move this logic to the simple implementation ?
	// @Test(expected = IllegalArgumentException.class)
	public void updatingAccountIdShouldFail() {

		Account account = createDefaultValidAccount();
		Integer invalidId = account.getId() + 1;
		account.setId(invalidId);
		instance.updateAccount(account);
	}

	/*
	 * A newly created account should have null or default values and a valid
	 * application id
	 */
	@Test
	public void createdAccountHasNullMembers() {

		//
		// Persist a simple Account and test
		//
		Account result = createDefaultValidAccount();
		assertNotNull(result);
		assertNotNull(result.getId());
		assertNotNull(result.getApplicationId());
		//
		// Active defaults to 'N'
		//
		final Character DEFAULT_ACTIVE = 'N';
		assertEquals(result.getActive(), DEFAULT_ACTIVE);
		//
		// The remaining members should have null values
		//
		assertNull(result.getCreateDate());
		assertNull(result.getResourceName());
	}

	/*
	 * An Account that has been deleted should not be found
	 */
	@Test
	public void deletedAccountShouldNotExist() {

		//
		// Create a simple account
		//
		Account result = createDefaultValidAccount();
		//
		// Delete the account
		//
		instance.deleteAccount(result);
		//
		// Re-reading the Account should now result in a null value
		//
		Integer accountId = result.getId();
		assertNull(instance.getAccount(accountId));
	}

	/*
	 * An empty database should contain no Accounts
	 */
	@Test
	public void accountShouldNotExistInAnEmptyDatabase() {

		final Integer nonExistentAccountId = 1;
		assertNull(instance.getAccount(nonExistentAccountId));
	}

	/*
	 * An empty database should contain no Accounts
	 */
	@Test
	public void accountsShouldNotExistInAnEmptyDatabase() {

		assertTrue(instance.getAccounts().isEmpty());
	}

	/*
	 * Adding an Account should result in it being present in the list of all
	 * Accounts
	 */
	@Test
	public void createdAccountInListOfAllAccounts() {

		final Account account = createDefaultValidAccount();
		;
		List<? extends Account> result = instance.getAccounts();
		assertTrue(result.size() == 1);
		assertTrue(result.contains(account));
	}

	/*
	 * Adding multiple Accounts should result in them all being present in the
	 * list of all Accounts. No other accounts should exist in the list.
	 */
	@Test
	public void createdAccountsInListOfAllAccounts() {

		List<Account> accounts = Arrays.asList(new Account[] { createDefaultValidAccount(), createDefaultValidAccount(),
				createDefaultValidAccount() });
		List<? extends Account> result = instance.getAccounts();
		assertTrue(result.size() == accounts.size());
		assertTrue(result.containsAll(accounts));
	}

	/*
	 * When a newly created Account is read back it should contain the
	 * appropriate member values
	 */
	@Test
	public void createAccountAndReadBack() {

		Integer accountId = null;
		final String resourceName = "Application Account";
		final Character active = 'Y';
		final Date createDate = getDateWithoutMillis();
		{
			//
			// Insert an Account with the above values
			//
			Account account = SimpleComponentFactory.getAccount();
			account.setActive(active);
			account.setCreateDate(createDate);
			account.setResourceName(resourceName);
			account.setApplicationId(getValidApplicationId());
			account = instance.createAccount(account);
			accountId = account.getId();
		}
		final Account createdAccount = instance.getAccount(accountId);
		assertEquals(createdAccount.getActive(), active);
		assertEquals(createdAccount.getCreateDate(), createDate);
		assertEquals(createdAccount.getResourceName(), resourceName);
	}

	/*
	 * When a newly created Account is subsequently updated, reading back by
	 * it's id should reflect the updated values
	 */
	@Test
	public void updatedAccountReflectsChanges() {

		//
		// Apply test values and update
		//
		Integer accountId = null;
		final String resourceName = "Application Account";
		final Character active = 'Y';
		final Date createDate = getDateWithoutMillis();
		//
		{
			final Account account = createDefaultValidAccount();
			account.setActive(active);
			account.setCreateDate(createDate);
			account.setResourceName(resourceName);
			accountId = account.getId();
			instance.updateAccount(account);
		}
		//
		// Read back the Account and confirm changes were persisted
		//
		final Account updatedAccount = instance.getAccount(accountId);
		assertEquals(updatedAccount.getActive(), active);
		assertEquals(updatedAccount.getCreateDate(), createDate);
		assertEquals(updatedAccount.getResourceName(), resourceName);
	}
	/*
	 * When an application is deleted any associated accounts should also be
	 * deleted
	 */
	@Test
	public void accountShouldBeDeletedWhenApplicationIsDeleted() {
		
		final Account account = createDefaultValidAccount();
		//
		//	Delete the application
		//
		instance.deleteApplication(instance.getApplication(account.getApplicationId()));
		//
		//	Ensure the Account does not exist any longer
		//
		assertTrue(instance.getAccounts().isEmpty());
	}
	

	private Account createDefaultValidAccount() {

		Integer validApplicationId = getValidApplicationId();
		Account account = SimpleComponentFactory.getAccount();

		account.setApplicationId(validApplicationId);
		Account result = instance.createAccount(account);
		return result;
	}

	private Integer getValidApplicationId() {

		Application valid = instance.createApplication(SimpleComponentFactory.getApplication());
		logger.info("There are now " + instance.getApplications().size() + " Applications");
		return valid.getId();
	}
}
