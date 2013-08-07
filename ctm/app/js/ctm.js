/**
 * Created with JetBrains WebStorm.
 * User: phaggood
 * Date: 7/15/13
 * Time: 9:37 AM
 * To change this template use File | Settings | File Templates.
 */

'use strict';


// Declare app level module which depends on filters, and services
var app;
angular.module('ctm', ['ngCookies'])
    .config(['$routeProvider', function ($routeProvider) {
        $routeProvider.when('/', {templateUrl: 'partials/welcome.html' });
        $routeProvider.when('/login', {templateUrl: 'partials/loginform.html', controller: 'LoginCtl' });
        $routeProvider.when('/login/:usrname/:statMsg', {templateUrl: 'partials/loginform.html', controller: 'LoginCtl' });
        $routeProvider.when('/logout', {templateUrl: 'partials/logout.html', controller: 'LoginCtl' });
        $routeProvider.when('/loginfail', {templateUrl: 'partials/loginfail.html' });
        $routeProvider.when('/sites', {templateUrl: 'partials/sites.html', controller: 'SitesCtl' });
        $routeProvider.when('/site/:siteId', {templateUrl: 'partials/site.html', controller: 'SiteCtl' });
        $routeProvider.when('/tools/:siteId', {templateUrl: 'partials/tools.html', controller: 'ToolsCtl'});
        $routeProvider.when('/tool/:toolId', {templateUrl: 'partials/tool.html', controller: 'ToolCtl' });
        $routeProvider.when('/user/:userId', {templateUrl: 'partials/user.html', controller: 'UserCtl' });
        $routeProvider.when('/sakai-home', {templateUrl: 'partials/home.html' }); //, controller: 'UserCtl' });
        $routeProvider.when('/sakai-home', {templateUrl: 'partials/assignments.html' }); //, controller: 'UserCtl' });
        $routeProvider.when('/sakai-chat', {templateUrl: 'partials/chat.html' , controller: 'ChatCtl' });
        $routeProvider.when('/sakai-home', {templateUrl: 'partials/stats.html' }); //, controller: 'UserCtl' });
        // none of these partials exist yet
        $routeProvider.when('/kaltura', {templateUrl: 'partials/construction.html' }); //, controller: 'UserCtl' });
        $routeProvider.when('/sakai-gradebook', {templateUrl: 'partials/construction.html' , controller: 'GradebookCtl' });
        $routeProvider.when('/sakai-announce', {templateUrl: 'partials/construction.html' }); //, controller: 'UserCtl' });
        $routeProvider.when('/sakai-assignments', {templateUrl: 'partials/construction.html' }); //, controller: 'UserCtl' });
        $routeProvider.when('/sakai-rsc', {templateUrl: 'partials/construction.html' }); //, controller: 'UserCtl' });
        $routeProvider.otherwise({redirectTo: '/'})
    }]);



