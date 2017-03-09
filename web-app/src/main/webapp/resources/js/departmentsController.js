var app = angular.module('departments', []);
app.controller('departmentsCtrl', function($scope, $http) {
    $http.get("http://localhost:8080/server/departments").then(function (response) {
        $scope.data = response.data;
    });
});
