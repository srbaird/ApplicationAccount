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

import com.bac.applicationaccount.User;
import com.bac.applicationaccount.impl.SimpleComponentFactory;

public class HibernateApplicationAccountTestUser extends AbstractHibernateTestCase {

	/*
	 * Simple test to ensure that the test instance is correctly generated
	 */
	@Test
	public void instanceIsSetUpCorrectly() {

		assertNotNull(instance);
	}

	/*
	 * Creating a new User should have either null or default values for each
	 * member apart from the id.
	 */
	@Test
	public void createdUserHasNullMembers() {

		//
		// Persist a simple user and test
		//
		User result = instance.createUser(SimpleComponentFactory.getUser());
		assertNotNull(result);
		assertNotNull(result.getId());
		//
		// Active defaults to 'N'
		//
		final Character DEFAULT_ACTIVE = 'N';
		assertEquals(result.getActive(), DEFAULT_ACTIVE);
		//
		// The remaining members should have null values
		//
		assertNull(result.getCreateDate());
		assertNull(result.getPasswordSalt());
		assertNull(result.getUserKey());
		assertNull(result.getUserName());
		assertNull(result.getUserPassword());
		//
		// Accounts should be an empty Set
		//
		assertTrue(result.getAccounts().isEmpty());
	}

	/*
	 * A User that has been deleted should not be found
	 */
	@Test
	public void deletedUserShouldNotExist() {

		//
		// Create a simple user
		//
		User result = instance.createUser(SimpleComponentFactory.getUser());
		assertNotNull(result);
		assertNotNull(result.getId());
		//
		// Delete the user
		//
		instance.deleteUser(result);
		//
		// Re-reading the User should now result in a null value
		//
		Integer userId = result.getId();
		assertNull(instance.getUser(userId));
	}

	/*
	 * An empty database should contain no Users
	 */
	@Test
	public void userShouldNotExistInAnEmptyDatabase() {

		final Integer nonExistentUserId = 1;
		assertNull(instance.getUser(nonExistentUserId));
	}

	/*
	 * An empty database should contain no Users
	 */
	@Test
	public void usersShouldNotExistInAnEmptyDatabase() {

		assertTrue(instance.getUsers().isEmpty());
	}

	/*
	 * Adding a User should result in it being present in the list of all Users
	 */
	@Test
	public void createdUserInListOfAllUsers() {

		final User user = instance.createUser(SimpleComponentFactory.getUser());
		List<? extends User> result = instance.getUsers();
		assertTrue(result.size() == 1);
		assertTrue(result.contains(user));
	}

	/*
	 * Adding a multiple Users should result in them all being present in the
	 * list of all Users. No other users should exist in the list.
	 */
	@Test
	public void createdUsersInListOfAllUsers() {

		List<User> users = Arrays.asList(new User[] { instance.createUser(SimpleComponentFactory.getUser()),
				instance.createUser(SimpleComponentFactory.getUser()),
				instance.createUser(SimpleComponentFactory.getUser()) });
		List<? extends User> result = instance.getUsers();
		assertTrue(result.size() == users.size());
		assertTrue(result.containsAll(users));
	}

	/*
	 * When a newly created User is read back it should contain the appropriate
	 * member values
	 */
	@Test
	public void createUserAndReadBack() {

		Integer userId = null;
		final String userName = "Desperate Dan";
		final String userEmail = "ddan@beano.com";
		final Character active = 'Y';
		final Date createDate = getDateWithoutMillis();
		final byte[] userPassword = "comic book".getBytes();
		final byte[] passwordSalt = "password salt".getBytes();
		{
			//
			// Insert a User with the above values
			//
			final User user = SimpleComponentFactory.getUser();
			user.setActive(active);
			user.setCreateDate(createDate);
			user.setPasswordSalt(passwordSalt);
			user.setUserKey(userEmail);
			user.setUserName(userName);
			user.setUserPassword(userPassword);
			instance.createUser(user);
			userId = user.getId();
		}
		final User createdUser = instance.getUser(userId);
		assertEquals(createdUser.getActive(), active);
		assertEquals(createdUser.getCreateDate(), createDate);
		assertEquals(createdUser.getPasswordSalt(), passwordSalt);
		assertEquals(createdUser.getUserKey(), userEmail);
		assertEquals(createdUser.getUserName(), userName);
		assertEquals(createdUser.getUserPassword(), userPassword);
	}

	/*
	 * When a newly created User is subsequently updated, reading back by it's
	 * id should reflect the updated values
	 */
	@Test
	public void updatedUserReflectsChanges() {

		//
		// Apply test values and update
		//
		Integer userId = null;
		final String userName = "Desperate Dan";
		final String userEmail = "ddan@beano.com";
		final Character active = 'Y';
		final Date createDate = getDateWithoutMillis();
		final byte[] userPassword = "comic book".getBytes();
		final byte[] passwordSalt = "password salt".getBytes();
		//
		{
			final User user = instance.createUser(SimpleComponentFactory.getUser());
			userId = user.getId();
			user.setActive(active);
			user.setCreateDate(createDate);
			user.setPasswordSalt(passwordSalt);
			user.setUserKey(userEmail);
			user.setUserName(userName);
			user.setUserPassword(userPassword);
			instance.updateUser(user);
		}
		//
		// Read back the User and confirm changes were persisted
		//
		final User updatedUser = instance.getUser(userId);
		assertEquals(updatedUser.getActive(), active);
		assertEquals(updatedUser.getCreateDate(), createDate);
		assertEquals(updatedUser.getPasswordSalt(), passwordSalt);
		assertEquals(updatedUser.getUserKey(), userEmail);
		assertEquals(updatedUser.getUserName(), userName);
		assertEquals(updatedUser.getUserPassword(), userPassword);
	}

	/*
	 * Secondary keys must be unique for Users. Creating more than one User with
	 * the same secondary key (email) should result in an exception
	 */
	@Test(expected = ConstraintViolationException.class)
	public void duplicate_Secondary_Keys_Should_Cause_An_Exception() {

		final String secondaryKey = "user@email.address";

		User user1 = SimpleComponentFactory.getUser();
		User user2 = SimpleComponentFactory.getUser();

		user1.setUserKey(secondaryKey);
		user2.setUserKey(secondaryKey);

		instance.createUser(user1);
		instance.createUser(user2);
	}

	/*
	 * A newly created user should be available through a secondary key search
	 * which in this case is the user email.
	 */
	@Test
	public void testGetUserBySecondaryKey() {

		Integer userId = null;
		final String userEmail = "ddan@beano.com";
		{
			//
			// Insert a User with the above value
			//
			final User user = SimpleComponentFactory.getUser();
			user.setUserKey(userEmail);
			instance.createUser(user);
			userId = user.getId();
		}
		//
		// The secondary key is the email. Searching via this should return the
		// same user id
		//
		User scondaryKeyUser = SimpleComponentFactory.getUser();
		scondaryKeyUser.setUserKey(userEmail);
		scondaryKeyUser = instance.getUserBySecondaryKey(scondaryKeyUser);
		assertEquals(userId, scondaryKeyUser.getId());
	}
}
