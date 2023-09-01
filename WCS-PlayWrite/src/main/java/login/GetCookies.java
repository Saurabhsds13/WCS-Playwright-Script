package login;

import java.nio.file.Paths;
import com.microsoft.playwright.*;
import com.microsoft.playwright.options.*;


public class GetCookies {
	
	  public static void main(String[] args) {
	  
	  try (Playwright playwright = Playwright.create()) {
		  
	  Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
      BrowserContext context = browser.newContext();
      Page page = context.newPage();
      
      page.navigate("https://www.dmart.in/");
      page.getByPlaceholder("Enter your city, area or pincode").type("400053");
      page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("pincodeimg 400053 Andheri (W), Mumbai")).click();
      page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("START SHOPPING")).click();
      page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Sign In / Register")).click();
      page.getByRole(AriaRole.TEXTBOX).fill("7972227009");
      page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Continue")).click();
      page.pause();
     
      //Put OTP manually and Login to site.
      System.out.println("Sing-In Completed");
      
      //Get Cookies from session and save in a j-son file.
      context.storageState(new BrowserContext.StorageStateOptions().setPath(Paths.get
      ("C:\\Users\\saurabh.sonawane\\Desktop\\login.json")));
      System.out.println("Cookies Fetched.");
      
      page.close();
      
    }catch (Exception e) {
    	System.out.println(e);
	}
  }
}