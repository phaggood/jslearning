/**
 * Created with JetBrains WebStorm.
 * User: phaggood
 * Date: 7/24/13
 * Time: 10:53 AM
 * To change this template use File | Settings | File Templates.
 */
// http://www.benlesh.com/2013/02/angularjs-creating-service-with-http.html

// route params and test http://docs.angularjs.org/tutorial/step_11
ctm.factory('siteService', function($http) {
    var data;
    return {
        getSites: function(sessionId) {
            console.log("sites for " + sessionId)
            var params = "?sessionId=" + sessionId;
            return $http.get('http://localhost:9090/sites.groovy'+params).then( function(result) {
                return result.data.Data;
            });
        },
        // get site for session by siteid
        getSite: function(sessionId, siteId) {
            var params = "?sessionId=" + sessionId + "&siteId="+siteId;
            console.log("site " + params)
            //since $http.get returns a promise,
            //and promise.then() also returns a promise
            //that resolves to whatever value is returned in it's
            //callback argument, we can return that.
            return $http.get('http://localhost:9090/sites.groovy'+params).then(function (result) {
                return result.data;
            });
        },
        // get list of tools in a site
        getTools: function(sessionId, siteId) {
            var params = "?sessionId=" + sessionId + "&siteId="+siteId;
            //since $http.get returns a promise,
            //and promise.then() also returns a promise
            //that resolves to whatever value is returned in it's
            //callback argument, we can return that.
            return $http.get('http://localhost:9090/sitetools.groovy'+params).then(function(result) {
                return result.data.Data;
            });
        },
        // get tool by toolId
        getTool: function(sessionId, toolId) {
            var params = "?sessionId=" + sessionId + "&toolId="+toolId;
            //since $http.get returns a promise,
            //and promise.then() also returns a promise
            //that resolves to whatever value is returned in it's
            //callback argument, we can return that.
            return $http.get('http://localhost:9090/sitetools.groovy'+params).then(function(result) {
                return result.data;
            });
        }
    }
});

ctm.factory('authService', function($http) {
    var result;
    return {
        authenticate: function(uname,pwd) {
            var params = "?username=" + uname + "&password="+pwd;

            //since $http.get returns a promise,
            //and promise.then() also returns a promise
            //that resolves to whatever value is returned in it's
            //callback argument, we can return that.
            return $http.get('http://localhost:9090/auth.groovy'+params).then(function(result) {
                // if (result.status == 200) return (invalid session) else return result.sesionId
                return result;
            });
        },

        // get the user by current session or userid
        getUser: function(sessId,userId) {
            var params = "?sessionId=" + sessId + "&userId="+userId;
            //since $http.get returns a promise,
            //and promise.then() also returns a promise
            //that resolves to whatever value is returned in it's
            //callback argument, we can return that.
            $http.get('http://localhost:9090/user.groovy'+params).then(function(result) {
                return result.data;
            });
        },

        // expire ths session on the server
        logout: function(sessionId) {
            var params = "?sessionId=" + sessionId;
            return $http.get('http://localhost:9090/user.groovy'+params).then(function (result) {
                return result.data;
            });
        }
    }
});
