cordova.define("org.apache.cordova.echo.echo", function(require, exports, module) {
var cordova=require('cordova');
   
  var Echo =function(){
	   Echo.prototype.echo=function(success,error,str){
		 cordova.exec(sucess,error,'Echo','echo',str)
		 //'Echo'对应我们在java文件中定义的类名，echo对应我们在这个类中调用的自定义方法，str是我们客户端传递给这个方法的参数，是个数组
	   }
  }
  var  echo=new Echo();
  module.exports=echo;
});
