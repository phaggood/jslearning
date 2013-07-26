/**
 * Created with JetBrains WebStorm.
 * User: phaggood
 * Date: 7/24/13
 * Time: 10:53 AM
 * To change this template use File | Settings | File Templates.
 */


// route params and test http://docs.angularjs.org/tutorial/step_11
ctoolsMobile.factory('siteService', function($http) {
    return {
        // return all sites beloging to this session
        getSites: function(sessionId) {
            var params = "?sessionId=" + sessionId;
            //since $http.get returns a promise,
            //and promise.then() also returns a promise
            //that resolves to whatever value is returned in it's
            //callback argument, we can return that.
            return $http.get('http://localhost:9090/sites.groovy'+params).then(result) {
                return result.data;
            }
        },
        // get site for session by siteid
        getSite: function(sessionId, siteId) {
            var params = "?sessionId=" + sessionId + "&siteId="+siteId;
            //since $http.get returns a promise,
            //and promise.then() also returns a promise
            //that resolves to whatever value is returned in it's
            //callback argument, we can return that.
            return $http.get('http://localhost:9090/site.groovy'+params).then(result) {
                return result.data;
            }
        },
        // get list of tools in a site
        getTools: function(sessionId, siteId) {
            var params = "?sessionId=" + sessionId + "&siteId="+siteId;
            //since $http.get returns a promise,
            //and promise.then() also returns a promise
            //that resolves to whatever value is returned in it's
            //callback argument, we can return that.
            return $http.get('http://localhost:9090/sitetools.groovy'+params).then(result) {
                return result.data;
            }
        },
        // get tool by toolId
        getTool: function(sessionId, toolId) {
            var params = "?sessionId=" + sessionId + "&toolId="+toolId;
            //since $http.get returns a promise,
            //and promise.then() also returns a promise
            //that resolves to whatever value is returned in it's
            //callback argument, we can return that.
            return $http.get('http://localhost:9090/sitetools.groovy'+params).then(result) {
                return result.data;
            }
        }
    }
});

ctoolsMobile.factory('authService', function($http) {
    return {
        authentiate: function(uname,pwd) {
            var params = "?username=" + uname + "&password="+pwd;

            //since $http.get returns a promise,
            //and promise.then() also returns a promise
            //that resolves to whatever value is returned in it's
            //callback argument, we can return that.
            return $http.get('http://localhost:9090/auth.groovy'+params).then(result) {
                return result.data;
            }
        },

        // get the user by current session or userid
        getUser: function(sessId,userId) {
            var params = "?sessionId=" + sessId + "&userId="+userId;
            //since $http.get returns a promise,
            //and promise.then() also returns a promise
            //that resolves to whatever value is returned in it's
            //callback argument, we can return that.
            return $http.get('http://localhost:9090/user.groovy'+params).then(result) {
                return result.data;
            }
        },

        // expire ths session on the server
        logout: function(sessionId) {
            var params = "?sessionId=" + sessionId;
            return $http.get('http://localhost:9090/user.groovy'+params).then(result) {
                return result.data;
            }
        }
    }
});