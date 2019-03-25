package com.quang.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.quang.service.CnnApiCrawler;
import com.quang.service.CnnWebCrawler;
import com.quang.service.TwitterCrawler;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


/**
 * @author Vu Ngoc Quang
 */
@RunWith(SpringRunner.class)
@WebMvcTest(CrawlerRestController.class)
public class CrawlerRestControllerTests {

    @Autowired
    private CrawlerRestController target;

    @MockBean
    private TwitterCrawler twitterCrawler;

    @MockBean
    private CnnApiCrawler cnnApiCrawler;

//    @MockBean
//    private CnnWebCrawler cnnWebCrawler;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Value("classpath:sample.json")
    Resource testData;

    @Test
    public void testGetTwitter_GivenTrump_ReturnListLastPost() throws Exception {
        String userId = "trump";
        int noOfTweets = 5;

        List<String> results = Arrays.asList("Tweet 1", "Tweet 2", "Tweet 3", "Tweet 4", "Tweet 5");
        Mockito.when(twitterCrawler.getTweetsFromUser(userId, noOfTweets)).thenReturn(results);

        mockMvc.perform(get("/api/twitter")
                .param("user", userId)
                .param("noOfTweets", noOfTweets + "")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(results)));
    }

    @Test
    public void testGetTwitter_GivenTrump_ReturnEmptyList() throws Exception {
        String userId = "trump";
        int noOfTweets = 3;
        Mockito.when(twitterCrawler.getTweetsFromUser(userId, noOfTweets))
                .thenThrow(new RuntimeException("Error retrieved tweets"));

        mockMvc.perform(get("/api/twitter")
                .param("user", userId)
                .param("noOfTweets", noOfTweets + "")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("[]"));
    }

    @Test
    public void testGetCnnArticles_GivenTrump_ReturnListOfJsonNodes() throws Exception {
        List<JsonNode> jsonNodes = objectMapper.readValue(testData.getFile(), List.class);

        String key = "trump";
        String language = "en";
        int num = 25;

        Mockito.when(cnnApiCrawler.getCnnNews(key, num, language)).thenReturn(jsonNodes);

        mockMvc.perform(get("/api/cnnArticles/")
                .param("keyWord", key)
                .param("noOfResults", num + "")
                .param("language", language)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(jsonNodes)));
    }

    @Test
    public void testGetCnnArticles_GivenTrump_ReturnEmptyList() throws Exception {
        List<JsonNode> jsonNodes = objectMapper.readValue(testData.getFile(), List.class);

        String key = "trump";
        String language = "en";
        int num = 25;

        Mockito.when(cnnApiCrawler.getCnnNews(key, num, language))
                .thenThrow(new RuntimeException("Error retrieving CNN articles."));

        mockMvc.perform(get("/api/cnnArticles/")
                .param("keyWord", key)
                .param("noOfResults", num + "")
                .param("language", language)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("[]"));

    }
}