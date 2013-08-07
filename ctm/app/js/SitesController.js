/**
 * Created with JetBrains WebStorm.
 * User: phaggood
 * Date: 7/15/13
 * Time: 9:27 AM
 * To change this template use File | Settings | File Templates.
 * // http://www.benlesh.com/2013/02/angularjs-creating-service-with-http.html
 * http://docs.angularjs.org/guide/dev_guide.mvc.understanding_controller
 */
app.controller('SitesCtl', function ($scope, $location, $routeParams, $cookies, siteService, authService) {
    var currentSession   = $cookies.sessionId;     // used for each call
    var currentUser = authService.getSessionUser(currentSession);
    $scope.sites =  siteService.getSites(currentSession);
    $scope.currentuser = currentUser;
    var oldVal = currentUser;
    $window.scope = $scope
    $scope.$watch(authService.getSessionUser, function(currentUser, oldVal, scope) {
        if (currentUser && currentUser !== oldVal) {
            $scope.currentuser = currentUser;
        }
    });

});

app.controller('SiteCtl', function ($scope, $location, $routeParams, $cookies, siteService) {

    var currentSession   = $cookies.sessionId;     // used for each call
    var siteId = $routeParams.siteId;              // if siteId is set

    // site.html
    $scope.site  =
        siteService.getSite(currentSession,$routeParams.siteId);

});

app.controller('ToolsCtl', function ($scope, $location, $routeParams, $cookies, siteService) {

    var currentSession   = $cookies.sessionId;     // used for each call
    var siteId = $routeParams.siteId;              // if siteId is set

    // tools.html
    $scope.tools =  siteService.getTools(currentSession, $routeParams.siteId);

});

app.controller('ToolCtl', function ($scope, $location, $routeParams, $cookies, siteService) {

    var currentSession   = $cookies.sessionId;     // used for each call
    var toolId = $routeParams.toolId;              // if toolId is set

    // tool.html
    $scope.tool  = siteService.getTool($routeParams.toolId);
});