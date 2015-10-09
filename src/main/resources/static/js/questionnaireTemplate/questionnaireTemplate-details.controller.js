(function () {
   'use strict';

   angular
           .module('myStartupApp')
           .controller('QuestionnaireTemplateDetailsCtrl', QuestionnaireTemplateDetailsCtrl);

   QuestionnaireTemplateDetailsCtrl.$inject = ['$scope',
      '$modal',
      '$translatePartialLoader',
      'popupService',
      'logToServerUtil',
      'QuestionnaireTemplate',
      'questionnaireTemplate',
      '$translate',
      'QuestionnaireTemplateUtilService',
      '$filter'];

   function QuestionnaireTemplateDetailsCtrl($scope, $modal, $translatePartialLoader, popupService, logToServerUtil,
            QuestionnaireTemplate, questionnaireTemplate, 
            $translate, QuestionnaireTemplateUtilService, $filter) {
      QuestionnaireTemplateUtilService.setConstantsOn($scope);
      $scope.questionnaireTemplate = questionnaireTemplate;
      $scope.updateName = updateName;
      $scope.updateLanguage = updateLanguage;
      $scope.collapseAll = collapseAll;
      $scope.expandAll = expandAll;
      $translate(['questionnaireTemplate-details.language.polish', 'questionnaireTemplate-details.language.english']).then(function (translations) {
         $scope.languages = [{
               value: 'pl-PL',
               text: translations['questionnaireTemplate-details.language.polish']
            }, {
               value: 'en-GB',
               text: translations['questionnaireTemplate-details.language.english']
            }];
      });


      function updateQuestionnaireTemplate(fields) {
         var questionnaireTemplate = new QuestionnaireTemplate();
         questionnaireTemplate.questionnaireTemplateId = $scope.questionnaireTemplate.questionnaireTemplateId;

         if (fields.hasOwnProperty('name')) {
            questionnaireTemplate.name = fields.name;
         } else {
            questionnaireTemplate.name = $scope.questionnaireTemplate.name;
         }
         if (fields.hasOwnProperty('language')) {
            questionnaireTemplate.language = fields.language;
         } else {
            questionnaireTemplate.language = $scope.questionnaireTemplate.language;
         }

         questionnaireTemplate.$update().
                 then(function (updatedQuestionnaireTemplate) {
                    $scope.questionnaireTemplate.name = updatedQuestionnaireTemplate.name;
                    $scope.questionnaireTemplate.language = updatedQuestionnaireTemplate.language;
                    popupService.success('questionnaireTemplate-details.popup.save.success.title', 'questionnaireTemplate-details.popup.save.success.body');
                 }, function (reason) {
                    if (questionnaireTemplateWithGivenNameAlreadyExist(reason)) {
                       popupService.error('questionnaireTemplate-details.popup.save.failure.title', 'questionnaireTemplate-details.popup.save.failure.body.duplicate');
                    } else {
                       logToServerUtil.trace("Edit Questionnaire Template failure", reason);
                       popupService.error('questionnaireTemplate-details.popup.save.failure.title', 'questionnaireTemplate-details.popup.save.failure.body');
                    }
                 });
      }

      function questionnaireTemplateWithGivenNameAlreadyExist(reason) {
         if (!reason.data || !reason.data.fieldErrors) {
            return false;
         }

         for (var i = 0; i < reason.data.fieldErrors.length; i++) {
            if (reason.data.fieldErrors[i].message === 'QuestionnaireTemplateWithGivenNameAlreadyExists.questionnaireTemplateEdit.name') {
               return true;
            }
         }

         return false;
      }

      function updateName(newName) {
         newName = newName.trim();
         if (newName === '') {
            return $translate.instant('questionnaireTemplate-details.validator.name.required');
         } else if (newName.length < $scope.constant.nameMin) {
            return $translate.instant('questionnaireTemplate-details.validator.name.tooShort', {min: $scope.constant.nameMin});
         } else if (newName.length > $scope.constant.nameMax) {
            return $translate.instant('questionnaireTemplate-details.validator.name.tooLong', {max: $scope.constant.nameMax});
         }

         updateQuestionnaireTemplate({name: newName});
         return false;
      }

      function updateLanguage(newLanguage) {
         updateQuestionnaireTemplate({language: newLanguage});
         return false;
      }

      function reloadQuestionnaireTemplate() {
         QuestionnaireTemplate.get({
            id: $scope.questionnaireTemplate.questionnaireTemplateId
         }).$promise.then(function (questionnaireTemplate) {
            $scope.questionnaireTemplate = questionnaireTemplate;
         }, function () {
            logToServerUtil.trace('get Questionnaire Template failed', reason);
            popupService.error('shared.popup.general-failure.title',
                    'shared.popup.general-failure.body');
         });
      }

      function collapseAll() {
         angular.forEach(questionnaireTemplate.questions, function (question) {
            question.collapsed = true;
         });
      }

      function expandAll() {
         angular.forEach(questionnaireTemplate.questions, function (question) {
            question.collapsed = false;
         });
      }


   }
})();
