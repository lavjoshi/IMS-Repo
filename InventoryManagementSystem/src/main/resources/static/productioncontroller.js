/**
 * Created by lav on 3/8/16.
 */
routerApp.controller("createProduct",function ($scope,$http,$state,$stateParams){

    alert("createProduct");

    var  url="http://localhost:8080/viewAllInventory";
    $http.get(url).success(function(response){
        alert("inventories fetched");
        $scope.result=response;
    });

    $scope.table = { itemName: [],quantity:[] };




    $scope.addFormField = function() {
        $scope.table.itemName.push('');

        $scope.table.quantity.push('');
    }

    $scope.submitTable = function() {

        alert("submit");
        var productName=$scope.productName;
        var totalManufactured=$scope.totalManufactured;
        var arr=[];
        for(var i=0;i<$scope.table.itemName.length;i++)
        {
            arr[i]={"name":$scope.table.itemName[i],"quantityRequired":$scope.table.quantity[i]};
        }

        var data={"productName":productName,"totalManufactured":totalManufactured,"productionItem":arr}

        console.log(arr);
        console.log(data);
        var url="/checkAvailableQuantity";

        $http({
            url:url,
            data:data,
            dataType:'json',
            method:'POST',
            headers:{
                "Content-Type":"application/json"
            }
        }).success(function(response){
            alert("success");
            $scope.data=response.data;
            $scope.error=response.error;



        }).error(function(response){
           alert("error");
            $scope.error=response.error;
            $scope.data=response.data;

        })

    }


    $scope.confirmProduct=function(){
        alert("submit");
        var productName=$scope.productName;
        var totalManufactured=$scope.totalManufactured;
        var arr=[];
        for(var i=0;i<$scope.table.itemName.length;i++)
        {
            arr[i]={"name":$scope.table.itemName[i],"quantityRequired":$scope.table.quantity[i]};
        }

        var data={"productName":productName,"totalManufactured":totalManufactured,"productionItem":arr}


        console.log(arr);
        console.log(data);
        var url="/createNewProduct";

        $http({
            url:url,
            data:data,
            dataType:'json',
            method:'POST',
            headers:{
                "Content-Type":"application/json"
            }
        }).success(function(response){
            alert("success");
            var a=response.data;

            $state.go('product',{data:a});



        })

    }

});
routerApp.controller("initialController",function($scope,$http,$state){
    alert("productionpanel");


    $scope.start=function(){
        $state.go('product');
    }
});





routerApp.controller("viewProduct",function($scope,$http){

    alert("viewpurchase");

    var  url="http://localhost:8080/viewProductForUser";
    $http.get(url).success(function(response){


        $scope.result=response;
    })});






routerApp.controller("homeController", function ($scope,$stateParams) {
    alert("producthome");

    alert($stateParams.data);
    $scope.data1=$stateParams.data;




});
routerApp.controller("changePassword",function($scope,$http,$state){
    alert("change");

    $scope.update=function(){
        var data={"userName":'',"password":$scope.password,"role":'',"email":''};
        var url="updatePassword?newpassword="+$scope.newpassword;
        $http({
            url:url,
            data:data,
            dataType:'json',
            method:'POST',
            headers:{
                "Content-Type":"application/json"
            }
        }).success(function(response){
            alert("success");
            var a=response.data;
            $scope.error=null;
            $state.go('product',{data:a});
        }).error(function(response){
            alert("error") ;
            $scope.error=response.error;
        });
    }

});



