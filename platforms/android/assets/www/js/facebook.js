var appId="553068804738984";var redirectUrl="http://goo.gl/rJNWs9";var permissions="basic_info, create_note, email, manage_notifications, photo_upload, public_profile, publish_actions, publish_checkins, publish_stream, share_item, status_update, user_about_me, user_activities, user_friends, user_status, video_upload";var facebook={onFacebookLogin:function(e){var t="https://m.facebook.com/dialog/oauth?";t+="client_id="+appId;t+="&redirect_uri="+redirectUrl;t+="&display=touch";t+="&response_type=token";t+="&type=user_agent";if(permissions!==""){t+="&scope="+permissions}e=e?e:"location=no";var n=window.open(t,"_blank",e);n.addEventListener("loadstart",function(e){if(e.url.indexOf("access_token")!==-1){var t=e.url.match(/access_token=(.*)$/)[1].split("&expires_in")[0];window.localStorage.setItem("facebook_accessToken",t);n.close()}if(e.url.indexOf("error_reason=user_denied")!==-1){window.localStorage.setItem("facebook_accessToken",null);n.close()}})},onFacebookShare:function(e){var t="http://goo.gl/rJNWs9";e=e?e:"location=no";var n=window.open(t,"_blank",e);n.addEventListener("loadstart",function(e){if(e.url.indexOf("access_token")!==-1){var t=e.url.match(/access_token=(.*)$/)[1].split("&expires_in")[0];window.localStorage.setItem("facebook_accessToken",t);n.close()}if(e.url.indexOf("error_reason=user_denied")!==-1){window.localStorage.setItem("facebook_accessToken",null);n.close()}})},onFacebookLogout:function(){var e=encodeURI("https://www.facebook.com/logout.php?next="+redirectUrl+"&access_token="+window.localStorage.getItem("facebook_accessToken"));var t=window.open(e,"_blank","hidden=yes,location=no");t.addEventListener("loadstart",function(n){if(n.url==e){}else if(n.url===redirectUrl+"#_=_"||n.url===redirectUrl){window.localStorage.setItem("facebook_accessToken",null);t.close()}})},onFacebookCheckWithLogin:function(){var e=window.localStorage.getItem("facebook_accessToken");var t="https://graph.facebook.com/me?access_token="+e;$.getJSON(t,function(){facebook.onFacebookLogin("hidden=yes,location=no")}).error(function(){facebook.onFacebookLogin()})},onFacebookGetInfo:function(){if(window.localStorage.getItem("facebook_accessToken")===null){return false}var e="https://graph.facebook.com/me?access_token="+window.localStorage.getItem("facebook_accessToken");$.getJSON(e,function(e){window.localStorage.setItem("facebook_uid",e.id)}).error(function(){window.localStorage.setItem("facebook_accessToken",null);window.localStorage.setItem("facebook_uid",null)})},onFacebookPostFeed:function(e){if(window.localStorage.getItem("facebook_accessToken")===null){return false}var t="https://graph.facebook.com/me/feed?access_token="+window.localStorage.getItem("facebook_accessToken");$.post(t,e).error(function(){window.localStorage.setItem("facebook_accessToken",null);window.localStorage.setItem("facebook_uid",null)})}}
