# Crawler Application

This project is used to:
 - Get 25 latest tweets from [@realDonaldTrump](https://twitter.com/realDonaldTrump).
 - Get 25 latest news with a specified keyword from [CNN](https://edition.cnn.com/).

## Build

Run `gradlew clean build` to build the project. The build artifacts will be stored in the `build/` directory. Use the `--refresh-dependencies` flag to refresh all cached dependencies.

## Unit Test

Run `gradlew test` to run all Unit tests.

## Verify webpage

The main page is located at [http://localhost:4300/](http://localhost:4300/):


## Endpoints

#### Twitter endpoint `/api/twitter` with parameter: 
   + `user`    (**required**) : the user whose tweets to retrieve.
   + `noOfTweets` (**required**) : number of Tweets to retrieve.
 
#### CNN endpoint `/api/cnnArticles` with parameters:
   + `keyWord`    (**required**) : the keyword to search all articles.
   + `noOfResuls` (**required**) : number of articles to retrieve.
   + `language`   (*optional*)   : language of the retrieved article (default blank = `en`).

## Further help

To get more information, please contact the author.
