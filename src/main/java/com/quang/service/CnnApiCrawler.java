package com.quang.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * This class hits the NewsAPI endpoint to retrieve the matching articles.
 *
 * @author Vu Ngoc Quang
 */
@Service
public class CnnApiCrawler {

    private static final Logger LOGGER = LoggerFactory.getLogger(CnnApiCrawler.class);

    private static final String NEWS_API_URL = "https://newsapi.org/v2/everything?sortBy=publishedAt&sources=cnn";

    @Value("${cnn.apiKey}")
    private String apiKey;

    private RestTemplate restTemplate;

    /**
     * Constructor to initialize RestTemplate one time.
     */
    public CnnApiCrawler() {
        this.restTemplate = new RestTemplate();
    }

    /**
     * This method returns a list of tweets as strings from a user.
     *
     * @param keyWord     the Twitter user whose tweets to retrieve.
     * @param noOfResults indicates the number of articles to retrieve.
     * @param language    indicates the language of the articles to retrieve.
     * @return a List of their tweets as String.
     */
    public List<JsonNode> getCnnNews(String keyWord, int noOfResults, String language) {

        LOGGER.info("Retrieving {} latest articles with key word {}...", noOfResults, keyWord);

        List<JsonNode> results = new ArrayList<>();

        String urlString = NEWS_API_URL
                + "&q=" + keyWord
                + "&pageSize=" + noOfResults
                + "&to=" + LocalDate.now()
                + "&language=" + language
                + "&apiKey=" + apiKey;

        ResponseEntity<String> response = restTemplate.getForEntity(urlString, String.class);
        ObjectMapper mapper = new ObjectMapper();
        try {
            JsonNode root = mapper.readTree(response.getBody());
            JsonNode articlesNode = root.get("articles");

            LOGGER.info("{} articles of {} results.", articlesNode.size(), root.get("totalResults"));

            for (int i = 0; i < articlesNode.size(); i++) {
                results.add(articlesNode.get(i));
            }

        } catch (Exception e) {
            LOGGER.error("Error retrieving news from CNN: {}.", e.getMessage());
            return Collections.emptyList();
        }

        return results;
    }
}
