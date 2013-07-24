/**
 * Created with JetBrains WebStorm.
 * User: phaggood
 * Date: 7/16/13
 * Time: 10:22 PM
 * To change this template use File | Settings | File Templates.
 */
CToolsMobile.controller('AuthController', function ($scope, $location, $routeParams,$cookies, UserModel) {
    var sites = SiteModel.getSites();  // ajax.getSites()

    $scope.user = user;
    $scope.selectedSiteId = $routeParams.siteId;

    $scope.onLogin = function() {
        var uname = $routeParams.username;
        var pwd = $routeParams.password;
        var auth = AuthFactory.login(uname, pwd);
        if (auth) {
            $cookies.sessionId = auth.sessionId;
            $location.path('#/sites/' + auth.userId);
        } else {
            $location.path('#/login/' + $routeParams.username + '/' + "Login failed");
        }
    };

    $scope.validateLogin = function (username, password) {
        return !username && !password;
    }

    $scope.onLogout = function() {
        // invalidate session
    }
};
