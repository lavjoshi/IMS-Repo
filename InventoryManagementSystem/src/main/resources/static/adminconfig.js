/**
 * Created by lav on 27/7/16.
 */
routerApp.config(function($stateProvider,$urlRouterProvider){


    $stateProvider
        .state('admin',{
            url:'/admin',
            views:{
                "admin":{templateUrl:'adminhome.html'},




            },
            params:{'data':null},
            controller:'homeController'


        })
        .state('createInventory',{
            url:'/createInventory',
            views:{
                "admin":{templateUrl:'createInventory.html'}

            },
            controller:'inventoryController'

        })
        .state('viewInventory',{
            url:'/viewInventory',
            views:{
                "admin":{templateUrl:'viewInventory.html'}

            }


        })
        .state('createUser',{
            url:'/createRole',
            views:{
                "admin":{templateUrl:'createUser.html'}

            },
            controller:'newUser'

        })
        .state('viewUser',{
            url:'/viewRole',
            views:{
                "admin":{templateUrl:'viewUsers.html'}

            },
            controller:'viewUsers'

        })
        .state('viewPurchaseHistory',{
            url:'/viewPurchaseHistory',
            views:{
                "admin":{templateUrl:'purchasehistory.html'}

            },
            controller:'PurchaseHistory'

        })
        .state('viewProductionHistory',{
            url:'/viewProductionHistory',
            views:{
                "admin":{templateUrl:'productionhistory.html'}

            },
            controller:'ProductionHistory'

        })







})
/*
routerApp.run(function ($rootScope, $state) {
    $rootScope.$on("$stateChangeStart", function(event, toState, toParams, fromState, fromParams){
        if (toState.authenticate){
            // User isn’t authenticated
            $state.transitionTo("home");
            //event.preventDefault();
        }
    });
});*/
