// Filename: main.js

require.config({
  paths: {
    "jquery": "/js/ext/jquery/dist/jquery.min",
    "underscore": "/js/ext/underscore/underscore",
    "backbone": "/js/ext/backbone/backbone",
    "datatables": "/dnsguard/web/js/ext/datatables/media/js/jquery.dataTables",
    "d3": "/dnsguard/web/js/ext/d3/d3.min",
    "d3pie": "/dnsguard/web/js/ext/d3pie/d3pie"
  },
  shim: {
      "d3pie": ['d3', 'jquery'],
      "datatables": ['jquery']
 }
});
require([
  'dns', '/js/phoenix.js'
], function(App, Phoenix) {
  new App.initialize();
  new Phoenix.initialize();
});
