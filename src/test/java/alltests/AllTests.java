package alltests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ 
	com.bac.applicationaccount.hibernate.AllTests.class, 
	com.bac.applicationaccount.impl.AllTests.class
	})
public class AllTests {

}
