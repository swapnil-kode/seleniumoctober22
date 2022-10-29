package listener;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

public class MyTestNGListener implements ITestListener{

	public void onTestSuccess(ITestResult result) 
	{
	//System.out.println("Test has passed "+result.getName());	
	ExtentTest test = (ExtentTest)result.getTestContext().getAttribute("test");
	test.log(Status.PASS, "Test passed"+result.getName());
	}

	public void onTestFailure(ITestResult result) 
	{
	ExtentTest test = (ExtentTest)result.getTestContext().getAttribute("test");
	test.log(Status.FAIL, result.getName());
	}

	public void onTestSkipped(ITestResult result) 
	{
	ExtentTest test = (ExtentTest)result.getTestContext().getAttribute("test");
	test.log(Status.SKIP, "Test is skipped");
	}

	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		// TODO Auto-generated method stub
		
	}

	public void onStart(ITestContext context) {
		// TODO Auto-generated method stub
		
	}

	public void onFinish(ITestContext context) {
		// TODO Auto-generated method stub
		
	}
	
	public void onTestStart(ITestResult result) {
		// TODO Auto-generated method stub
		
	}

}
