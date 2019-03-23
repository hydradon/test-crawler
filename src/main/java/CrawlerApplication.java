/**
 * https://stackoverflow.com/questions/28354754/is-there-any-way-to-set-the-user-agent-in-phantomjs-using-phantomjsdriver
 * https://stackoverflow.com/questions/18433453/python-selenium-with-phantomjs-click-failed-referenceerror-cantt-find-varia/18433763
 * https://stackoverflow.com/questions/7488872/page-content-is-loaded-with-javascript-and-jsoup-doesnt-see-it
 * https://stackoverflow.com/questions/16316691/parsing-html-page-containing-js-in-java
*/

import java.io.IOException;

/**
 * @author Vu Ngoc Quang
 */
public class CrawlerApplication {

    public static void main(String[] args) throws IOException {

        Crawler c = new Crawler();
        c.getLatestResultsFromCNN("trump", 25);
    }

}
