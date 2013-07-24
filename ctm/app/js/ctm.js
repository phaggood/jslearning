/**
 * Created with JetBrains WebStorm.
 * User: phaggood
 * Date: 7/15/13
 * Time: 9:37 AM
 * To change this template use File | Settings | File Templates.
 */

var ctmConfig = function($routeProvider) {
    $routeProvider
        .when('/', {
            controller: 'SitesController',
            templateUrl: 'view/main.html'
        })
        .when('/sites', {
            controlller: 'SitesController',
            templateUrl: 'view/sites.html'
        })
        .when('/site/:siteId', {
            controller: 'SitesController',
            templateUrl: 'view/site.html'
        })
        .when('/login', {
            controller: 'AuthController',
            templateUrl: 'view/loginForm'
        })
        .when('/auth/:username/:password', {
            controller: 'AuthController'
        })
        .when('/logout', {
            controller: 'AuthController'
        })
    ; };

var CToolsMobile = angular.module('CToolsMobile', [ 'ngCookies']). config(CToolsMobile);