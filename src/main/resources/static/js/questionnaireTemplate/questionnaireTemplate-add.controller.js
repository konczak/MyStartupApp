(function(){
	'use strict';
	
	angular
		.module('myStartupApp')
		.controller('QuestionnaireTemplateAddCtrl' , QuestionnaireTemplateAddCtrl);
	
	QuestionnaireTemplateAddCtrl.$inject = [
	     '$scope', 
	     '$state', 
	     'QuestionnaireTemplate', 
	     'QuestionnaireTemplateUtilService' , 
	     'CommonUtilService',
	     'logToServerUtil',
	     'popupService',
	     'languageUtil'];
	
	function QuestionnaireTemplateAddCtrl($scope, $state, QuestionnaireTemplate, QuestionnaireTemplateUtilService, CommonUtilService, logToServerUtil, popupService, languageUtil){
		QuestionnaireTemplateUtilService.setConstantsOn($scope);
		CommonUtilService.initCommonAndOperator($scope);
		
		$scope.validationErrors = {};
		$scope.questionnaireTemplate = {
			language: languageUtil.tagFor('polish'),
			name: ''
		};
		$scope.saveQuestionnaireTemplate = saveQuestionnaireTemplate;
		
		function saveQuestionnaireTemplate(){
			var questionnaireTemplate = new QuestionnaireTemplate();
			questionnaireTemplate.name = $scope.questionnaireTemplate.name;
			questionnaireTemplate.language = $scope.questionnaireTemplate.language;
			
			questionnaireTemplate.$save()
				.then(function(result){
					popupService.success( 'questionnaireTemplate-add.popup.save.success.title','questionnaireTemplate-add.popup.save.success.body');
					$state.go('questionnaireTemplate.details', {id: result.questionnaireTemplateId});
				}, function(reason){
					if (questionnaireTemplateWithGivenNameAlreadyExist(reason)) {
						$scope.validationErrors.duplicatedName = true;
					} else {
						logToServerUtil.trace('Save Questionnaire Template failure', reason);
						popupService.error( 'questionnaireTemplate-add.popup.save.failure.title','questionnaireTemplate-add.popup.save.failure.body');
					}
				});
		}
		
		function questionnaireTemplateWithGivenNameAlreadyExist(reason) {
		   if (!reason.data || !reason.data.fieldErrors) {
			   return false;
		   }
		   
		   for (var i = 0; i < reason.data.fieldErrors.length; i++) {
			   if (reason.data.fieldErrors[i].message === 'QuestionnaireTemplateWithGivenNameAlreadyExists.questionnaireTemplateNew.name') {
				   return true;
			   }
		   }
		   
		   return false;
	   }
	}
})();