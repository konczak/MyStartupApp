(function () {
   'use strict';

   angular
           .module('myStartupApp')
           .filter('questionnaireTemplateListFilter', questionnaireTemplateListFilter);

   function questionnaireTemplateListFilter() {
      return filter;

      //////////

      function filter(value, input) {
         var result = [];
         for (var i = 0; i < value.length; i++) {
            if (input !== undefined) {
               if (!isMatchName(value[i], input) || !isMatchLanguage(value[i], input)
            		   || !isMatchAuthor(value[i], input) || !isMatchCreatedAt(value[i], input)
            		   || !isMatchUpdatedAt(value[i], input)) {
                  continue;
               }
            }
            result.push(value[i]);
         }
         return result;
      }

      function isMatchName(value, input) {
         if (input.name !== undefined) {
            if (!(value.name.toUpperCase().match(input.name.toUpperCase()))) {
               return false;
            }
         }
         return true;
      }

      function isMatchLanguage(value, input) {
         if (input.language !== undefined) {
            if (!(value.language.toUpperCase().match(input.language.toUpperCase()))) {
               return false;
            }
         }
         return true;
      }
      
      function isMatchAuthor(value, input) {
          if (input.author !== undefined) {
             if (!(value.author.toUpperCase().match(input.author.toUpperCase()))) {
                return false;
             }
          }
          return true;
      }
      
      function isMatchCreatedAt(value, input) {
    	  if (input.createdAtFrom !== undefined) {
    		  if (value.createdAt < input.createdAtFrom) {
    			  return false;
    		  }
    	  }
    	  if (input.createdAtTo !== undefined) {
    		  if (value.createdAt > input.createdAtTo) {
    			  return false;
    		  }
    	  }
    	  
    	  return true;
      }
      
      function isMatchUpdatedAt(value, input) {
    	  if (input.updatedAtFrom) {
    		  if (value.updatedAt < input.updatedAtFrom) {
    			  return false;
    		  }
    	  }
    	  if (input.updatedAtTo) {
    		  if (value.updatedAt > input.updatedAtTo) {
    			  return false;
    		  }
    	  }
    	  
    	  return true;
      }
   }
})();
