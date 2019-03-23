var app = angular.module('app', []);

//#######################
//JSA CONTROLLER
//#######################

app.controller('jsaController', function($scope, $http, $location, $interval) {
    $scope.listAllTweets = [];

    //$scope.reload = function () {
	var auto = $interval(function() {
        // get URL
        var url = $location.absUrl() + "api/twitter?user=realdonaldtrump";
        // do gettingcustList
        $http.get(url).then(function (response) {
            $scope.getDivAvailable = true;
            $scope.listAllTweets = response.data;
        }, function error(response) {
            $scope.postResultMessage = "Error Status: " +  response.statusText;
        });

    }, 5000);
    //$scope.reload();
});