/**
 * Created by lav on 3/8/16.
 */
routerApp.controller("createPurchase",function ($scope,$http,$state,$stateParams){

    alert("createPurchase");
    var  url="http://localhost:8080/viewAllInventory";
    $http.get(url).success(function(response){
        alert("inventories fetched");
        $scope.result=response;
    });

    $scope.table = { itemName: [],cost:[],quantity:[] };






    $scope.addFormField = function() {
        $scope.table.itemName.push('');
        $scope.table.cost.push('');
        $scope.table.quantity.push('');
    }

    $scope.submitTable = function() {
        alert("submit");
        var url="/createNewPurchase";
        console.log($scope.table);
        $http({
            url:url,
            data:$scope.table,
            dataType:'json',
            method:'POST',
            headers:{
                "Content-Type":"application/json"
            }
        }).success(function(response){
            alert("success");
            var a=response.data;
            $state.go('purchase',{data:a});
        }).error(function(response){
           alert("error") ;
            $scope.error=response.error;
        });

    }






});
routerApp.controller("initialController",function($scope,$http,$state){
    alert("purchasepannel");


    $scope.start=function(){
        $state.go('purchase');
    }
});





routerApp.controller("viewPurchase",function($scope,$http){

    alert("viewpurchase");

    var  url="http://localhost:8080/viewPurchaseForUser";
    $http.get(url).success(function(response){

       $scope.item1=response.purchaseItems;
        $scope.result=response;
    })


    $scope.delete=function(purchaseno,name){
        alert("delete");
        alert(name);
        alert(purchaseno);
        var  url="http://localhost:8080/deletePurchaseItem?purchaseno="+purchaseno+"&name="+name;
        $http.get(url).success(function(response){

            alert("success");
            $scope.result=response;
            $scope.data2=null;
        }).error(function(response){
            alert("error");

            $scope.data1=response.data;
            $scope.data2=null;
        })
    }


    $scope.edit=function(purchaseno,name)
    {
        alert("edit");
        alert(name);
        alert(purchaseno);
        var  url="http://localhost:8080/editPurchaseItem?purchaseno="+purchaseno+"&name="+name;
        $http.get(url).success(function(response){

            alert("success");
            $scope.data2=response;
            $scope.pno=purchaseno;
        }).error(function(response){
            alert("error");
            $scope.data2=null;
            $scope.data1=response.error;
        })
    }

    $scope.update= function (itemid,quantity,pno) {
        alert("update");
        alert(itemid);
        alert(quantity);
        alert(pno);

        var  url="http://localhost:8080/updatePurchaseOrder?purchaseno="+pno+"&itemid="+itemid+"&updatedQuantity="+$scope.modifiedQuantity;
        $http.get(url).success(function(response){
                alert("success");
            $scope.result=response;
            $scope.data2=null;
        })

    }

});






routerApp.controller("homeController", function ($scope,$stateParams) {
    alert("purchasehome");

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
            $state.go('purchase',{data:a});
        }).error(function(response){
            alert("error") ;
            $scope.error=response.error;
        });
    }

});


