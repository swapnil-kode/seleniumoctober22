package testcases.rediff;

import java.text.ParseException;

import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;

import testbase.BaseTest;

public class StockManagement extends BaseTest
{
String portofolioToSelect = "SKTest29092202";
@Test
public void addFreshStock() throws InterruptedException, ParseException
{
test.log(Status.INFO, "Adding stock");
app.selectByVisibleText("select_portfolio_dropdown_id", portofolioToSelect);
app.waitForPageToLoad();
app.click("add_stock_button_id");
app.sendText("add_stock_name_text_id", "Reliance Infrastructure Ltd");
app.wait(1);
app.clickEnterButton("add_stock_name_text_id");
app.click("add_stock_purchase_datepicker_id");
app.selectDateFromCalendar("12-02-2022");
}
@Test
public void modifyStock()
{
	
}
}
