package com.quang.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Vu Ngoc Quang
 */
@Service
public class TwitterCrawler {

    private static final Logger LOGGER = LoggerFactory.getLogger(TwitterCrawler.class);

    private Twitter twitter;

    /**
     * Constructor that initialize the class with necessary tokens.
     *
     * @param consumerKey       consumerKey
     * @param consumerSecret    consumerSecret
     * @param accessToken       accessToken
     * @param accessTokenSecret accessTokenSecret
     */
    public TwitterCrawler(@Value("${twitter.oauth.consumerKey}") String consumerKey,
                          @Value("${twitter.oauth.consumerSecret}") String consumerSecret,
                          @Value("${twitter.oauth.accessToken}") String accessToken,
                          @Value("${twitter.oauth.accessTokenSecret}") String accessTokenSecret) {

        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
                .setOAuthConsumerKey(consumerKey)
                .setOAuthConsumerSecret(consumerSecret)
                .setOAuthAccessToken(accessToken)
                .setOAuthAccessTokenSecret(accessTokenSecret)
                .setTweetModeExtended(true);

        TwitterFactory tf = new TwitterFactory(cb.build());
        twitter = tf.getInstance();
    }

    /**
     * This method returns a list of tweets as strings from a user.
     *
     * @param user       the Twitter user whose tweets to retrieve.
     * @param noOfTweets number of tweets from user to retrieve.
     * @return a List of their tweets as String.
     */
    public List<String> getTweetsFromUser(String user, int noOfTweets) {

        LOGGER.info("Retrieving {} tweets from user {}:", noOfTweets, user);

        List<String> result = new ArrayList<>();

        try {
            List<Status> statuses = twitter.getUserTimeline(user, new Paging(1, noOfTweets));
            for (Status status : statuses) {
                result.add(status.getText());
            }
        } catch (TwitterException te) {
            LOGGER.error("Failed to retrieve tweets for user: {}, failure reason {} " + user, te.getMessage());
            return Collections.emptyList();
        }
        return result;
    }
}
