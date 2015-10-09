describe('dateServices tests', function () {

   describe('dateResolveUtil', function () {

      var date = {};
      var dateString = {};
      var ISOdate = {};
      var onlyDate = {};
      beforeEach(module('myStartupApp'));
      beforeEach(function () {
         date = new Date(2014, 9, 10, 12, 00, 00);
         dateString = "2014-10-10T12:00:00";
         ISOdate = "2014-10-10 12:00:00";
         onlyDate = "2014-10-10";
      });

      describe('convertIfDateToString method', function () {

         it('Should convert DATE to STRING', inject(function (dateResolveUtil) {
            expect(dateResolveUtil.convertIfDateToString(date)).toEqual(dateString);
         }));
         it('Should return STRING when argument is STRING', inject(function (dateResolveUtil) {
            expect(dateResolveUtil.convertIfDateToString(dateString)).toEqual(dateString);
         }));

      });

      describe('formatToISODate method', function () {
         it('Should convert DATE to STRING', inject(function (dateResolveUtil) {
            expect(dateResolveUtil.formatToISODate(date)).toEqual(dateString);
         }));

         it('Should return null when DATE is null', inject(function (dateResolveUtil) {
            expect(dateResolveUtil.convertToDate(null)).toEqual(null);
         }));

      });
      describe('convertToDate method', function () {

         it('Should convert STRING to DATE', inject(function (dateResolveUtil) {
            expect(dateResolveUtil.convertToDate(dateString)).toEqual(date);
         }));

         it('Should return null when STRING is null', inject(function (dateResolveUtil) {
            expect(dateResolveUtil.convertToDate(null)).toEqual(null);
         }));

      });

      describe('isAfterNow method', function () {

         it('Should return false when date is before now', inject(function (dateResolveUtil) {
            expect(dateResolveUtil.isAfterNow(date)).toEqual(false);
         }));

         it('Should return true when date is after now', inject(function (dateResolveUtil) {
            expect(dateResolveUtil.isAfterNow(new Date().setDate(new Date().getDate() + 1))).toEqual(true);
         }));

         it('Should return true when date is null', inject(function (dateResolveUtil) {
            expect(dateResolveUtil.isAfterNow(null)).toEqual(true);
         }));

      });

      describe('onlyDate method', function () {

         it('Should return only date from DATE', inject(function (dateResolveUtil) {
            expect(dateResolveUtil.onlyDate(dateString)).toEqual(onlyDate);
         }));

         it('Should return null when DATE is null', inject(function (dateResolveUtil) {
            expect(dateResolveUtil.onlyDate(null)).toEqual(null);
         }));

      });

   });
});