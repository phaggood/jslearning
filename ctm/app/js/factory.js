/**
 * Created with JetBrains WebStorm.
 * User: phaggood
 * Date: 7/24/13
 * Time: 10:53 AM
 * To change this template use File | Settings | File Templates.
 */
ctoolsMobile.factory('siteService', function($http) {
    return {
        // return all sites beloging to this session
        getSites: function(sessionId) {
            //since $http.get returns a promise,
            //and promise.then() also returns a promise
            //that resolves to whatever value is returned in it's
            //callback argument, we can return that.
            return $http.get('http://localhost:9090/sites.groovy/:sessionId').then(result) {
                return result.data;
            }
        },
        // get site for session by siteid
        getSite: function(sessionId, siteId) {
            //since $http.get returns a promise,
            //and promise.then() also returns a promise
            //that resolves to whatever value is returned in it's
            //callback argument, we can return that.
            return $http.get('http://localhost:9090/site.groovy/:siteId').then(result) {
                return result.data;
            };
        }
        // get list of tools in a site
        getTools: function(sessionId, siteId) {
            //since $http.get returns a promise,
            //and promise.then() also returns a promise
            //that resolves to whatever value is returned in it's
            //callback argument, we can return that.
            return $http.get('http://localhost:9090/sitetools.groovy/:sessionId/:siteId').then(result) {
                return result.data;
            };
        }
        // get tool by toolId
        getTools: function(sessionId, toolId) {
            //since $http.get returns a promise,
            //and promise.then() also returns a promise
            //that resolves to whatever value is returned in it's
            //callback argument, we can return that.
            return $http.get('http://localhost:9090/sitetools.groovy/:toolId').then(result) {
                return result.data;
            };
        }
    }
});

ctoolsMobile.factory('authService', function($http) {
    return {
        authentiate: function(uname,pwd) {
            //since $http.get returns a promise,
            //and promise.then() also returns a promise
            //that resolves to whatever value is returned in it's
            //callback argument, we can return that.
            return $http.get('http://localhost:9090/auth.groovy/:username/:password').then(result) {

                return result.data;
            };
        },

        // get the user by current session or userid
        getUser: function(sessid,userId) {
            //since $http.get returns a promise,
            //and promise.then() also returns a promise
            //that resolves to whatever value is returned in it's
            //callback argument, we can return that.
            return $http.get('http://localhost:9090/user.groovy/:sessionId/:userId').then(result) {
                return result.data;
            };
        },

        // expire ths session on the server
        logout: function(sessid) {
            return $http.get('http://localhost:9090/user.groovy/:sessionId').then(result) {
                return result.data;
            };
        }
    }
});