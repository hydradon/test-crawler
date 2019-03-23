package com.quang.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Vu Ngoc Quang
 */
@Service
public class TwitterCrawler {

    @Value("${twitter.oauth.consumerKey}")
    private String consumerKey;

    @Value("${twitter.oauth.consumerSecret}")
    private String consumerSecret;

    @Value("${twitter.oauth.accessToken}")
    private String accessToken;

    @Value("${twitter.oauth.accessTokenSecret}")
    private String accessTokenSecret;

    private static final Logger logger = LoggerFactory.getLogger(TwitterCrawler.class);

    public List<String> getTweetsFromUser(String user) {

        List<String> result = new ArrayList<>();

        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
                .setOAuthConsumerKey(consumerKey)
                .setOAuthConsumerSecret(consumerSecret)
                .setOAuthAccessToken(accessToken)
                .setOAuthAccessTokenSecret(accessTokenSecret);

        TwitterFactory tf = new TwitterFactory(cb.build());
        Twitter twitter = tf.getInstance();

        try {
            List<Status> statuses = twitter.getUserTimeline(user);

            int tweetCount = 1;
            for (Status status : statuses) {
                result.add(status.getText());
                logger.info("Tweet {} of user {} : {}:", tweetCount++, user, status.getText());
            }

        } catch (TwitterException te) {
            logger.error("Failed to retrieve tweets for user: {}, failure reason {} " + user, te.getMessage());
            return Collections.emptyList();
        }

        return result;
    }
}
