angular.module('starter.controllers', [])

.controller('DashCtrl', function($scope) {})

.controller('ChatsCtrl', function($scope, Chats) {
  // With the new view caching in Ionic, Controllers are only called
  // when they are recreated or on app start, instead of every page change.
  // To listen for when this page is active (for example, to refresh data),
  // listen for the $ionicView.enter event:
  //
  //$scope.$on('$ionicView.enter', function(e) {
  //});
//  $scope.showMsg = function () {  //调用这个方法就OK了
//    alert('showMsg威武...威武...');
//      window.plugins.PluginDemo.showmsg("测试插件", success, error);
//	};
  $scope.chats = Chats.all();
  $scope.remove = function(chat) {
    Chats.remove(chat);
  };

	var success = function (success) {//成功回调函数
	  alert(success);
	};

	var error = function (fail) {//失败回调函数
	  alert(fail);
	};
  $scope.showAlert = function() {
    alert('开始连接...威武...');
    cordova.exec(function(succ){
                       		   alert('成功'+succ);
                         },function(err){
                       		   alert('失败'+err);
                         }, "CordovaSuperSocketClient", "connect", ['MyApp','testWorld']);
  };

})

.controller('ChatDetailCtrl', function($scope, $stateParams, Chats) {
  $scope.chat = Chats.get($stateParams.chatId);
})

.controller('AccountCtrl', function($scope) {
  $scope.settings = {
    enableFriends: true
  };
});
