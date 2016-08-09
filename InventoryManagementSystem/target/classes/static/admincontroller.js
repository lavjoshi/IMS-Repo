/**
 * Created by lav on 27/7/16.
 */
routerApp.controller("inventoryController",function ($scope,$http,$state,$stateParams){

    alert("insert");
    $scope.msg=$stateParams.msg;

     $scope.insert=function() {
         var name = $scope.name;
         var unitOfMeasurement = $scope.unitOfMeasurement;
         var unitCost=$scope.unitCost;

         var dimension=$scope.dimension;
         //var data={'name':name,'unitOfMeasurement':unitOfMeasurement,'unitCost':unitCost,'openingBalance':openingBalance,'dimension':dimension};

         var url = "http://localhost:8080/createnewInventory?name=" + name + "&unitOfMeasurement=" + unitOfMeasurement+ "&unitCost="+unitCost+ "&dimension="+dimension;
         $http({
             url:url,
             method:'GET',

         }).success(function (response) {

            alert("success");
             var a=response.data;
             alert(a);
             $state.go('admin',{data:a});


         }).error(function (response) {
             alert("error");
             $scope.msg=response.error;
             alert(response.error);
         })
     } });
routerApp.controller("initialController",function($scope,$http,$state){
  alert("initial");


    $scope.start=function(){
       $state.go('admin');
   }
     });





routerApp.controller("viewInventory",function($scope,$http){

      alert("display");

         var  url="http://localhost:8080/viewAllInventory";
         $http.get(url).success(function(response){
             $scope.result=response;
             $scope.data1=null;
     })
    $scope.edit=function(name){
        alert("edit");
        alert(name);
        $scope.data1=name;
    }

    $scope.update=function(name,modifiedName,modifiedCost){
        alert("update");

        var  url="http://localhost:8080/updateInventory?name="+name+"&modifiedName="+modifiedName+"&modifiedCost="+modifiedCost;
        $http.get(url).success(function (response) {
            $scope.data1=null;
            $scope.result=response;
        })
    }

    $scope.delete=function(name){
        alert("delete");
        alert(name);
        var  url="http://localhost:8080/deleteInventory?name="+name;
        $http.get(url).success(function (response) {
        $scope.result=response;
            $scope.data1=null;
            $scope.error=null;
        }).error(function(response){
            $scope.data1=null;
            $scope.error=response.error;
        })

    }

});






routerApp.controller("homeController", function ($scope,$stateParams) {
    alert("home");

    alert($stateParams.data);
    $scope.data1=$stateParams.data;




});

routerApp.controller("newUser", function ($http,$scope,$state,$stateParams) {
    alert("newUser");

    $scope.insert=function(){
        alert("inside create");

        var data={"userName":$scope.username,"password":$scope.password,"role":$scope.role,"email":$scope.email};
        var url="/createNewUser";
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

            $state.go('admin',{data:a});
        }).error(function(response){
           alert("error");
            $scope.error=response.error;
        });
    }




});


routerApp.controller("viewUsers", function ($scope,$http) {
    alert("view users");
    var url="/viewAllUsers";
   $http.post(url).success(function (response){
       $scope.result=response;
   });

    $scope.delete=function(name){
        var  url="/deleteUser?userName="+name;
        $http.get(url).success(function(response){
            $scope.result=response;
        }) ;
    }


});

routerApp.controller("PurchaseHistory", function ($scope,$http) {
    alert("Purchase history");
    var  url="http://localhost:8080/viewAllInventory";
    $http.get(url).success(function(response){
        $scope.result=response;
    })


    $scope.fetch=function(){
        alert("fetch");
        var  url="http://localhost:8080/purchaseHistory?name="+$scope.name;
        $http.get(url).success(function(response){
            $scope.result1=response;
            $scope.error=null;

        }).error(function(response){
            $scope.error=response.error;
            $scope.result1=null;
        })

    }

});

routerApp.controller("ProductionHistory", function ($scope,$http) {
    alert("Production history");
    var  url="http://localhost:8080/viewAllInventory";
    $http.get(url).success(function(response){
        $scope.result=response;
    })

    $scope.fetch=function(){
        alert("fetch");
        var  url="http://localhost:8080/productionHistory?name="+$scope.name;
        $http.get(url).success(function(response){
            $scope.result1=response;
            $scope.error=null;

        }).error(function(response){
            $scope.error=response.error;
            $scope.result1=null;
        })

    }



});