package com.quang.controller;

import com.quang.service.TwitterCrawler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

/**
 * @author Vu Ngoc Quang
 */
@RestController
@RequestMapping("/api")
public class CrawlApplicationRestController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CrawlApplicationRestController.class);

    @Autowired
    TwitterCrawler twitterCrawler;

//    @Autowired
//    private CnnCrawler cnnCrawler;

//    @GetMapping(value = "/cnn")
//    public ResponseEntity getCnnNews(@RequestParam String keyWord,
//                                     @RequestParam int noOfResults) {
//
//        List<Article> results;
//
//        try {
//            results = cnnCrawler.getLatestResultsFromCNN(keyWord, noOfResults);
//        } catch (Exception e) {
//            LOGGER.error("Error retrieving CNN news.");
//            results = Collections.emptyList();
//        }
//
//        return ResponseEntity.ok(results);
//    }

    @GetMapping(value = "/twitter")
    public List<String> getTwitter(@RequestParam String user) {

        List<String> result;
        try {
            result = twitterCrawler.getTweetsFromUser(user);
        } catch (Exception e) {
            LOGGER.error("Error retrieving Tweets from user {}.", user);
            result = Collections.emptyList();
        }

        return result;
    }
}
