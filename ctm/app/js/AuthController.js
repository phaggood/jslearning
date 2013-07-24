/**
 * Created with JetBrains WebStorm.
 * User: phaggood
 * Date: 7/16/13
 * Time: 10:22 PM
 * To change this template use File | Settings | File Templates.
 */
CToolsMobile.controller('AuthController', function ($scope, $location, $routeParams, $cookies, authService) {

    var currentSession = $cookies.sessionId;        // used for each call

    $scope.user = authService.getUser($cookies.sessionId);

    $scope.doLogin = function () {
        var uname = $routeParams.username;
        var pwd = $routeParams.password;
        var auth = authService.authenticate(uname, pwd);
        if (auth.data.sessionId != undefined) {
            $cookies.sessionId = auth.sessionId;
            $location.path('#/sites/' + $cookies.sessionId);
        } else {
            $location.path('#/login/' + $routeParams.username + '/' + "Login failed");
        }
        return false;
    }

    $scope.doLogout = function () {
            $cookies.sessionId = null;
            $location.path('#/login/' + $routeParams.username + '/' + "Login failed");
            // invalidate session
            return false;
     }



     $scope.validateLogin = function (username, password) {
            return (!username && !password);
     }
};
