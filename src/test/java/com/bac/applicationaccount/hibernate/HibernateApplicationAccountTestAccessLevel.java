package com.bac.applicationaccount.hibernate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import com.bac.applicationaccount.AccessLevel;
import com.bac.applicationaccount.impl.SimpleComponentFactory;

public class HibernateApplicationAccountTestAccessLevel extends AbstractHibernateTestCase {


	/*
	 * Creating a new AccessLevel should have either null or default values for each
	 * member apart from the id.
	 */
	@Test
	public void createdAccessLevelHasNullMembers() {

		//
		// Persist a simple accessLevel and test
		//
		AccessLevel result = instance.createAccessLevel(SimpleComponentFactory.getAccessLevel());
		assertNotNull(result);
		assertNotNull(result.getId());
		//
		// The remaining members should have null values
		//
		assertNull(result.getDescription());
	}

	/*
	 * A AccessLevel that has been deleted should not be found
	 */
	@Test
	public void deletedAccessLevelShouldNotExist() {

		//
		// Create a simple accessLevel
		//
		AccessLevel result = instance.createAccessLevel(SimpleComponentFactory.getAccessLevel());
		assertNotNull(result);
		assertNotNull(result.getId());
		//
		// Delete the accessLevel
		//
		instance.deleteAccessLevel(result);
		//
		// Re-reading the AccessLevel should now result in a null value
		//
		Integer accessLevelId = result.getId();
		assertNull(instance.getAccessLevel(accessLevelId));
	}

	/*
	 * An empty database should contain no AccessLevels
	 */
	@Test
	public void accessLevelShouldNotExistInAnEmptyDatabase() {

		final Integer nonExistentAccessLevelId = 1;
		assertNull(instance.getAccessLevel(nonExistentAccessLevelId));
	}

	/*
	 * An empty database should contain no AccessLevels
	 */
	@Test
	public void accessLevelsShouldNotExistInAnEmptyDatabase() {

		assertTrue(instance.getAccessLevels().isEmpty());
	}

	/*
	 * Adding a AccessLevel should result in it being present in the list of all AccessLevels
	 */
	@Test
	public void createdAccessLevelInListOfAllAccessLevels() {

		final AccessLevel accessLevel = instance.createAccessLevel(SimpleComponentFactory.getAccessLevel());
		List<? extends AccessLevel> result = instance.getAccessLevels();
		assertTrue(result.size() == 1);
		assertTrue(result.contains(accessLevel));
	}

	/*
	 * Adding a multiple AccessLevels should result in them all being present in the
	 * list of all AccessLevels. No other accessLevels should exist in the list.
	 */
	@Test
	public void createdAccessLevelsInListOfAllAccessLevels() {

		List<AccessLevel> accessLevels = Arrays.asList(new AccessLevel[] {instance.createAccessLevel(SimpleComponentFactory.getAccessLevel())
				, instance.createAccessLevel(SimpleComponentFactory.getAccessLevel())
				, instance.createAccessLevel(SimpleComponentFactory.getAccessLevel())});
		List<? extends AccessLevel> result = instance.getAccessLevels();
		assertTrue(result.size() == accessLevels.size());
		assertTrue(result.containsAll(accessLevels));
	}

	/*
	 * When a newly created AccessLevel is read back it should contain the appropriate
	 * member values
	 */
	@Test
	public void createAccessLevelAndReadBack() {

		Integer accessLevelId = null;
		final String accessLevelDescription = "Access All Areas";

		{
			//
			// Insert a AccessLevel with the above values
			//
			final AccessLevel accessLevel = SimpleComponentFactory.getAccessLevel();
			accessLevel.setDescription(accessLevelDescription);
			instance.createAccessLevel(accessLevel);
			accessLevelId = accessLevel.getId();
		}
		final AccessLevel createdAccessLevel = instance.getAccessLevel(accessLevelId);
		assertEquals(createdAccessLevel.getDescription(), accessLevelDescription);
	}

	/*
	 * When a newly created AccessLevel is subsequently updated, reading back by it's
	 * id should reflect the updated values
	 */
	@Test
	public void updatedAccessLevelReflectsChanges() {

		//
		// Apply test values and update
		//
		Integer accessLevelId = null;
		final String accessLevelDescription = "Access All Areas";
		//
		{
			final AccessLevel accessLevel = instance.createAccessLevel(SimpleComponentFactory.getAccessLevel());
			accessLevelId = accessLevel.getId();
			accessLevel.setDescription(accessLevelDescription);
			instance.updateAccessLevel(accessLevel);
		}
		//
		// Read back the AccessLevel and confirm changes were persisted
		//
		final AccessLevel updatedAccessLevel = instance.getAccessLevel(accessLevelId);
		assertEquals(updatedAccessLevel.getDescription(), accessLevelDescription);
	}
}
