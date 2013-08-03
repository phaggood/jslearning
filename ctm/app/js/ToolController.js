/**
 * Created with JetBrains WebStorm.
 * User: phaggood
 * Date: 8/3/13
 * Time: 3:27 AM
 * To change this template use File | Settings | File Templates.
 */


ctm.controller('GradebookCtl', function ($scope, $location, $routeParams, $cookies,  authService) {

    var currentSession   = $cookies.sessionId;     // used for each call

    // sites.html
    var cuser =  authService.getSessionUser(currentSession);
    $scope.currentuser = function () {
        cuser.lastname;
    }
    var dummy =1;

});

ctm.controller('ChatCtl', function ($scope, $location, $routeParams, $cookies,  authService) {

    var currentSession   = $cookies.sessionId;     // used for each call

   $scope.currentuser =  function () {
       var usr = authService.getSessionUser(currentSession);
       return usr.lastname + ", " + usr.firstname;
   }

   $scope.curentuser();

});