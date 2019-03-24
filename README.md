# Twitter and CNN Crawler Application

This project is used to:
 - Get 25 latest tweets from any Twitter user (Default search: [@realDonaldTrump](https://twitter.com/realDonaldTrump)).
 - Get 25 latest news with a keyword from [CNN](https://edition.cnn.com/) (Default: "trump").
 
The application will refresh every 5 seconds to check if there is any new Tweet or new article matching the username or search keyword.

## Build

Run `gradlew clean build` to build the project. The build artifacts will be stored in the `build/` directory. 
Use the `--refresh-dependencies` flag to refresh all cached dependencies.

## Run the application

Use the below command to start the `jar`: (`PATH` environment variable must be set. Please see [here](https://www.javatpoint.com/how-to-set-path-in-java))

```jshelllanguage
java -jar <path_to_jar>
```

The main page is located at [http://localhost:4300/](http://localhost:4300/) 


## Endpoints

#### Twitter endpoint `/api/twitter` with parameter: 
   + `user`    (**required**) : the user whose tweets to retrieve.
   + `noOfTweets` (**required**) : number of Tweets to retrieve.
   
   Sample call: 
   > [http://localhost:4300/api/twitter?user=realdonaldtrump&noOfTweets=3](http://localhost:4300/api/twitter?user=realdonaldtrump&noOfTweets=3)
   

   Sample response:
   ```
   [
    "Tweet 1",
    "Tweet 2",
    "Tweet 3"
   ]
   ```
  
   
 
#### CNN endpoint `/api/cnnArticles` with parameters:
   + `keyWord`    (**required**) : the keyword to search all articles.
   + `noOfResuls` (**required**) : number of articles to retrieve.
   + `language`   (*optional*)   : language of the retrieved article (default blank = `en`).
   
   Sample call:
   > [http://localhost:4300/api/cnnArticles?keyWord=trump&noOfResults=3](http://localhost:4300/api/cnnArticles?keyWord=trump&noOfResults=3)
   
   Sample response:
   ```
   [
     {
       "source": {
         "id": null,
         "name": "Timesofisrael.com"
       },
       "author": null,
       "title": "Barr prepares summary of Mueller’s key findings as Democrats demand full access",
       "description": "First details expected Sunday from long-gestating report on Russia's intervention in 2016 elections, as attorney general mulls how much Congress and public will get to see",
       "url": "https://www.timesofisrael.com/barr-prepares-summary-of-muellers-key-findings-as-democrats-demand-full-access/",
       "urlToImage": "https://static.timesofisrael.com/www/uploads/2019/03/AP_19082844038221-e1553410058842-1024x640.jpg",
       "publishedAt": "2019-03-24T06:47:52Z",
       "content": "WASHINGTON (AP) Attorney General William Barr scoured special counsel Robert Mueller’s confidential report on the Russia investigation with his advisers, deciding how much Congress and the American public will get to see about the two-year probe into US Presi… [+7026 chars]"
     },
     {
       "source": {
         "id": null,
         "name": "Smh.com.au"
       },
       "author": "David Wroe",
       "title": "Fall of IS in Syria a blow to global jihadist push but risks remain",
       "description": "The final victory also crystallises the vexed question of what to do with the thousands of captured fighters and their families who are now in Kurdish-run camps.",
       "url": "https://www.smh.com.au/world/middle-east/fall-of-is-in-syria-a-blow-to-global-jihadist-push-but-risks-remain-20190324-p5173r.html",
       "urlToImage": "https://static.ffx.io/images/$zoom_1.6479276094276094,$multiply_1,$ratio_1.776846,$width_1059,$x_909,$y_1314/t_crop_custom/w_800/q_86,f_auto/0711bd0feeda8e816edf753873d71a2055b3aad8",
       "publishedAt": "2019-03-24T06:43:03Z",
       "content": "But western leaders, while hailing the announcement by Kurdish forces, warned that IS remained a danger.\r\nWe will remain vigilant until it is finally defeated wherever it operates, US President Donald Trump said.\r\nIslamist extremist groups from the Philippine… [+5371 chars]"
     },
     {
       "source": {
         "id": null,
         "name": "Fark.com"
       },
       "author": null,
       "title": "GOP cancel annual fundraiser after learning protesters would be showing up dressed as cows and sporting cowbells to mock guest of honor Devin Nunes. COWards [Awkward]",
       "description": "GOP cancel annual fundraiser after learning protesters would be showing up dressed as cows and sporting cowbells to mock guest of honor Devin Nunes. COWards",
       "url": "https://www.fark.com/comments/10361728/GOP-cancel-annual-fundraiser-after-learning-protesters-would-be-showing-up-dressed-as-cows-sporting-cowbells-to-mock-guest-of-honor-Devin-Nunes-COWards",
       "urlToImage": "https://img.fark.net/images/cache/orig/K/KF/fark_KFbG9BmdxGl-iidDM9boxS3xYL4.jpg?t=UfsJCgyXXqFUQaY_69dPJA&f=1554091200",
       "publishedAt": "2019-03-24T06:39:59Z",
       "content": "remember last week when trump was implying that if the Mueller report didn't go his way then trumpers would riot, rape, loot and murder with abandon?  the people he was talking about are folks like Nunes.all talk, no action.  the big bad super tough 'willing … [+415 chars]"
     }
   ]
   ```
   
      

## Further help

To get more information, please contact the author.
