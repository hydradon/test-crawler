package com.quang.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * @author Vu Ngoc Quang
 */
@RunWith(SpringRunner.class)
public class CnnApiCrawlerTests {

    @MockBean
    private RestTemplate restTemplate;

    @Autowired
    private CnnApiCrawler cnnApiCrawler;

    @TestConfiguration
    static class CnnApiCrawlerTestConfig {

        @Bean
        public CnnApiCrawler cnnApiCrawler() {
            return new CnnApiCrawler();
        }
    }

    @Test
    public void testGetCnnNews_GivenTrump_returnJsonNodes() {
        ReflectionTestUtils.setField(cnnApiCrawler, "apiKey", "testKey");
        ReflectionTestUtils.setField(cnnApiCrawler, "restTemplate", restTemplate);

        String json = readSampleJson();

        String key = "trump";
        String language = "en";
        int num = 25;

        Mockito.when(restTemplate.getForEntity(Mockito.anyString(), Mockito.any()))
                .thenReturn(new ResponseEntity<>(json, HttpStatus.OK));

        Assert.assertTrue(this.cnnApiCrawler.getCnnNews(key, 25, language).size() == 25);
    }

    private String readSampleJson() {
        try {
            InputStream inputStream = CnnApiCrawler.class.getResourceAsStream("/sampleNewsApiResponse.json");
            StringBuilder stringBuilder = new StringBuilder();

            try(BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
                String line;
                while ((line = br.readLine()) != null) {
                    stringBuilder.append(line).append("\n");
                }
            }
            return stringBuilder.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
}