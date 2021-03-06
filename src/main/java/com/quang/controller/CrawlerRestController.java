package com.quang.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.quang.service.Article;
import com.quang.service.CnnApiCrawler;
import com.quang.service.CnnWebCrawler;
import com.quang.service.TwitterCrawler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

/**
 * Main controller that handles GET request from front end.
 *
 * @author Vu Ngoc Quang
 */
@Slf4j
@RestController
@RequestMapping("/api")
public class CrawlerRestController {

    @Autowired
    private TwitterCrawler twitterCrawler;
    
    @Autowired
    private CnnApiCrawler cnnApiCrawler;


    /**
     * This REST endpoint returns to a GET request a list of tweets as Strings from the user.
     *
     * @param user       the Twitter use whose tweets to retrieve.
     * @param noOfTweets number of Tweets to retrieve.
     * @return a List of String that are their tweets.
     */
    @RequestMapping(value = "twitter", method = RequestMethod.GET)
    public List<String> getTwitter(@RequestParam String user,
                                   @RequestParam int noOfTweets) {

        log.info("Twitter CrawlRestApi hit with parameter: {}, {}.", user, noOfTweets);

        try {
            return twitterCrawler.getTweetsFromUser(user, noOfTweets);
        } catch (Exception e) {
            log.error("Error retrieving Tweets from user: " + user, e);
            return Collections.emptyList();
        }
    }

    /**
     * This REST endpoint returns a GET request a list of CNN articles.
     *
     * @param keyWord     the specific keyword the articles contains.
     * @param noOfResults the number of articles to retrieve.
     * @param language    the language of the articles.
     * @return a List of Json objects.
     */
    @RequestMapping(value = "cnnArticles", method = RequestMethod.GET)
    public List<JsonNode> getCnnArticles(@RequestParam String keyWord,
                                         @RequestParam int noOfResults,
                                         @RequestParam(defaultValue = "en") String language) {

        log.info("CNN CrawlRestApi hit with parameter: {}, {}, {}.", keyWord, noOfResults, language);

        try {
            return cnnApiCrawler.getCnnNews(keyWord, noOfResults, language);
        } catch (Exception e) {
            log.error("Error retrieving CNN news.", e);
            return Collections.emptyList();
        }
    }


//    @Autowired
    private CnnWebCrawler cnnWebCrawler;

    /**
     * @deprecated
     * Rest controller to use CnnWebCrawler to retrieve results.
     *
     * @param keyWord     keyWord to search in the CNN search's texbox
     * @param noOfResults number of Articles to retrieve.
     * @return a List of Article objects that contain the news article.
     */
//    @RequestMapping(value = "cnn1", method = RequestMethod.GET)
    public List<Article> getCnnNews(@RequestParam String keyWord,
                                    @RequestParam int noOfResults) {
        try {
            return cnnWebCrawler.getLatestResultsFromCNN(keyWord, noOfResults);
        } catch (Exception e) {
            log.error("Error retrieving CNN news.", e);
            return Collections.emptyList();
        }
    }
}
