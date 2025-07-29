package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.AccountRegistrationPage;
import pageObjects.HomePage;
import testBase.BaseClass;

public class TC001_AccountRegistrationTest extends BaseClass{

	
	
	
	@Test(groups = {"Regression","Master"})
	public void verify_account_registration() {
		
		
		logger.info("******* Starting  verify_account_registration *******");
		
		
		try {
		HomePage hp = new HomePage(driver);
		
		
		hp.clickMyAccount();
		
		logger.info("Clicked on myaccount link");
		hp.clickRegister();
		logger.info("Clicked on register link");

		
		AccountRegistrationPage reg = new AccountRegistrationPage(driver);
		
		
		logger.info("Providing customer details");

		reg.setFirstName(randomString().toUpperCase());
		reg.setLastName(randomString().toUpperCase());
		reg.setEmail(randomString()+"@gmail.com");
		reg.setTelephone(randomNumber());
		
		String password = randomAlphaNumeric();
		
		reg.setPassword(password);
		reg.setConfirmPassword(password);
		
		reg.setPrivacyPolicy();
		reg.clickContinue();
		
		
		logger.info("Validating expected message");
		String confmsg = reg.getConfirmationMsg();
		
		if(confmsg.trim().equals("Your Account Has Been Created!")) {
			Assert.assertTrue(true);
		}
		else {
			
			logger.error("Test failed");
			logger.debug("Debugger logs");
			Assert.assertTrue(false);
		}
		
	//	Assert.assertEquals(confmsg.trim(), "Your Account Has Been Created[]!");
		}
		catch(Exception e) {
			
	         Assert.fail();
		}
		logger.info("******* Finished  verify_account_registration *******");

	}
	
	
}
