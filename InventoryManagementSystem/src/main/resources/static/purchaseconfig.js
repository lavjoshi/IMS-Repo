/**
 * Created by lav on 3/8/16.
 */
routerApp.config(function($stateProvider,$urlRouterProvider){


    $stateProvider
        .state('purchase',{
            url:'/purchase',
            views:{
                "purchase":{templateUrl:'purchasehome.html'},




            },
            params:{'data':null},
            controller:'homeController'


        })
        .state('createPurchase',{
            url:'/createPurchase',
            views:{
                "purchase":{templateUrl:'createPurchase.html'}

            },
            controller:'createPurchase'

        })
        .state('viewPurchase',{
            url:'/viewPurchase',
            views:{
                "purchase":{templateUrl:'viewPurchase.html'}

            },

            controller:'viewPurchase'
        })
        .state('changePass',{
            url:'/changePassword',
            views:{
                "purchase":{templateUrl:'changePassword.html'}

            },

            controller:'changePassword'
        })





})