package classification;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class ProductScraper {

    public static void test () throws InterruptedException {
    	
    	System.setProperty("webdriver.chrome.driver", "C:/Users/bouss/Desktop/chromedriver/chromedriver.exe");
        System.out.println("aaaaaaaaaaaaa");
        ChromeOptions ops = new ChromeOptions();
    	ops.addArguments("--remote-allow-origins=*");
    	DesiredCapabilities cp = new DesiredCapabilities();
    	cp.setCapability(ChromeOptions.CAPABILITY, ops);
    	ops.merge(cp);
    	WebDriver driver = new ChromeDriver(ops);

       System.out.println("hhhhhhhhhhhhhhhhh");
       // Ouvrir le site e-commerce Wix
	   driver.get("https://manage.wix.com/dashboard/58cf3775-a604-45da-a1dd-839f371091b4/wix-stores/products?referralInfo=sidebar&viewId=all-items-view&selectedColumns=0%2CName%2CProductType%2CProductSku%2CComparePrice%2CProductInventoryStatus%2CProductRibbon+false%2CProductBrand+false");
	   
	  

}
    }
