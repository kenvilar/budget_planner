var app={initialize:function(){this.bindEvents()},bindEvents:function(){document.addEventListener("deviceready",app.onDeviceReady,false)},onDeviceReady:function(){twitter.login()},getInfo:function(){facebook.onFacebookGetInfo();alert(window.localStorage.getItem("facebook_uid"))}}
