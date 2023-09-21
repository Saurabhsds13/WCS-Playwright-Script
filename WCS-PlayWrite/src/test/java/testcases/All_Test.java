package testcases;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import org.testng.Reporter;
import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.options.AriaRole;

    public class All_Test {

	protected Browser browser;
	private BrowserContext browsercontext;
	protected byte[] array;
	protected static Page page;
	
	@BeforeTest
	public void Login() {
		
		Playwright pl = Playwright.create();
		browser =pl.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
		browsercontext = browser.newContext(new Browser.NewContextOptions().setStorageStatePath(Paths.get
		("C:\\Users\\saurabh.sonawane\\Desktop\\login.json")));
		page =browsercontext.newPage();
		Reporter.log("Browser Launched");
		
	}
	
	@Test(priority = 1)
	public void SetPincode() throws InterruptedException{
		page.navigate("https://www.dmart.in/");
		Thread.sleep(10000);
		page.getByTestId("ExpandMoreIcon").click();
		String[] names= {"400076","400053","400078","400058"};
		int index = (int) (Math.random()* names.length);
		String pincode = names[index];
		System.out.println("Pincode Selected is : " + pincode);
		Reporter.log(pincode);
		
		page.getByPlaceholder("Enter your city, area or pincode").type(pincode);
		page.locator("//img[@alt='pincodeimg']").click();
		page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("START SHOPPING")).click();
		Thread.sleep(3000);
	}

	
	@Test(priority = 2)
	public void SearchAndAddtoCart() throws Exception {
		String[] names = { "rice", "pulses", "fruits", "oil", "grocery", "Ready to cook" };
		int index = (int) (Math.random() * names.length);
		String name = names[index];
		System.out.println("Grocery:" + name);
		page.getByPlaceholder("Aapko Kya Chahiye?").type(name);
		Reporter.log(name);
		page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Search")).click();
		Random rn = new Random();
		for (int i = 0; i < 15; i++) {
			int answer = rn.nextInt(20) + 1;
			page.locator("(//i[@class='dmart-icon-cart cart-action_icon__Aptj0'])").nth(answer).click();
			System.out.println(answer);
		}
	}
	
	
	@Test(priority = 3)
	public void MyCartScreen() throws Exception {
		page.navigate("https://www.dmart.in/cart");
		page.waitForURL("https://www.dmart.in/cart");
		page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Proceed to checkout")).click();
	}
	
	
	@Test(priority = 4)
	public void AddressCheck() throws Exception {
		Thread.sleep(2000);
		page.locator("input[name=\"hdlist\"]").click();
		Thread.sleep(2000);
		if (page.locator("//button[text()='YES']").isVisible()) {
			page.locator("//button[text()='YES']").click();
			System.out.println("Address Selected");
			Thread.sleep(2000);
		} else {
			System.out.println("Address Already Selected");
		}
	}
	
	
	@Test(priority = 5)
	public void CheckoutScreens() throws Exception {
		page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Confirm Address")).click();
		Thread.sleep(3000);
		page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Confirm Time Slot")).click();
		Thread.sleep(5000);
		page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Pay on Delivery (COD)")).click();
		Thread.sleep(3000);
		String filename = System.getProperty("user.dir") + "\\Screenshot\\";
		LocalDateTime current = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMyyyy HHmmss");
		String formatted = current.format(formatter);
		setArray(page.screenshot(
				new Page.ScreenshotOptions().setFullPage(true).setPath(Paths.get(filename + formatted + ".png"))));
		Reporter.log(formatted);
	}

	@AfterTest
	public void tearDown() throws InterruptedException {
		page.navigate("https://www.dmart.in/cart");
		page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName(" Remove all")).click();
		page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("YES")).click();
		Thread.sleep(2000);
		this.browser.close();
	}
	
	private void setArray(byte[] array) {
		this.array=array;
		}
}
