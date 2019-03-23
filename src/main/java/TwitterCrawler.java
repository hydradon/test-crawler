import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

import java.util.List;

/**
 * @author Vu Ngoc Quang
 */
public class TwitterCrawler {

    private static final Logger logger = LoggerFactory.getLogger(TwitterCrawler.class);
    private Twitter twitter;

    public TwitterCrawler() {
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
                .setOAuthConsumerKey("eVnSMiKZnlSoXGtVUU81UkkFn")
                .setOAuthConsumerSecret("2NCV6efJMMa7d9CO5Q5M5yQjvL4mfKL5U2zyZIXAVZktM4RnTu")
                .setOAuthAccessToken("92231432-VvX3K4dNCx4ZQg6que7HgzUYeA3Onnkye4TsH8RHr")
                .setOAuthAccessTokenSecret("Z3bVWAoSn4YufG4O73J63Kun33rd9jR26S95cPQwy6vxp");
        TwitterFactory tf = new TwitterFactory(cb.build());
        twitter = tf.getInstance();
    }

    public  void getTweetsFromUser(String user, int numOfTweets) {

        try {
            List<Status> statuses = twitter.getUserTimeline(user);

            int tweetCount = 1;
            for (Status status : statuses) {
                if(tweetCount++ < numOfTweets)
                    logger.info("Tweet {} of user {} : {}:", tweetCount, user, status.getText());
            }

        } catch (TwitterException te) {
            logger.error("Failed to retrieve tweets for user: {}, failure reason {} " + user, te.getMessage());
        }
    }

}
