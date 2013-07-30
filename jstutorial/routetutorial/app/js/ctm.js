/**
 * Created with JetBrains WebStorm.
 * User: phaggood
 * Date: 7/15/13
 * Time: 9:37 AM
 * To change this template use File | Settings | File Templates.
 */

angular.module('myApp', [$routeProvider, $locationProvder]);

myApp.config( function $routeProvider, $locationProvder) {
    $locationProvder.html5Mode(true);

    $routeProvider.when('/index',   {templateURL: 'index.html'});
    $routeProvider.when('/welcome', {templateURL: 'partials/welcome.html'});
    $routeProvider.when('/route1',  {templateURL: 'partials/route1.html'});
    $routeProvider.when('/route2',  {templateURL: 'partials/route2.html'});
    $routeProvider.when('/route3',  {templateURL: 'partials/route3.html'});
    $routeProvider.when('/route4',  {templateURL: 'partials/route4.html' });
    $routeProvider.when('/route5',  {templateURL: 'partials/route5.html' });
    //$routeProvider.otherwise({ redirectTo: '/welcome' });
};

