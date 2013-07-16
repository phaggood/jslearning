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
            templateUrl: 'view/sites.html'
        })
        .when('/site/:siteId', {
            controller: 'SitesController',
            templateUrl: 'view/site.html'
        })
        .when('/login/:username/:password', {
            controller: 'AuthController',
            templateUrl: 'view/login.html'
        })
    ; };
var CtoolsMobile = angular.module('CToolsMobile', []).
config(ctmConfig);