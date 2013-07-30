/**
 * Created with JetBrains WebStorm.
 * User: phaggood
 * Date: 7/15/13
 * Time: 9:37 AM
 * To change this template use File | Settings | File Templates.
 */

'use strict';


// Declare app level module which depends on filters, and services
var ctm = angular.module('ctm', ['ngCookies'])
  .config(['$routeProvider', function($routeProvider) {
    $routeProvider.when('/', {templateUrl: 'partials/welcome.html' });
    $routeProvider.when('/login', {templateUrl: 'partials/loginform.html', controller:'AuthController' });
    $routeProvider.when('/logout', {templateUrl: 'partials/logout.html', controller:'AuthController' });
    $routeProvider.when('/sites/:sessionId', {templateUrl: 'partials/sites.html', controller:'SitesController' });
    $routeProvider.when('/site/:sessionId/:siteId', {templateUrl: 'partials/site.html', controller:'SitesController' });
    $routeProvider.when('/tools/:sessionId/:siteId', {templateUrl: 'partials/tools.html', controller:'SitesController'});
    $routeProvider.when('/tool/:toolId', {templateUrl: 'partials/tool.html', controller:'SitesController' });
    $routeProvider.when('/user/:sessionId/:userId', {templateUrl: 'partials/user.html', controller:'AuthController' });
    $routeProvider.when('/tool/:toolId', {templateUrl: 'partials/tool.html', controller:'SitesController' });
    $routeProvider.otherwise({redirectTo: '/'})
}]);



