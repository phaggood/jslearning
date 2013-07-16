/**
 * Created with JetBrains WebStorm.
 * User: phaggood
 * Date: 7/15/13
 * Time: 9:27 AM
 * To change this template use File | Settings | File Templates.
 */
CToolsMobile.controller('SitesController', function ($scope, $location, $routeParams, SiteModel) {
    var sites = SiteModel.getSites();  // ajax.getSites()

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