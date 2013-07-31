/**
 * Created with JetBrains WebStorm.
 * User: phaggood
 * Date: 7/16/13
 * Time: 10:22 PM
 * To change this template use File | Settings | File Templates.
 */
ctm.controller('AuthController', function ($scope, $location, $routeParams, $cookies, authService) {

    var userId = $routeParams.userId;

    if ($cookies.sessionId) {
        var currentSession = $cookies.sessionId;
        $scope.user = authService.getUser(currentSession, $routeParams.userId);
    }

    // if authentication works, redirect to sitelist view else go back to login
    $scope.onLogin = function () {
        authService.authenticate($scope.username, $scope.userpass).then(function (data) {
            if (typeof data != 'undefined') {
                $cookies.sessionId = data.data.Data.sessionId;
                var newLoc =  '#/sites/' + $cookies.sessionId;
                console.log(newLoc);
                $location.path(newLoc);
            } else {
                $location.path('#/login/' + $routeParams.username + '/' + "Login failed");
            }
            return false;
        });
    };

    $scope.onLogout = function () {
        $cookies.sessionId = null;
        $location.path('#/login/' + $routeParams.username + '/' + "Logged out");
        // invalidate session
        return false;
    };

    $scope.validateLogin = function (username, password) {
        return (!username && !password);
    };

    $scope.cancel = function () {
        //alert("logout");
        $location.path('#/');
        return false;
    }

});
