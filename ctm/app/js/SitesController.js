/**
 * Created with JetBrains WebStorm.
 * User: phaggood
 * Date: 7/15/13
 * Time: 9:27 AM
 * To change this template use File | Settings | File Templates.
 * // http://www.benlesh.com/2013/02/angularjs-creating-service-with-http.html
 */
CToolsMobile.controller('SitesController', function ($scope, $location, $routeParams, $cookies, siteService) {

    var currentSession   = $cookies.sessionId;        // used for each call
    var siteId = $routeParams.siteId

    $scope.selectedSiteId = $routeParams.siteId;

    $scope.sites = siteService.getSites(currentSession);
    $scope.site =  siteService.getSite(currentSession,selectedSiteId);
    $scope.tools = siteService.getTools(currentSession, siteId);

});