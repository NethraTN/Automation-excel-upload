package PageObjectModel;



import java.time.Duration;



import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;


public class HomePage extends BasePage {
    public HomePage(WebDriver driver) {
        super(driver);
    }
    
    @FindBy(xpath="//*[@id='leftsidebar']/div/ul/li[3]/a")
    private WebElement ModulesDropDown;
    
    @FindBy(xpath="//a[normalize-space()='Settings']")
    private WebElement Settings;
    
    @FindBy(xpath="//iframe[@allow='geolocation *; camera *']")
    private WebElement frameforCustomermodule;

    @FindBy(xpath="//*[@id='kt_stats_widget_16_tab_link_2']")
    private WebElement Customer;

    @FindBy(xpath="//*[@id='kt_stats_widget_16_tab_2']/div/table/tbody/tr[1]/td[1]/div/div/a")
    private WebElement CustomerMaster;

    @FindBy(xpath="//*[@id='kt_app_content_container']/div[1]/div[1]/div[2]/div[1]/button[3]")
    private WebElement CustomerMasterImportButton;
    @FindBy(xpath="//*[@type='file']")
    private WebElement inputField;
    
    @FindBy(xpath="//*[@id='kt_app_body']/div[14]/div/div[6]/button[1]")
    private WebElement YesUploadButton;
    
    
    @FindBy(xpath="//button[text()='OK']")
    private WebElement OkButton;
    
    @FindBy(xpath="//*[@id='closeXlsx']")
    private WebElement CrossButton;
    
    @FindBy(xpath="//*[text()='Yes, cancel it!']")
    private WebElement YesCancelIt;
    

    public void clickModulesAndSettings() {
        // Wait for the dropdown to be clickable and then click it
        explicitWait(ModulesDropDown, Duration.ofSeconds(10));
        ModulesDropDown.click();

        // Wait for the Settings option to be visible and then click it
        explicitWait(Settings, Duration.ofSeconds(10));
        Settings.click();
    }

    // Explicit wait method
    private void explicitWait(WebElement element, Duration timeOut) {
        WebDriverWait wait = new WebDriverWait(driver, timeOut);
        wait.until(ExpectedConditions.visibilityOf(element));
        wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    public void clickCustomerInFrame() {
        // Switch to the iframe
        driver.switchTo().frame(frameforCustomermodule);
        
        // Wait for the Customer element to be clickable
        explicitWait(Customer, Duration.ofSeconds(10));
        Customer.click();

        // Wait for the CustomerMaster element to be clickable
        explicitWait(CustomerMaster, Duration.ofSeconds(15));
        CustomerMaster.click();

        // Wait for the import button to be clickable
        explicitWait(CustomerMasterImportButton, Duration.ofSeconds(10));
        
        // Click the import button using JavaScript if it’s not clickable directly
        try {
            CustomerMasterImportButton.click();
        } catch (ElementClickInterceptedException e) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", CustomerMasterImportButton);
        }
        
        

        // Switch back to the main content
        driver.switchTo().defaultContent();
    }
   
    public void uploadFile(String filePath) throws InterruptedException {
        // Switch to the iframe first
        driver.switchTo().frame(0);
        
        // Wait for the file input element to be visible and clickable
        explicitWait(inputField, Duration.ofSeconds(10));
        
        // Send the file path to the input field
        inputField.sendKeys(filePath);
        Thread.sleep(1000);
       // String Number=YesUploadButton.getText();
       
        
        
        
        // Optionally switch back to the main content if needed
       
    }
    
     private int extractNumberFromMessage() {
         String messageText = YesUploadButton.getText();
         String[] parts = messageText.split(":");
         String numberPart = parts[1].trim(); // Assuming the format "Yes, Upload, No. of items: 1"
         return Integer.parseInt(numberPart);
     }
     public void validateUpload(String excelFilePath) throws IOException {
         int expectedRecords = getNumberOfRecordsFromExcel(excelFilePath);
         int actualRecords = extractNumberFromMessage();

         if (expectedRecords == actualRecords) {
             System.out.println("Upload validation successful:Expected " + actualRecords + " records uploaded:"+actualRecords);
         } else {
             System.out.println("Upload validation failed: Expected " + expectedRecords + " but found: " + actualRecords);
         }
     }

    

    public int getNumberOfRecordsFromExcel(String excelFilePath) throws IOException {
        int numberOfRows = 0;

        try (FileInputStream fis = new FileInputStream(new File("C:\\Users\\Nethra\\Downloads\\customer_master_new.xlsx"));
             Workbook workbook = new XSSFWorkbook(fis)) {
            
            Sheet sheet = workbook.getSheetAt(0); // Adjust index based on your sheet
            numberOfRows = sheet.getLastRowNum() + 1; // Get the last row index and add 1
        } catch (IOException e) {
            e.printStackTrace();
            throw e; // Rethrow if you want to propagate the exception
        }

        return numberOfRows - 1; // Subtract 1 if there is a header row
    }
    public void exitUpload() throws InterruptedException {
    YesUploadButton.click();
    Thread.sleep(1000);
    explicitWait(OkButton, Duration.ofSeconds(10));
    
    OkButton.click();
    explicitWait(CrossButton, Duration.ofSeconds(10));
    CrossButton.click();
    
    Thread.sleep(1000);
    YesCancelIt.click();
    Thread.sleep(1000);

    }
    public void clickRandomly() {
        // Get the dimensions of the window
        long width = (long) ((JavascriptExecutor) driver).executeScript("return window.innerWidth");
        long height = (long) ((JavascriptExecutor) driver).executeScript("return window.innerHeight");

        // Generate random x and y coordinates
        int randomX = (int) (Math.random() * width);
        int randomY = (int) (Math.random() * height);

        // Use JavaScript to perform the click at the random coordinates
        String script = String.format("var evt = new MouseEvent('click', { clientX: %d, clientY: %d }); document.dispatchEvent(evt);", randomX, randomY);
        ((JavascriptExecutor) driver).executeScript(script);
    } 
}


