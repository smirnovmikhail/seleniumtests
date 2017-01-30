package tk.msmirnoff.selenium.steps;

import org.jbehave.core.annotations.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import tk.msmirnoff.selenium.HtmlLink;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class MySteps {


    private WebDriver driver = null;
    private List<HtmlLink> links = null;

    @BeforeStories
    public void scenarioSetup() {
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(120, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        links = new ArrayList<HtmlLink>();
    }

    @AfterStories
    public void scenarioDestroy() {
        driver.close();
        System.out.println("afterScenario");
    }


    private void addLink(HtmlLink htmlLink) {
        boolean isExist = false;
        for (HtmlLink sitelink : links) {
            if (sitelink.getUrl().equals(htmlLink.getUrl())) {
                isExist = true;
                break;
            }
        }
        if (isExist) {
            System.out.println("LINK ALREADY EXIST");
            htmlLink.print();
        } else {
            if (htmlLink.getUrl().toLowerCase().startsWith("http")){
            links.add(htmlLink);}else
            {
                System.out.println("NON HTTP LINK");
                htmlLink.print();
            }
        }
    }

    private void setVisited(String url, boolean bVisited) {
        for (HtmlLink sitelink : links) {
            if (sitelink.getUrl().equals(url)) {
                sitelink.setVisited(bVisited);
                break;
            }
        }
    }

    @When("I open site $url")
    public void openUrl(@Named("url") String url) {
        driver.get(url);
        setVisited(url, true);
    }



    @When("I find all links on the site with $depth")
    public void findLinks(@Named("depth") int depth) {
        try {
            List<WebElement> links = driver.findElements(By.tagName("a"));
            for (WebElement myElement : links) {
                if (myElement.getText() != "") {
                    addLink(new HtmlLink(myElement.getText(), myElement.getAttribute("href"), driver.getCurrentUrl(), depth, myElement.getLocation()));
                }
            }

            System.out.println("Checked " + driver.getCurrentUrl());
            if (depth > 0) {
                for (HtmlLink pageLink : this.links) {
                    if (!pageLink.isVisited()) {
                        openUrl(pageLink.getUrl());
                        findLinks(depth-1);
                    }
                }
            }
        }
            catch (org.openqa.selenium.StaleElementReferenceException ex){
                System.out.println("ERROR org.openqa.selenium.StaleElementReferenceException");
                ex.printStackTrace();
            }

       // } catch (Exception e) {
      //      System.out.println("error " + e.getStackTrace().toString());
     //   }
    }

    @Given("I am a pending step")
    public void fff1() {
        System.out.println("fff1");
    }

    @When("Show statistic")
    public void showStatistic() {
        System.out.println("Total links: " + this.links.size());
    }

    @Then("I shall be happy")
    public void amIHappy() {
        //TODO:
        // assertEquals(0, 0);
        System.out.println("I'm happy");
    }
}
