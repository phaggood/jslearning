'use strict';


// Declare app level module which depends on filters, and services
angular.module('myApp', ['myApp.filters', 'myApp.services', 'myApp.directives', 'myApp.controllers']).
  config(['$routeProvider', function($routeProvider) {
    $routeProvider.when('/view1', {templateUrl: 'partials/partial1.html'});
    $routeProvider.when('/view2', {templateUrl: 'partials/partial2.html'});
    $routeProvider.when('/route1', {templateUrl: 'partials/route1.html'});
    $routeProvider.when('/route2', {templateUrl: 'partials/route2.html'});
    $routeProvider.when('/route3', {templateUrl: 'partials/route3.html'});
    $routeProvider.when('/route4', {templateUrl: 'partials/route4.html'});
    $routeProvider.otherwise({redirectTo: '/view1'});
  }]);
