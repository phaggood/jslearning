/**
 * Created with JetBrains WebStorm.
 * User: phaggood
 * Date: 7/16/13
 * Time: 10:22 PM
 * To change this template use File | Settings | File Templates.
 */
ctm.controller('LoginCtl', function ($scope, $location, $routeParams, $cookies, authService) {

    var userId = $routeParams.userId;
    $scope.usrname = $routeParams.usrname;
    $scope.statusMsg = $routeParams.statMsg;

    if ($cookies.sessionId) {
        var currentSession = $cookies.sessionId;
        $scope.user = authService.getUser(currentSession, $routeParams.userId);
    }

    // if authentication works, redirect to sitelist view else go back to loginform.html
    $scope.onLogin = function () {
        authService.authenticate($scope.username, $scope.userpass).then(function (data) {
            if (data.status == '200') {
                $cookies.sessionId = data.sessionId;
                var newLoc =  '#/sites';
                console.log("To sites at " + newLoc);
                window.location=newLoc;
                //$scope.$apply();
            } else {
                var logURL = '#/login/'+ $scope.username + '/' + "Bad credentials for user:";
                window.location=logURL ;
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