package TestCases;


import java.io.IOException;

import org.testng.annotations.Test;

import PageObjectModel.HomePage;
import PageObjectModel.SignUpPage;
import BaseClass.BaseClass;


public class CustomerModule extends BaseClass {
	@Test
public void Login() throws IOException, InterruptedException {
	
 SignUpPage signup=new SignUpPage(driver);
 signup.enterUserName(prop.getProperty("userName"));
 logger.info("UserName is Entered");
 signup.enterPassword(prop.getProperty("password"));
 signup.clickLoginButton();
 HomePage home=new HomePage(driver);
 home.clickModulesAndSettings();
 home.clickCustomerInFrame();
 String filePath = "C:\\Users\\Nethra\\Downloads\\customer_master_new.xlsx";
 home.uploadFile(filePath);
 home.validateUpload("C:\\Users\\Nethra\\Downloads\\customer_master_new.xlsx");
 //home.validateUpload("C:\\Users\\Nethra\\Downloads\\customer_master_new.xlsx");
home.exitUpload();
 home.clickRandomly();

  

	
}

}
