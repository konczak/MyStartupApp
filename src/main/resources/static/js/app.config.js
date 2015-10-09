(function () {
   'use strict';

   angular
           .module('myStartupApp')
           .config(configure);

   configure.$inject = [
      '$stateProvider',
      '$urlRouterProvider',
      '$httpProvider',
      '$translateProvider',
      'dateTimePickerProvider',
      '$breadcrumbProvider'
   ];

   function configure(
           $stateProvider, $urlRouterProvider, $httpProvider,
           $translateProvider, dateTimePickerProvider ,$breadcrumbProvider) {
      $urlRouterProvider.otherwise('/dashboard');
      
      $breadcrumbProvider.setOptions({
    	  templateUrl: 'fragments/breadcrumb',
        includeAbstract: 'true'
      });
      
      $stateProvider.state('root', {
    	  abstract: true,
    	  resolve: {
    		  loggedEmployee: ['LoggedEmployee', function (LoggedEmployee) {
    			  return LoggedEmployee.$promise;
    		  }]
    	  },
    	  views: {
			  navbar: {
				  templateUrl: 'fragments/navbar.html',
				  controller: 'NavbarController',
				  resolve: {
					  translations: ['$translatePartialLoader', '$translate', 
					                 function ($translatePartialLoader, $translate) {
						  $translatePartialLoader.addPart('shared');
						  return $translate.refresh();
					  }]
				  }
			  },
			  body: {
				  template: '<ui-view />'
			  }
		  },
        ncyBreadcrumb: {
            label: 'skipThisLabelInFavorOfManualHandling'
        }
      })
      .state('root.dashboard', {
    	  url: '/dashboard',
  		  ncyBreadcrumb: {
		    label: 'breadcrumb.dashboard'
		  }
      });

      $httpProvider.interceptors.push(function ($q) {
         return {
            'request': function (config) {
               if (!isUrlToSkipPrefixing(config.url)) {
                  config.url = '/MyStartupApp' + (config.url[0] !== '/' ? '/' : '') + config.url;
               }
               return config || $q.when(config);
            }
         };
      });

      function isUrlToSkipPrefixing(url) {
         var urlsToSkip = ['template/modal/backdrop.html',
            'template/modal/window.html',
            'templates/swagger-ui.html',
            'template/progressbar/progressbar.html',
            'ngTagsInput/tags-input.html',
            'ngTagsInput/auto-complete.html',
            'ngTagsInput/tag-item.html',
            'ngTagsInput/auto-complete-match.html',
            'templates/dateTimePickerCalendar.html',
            'templates/dateTimePickerInput.html',
            'templates/dateTimePickerModal.html',
            'templates/dateTimePickerTimer.html',
            'template/accordion/accordion.html',
            'template/accordion/accordion-group.html',
            'isteven-multi-select.htm'];
         var isUrlToSkipPrefixing = false;

         angular.forEach(urlsToSkip, function (value) {
            if (value === url) {
               isUrlToSkipPrefixing = true;
            }
         });


         if (url.indexOf('http') === 0) {
            isUrlToSkipPrefixing = true;
         }

         return isUrlToSkipPrefixing;
      }

      //angular-translate
      $translateProvider.useLoader('$translatePartialLoader', {
         urlTemplate: '/i18n/{lang}/{part}.json'
      });
      $translateProvider.use('pl_PL');
      $translateProvider.useStorage('languageStorageService');
      $translateProvider.useSanitizeValueStrategy('escaped');
      $translateProvider.forceAsyncReload(true);

      
      dateTimePickerProvider.showWeekNumbers = true;
      dateTimePickerProvider.minuteStep = 15;
      dateTimePickerProvider.modalTemplate = 'dateTimePicker/dateTimePickerModal.html';
      dateTimePickerProvider.calendarTemplate = 'dateTimePicker/dateTimePickerCalendar.html';
   }

})();
