package com.quang.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.quang.service.CnnApiCrawler;
import com.quang.service.TwitterCrawler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

/**
 * @author Vu Ngoc Quang
 */
@RestController
@RequestMapping("/api")
public class CrawlerRestController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CrawlerRestController.class);

    @Autowired
    TwitterCrawler twitterCrawler;

    @Autowired
    CnnApiCrawler cnnApiCrawler;

    /**
     * The method return to a GET request a list of tweets as Strings from the user.
     * @param user the Twitter use whose tweets to retrieve.
     * @return a List of String that are their tweets.
     */
    @RequestMapping(value = "twitter", method = RequestMethod.GET)
    public List<String> getTwitter(@RequestParam String user) {

        LOGGER.info("Twitter CrawlRestApi hit with parameter: {}.", user);

        try {
            return twitterCrawler.getTweetsFromUser(user);
        } catch (Exception e) {
            LOGGER.error("Error retrieving Tweets from user {}.", user);
            return Collections.emptyList();
        }
    }

    @RequestMapping(value = "cnnArticles", method = RequestMethod.GET)
    public List<JsonNode> getCnnArticles(@RequestParam String keyWord,
                                         @RequestParam int noOfResults,
                                         @RequestParam(defaultValue = "en") String language) {

        LOGGER.info("CNN CrawlRestApi hit with parameter: {}, {}, {}.", keyWord, noOfResults, language);

        try {
            return cnnApiCrawler.getCnnNews(keyWord, noOfResults, language);
        } catch (Exception e) {
            LOGGER.error("Error retrieving CNN news.");
            return Collections.emptyList();
        }
    }

    //    @Autowired
//    CnnWebCrawler cnnWebCrawler;
//
//    @RequestMapping(value = "cnn1", method = RequestMethod.GET)
//    public List<Article> getCnnNews(@RequestParam String keyWord,
//                                    @RequestParam int noOfResults) {
//        try {
//            return cnnWebCrawler.getLatestResultsFromCNN(keyWord, noOfResults);
//        } catch (Exception e) {
//            LOGGER.error("Error retrieving CNN news.");
//            return Collections.emptyList();
//        }
//    }
}
