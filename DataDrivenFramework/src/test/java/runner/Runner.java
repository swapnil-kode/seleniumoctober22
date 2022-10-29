package runner;

import java.util.ArrayList;
import java.util.List;

public class Runner 
{
public static void main(String[]args)
{
	TestNGRunner testNG = new TestNGRunner(1);
	testNG.createSuite("Portfolio Suite", false);
	testNG.addTest("Create Portfolio Test");
	List<String>includedMethods = new ArrayList<String>();
	includedMethods.add("doLogin");
	testNG.addTestClass("testcases.rediff.Session", includedMethods);
	includedMethods = new ArrayList<String>();
	includedMethods.add("createPortfolio");
	testNG.addTestClass("testcases.rediff.PortfolioManagement", includedMethods);
	
	//Delete portfolio
	testNG.addTest("Delete Portfolio test");
	includedMethods = new ArrayList<String>();
	includedMethods.add("doLogin");
	testNG.addTestClass("testcases.rediff.PortfolioManagement", includedMethods);
	includedMethods = new ArrayList<String>();
	includedMethods.add("deletePortfolio");
	testNG.addTestClass("testcases.rediff.PortfolioManagement", includedMethods);
	testNG.run();
}
}
