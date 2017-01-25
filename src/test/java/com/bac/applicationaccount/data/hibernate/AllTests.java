package com.bac.applicationaccount.data.hibernate;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ 
		HibernateApplicationAccountTestAccessLevel.class, 
		HibernateApplicationAccountTestAccount.class,
		HibernateApplicationAccountTestAccountUser.class, 
		HibernateApplicationAccountTestApplication.class,
		HibernateApplicationAccountTestUser.class, 
		HibernateApplicationAccountTestUserWithAccounts.class 
		})

public class AllTests {

}
