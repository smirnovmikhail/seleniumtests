package tk.msmirnoff.selenium.steps;

import org.apache.commons.collections4.map.HashedMap;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.jbehave.core.annotations.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import tk.msmirnoff.selenium.HtmlLink;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;

public class MySteps {

    private WebDriver driver = null;
    private List<HtmlLink> links = null;
    private Map<String, Integer> brokenImages = null;

    @BeforeStories
    public void scenarioSetup() {
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(120, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        links = new ArrayList<HtmlLink>();
        brokenImages = new HashedMap<String, Integer>();
    }

    @AfterStories
    public void scenarioDestroy() {
        driver.close();
        System.out.println("afterScenario");
    }

    private void addBrokenImage(String pageUrl) {
        if (brokenImages.containsKey(pageUrl)) {
            brokenImages.put(pageUrl, brokenImages.get(pageUrl) + 1);
        } else brokenImages.put(pageUrl, 1);
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
            System.out.print("LINK ALREADY EXIST ");
            //  htmlLink.print();
            System.out.println(htmlLink.getUrl() + " " + htmlLink.getDepth());
        } else {
            if (htmlLink.getUrl().toLowerCase().startsWith("http")) {
                links.add(htmlLink);
            } else {
 //               System.out.println("NON HTTP LINK");
 //               htmlLink.print();
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
        findBrokenImages();
    }

    @When("I find all links on the site with $depth")
    public void findLinks(@Named("depth") int depth) {

        addLink(new HtmlLink("START PAGE", driver.getCurrentUrl(), driver.getCurrentUrl(), depth, new Point(0, 0)));

        while (true) {
            //TODO: add max iteration
            List<HtmlLink> allunvisitedLinks = getAllUnvisitedLinks();
        //    System.out.println("To check " + allunvisitedLinks.size());

            for (HtmlLink myLink : allunvisitedLinks) {
                List<HtmlLink> allLinksFromPage = getAllLinksFromPage(myLink.getUrl());
                for (HtmlLink link : allLinksFromPage) {
                    if (myLink.getDepth() > 0) {
                        link.setDepth(myLink.getDepth() - 1);
                        addLink(link);
                    }
                }
           //     System.out.println("Checked " + driver.getCurrentUrl());
            }
            setVisited(driver.getCurrentUrl(), true);

            if (allunvisitedLinks.size() == 0) {
                System.out.println("EXIT_Depth: " + depth);
                break;
            }
            depth--;
      //      System.out.println("Depth: " + depth);
        }
    }

    @Given("I am a pending step")
    public void fff1() {
        System.out.println("fff1");
    }

    @When("Show statistic")
    public void showStatistic() {
        System.out.println("Total links:   " + this.links.size());
        System.out.println("Broken images: " + brokenImages.size());
    }

    @Then("I shall be happy")
    public void amIHappy() {
        //TODO:
        // assertEquals(0, 0);
        //  System.out.println("I'm happy");
    }

    @Then("No broken images on all pages")
    public void noBrokenImages() {
        assertEquals(0, brokenImages.size());
    }

    public void findBrokenImages() {
        List<WebElement> imagesList = driver.findElements(By.tagName("img"));
        for (WebElement image : imagesList) {
            CloseableHttpResponse response = null;
            try {
                response = new DefaultHttpClient().execute(new HttpGet(image.getAttribute("src")));
                if (response != null) {
                    if (response.getStatusLine().getStatusCode() != 200) { // Do whatever you want with broken images
                        // System.out.println("ERROR: Broken image on page " + driver.getCurrentUrl());
                        addBrokenImage(driver.getCurrentUrl());
                    }
                }
            } catch (IOException e) {
                System.out.println("ERROR: Can not parse page " + driver.getCurrentUrl() + " for broken images");
                e.printStackTrace();
            }
        }
    }

    @When("I sleep $sec sec")
    public void sleep(@Named("sec") int sec) {
        try {
            Thread.sleep(sec * 1000);
        } catch (InterruptedException e) {
            System.out.println("Exception: Failed to sleep " + sec + "sec");
        }
    }

    public List<HtmlLink> getAllLinksFromPage(String url) {

        System.out.println("Processing " + url);
        openUrl(url);
        List<HtmlLink> list = new ArrayList<HtmlLink>();

        List<WebElement> links = driver.findElements(By.tagName("a"));
        for (WebElement myElement : links) {
            try {
                if (myElement.getText() != "") {
                    list.add(new HtmlLink(myElement.getText(), myElement.getAttribute("href"), driver.getCurrentUrl(), 0, myElement.getLocation()));
                }
            } catch (org.openqa.selenium.StaleElementReferenceException ex) {
                System.out.println("ERROR org.openqa.selenium.StaleElementReferenceException");
                ex.printStackTrace();
            }
        }
        return list;
    }

    private List<HtmlLink> getAllUnvisitedLinks() {
        List<HtmlLink> allUnvisitedLinks = new ArrayList();
        for (HtmlLink myLink : links) {

            if (!myLink.isVisited()) {
                allUnvisitedLinks.add(myLink);
            }
        }
        return allUnvisitedLinks;
    }
}
