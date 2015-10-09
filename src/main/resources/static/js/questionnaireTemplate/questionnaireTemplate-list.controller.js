(function () {
   'use strict';

   angular
           .module('myStartupApp')
           .controller('QuestionnaireTemplateListCtrl', QuestionnaireTemplateListCtrl);

   QuestionnaireTemplateListCtrl.$inject = [
       '$scope', 
       'QuestionnaireTemplate',
       'questionnaireTemplates'];

   function QuestionnaireTemplateListCtrl($scope,  
		   QuestionnaireTemplate, questionnaireTemplates) {
      $scope.questionnaireTemplates = questionnaireTemplates;
      $scope.searchParams = [];
      $scope.order = {
    		  predicate: 'name',
    		  reverse: false
      };
      $scope.sortBy = sortBy;
      $scope.clearSearchParams = clearSearchParams;
      $scope.copy = copy;
      
      angular.forEach($scope.questionnaireTemplates, function(q){
    	  q.author = q.employee.lastname + ' ' + q.employee.firstname;
      });
      
      function sortBy(predicate){
    	  if($scope.order.predicate === predicate){
    		  $scope.order.reverse = !$scope.order.reverse;
    	  }else{
    		  $scope.order.predicate = predicate;
    		  $scope.order.reverse = false;
    	  }
      }
      
      function clearSearchParams(){
    	  $scope.searchParams = [];
      }
      
      function copy(questionnaireTemplate) {
    	  QuestionnaireTemplateCopyService.openModal(questionnaireTemplate);
      }
   }
})();
