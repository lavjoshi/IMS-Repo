/**
 * Created by lav on 3/8/16.
 */
routerApp.config(function($stateProvider,$urlRouterProvider){


    $stateProvider
        .state('product',{
            url:'/product',
            views:{
                "product":{templateUrl:'producthome.html'},




            },
            params:{'data':null},
            controller:'homeController'


        })
        .state('createProduct',{
            url:'/createProduct',
            views:{
                "product":{templateUrl:'createProduct.html'}

            },
            controller:'createProduct'

        })
        .state('viewProduct',{
            url:'/viewProduct',
            views:{
                "product":{templateUrl:'viewProduct.html'}

            },

            controller:'viewProduct'
        })
        .state('changePass',{
            url:'/changePassword',
            views:{
                "product":{templateUrl:'changePassword.html'}

            },
            controller:'changePassword'

        })





})