package testbase;

import org.testng.ITestContext;
import org.testng.SkipException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import Keywords.ApplicationKeywords;
import Reports.ExtentManager;

public class BaseTest 
{
public ApplicationKeywords app;
public ExtentReports report;
public ExtentTest test;

@BeforeTest(alwaysRun=true)
public void beforeTest(ITestContext context)
{
	String dataFilePath = context.getCurrentXmlTest().getParameter("datafilepath");
	String dataFlag = context.getCurrentXmlTest().getParameter("dataflag");
	String iteration = context.getCurrentXmlTest().getParameter("iteration");
	System.out.println("-------Before Test-------");
	//initialize extent reports and test object
	report = ExtentManager.getReports();
	test = report.createTest(context.getCurrentXmlTest().getName());
	test.log(Status.INFO, "Starting Test"+context.getCurrentXmlTest().getName());
	context.setAttribute("report", report);
	context.setAttribute("tests", test);
	
	
	app = new ApplicationKeywords();
	app.setReport(test);
	/*app.openBrowser("Chrome");
	app.defaultLogin();*/
	context.setAttribute("app", app);
	
	
}
@BeforeMethod(alwaysRun=true)
public void beforeMethod(ITestContext context)
{
	System.out.println("*******Before Method*******");	
	test = (ExtentTest)context.getAttribute("tests");
	String criFailure = (String)context.getAttribute("criticalFailure");
	if(criFailure!=null && criFailure.equals("Y"))
	{
	test.log(Status.SKIP, "Skipping test as critical error is observed");
	throw new SkipException("Skipping test as critical error is observed");
	}
	app = (ApplicationKeywords)context.getAttribute("app");
	report = (ExtentReports)context.getAttribute("report");
}
@AfterTest(alwaysRun=true)
public void quit(ITestContext context)
{
app=(ApplicationKeywords)context.getAttribute("app");
if(app!=null)
	app.quit();
if(report!=null)
	report.flush();	
}
}
