package tk.msmirnoff.selenium.steps;

import org.jbehave.core.annotations.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class MySteps {


    private WebDriver driver = null;
    private List<WebElement> links = null;

    @BeforeStories
    public void scenarioSetup() {
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(120, TimeUnit.SECONDS);
        driver.manage().window().maximize();

        //links = new ArrayList<WebElement>();
    }

    @AfterStories
    public void scenarioDestroy() {
        driver.close();
        System.out.println("afterScenario");
    }

    @When("I open site $url")
    public void findAllLinks(@Named("url") String url) {
        driver.get(url);
    }

    @When("I find all links on the page")
    public void findLinks() {
        try {
            List<WebElement> links = driver.findElements(By.tagName("a"));

            System.out.println(links.size());
            for (WebElement myElement : links) {
                String link = myElement.getText();
                System.out.println(link);
                System.out.println(myElement);
                if (link != "") {
                     System.out.println("Link " + link);
                }
            }
            System.out.println("Checked " + driver.getCurrentUrl());
        } catch (Exception e) {
            System.out.println("error " + e);
        }
    }

    @Given("I am a pending step")
    public void fff1() {
        System.out.println("fff1");
    }

    @When("a good soul will implement me")
    public void fff2() {

        System.out.println("fff2");
    }

    @Then("I shall be happy")
    public void amIHappy() {
        //TODO:
        // assertEquals(0, 0);
        System.out.println("I'm happy");
    }
}
