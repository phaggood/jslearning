/**
 * Created with JetBrains WebStorm.
 * User: phaggood
 * Date: 7/16/13
 * Time: 10:22 PM
 * To change this template use File | Settings | File Templates.
 */
ctm.controller('LoginCtl', function ($scope, $location, $routeParams, $cookies, authService) {

    var userId = $routeParams.userId;

    if ($cookies.sessionId) {
        var currentSession = $cookies.sessionId;
        $scope.user = authService.getUser(currentSession, $routeParams.userId);
    }

    // if authentication works, redirect to sitelist view else go back to loginform.html
    $scope.onLogin = function () {
        authService.authenticate($scope.username, $scope.userpass).then(function (data) {
            if (typeof data != 'undefined') {
                $cookies.sessionId = data.data.Data.sessionId;
                var newLoc =  '#/sites';
                console.log("To sites at " + newLoc);
                window.location=newLoc;
                //$scope.$apply();
            } else {
                window.location='/login/homepage'
                $location.path('#/loginfail');
                //$scope.$apply();
            }
            //return false;
        });
    };

    //
    $scope.onLogout = function () {
        $cookies.sessionId = null;
        $location.path('#/login/' + $routeParams.username + '/' + "Logged out");
        // invalidate session
        //return false;
    };

    $scope.validateLogin = function (username, password) {
        return (!username && !password);
    };

    //
    $scope.cancel = function () {
        //alert("logout");
        $location.path('#/');
        return false;
    }

});

ctm.controller('UserCtl', function ($scope, $location, $routeParams, $cookies, authService) {

    var userId = $routeParams.userId;

    $scope.user = authService.getUser(currentSession, $routeParams.userId);

});