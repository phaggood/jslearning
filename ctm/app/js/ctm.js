/**
 * Created with JetBrains WebStorm.
 * User: phaggood
 * Date: 7/15/13
 * Time: 9:37 AM
 * To change this template use File | Settings | File Templates.
 */

var CToolsMobile = angular.module('CToolsMobile', ['ngCookies']).
    config(['$routeProvider', function($routeProvider) {
    $routeProvider
        .when('/', {
            templateUrl: '/index.html'
        })
        .when('/sites/:sessionId', {
            controller: 'SitesController',
            templateUrl: 'partials/sites.html'
        })
        .when('/site/:sessionId/:siteId', {
            controller: 'SitesController',
            templateUrl: 'partials/site.html'
        })
        .when('/tools/:sessionId/:siteId', {
            controller: 'SitesController',
            templateUrl: 'partials/tools.html'
        })
        .when('/tool/:toolId', {
            controller: 'SitesController',
            templateUrl: 'partials/tools.html'
        })
        .when('/login', {
            controller: 'AuthController',
            templateUrl: 'partials/loginForm.html'
        })
        .when('/user/:sessionId/:userId', {
            controller: 'AuthController'
        })
        .when('/logout', {
            controller: 'AuthController'
        })
        .when('/user/:sessionId/:userId', {
            controller: 'AuthController',
            templateUrl: 'partials/user.html'
        })
        .otherwise({redirectTo: '/'})
}]);



