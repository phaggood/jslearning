/**
 * Created with JetBrains WebStorm.
 * User: phaggood
 * Date: 7/15/13
 * Time: 9:27 AM
 * To change this template use File | Settings | File Templates.
 */
CToolsMobile.controller('SitesController', function ($scope, $location, $routeParams, $cookies, SiteModel) {
    var sites = [site1, site2, site3, site4, site5] ;   // ajax.getSites()
    var currentSession   = $cookies.sessionId;        // used for each call

    $scope.sites = sites;
    $scope.selectedSiteId = $routeParams.siteId;

    $scope.onLogin = function(username, password) {
        var auth = SiteModel.auth(username, password);
        if (auth) {
            $location.path('#/sites/' + $routeParams.userId);
        } else {
            $location.path('#/login/' + $routeParams.usernam + '/' + "Login failed");
        }
    };

    $scope.validateLogin = function (username, password) {
        return !username && !password;
    }
};