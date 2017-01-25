package com.bac.applicationaccount.data.hibernate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.hibernate.exception.ConstraintViolationException;
import org.junit.Test;

import com.bac.applicationaccount.data.Application;
import com.bac.applicationaccount.data.SimpleComponentFactory;

public class HibernateApplicationAccountTestApplication extends AbstractHibernateTestCase {

	/*
	 * Creating a new Application should have either null or default values for
	 * each member apart from the id.
	 */
	@Test
	public void createdApplicationHasNullMembers() {

		//
		// Persist a simple Application and test
		//
		Application result = instance.createApplication(SimpleComponentFactory.getApplication());
		assertNotNull(result);
		assertNotNull(result.getId());
		//
		// Active defaults to 'N'
		//
		final Character DEFAULT_ACTIVE = 'N';
		assertEquals(result.getActive(), DEFAULT_ACTIVE);
		//
		// Accept registration defaults to 'N'
		//
		final Character DEFAULT_ACCEPT_REGISTRATION = 'N';
		assertEquals(result.getAcceptRegistration(), DEFAULT_ACCEPT_REGISTRATION);
		//
		// The remaining members should have null values
		//
		assertNull(result.getName());
	}

	/*
	 * A Application that has been deleted should not be found
	 */
	@Test
	public void deletedApplicationShouldNotExist() {

		//
		// Create a simple Application
		//
		Application result = instance.createApplication(SimpleComponentFactory.getApplication());
		assertNotNull(result);
		assertNotNull(result.getId());
		//
		// Delete the Application
		//
		instance.deleteApplication(result);
		//
		// Re-reading the Application should now result in a null value
		//
		Integer ApplicationId = result.getId();
		assertNull(instance.getApplication(ApplicationId));
	}

	/*
	 * An empty database should contain no Applications
	 */
	@Test
	public void ApplicationShouldNotExistInAnEmptyDatabase() {

		final Integer nonExistentApplicationId = 1;
		assertNull(instance.getApplication(nonExistentApplicationId));
	}

	/*
	 * An empty database should contain no Applications
	 */
	@Test
	public void ApplicationsShouldNotExistInAnEmptyDatabase() {

		assertTrue(instance.getApplications().isEmpty());
	}

	/*
	 * Adding a Application should result in it being present in the list of all
	 * Applications
	 */
	@Test
	public void createdApplicationInListOfAllApplications() {

		final Application application = instance.createApplication(SimpleComponentFactory.getApplication());
		List<? extends Application> result = instance.getApplications();
		assertTrue(result.size() == 1);
		assertTrue(result.contains(application));
	}

	/*
	 * Adding a multiple Applications should result in them all being present in
	 * the list of all Applications. No other Applications should exist in the
	 * list.
	 */
	@Test
	public void createdApplicationsInListOfAllApplications() {

		List<Application> applications = Arrays
				.asList(new Application[] { instance.createApplication(SimpleComponentFactory.getApplication()),
						instance.createApplication(SimpleComponentFactory.getApplication()),
						instance.createApplication(SimpleComponentFactory.getApplication()) });
		List<? extends Application> result = instance.getApplications();
		assertTrue(result.size() == applications.size());
		assertTrue(result.containsAll(applications));
	}

	/*
	 * When a newly created Application is read back it should contain the
	 * appropriate member values
	 */
	@Test
	public void createApplicationAndReadBack() {

		Integer applicationId = null;
		final String applicationName = "Application Account";
		final Character active = 'Y';
		final Character acceptRegistration = 'Y';
		{
			//
			// Insert a Application with the above values
			//
			final Application application = SimpleComponentFactory.getApplication();
			application.setActive(active);
			application.setAcceptRegistration(acceptRegistration);
			application.setName(applicationName);
			instance.createApplication(application);
			applicationId = application.getId();
		}
		final Application createdApplication = instance.getApplication(applicationId);
		assertEquals(createdApplication.getActive(), active);
		assertEquals(createdApplication.getAcceptRegistration(), acceptRegistration);
		assertEquals(createdApplication.getName(), applicationName);
	}

	/*
	 * When a newly created Application is subsequently updated, reading back by
	 * it's id should reflect the updated values
	 */
	@Test
	public void updatedApplicationReflectsChanges() {

		//
		// Apply test values and update
		//
		Integer applicationId = null;
		final String applicationName = "Application Account";
		final Character active = 'Y';
		final Character acceptRegistration = 'Y';
		{
			final Application application = instance.createApplication(SimpleComponentFactory.getApplication());
			applicationId = application.getId();
			application.setActive(active);
			application.setAcceptRegistration(acceptRegistration);
			application.setName(applicationName);
			instance.updateApplication(application);
		}
		//
		// Read back the Application and confirm changes were persisted
		//
		final Application updatedApplication = instance.getApplication(applicationId);
		assertEquals(updatedApplication.getActive(), active);
		assertEquals(updatedApplication.getAcceptRegistration(), acceptRegistration);
		assertEquals(updatedApplication.getName(), applicationName);
	}

	/*
	 * Application secondary keys should be unique. Creating more than one
	 * Application with the same secondary key (name) should throw an exception
	 */
	@Test(expected=ConstraintViolationException.class)
	public void duplicate_Secondary_Keys_Should_Cause_An_Exception() {
		
		final String secondaryKey = "ApplicationAccount";
		
		Application  application1 = SimpleComponentFactory.getApplication();
		Application  application2 = SimpleComponentFactory.getApplication();
		
		application1.setName(secondaryKey);
		application2.setName(secondaryKey);
		
		instance.createApplication(application1);
		instance.createApplication(application2);	
	}
	
	/*
	 * A newly created Application should be available through a secondary key
	 * search which in this case is the Application name.
	 */
	@Test
	public void testGetApplicationBySecondaryKey() {

		Integer applicationId = null;
		final String applicationName = "Application Account";
		{
			//
			// Insert a Application with the above value
			//
			final Application application = SimpleComponentFactory.getApplication();
			application.setName(applicationName);
			instance.createApplication(application);
			applicationId = application.getId();
		}
		//
		// The secondary key is the name. Searching via this should return the
		// same Application id
		//
		Application scondaryKeyApplication = SimpleComponentFactory.getApplication();
		scondaryKeyApplication.setName(applicationName);
		scondaryKeyApplication = instance.getApplicationBySecondaryKey(scondaryKeyApplication);
		assertEquals(applicationId, scondaryKeyApplication.getId());
	}
}
