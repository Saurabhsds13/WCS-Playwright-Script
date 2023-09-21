package login;

// This is Old script without test cases.
import java.nio.file.Paths;
import java.util.Random;
import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.options.AriaRole;


public class UseCookieLogin {
	
		public static void main(String[] args) throws InterruptedException {
			
			Playwright pl = Playwright.create();
			Browser br =pl.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
			
			BrowserContext browsercontext = br.newContext(new Browser.NewContextOptions().setStorageStatePath(Paths.get
			("C:\\Users\\saurabh.sonawane\\Desktop\\login.json")));
			Page page =browsercontext.newPage();
			page.navigate("https://www.dmart.in/");
			
			
			String[] names = { "rice", "wheat", "pulses", "fruits", "oil", "grocery", "Ready to cook" };
			int index = (int) (Math.random() * names.length);
			String name = names[index];
			System.out.println("Grocery:" + name);
			page.getByPlaceholder("Aapko Kya Chahiye?").fill(name);
			page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Search")).click();
			Random rn = new Random();
			
			for (int i = 0; i < 15; i++) {
				int answer = rn.nextInt(10) + 1;
				page.locator("(//i[@class='dmart-icon-cart cart-action_icon__Aptj0'])").nth(answer).click();
				System.out.println(answer);
			}
			
			page.navigate("https://www.dmart.in/cart");
			page.waitForURL("https://www.dmart.in/cart");
			System.out.println("/Cart fetch");
			
			page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Proceed to checkout")).click();
			page.locator("input[value=\"hd\"]").check();
			
			page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Confirm Address")).click();
			Thread.sleep(5000);
			page.click("//button[text()='Confirm Time Slot']");

		
			page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Pay on Delivery (COD)")).click();
			Thread.sleep(3000);
			
			page.navigate("https://www.dmart.in/cart");
			page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName(" Remove all")).click();
			Thread.sleep(5000);
			System.out.println("return to cart for remove all items");
			page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("YES")).click();
		
			page.close();
			
		}
}