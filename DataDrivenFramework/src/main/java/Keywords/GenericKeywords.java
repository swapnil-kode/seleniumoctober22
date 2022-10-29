package Keywords;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;
import org.testng.asserts.SoftAssert;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import Reports.ExtentManager;

public class GenericKeywords 
{
public WebDriver driver;
public WebDriverWait wait;
public Properties prop;
public ExtentTest test;
public SoftAssert softAssert;
public void openBrowser(String browser)
{
	if(prop.getProperty("gridRun").equals("Y"))
	{
		//grid execution using RemoteWebDriver
		DesiredCapabilities cap = new DesiredCapabilities();
		if(browser.equals("Chrome"))
		{
			cap.setBrowserName("chrome");
			cap.setPlatform(org.openqa.selenium.Platform.WINDOWS);
		}
		else if(browser.equals("firefox"))
		{
			cap.setBrowserName("firefox");
			cap.setJavascriptEnabled(true);
			cap.setPlatform(org.openqa.selenium.Platform.WINDOWS);
		}
		try
		{
			driver=new RemoteWebDriver(new URL("http://localhost:4444"),cap);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	else //local machine
	{
	log("Opening browser"+browser);
	if(browser.equals("Chrome"))
	{
		System.setProperty("webdriver.chrome.driver", "C:\\Users\\HP\\Desktop\\Learning Automation - Prathap\\Softwares\\Drivers\\chromedriver.exe");
		driver = new ChromeDriver();
	}
	else if(browser.equals("Firefox"))
	{
		System.setProperty("webdriver.gecko.driver", "C:\\Users\\HP\\Desktop\\Learning Automation - Prathap\\Softwares\\Drivers\\geckodriver.exe");
		driver = new FirefoxDriver();
	}
	else if(browser.equals("IE"))
	{
		System.setProperty("webdriver.ie.driver", "C:\\Users\\HP\\Desktop\\Learning Automation - Prathap\\Softwares\\Drivers\\IEDriverServer.exe");
		driver = new InternetExplorerDriver();
	}
	driver.manage().window().maximize();
	driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
	}
}
public void navigate(String urlKey)
{
	log("Navigating to the url");
	driver.get(prop.getProperty(urlKey));
}
public void sendText(String locatorKey, String data)
{
	log("Typing text"+data);
	getWebElement(locatorKey).sendKeys(data);
}
public void click(String locatorKey)
{
	log("Clicking");
	getWebElement(locatorKey).click();
}
public WebElement getWebElement(String locatorKey)
{
if(!isElementPresent(locatorKey))
{
System.out.println("Element is not present");	
}
if(!isElementVisible(locatorKey))
{
System.out.println("Element is not visible");	
}
WebElement e = null;
e = driver.findElement(getLocator(locatorKey));
return e;
}
public boolean isElementPresent(String locatorKey)
{
	wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	try {
		wait.until(ExpectedConditions.presenceOfElementLocated(getLocator(locatorKey)));
	}
	catch(Exception e)
	{
		return false;
	}
	return true;
}
public boolean isElementVisible(String locatorKey)
{
wait = new WebDriverWait(driver, Duration.ofSeconds(10));
try
{
	wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	wait.until(ExpectedConditions.visibilityOfElementLocated(getLocator(locatorKey)));
}
catch(Exception e)
{
return false;	
}
return true;
}
public By getLocator(String locatorKey)
{
	By by = null;
	if(locatorKey.endsWith("_id"))
		by = By.id(prop.getProperty(locatorKey));
	if(locatorKey.endsWith("_name"))
		by = By.name(prop.getProperty(locatorKey));
	if(locatorKey.endsWith("_xpath"))
		by = By.xpath(prop.getProperty(locatorKey));
	if(locatorKey.endsWith("_css"))
		by = By.cssSelector(prop.getProperty(locatorKey));
	
	return by;
}
public void log(String msg)
{
test.log(Status.INFO, msg);	
}
public void reportFailure(String msg, boolean stopOnFailure)
{
test.log(Status.FAIL, msg);
screenshot();
softAssert.fail(msg);
if(stopOnFailure)
{
Reporter.getCurrentTestResult().getTestContext().setAttribute("criticalFailure", "Y");
assertAll();	
}
}
public void assertAll()
{
softAssert.assertAll();	
}
public void screenshot()
{
Date d = new Date();
String screenshotFile = d.toString().replaceAll(":", "-")+".png";
File srcFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
try {
	FileUtils.copyFile(srcFile, new File(ExtentManager.screenshotFolderPath+"//"+screenshotFile));
	test.log(Status.INFO, "Screenshot->"+test.addScreenCaptureFromPath(ExtentManager.screenshotFolderPath+"//"+screenshotFile));
} catch (IOException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
}
}
public void waitForPageToLoad() throws InterruptedException
{
try
{
JavascriptExecutor js = (JavascriptExecutor)driver;
int i=0;
while(i!=10)
{
String state = (String)js.executeScript("return document.readyState");
System.out.println("State is "+state);
if(state.equals("complete"))
	break;
else
	wait(2);
i++;
}
i=0;
while(i!=10)
{
Long d = (Long)js.executeScript("return jQuery.active");	
System.out.println("Jquery value is "+d);
if(d.longValue()==0)
break;
else 
	wait(2);
i++;
}
}
catch(Exception e)
{
System.out.println(e.getMessage());
e.printStackTrace();
}
}
public void wait(int time) throws InterruptedException
{
	Thread.sleep(time*1000);
}
public void clear(String locatorKey)
{
	getWebElement(locatorKey).clear();
}
public void selectByVisibleText(String locatorKey, String data)
{
	Select s = new Select(getWebElement(locatorKey));
	s.selectByVisibleText(data);
}
public void validateSelectedValueInDropdown(String locatorKey, String data)
{
	Select s = new Select(getWebElement(locatorKey));
	String selectedValue = s.getFirstSelectedOption().getText();
	if(!selectedValue.equals(data))
	{
		reportFailure("Option "+data+" is not present in drop down "+locatorKey,true);	
	}
}
public void validateSelectedValueNotInDropdown(String locatorKey, String data)
{
	Select s = new Select(getWebElement(locatorKey));
	String selectedValue = s.getFirstSelectedOption().getText();
	if(selectedValue.equals(data))
	{
		reportFailure("Option "+data+" is present in drop down "+locatorKey,true);	
	}
}
public void acceptAlert()
{
Alert al = driver.switchTo().alert();
al.accept();
}
public void dismissAlert()
{
Alert al = driver.switchTo().alert();
al.dismiss();
}
public void clickEnterButton(String locatorKey)
{
getWebElement(locatorKey).sendKeys(Keys.ENTER);
}
public void selectDateFromCalendar(String date) throws ParseException
{
Date curentDate = new Date();
Date selectableDate = new SimpleDateFormat("dd-MM-yyyy").parse(date);
String day = new SimpleDateFormat("d").format(selectableDate);
String month = new SimpleDateFormat("MMMM").format(selectableDate);
String year = new SimpleDateFormat("yyyy").format(selectableDate);
String dateToBeSelected = month+" "+year;
String dateDisplayed = getWebElement("add_stock_purchase_currentdate_xpath").getText();
while(!dateDisplayed.equals(dateToBeSelected))
{
click("add_stock_purchase_previous_date_button_xpath");
dateDisplayed = getWebElement("add_stock_purchase_currentdate_xpath").getText();
}
driver.findElement(By.xpath("//td[text()='"+day+"']")).click();
}
public void quit()
{
driver.quit();	
}
}
