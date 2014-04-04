// Filename: app.js

define([
  // These are path alias that we configured in our bootstrap
  'jquery',     // lib/jquery/jquery
  'underscore', // lib/underscore/underscore
  'backbone',    // lib/backbone/backbone
  'views/HomeView'
], function($, _, Backbone, HomeView){
  var initialize = function() {
    var view = new HomeView(); // this calls initialize which in turn calls render
  }

  return {
    initialize : initialize
  };
});
