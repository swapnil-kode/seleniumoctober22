package testcases.rediff;

import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;

import testbase.BaseTest;

public class PortfolioManagement extends BaseTest
{
@Test
public void createPortfolio() throws InterruptedException
{
	String portfolio = "SKTest29092213";
test.log(Status.INFO, "Creating portfolio");
app.click("create_portfolio_id");
app.clear("enter_portfolio_name_xpath");
app.sendText("enter_portfolio_name_xpath", portfolio);
app.click("create_portfolio_button_id");
app.waitForPageToLoad();
app.selectByVisibleText("select_portfolio_dropdown_id", portfolio);
app.validateSelectedValueInDropdown("select_portfolio_dropdown_id", portfolio);
}
@Test
public void deletePortfolio() throws InterruptedException
{
String portfolio = "SKTest29092206";
test.log(Status.INFO, "Deleting portfolio");
app.selectByVisibleText("select_portfolio_dropdown_id", portfolio);
System.out.println("Before page load");
app.waitForPageToLoad();
System.out.println("After page load");
System.out.println("Before clicking delete");
app.click("delete_portfolio_button_id");
System.out.println("Aftre clicking delete");
app.acceptAlert();
app.validateSelectedValueNotInDropdown("select_portfolio_dropdown_id", portfolio);
}
}
