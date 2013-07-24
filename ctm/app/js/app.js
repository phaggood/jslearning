'use strict';


// Declare app level module which depends on filters, and services
angular.module('ctoolsMobile', ['ctoolsMobile.filters', 'ctoolsMobile.services', 'ctoolsMobile.directives', 'ctoolsMobile.controllers']).
  config(['$routeProvider', function($routeProvider) {
    $routeProvider.when('/view1', {templateUrl: 'partials/partial1.html', controller: 'SiteController'});
    $routeProvider.when('/view2', {templateUrl: 'partials/partial2.html', controller: 'AuthController'});
    $routeProvider.otherwise({redirectTo: '/view1'});
  }]);
