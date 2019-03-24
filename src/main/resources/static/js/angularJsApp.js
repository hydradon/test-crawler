var app = angular.module('app', []);

app.controller('jsaController', function($scope, $http, $location, $interval) {
    $scope.listAllTweets = [];

	var auto = $interval(function() {
        // get URL
        var url = $location.absUrl() + "api/twitter?user=realdonaldtrump&noOfTweets=25";
        // do gettingcustList
        $http.get(url).then(function (response) {
            $scope.getDivAvailable = true;
            $scope.listAllTweets = response.data;
        }, function error(response) {
            $scope.postResultMessage = "Error Status: " +  response.statusText;
        });

    }, 5000);


    $scope.listAllCnnArticles = [];
    var auto = $interval(function() {
            // get URL
            var url = $location.absUrl() + "api/cnnArticles?keyWord=trump&noOfResults=25";
            // do gettingcustList
            $http.get(url).then(function (response) {
                $scope.getDivAvailable = true;
                $scope.listAllCnnArticles = response.data;
            }, function error(response) {
                $scope.postResultMessage = "Error Status: " +  response.statusText;
            });

        }, 5000);

});