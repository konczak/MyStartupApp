(function () {
   'use strict';

   angular
           .module('myStartupApp')
           .factory('QuestionnaireTemplateUtilService', QuestionnaireTemplateUtilService);
   
   function QuestionnaireTemplateUtilService(){
	   return {
		   setConstantsOn: function($scope){
			   $scope.constant = {
				   nameMax: 250,
				   nameMin: 3,
               questionMax: 1000,
				   questionMin: 3,
				   answerMax: 500,
				   answerMin: 1
			   };
		   }
	   };
   }
})();