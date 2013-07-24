/**
 * Created with JetBrains WebStorm.
 * User: phaggood
 * Date: 7/24/13
 * Time: 10:53 AM
 * To change this template use File | Settings | File Templates.
 */
ctoolsMobile.factory('siteService', function($http) {
    return {
        // return all sites beloging to this ession
        getSites: function(sessId) {
            //since $http.get returns a promise,
            //and promise.then() also returns a promise
            //that resolves to whatever value is returned in it's
            //callback argument, we can return that.
            return $http.get('http://localhost:9090/siteslist.groovy').then(result) {
                return result.data;
            }
        },
        // get site for session by siteid
        getSite: function(sessId, siteId) {
            //since $http.get returns a promise,
            //and promise.then() also returns a promise
            //that resolves to whatever value is returned in it's
            //callback argument, we can return that.
            return $http.get('http://localhost:9090/site.groovy/:id').then(result) {
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
            return $http.get('http://localhost:9090/auth.groovy').then(result) {
                return result.data;
            };
        },

        // get the user record for this session
        getUser: function(sessid) {
            //since $http.get returns a promise,
            //and promise.then() also returns a promise
            //that resolves to whatever value is returned in it's
            //callback argument, we can return that.
            return $http.get('http://localhost:9090/user.groovy/:id').then(result) {
                return result.data;
            };
        },

        // expire ths session on the server
        logout: function(sessid) {

        }

    }
});