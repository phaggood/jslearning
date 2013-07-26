/**
 * Created with JetBrains WebStorm.
 * User: phaggood
 * Date: 7/15/13
 * Time: 9:27 AM
 * To change this template use File | Settings | File Templates.
 * // http://www.benlesh.com/2013/02/angularjs-creating-service-with-http.html
 */
CToolsMobile.controller('SitesController', function ($scope, $location, $routeParams, $cookies, siteService) {

    var currentSession   = $cookies.sessionId;     // used for each call
    var siteId = $routeParams.siteId;              // if siteId is set
    var toolId = $routeParams.toolId;              // if toolId is set

    $scope.sites = siteService.getSites(currentSession);
    $scope.site  = siteService.getSite(currentSession,$routeParams.siteId);
    $scope.tools = siteService.getTools(currentSession, $routeParams.siteId);
    $scope.tool  = siteService.getTool($routeParams.toolId);

});