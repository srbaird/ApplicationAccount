package com.bac.applicationaccount;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ 
	AccountLoginModuleTests.class,
	UserAccountSubjectDecoratorTests.class,
	LoginTests.class 
	})
public class AllTests {

}
