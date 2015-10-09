(function () {
   'use strict';

   angular
           .module('myStartupApp')
           .config(questionnaireTemplateRouteProvider);

   questionnaireTemplateRouteProvider.$inject = ['$stateProvider'];

   function questionnaireTemplateRouteProvider($stateProvider) {
      var resolveQuestionnaireTemplates = ['$state', 'QuestionnaireTemplate', 'popupService', 'logToServerUtil',
         loadQuestionnaireTemplates];
      var resolveSingleQuestionnaireTemplate = ['$state', '$stateParams', 'QuestionnaireTemplate', 'popupService',
         'logToServerUtil', loadQuestionnaireTemplate];
      var resolveTranslationsFor = resolveTranslationsFor;

      $stateProvider
              .state('questionnaireTemplate', {
                 parent: 'root',
                 url: '/questionnaireTemplate',
                 abstract: true,
                 template: '<ui-view />',
                 ncyBreadcrumb: {
                    label: 'skipThisLabelInFavorOfManualHandling'
                 }
              })
              .state('questionnaireTemplate.list', {
                 url: '/list',
                 templateUrl: '/questionnaireTemplate/list',
                 controller: 'QuestionnaireTemplateListCtrl',
                 resolve: {
                    questionnaireTemplates: resolveQuestionnaireTemplates,
                    translations: resolveTranslationsFor('questionnaireTemplate/list', 'questionnaireTemplate/list-dropdown')
                 },
                 ncyBreadcrumb: {
                    label: 'breadcrumb.questionnaireTemplate'
                 }
              })
              .state('questionnaireTemplate.add', {
                 url: '/add',
                 templateUrl: '/questionnaireTemplate/add',
                 controller: 'QuestionnaireTemplateAddCtrl',
                 resolve: {
                    translations: resolveTranslationsFor('questionnaireTemplate/add')
                 },
                 ncyBreadcrumb: {
                    parent: 'questionnaireTemplate.list',
                    label: 'shared.add'
                 }
              })
              .state('questionnaireTemplate.details', {
                 url: '/{id:int}',
                 templateUrl: '/questionnaireTemplate/details',
                 controller: 'QuestionnaireTemplateDetailsCtrl',
                 resolve: {
                    questionnaireTemplate: resolveSingleQuestionnaireTemplate,
                    translations: resolveTranslationsFor('questionnaireTemplate/details')
                 },
                 ncyBreadcrumb: {
                    parent: 'questionnaireTemplate.list',
                    label: 'shared.details'
                 }
              });


      function loadQuestionnaireTemplates($state, QuestionnaireTemplate, popupService, logToServerUtil) {
         var questionnaireTemplatesPromise = QuestionnaireTemplate.query().$promise;
         questionnaireTemplatesPromise
                 .catch(function (reason) {
                    logToServerUtil.trace('get Questionnaire Templates failed', reason);
                    popupService.error('shared.popup.general-failure.title',
                            'shared.popup.general-failure.body');
                    $state.go('dashboard');
                 });
         return questionnaireTemplatesPromise;
      }

      function loadQuestionnaireTemplate($state, $stateParams, QuestionnaireTemplate, popupService, logToServerUtil) {
         var questionnaireTemplatePromise = QuestionnaireTemplate.get({id: $stateParams.id}).$promise;
         questionnaireTemplatePromise
                 .catch(function (reason) {
                    logToServerUtil.trace('get Questionnaire Template failed', reason);
                    popupService.error('shared.popup.general-failure.title',
                            'shared.popup.general-failure.body');
                    $state.go('dashboard');
                 });
         return questionnaireTemplatePromise;
      }

      function resolveTranslationsFor() {
         var parts = arguments;

         return ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
               for (var i = 0; i < parts.length; i++) {
                  $translatePartialLoader.addPart(parts[i]);
               }

               return $translate.refresh();
            }];
      }
   }
})();
