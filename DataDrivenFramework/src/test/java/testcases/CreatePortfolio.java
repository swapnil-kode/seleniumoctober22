package testcases;

import org.testng.annotations.Test;

import Keywords.ApplicationKeywords;

public class CreatePortfolio 
{
@Test
public void createPortfolio()
{
ApplicationKeywords ap = new ApplicationKeywords();
ap.openBrowser("Chrome");
ap.navigate("URL");
ap.click("money_menu_xpath");
ap.click("signin_link_xpath");
ap.sendText("email_textfield_css", "kodeswapnil377@gmail.com");
ap.sendText("password_textfield_id", "Swapk@391");
ap.click("remember_checkbox_id");
ap.click("submit_button_id");
}
}
