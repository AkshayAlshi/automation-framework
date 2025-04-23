package API_utils;


import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
	import utils.ExtentManager;

	public class ExtentBase {

		@BeforeSuite
	    public void beforeSuite() {
	        System.out.println(">> Extent Report Initialization Started <<");
	        ExtentManager.initReport(); // 🔧 This ensures the report is created once per suite
	    }

	    @BeforeMethod
	    public void beforeEachTest(java.lang.reflect.Method method) {
	        // 👇 Dynamically use method name as test name
	        ExtentManager.createTest(method.getName());
	    }

	    @AfterSuite
	    public void afterSuite() {
	        System.out.println(">> Flushing Extent Report <<");
	        ExtentManager.flushReports();
	    }
	}


