cordova.define('cordova/plugin_list', function(require, exports, module) {
module.exports = [
    {
        "id": "org.apache.cordova.echo.echo",
        "file": "plugins/org.apache.cordova.echo/www/echo.js",
        "pluginId": "org.apache.cordova.echo",
        "clobbers": [
            "echo"
        ]
    },
    {
        "id": "cordova.super_socket.chat.cordovaSuperSoccketClient",
        "file": "plugins/cordova.super_socket.chat/www/cordova_super_socket_client.js",
        "pluginId": "cordova.super_socket.chat",
        "clobbers": [
            "cordova_super_socket_client"
        ]
    }
];
module.exports.metadata = 
// TOP OF METADATA
{
    "org.apache.cordova.echo": "0.0.1",
    "cordova.super_socket.chat": "0.0.1"
};
// BOTTOM OF METADATA
});