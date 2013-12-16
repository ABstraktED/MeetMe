<!doctype html>
<html>
	<head>
		<meta charset="UTF-8">
		<meta name="viewport"
			content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
		<link rel="stylesheet" href="${resource(dir: 'css', file: 'meetme-basic-layout.css')}" type="text/css">
		<g:javascript src="jquery-1.10.2.min.js" />
		
		<title><g:layoutTitle default="Meet Me" /></title>
		
		<g:set var="userId" value="${sec.loggedInUserInfo(field:'id')}" />
		<g:layoutHead />
	</head>

	<body>
		
		<div id="header">
		
			<div id="logo" class="clearfix">
			</div>
		
			<div id="menu-container" >
			
				<div id="hello">
					<sec:ifLoggedIn>
						hi, <sec:username />
					</sec:ifLoggedIn>		
				</div>
				
				<ul id="menu">
			    	<li><a href="#">events</a></li>
			    	<li><a href="#">locations</a></li>
			   		<li><a href="#">contacts</a></li>
			    	<li><a href="#">profile</a></li>
			    </ul>
			</div>
			
		</div>
		
		<div id="menu-highlight">
		
		</div>
		
		<g:layoutBody />
		
	</body>
</html>