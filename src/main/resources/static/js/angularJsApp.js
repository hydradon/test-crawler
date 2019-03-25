var app = angular.module('app', ['ngSanitize']);

app.controller('jsaController', function($scope, $http, $location, $interval) {

    var twitterEndpoint = "api/twitter?noOfTweets=25&user=";
    var cnnEndpoint = "api/cnnArticles?noOfResults=25&keyWord=";

    $scope.userId = "realdonaldtrump";
    $scope.searchKey = "trump";

    $scope.listAllTweets = [];

    $scope.loadTweets = function(userId) {
        $scope.userId = userId;
        loadTweetsByUser(userId);
    };

    var loadTweetsByUser = function(userId) {
        if (!userId) {
            console.log("Tweets Id is not given");
            return;
        }
        console.log("Loading Tweets for userId -> " + userId);
        // get URL
        var url = $location.absUrl() + twitterEndpoint + userId;
        $http.get(url).then(function (response) {
            $scope.getDivAvailable = true;
            $scope.listAllTweets = response.data;
        }, function error(response) {
            $scope.postResultMessage = "Error Status: " +  response.statusText;
            console.log("Error caused while loading Tweets for userId " + userId + ", msg -> " + response.statusText);
        });

    };

    $scope.searchCnn = function(searchKey) {
        $scope.searchKey = searchKey;
        searchCnnByKeyWord(searchKey);
    };

    $scope.listAllCnnArticles = [];
    var searchCnnByKeyWord = function(searchKey) {
        if (!searchKey) {
                console.log("CNN search key is not given");
                return;
        }
        console.log("Loading CNN news for search key -> " + searchKey);
        // get URL
        var url = $location.absUrl() + cnnEndpoint + searchKey;
        $http.get(url).then(function (response) {
            $scope.getDivAvailable = true;
            $scope.listAllCnnArticles = response.data;
        }, function error(response) {
            $scope.postResultMessage = "Error Status: " +  response.statusText;
            console.log("Error caused while loading CNN news for searchKey " + searchKey + ", msg -> " + response.statusText);
        });
    };

    var auto = $interval(function() {
        loadTweetsByUser($scope.userId);
        searchCnnByKeyWord($scope.searchKey);
    }, 5000);

});