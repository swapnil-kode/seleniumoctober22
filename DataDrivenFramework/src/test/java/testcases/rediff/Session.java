package testcases.rediff;

import org.testng.ITestContext;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;

import Keywords.ApplicationKeywords;
import testbase.BaseTest;

public class Session extends BaseTest
{
@Test
public void doLogin(ITestContext context)
{
	test.log(Status.INFO, "Logging in");
	//ApplicationKeywords app = (ApplicationKeywords)context.getAttribute("app");
	app.openBrowser("Chrome");
	app.navigate("URL");
	app.click("money_menu_xpath");
	app.click("signin_link_xpath");
	app.sendText("email_textfield_css", "kodeswapnil377@gmail.com");
	app.sendText("password_textfield_id", "Swapk@391");
	app.click("remember_checkbox_id");
	app.click("submit_button_id");
	//app.assertAll();
}
@Test
public void doLogout()
{
	test.log(Status.INFO, "Logging out");
}
}
