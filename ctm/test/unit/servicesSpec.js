'use strict';

/* jasmine specs for services go here */

beforeEach(angular.module('ctm'));


describe('service', function() {
  beforeEach(angular.module('ctm.services'));


  describe('version', function() {
    it('should return current version', inject(function(version) {
      expect(version).toEqual('0.11');
    }));
  });
});
