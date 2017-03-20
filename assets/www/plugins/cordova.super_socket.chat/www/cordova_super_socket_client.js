cordova.define("cordova.super_socket.chat.cordovaSuperSoccketClient", function(require, exports, module) {
var cordova=require('cordova');

  var CordovaSuperSocketClient =function(){
	   CordovaSuperSocketClient.prototype.cordovaSuperSoccketClient=function(success,error,str){
		 cordova.exec(sucess,error,'CordovaSuperSocketClient','cordovaSuperSoccketClient',str)
		 //'Echo'对应我们在java文件中定义的类名，echo对应我们在这个类中调用的自定义方法，str是我们客户端传递给这个方法的参数，是个数组
	   }
  }
  var  cordovaSuperSoccketClient = new CordovaSuperSocketClient();
  module.exports = cordovaSuperSoccketClient;

});
