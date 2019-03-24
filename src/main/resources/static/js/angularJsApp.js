var app = angular.module('app', ['ngSanitize']);

app.controller('jsaController', function($scope, $http, $location, $interval) {
    $scope.listAllTweets = [];

    $scope.userId = "realdonaldtrump";
    $scope.searchKey = "trump";

    $scope.loadTweetsByUserId = function(userId) {
        $scope.userId = userId;
        loadTweetsByUser(userId);
    };

    var loadTweetsByUser = function(userId) {
        // get URL
        var url = $location.absUrl() + "api/twitter?noOfTweets=25&user=" + userId;
        $http.get(url).then(function (response) {
            $scope.getDivAvailable = true;
            $scope.listAllTweets = response.data;
        }, function error(response) {
            $scope.postResultMessage = "Error Status: " +  response.statusText;
        });

    };

	var auto = $interval(function() {
        // get URL
        var url = $location.absUrl() + "api/twitter?noOfTweets=25&user=" + $scope.userId;
        // do gettingcustList
        $http.get(url).then(function (response) {
            $scope.getDivAvailable = true;
            $scope.listAllTweets = response.data;
        }, function error(response) {
            $scope.postResultMessage = "Error Status: " +  response.statusText;
        });
        }, 5000);


    $scope.searchKey = "trump";
    $scope.searchCnnByKeyWord = function(searchKey) {
        $scope.searchKey = searchKey;
        searchCnnByKeyWord(searchKey);
    };

    var searchCnnByKeyWord = function(searchKey) {
        // get URL
        var url = $location.absUrl() + "api/cnnArticles?noOfResults=25&keyWord=" + searchKey;
        $http.get(url).then(function (response) {
            $scope.getDivAvailable = true;
            $scope.listAllTweets = response.data;
        }, function error(response) {
            $scope.postResultMessage = "Error Status: " +  response.statusText;
        });
    };

    $scope.listAllCnnArticles = [];
    var auto = $interval(function() {
            // get URL
        var url = $location.absUrl() + "api/cnnArticles?noOfResults=25&keyWord=" + $scope.searchKey;
        // do gettingcustList
        $http.get(url).then(function (response) {
            $scope.getDivAvailable = true;
            $scope.listAllCnnArticles = response.data;
       }, function error(response) {
            $scope.postResultMessage = "Error Status: " +  response.statusText;
            });
        }, 5000);

});