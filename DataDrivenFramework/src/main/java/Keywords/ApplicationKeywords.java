package Keywords;

import java.io.FileInputStream;
import java.util.Properties;

import com.aventstack.extentreports.ExtentTest;

public class ApplicationKeywords extends ValidationKeywords
{
public ApplicationKeywords()
{
	String projectpath = System.getProperty("user.dir")+"\\src\\test\\resources\\project.properties";
try 
{
	FileInputStream fs = new FileInputStream(projectpath);
	prop = new Properties();
	prop.load(fs);
	
} catch (Exception e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
}
}
public void setReport(ExtentTest test)
{
this.test=test;	
}
public void defaultLogin() 
{
navigate("URL");
click("money_menu_xpath");
click("signin_link_xpath");
sendText("email_textfield_css", "kodeswapnil377@gmail.com");
sendText("password_textfield_id", "Swapk@391");
click("remember_checkbox_id");
click("submit_button_id");
}
}
