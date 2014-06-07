// Enter all relative paths to your app's files / directories here

var appname = 'simpleappfoo';

var Config = {
 index: "../" + appname + "/src/main/resources/WEB-INF/jsp/main.jsp",
 basepath:  "../web/src/main/resources/",  // common web bundle assets
 apppath: "../" + appname + "/src/main/resources/", // app bundle assets
 appdir_to_remove: appname + "/web",


}

module.exports = Config;
