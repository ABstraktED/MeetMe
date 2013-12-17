<!doctype html>
<html>
	<head>
		<meta charset="UTF-8">
		<meta name="viewport"
			content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
		<link rel="stylesheet" href="${resource(dir: 'css', file: 'meetme-basic-layout.css')}" type="text/css">
		<g:javascript src="jquery-1.10.2.min.js" />
		<g:javascript src="jquery-ui-1.10.3.custom.min.js" />
		
		<title><g:layoutTitle default="Meet Me" /></title>
		
		<g:set var="userId" value="${sec.loggedInUserInfo(field:'id')}" />
		<g:layoutHead />
	</head>

	<body>
		
		<div id="header">
		
			<div id="logo" class="clearfix">
			</div>
			
			<div id="login-panel">
			
				<sec:ifLoggedIn>
					<div id="login-icon"></div>
					<div id="login-user">
						<sec:username />	
					</div>
					<div id="login-separator"></div>
					<div class="black-btn"><a href="<g:createLink controller="authentication" action="logout"/>" id="logout">logout</a></div>
				</sec:ifLoggedIn>
				
			</div>
			
		
			<div id="menu-container" class="ui-widget-content ui-corner-all">
			
				<ul id="menu">
			    	<li><a href="#" id="a1">events</a></li>
			    	<li><a href="#" id="a2">locations</a></li>
			   		<li><a href="#" id="a3">contacts</a></li>
			    	<li><a href="#" id="a4">profile</a></li>
			    </ul>
			</div>
			
		</div>
		
		<div id="menu-highlight" >
			<div id="menu-h-1" class="ui-widget-content ui-corner-all"></div>
			<div id="menu-h-2"></div>
			<div id="menu-h-3"></div>
			<div id="menu-h-4"></div>
		</div>
		
		<div id="basic-content">
			<g:layoutBody />
		</div>
		
		
		<script>
			$(document).ready(function(){

				var t = 100;
				
				 $('#a1').hover(function(){
					  $(this).stop(true, false).animate({
				          color: "rgb(255,255,255)"
				      }, t);
				      $('#menu-h-1').stop(true, false).animate({
				    	  backgroundColor: "rgb(56,186,90)"
				      }, t);
				    },function(){
				    	 $(this).stop(true, false).animate({
				             color: "rgba(0,0,0,0.4)"
				         }, t);
				    	 $('#menu-h-1').stop(true, false).animate({
				    		 backgroundColor: "rgba(0,0,0,0.0)"
					      }, t);
				  });

				 $('#a2').hover(function(){
					  $(this).stop(true, false).animate({
				          color: "rgb(255,255,255)"
				      }, t);
				      $('#menu-h-2').stop(true, false).animate({
				    	  backgroundColor: "rgb(56,186,90)"
				      }, t);
				    },function(){
				    	 $(this).stop(true, false).animate({
				             color: "rgba(0,0,0,0.4)"
				         }, t);
				    	 $('#menu-h-2').stop(true, false).animate({
				    		 backgroundColor: "rgba(0,0,0,0.0)"
					      }, t);
				  });

				 $('#a3').hover(function(){
					  $(this).stop(true, false).animate({
				          color: "rgb(255,255,255)"
				      }, t);
				      $('#menu-h-3').stop(true, false).animate({
				    	  backgroundColor: "rgb(56,186,90)"
				      }, t);
				    },function(){
				    	 $(this).stop(true, false).animate({
				             color: "rgba(0,0,0,0.4)"
				         }, t);
				    	 $('#menu-h-3').stop(true, false).animate({
				    		 backgroundColor: "rgba(0,0,0,0.0)"
					      }, t);
				  });

				 $('#a4').hover(function(){
					  $(this).stop(true, false).animate({
				          color: "rgb(255,255,255)"
				      }, t);
				      $('#menu-h-4').stop(true, false).animate({
				    	  backgroundColor: "rgb(56,186,90)"
				      }, t);
				    },function(){
				    	 $(this).stop(true, false).animate({
				             color: "rgba(0,0,0,0.4)"
				         }, t);
				    	 $('#menu-h-4').stop(true, false).animate({
				    		 backgroundColor: "rgba(0,0,0,0.0)"
					      }, t);
				  });
			  
			});
		</script>
		
	</body>
</html>