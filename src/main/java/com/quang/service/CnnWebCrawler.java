/**
 * https://stackoverflow.com/questions/28354754/is-there-any-way-to-set-the-user-agent-in-phantomjs-using-phantomjsdriver
 * https://stackoverflow.com/questions/18433453/python-selenium-with-phantomjs-click-failed-referenceerror-cantt-find-varia/18433763
 * https://stackoverflow.com/questions/7488872/page-content-is-loaded-with-javascript-and-jsoup-doesnt-see-it
 * https://stackoverflow.com/questions/16316691/parsing-html-page-containing-js-in-java
 */

package com.quang.service;

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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * This class will hit CNN's search page with the required keyword and number of results and parse the resulting HTML.
 *
 * @author Vu Ngoc Quang
 */
public class CnnWebCrawler {

    private static final Logger LOGGER = LoggerFactory.getLogger(CnnWebCrawler.class);

    private static final String PHANTOMJS_PATH = System.getProperty("user.dir") + "\\src\\main\\resources\\phantomjs.exe";
    private static final String TEMP_FILE = System.getProperty("user.dir") + "\\src\\main\\resources\\temp.html";
    private static final String CNN_TOP_LATEST_RESULTS = "https://edition.cnn.com/search/?type=article";
    private static final String USER_AGENT = "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:25.0) Gecko/20160101 Firefox/66.0";

    private WebDriver driver;
    private static DateTimeFormatter formatter;

    /**
     * Constructor.
     * <p>
     * Initialize the PhantomJSDriver with the phantomJSdriver path, user agent, SSL cert config.
     */
    public CnnWebCrawler() {
        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setJavascriptEnabled(true);
        caps.setCapability(PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY, PHANTOMJS_PATH);
        caps.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
        System.setProperty("phantomjs.page.settings.userAgent", USER_AGENT);

        driver = new PhantomJSDriver(caps);
        formatter = DateTimeFormatter.ofPattern("MMM dd, yyyy");
    }

    /**
     * This method hits CNN search page with the required keyword and number of result required and parse the
     * resulting HTML.
     *
     * @param keyword      search query keyword.
     * @param numOfResults number of result required.
     * @return a list of Article objects that contain information of the articles.
     */
    public List<Article> getLatestResultsFromCNN(String keyword, int numOfResults) {

        LOGGER.info("Retrieving top {} news from CNN with keyword {}...", numOfResults, keyword);

        List<Article> resultList = new ArrayList<>();

        String queryParam = "&size=" + numOfResults + "&q=" + keyword;

        try {
            driver.get(CNN_TOP_LATEST_RESULTS + queryParam);

            LOGGER.info("Page title is {}.", driver.getTitle());

            // Wait for the page to load all results, timeout after 10 seconds.
            // WebElement myDynamicElement = (new WebDriverWait(driver, 10))
            //         .until(ExpectedConditions.presenceOfElementLocated(By.className("cnn-search__results-count")));

            String loadedPage = driver.getPageSource();
            driver.quit();

            Document document = Jsoup.parse(loadedPage);
            FileUtils.write(new File(TEMP_FILE), document.toString(), "UTF-8");

            Elements results = document.select("div.cnn-search__result");

            LOGGER.info("Number of result: {}.", results.size());

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
        } catch (Exception e) {
            LOGGER.error("Failed to retrieve CNN news for keyword: {}, failure reason {} " + keyword, e.getMessage());
            return Collections.emptyList();
        }

        return resultList;
    }
}
