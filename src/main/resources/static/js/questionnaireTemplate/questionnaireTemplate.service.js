(function () {
   'use strict';

   angular
           .module('myStartupApp')
           .factory('QuestionnaireTemplate', QuestionnaireTemplate);

   QuestionnaireTemplate.$inject = ['$resource', 'dateResolveUtil'];

   function QuestionnaireTemplate($resource, dateResolveUtil) {
      return $resource('/api/questionnaireTemplate/:id', {}, {
         query: {
            method: 'GET',
            isArray: true,
            transformResponse: transformResponse
         },
         get: {
            method: 'GET',
            transformResponse: transformResponse
         },
         update: {
            method: 'PUT',
            transformResponse: transformResponse
         }
      });

      ////////////

      function transformResponse(data) {
         data = angular.fromJson(data);

         if ($.isArray(data)) {
            for (var i in data) {
               transformSingle(data[i]);
            }
         } else {
            transformSingle(data);
         }

         return data;
      }

      function transformSingle(questionnaireTemplate) {
         if (questionnaireTemplate.createdAt) {
            questionnaireTemplate.createdAt = dateResolveUtil.convertToDate(questionnaireTemplate.createdAt);
         }
         if (questionnaireTemplate.updatedAt) {
            questionnaireTemplate.updatedAt = dateResolveUtil.convertToDate(questionnaireTemplate.updatedAt);
         }
      }
   }
})();
