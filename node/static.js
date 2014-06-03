var url = require('url'),
path = require('path'),
fs = require('fs'),
mime = require('mime');

var Static = function(request, response) {
  var uri = url.parse(request.url).pathname, filename = path.join(process.cwd(), uri);
  fs.exists(filename, function(exists) {
    if(!exists) {
      response.writeHead(404, {"Content-Type": "text/plain"});
      response.write("404 Not Found\n");
      response.end();
      return;
    }

    if (fs.statSync(filename).isDirectory()) filename += '/index.html';

    fs.readFile(filename, "binary", function(err, file) {
      if(err) {
        response.writeHead(500, {"Content-Type": "text/plain"});
        response.write(err + "\n");
        response.end();
        return;
      }

      console.log(request.url);
      response.writeHead(200, {
        "Content-Type": mime.lookup(filename),
        "Access-Control-Allow-Origin": "*",
      });
      response.write(file, "binary");
      response.end();
    });
  });
}

module.exports = Static;
