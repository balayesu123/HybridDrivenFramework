package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import pageObjects.AccountRegistrationPage;
import pageObjects.HomePage;
import testBase.BaseClass;

public class TC001_AccountRegistretionTest extends BaseClass{

	@Test(groups= {"Regression","Master"})
	public void verify_account_register()
	{
		logger.info("Starting TC001_AccountRegistretionTest...");

		try {
			HomePage hp = new HomePage(driver);
			hp.clickMyAccount();
			logger.info("Clicked on Mylink...");

			hp.clickRegister();
			logger.info("Clicked on Register...");

			AccountRegistrationPage repage = new AccountRegistrationPage(driver);
			repage.setFirstName(randomString().toUpperCase());
			repage.setLastName(randomString().toUpperCase());
			repage.setEmail(randomString()+"@gmail.com");
			repage.setTelephone(randomNumber());

			String password = randomAlphaNumaric();

			repage.setPassword(password);
			repage.setConfirmPassword(password);

			repage.setPrivacyPolicy();
			repage.clickContinue();	   
			logger.info("Entered customer details...");

			String confmsg = repage.getConfirmationMsg();	   
			//SoftAssert softAssert = new SoftAssert();
			// Assert.assertEquals(confmsg, "Your Account Has Been Created!");
			//softAssert.assertEquals(confmsg, "Your Account Has Been Created!!!");
			
			if(confmsg.equals("Your Account Has Been Created!")) 
			{
				logger.info("Your Account Has Been Created!...");
				Assert.assertTrue(true);
			}
			else {
				logger.error("Test Failed due to confirmation message not maching!...");
				logger.debug("Debug log...");
				Assert.assertTrue(false);
			}
		}
		catch(Exception e)
		{
			Assert.fail();
		}
		
		logger.info("Ended TC001_AccountRegistretionTest...");

	}

}
