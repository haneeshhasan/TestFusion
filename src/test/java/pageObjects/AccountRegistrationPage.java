package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;

public class AccountRegistrationPage extends BasePage {
	
	public AccountRegistrationPage(WebDriver driver) {
	  super(driver);
	}
	
	@FindBy(xpath="//input[@id='input-firstname']")
	WebElement txtFirstname;

	@FindBy(xpath="//input[@id='input-lastname']")
	WebElement txtLastname;

	@FindBy(xpath="//input[@id='input-email']")
	WebElement txtEmail;

	@FindBy(xpath="//input[@id='input-telephone']")
	WebElement txtTelephone;

	@FindBy(xpath="//input[@id='input-password']")
	WebElement txtPassword;

	@FindBy(xpath="//input[@id='input-confirm']")
	WebElement txtConfirmPassword;

	@FindBy(xpath = "//input[@name='agree']")
	WebElement chkdpolicy;

	@FindBy(xpath = "//input[@value='Continue']")
	WebElement btnContinue;

	@FindBy(xpath = "//h1[normalize-space()='Your Account Has Been Created!']")
	WebElement msgConfirmation;

	public void setFirstName(String fname) {
	    txtFirstname.sendKeys(fname);
	}

	public void setLastName(String lname) {
	    txtLastname.sendKeys(lname);
	}
	
	public void setEmail(String email) {
	    txtEmail.sendKeys(email);
	}

	public void setTelephone(String tel) {
	    txtTelephone.sendKeys(tel);
	}

	public void setPassword(String pwd) {
	    txtPassword.sendKeys(pwd);
	}

	public void setConfirmPassword(String pwd) {
	    txtConfirmPassword.sendKeys(pwd);
	}
	
	public void setPrivacyPolicy() {
	    chkdpolicy.click();
	}

	public void clickContinue() {
	    // Solution 1: Standard click
	    btnContinue.click();
	    
//	 // Solution 1: Standard click (most common)
//	    btnContinue.click();
//
//	    // Solution 2: Submit (for form elements only)
//	    btnContinue.submit();
//
//    // Solution 3: Actions class (for complex interactions)
	//    Actions act = new Actions(driver);
	//    act.moveToElement(btnContinue).click().perform();
//
//	    // Solution 4: JavaScript click (bypasses WebDriver)
//	    JavascriptExecutor js = (JavascriptExecutor)driver;
//	    js.executeScript("arguments[0].click();", btnContinue);
//
//	    // Solution 5: Simulate Enter key (for keyboard navigation)
//	    btnContinue.sendKeys(Keys.RETURN);
//
//	    // Solution 6: Explicit wait + click (most robust)
//	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
//	    wait.until(ExpectedConditions.elementToBeClickable(btnContinue)).click();
//	    
	}
	
	public String getConfirmationMsg() {
	    try {
	    return (msgConfirmation.getText());
	    } catch (Exception e) {
	    return (e.getMessage());
	    }
	}

}
