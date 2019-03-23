import org.apache.commons.io.FileUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Vu Ngoc Quang
 */
public class Crawler {

    private static final String PHANTOM_JS_PATH = System.getProperty("user.dir") + "\\src\\main\\resources\\phantomjs.exe";
    private static final String TEMP_FILE = System.getProperty("user.dir") + "\\src\\main\\resources\\temp.html";
    private static final String CNN_TOP_LATEST_RESULTS = "https://edition.cnn.com/search/?type=article";

    private static final String USER_AGENT = "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:25.0) Gecko/20160101 Firefox/66.0";

    private WebDriver driver;
    private static DateTimeFormatter formatter;

    public Crawler() {
        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setJavascriptEnabled(true);
        caps.setCapability(PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY, PHANTOM_JS_PATH);
        caps.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
        System.setProperty("phantomjs.page.settings.userAgent", USER_AGENT);

        driver = new PhantomJSDriver(caps);
        formatter = DateTimeFormatter.ofPattern("MMM dd, yyyy");
    }

    public List<Article> getLatestResultsFromCNN(String keyword, int numOfResults) throws IOException {

        List<Article> resultList = new ArrayList<>();

        String queryParam = "&size=" + numOfResults + "&q=" + keyword;
        driver.get(CNN_TOP_LATEST_RESULTS + queryParam);

        System.out.println("Page title is: " + driver.getTitle());

//        Wait for the page to load, timeout after 10 seconds
//        WebElement myDynamicElement = (new WebDriverWait(driver, 10))
//                .until(ExpectedConditions.presenceOfElementLocated(By.className("cnn-search__results-count")));

        String loadedPage = driver.getPageSource();
        driver.quit();

        Document document = Jsoup.parse(loadedPage);
        FileUtils.writeStringToFile(new File(TEMP_FILE), document.toString());

        Elements results = document.select("div.cnn-search__result");
        System.out.println("Number of result: " + results.size());

        for (Element result : results) {
            Elements headlineElement = result.select(".cnn-search__result-headline").select("a");
            String link = headlineElement.attr("href");
            String headline = headlineElement.text();

            Element publishedDateElement = result.select(".cnn-search__result-publish-date").select("span").last();
            LocalDate publishedDate = LocalDate.parse(publishedDateElement.text(), formatter);

            Elements bodyElement = result.select(".cnn-search__result-body");
            String body = bodyElement.text();

            resultList.add(new Article(link, headline, publishedDate, body));
        }

        return resultList;
    }
}
