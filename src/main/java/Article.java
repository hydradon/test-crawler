import java.time.LocalDate;

/**
 * @author Vu Ngoc Quang
 */
public class Article {

    private String link;
    private String headline;
    private LocalDate publishedDate;
    private String body;

    public Article(String link, String headline, LocalDate publishedDate, String body) {
        this.link = link;
        this.headline = headline;
        this.publishedDate = publishedDate;
        this.body = body;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getHeadline() {
        return headline;
    }

    public void setHeadline(String headline) {
        this.headline = headline;
    }

    public LocalDate getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(LocalDate publishedDate) {
        this.publishedDate = publishedDate;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String toString() {
        return "Link: " + this.link
                + "\nHeadline: " + this.headline
                + "\nPublished Date: " + this.publishedDate.toString()
                + "\nBody: " + this.body;
    }

}
