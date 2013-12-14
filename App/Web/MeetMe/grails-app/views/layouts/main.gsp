<!doctype html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
<link rel="stylesheet" href="${resource(dir: 'css', file: 'basic.css')}"
	type="text/css">
<g:javascript src="jquery-1.10.2.min.js" />
<title><g:layoutTitle default="Meet Me" /></title>
<g:set var="userId" value="${sec.loggedInUserInfo(field:'id')}" />
<g:layoutHead />
</head>

<body>
	<div class="basic-main">
		<div class="basic-main">
			<div class="basic-menu">
				<a href="<g:createLink controller="event" action="created"/>">My
					Events</a> <br /> <a
					href="<g:createLink controller="event" action="newEvent"/>">Create
					Event</a> <br /> <a
					href="<g:createLink controller="event" action="list"/>">Find
					Events</a><br /> -----<br /> <a
					href="<g:createLink controller="user" action="create"/>">Create
					user (as Everyone) </a> <br /> <a
					href="<g:createLink controller="user" action="createAdmin"/>">Create
					user (as Admin) </a> <br /> <a
					href="<g:createLink controller="user" action="listAdmin"/>">User
					list (as Admin)</a> <br />
				<sec:ifLoggedIn>
					<a href="<g:createLink controller="user" action="edit"/>/${userId}">
						Edit (myself) </a>
					<br />
					<a href="<g:createLink controller="user" action="show"/>/${userId}">
						Show (myself) </a>
					<br />
				Welcome <sec:username />, 
	<a href="<g:createLink controller="authentication" action="logout"/>">Logout</a>
				</sec:ifLoggedIn>
				<sec:ifNotLoggedIn>
					<a
						href="<g:createLink controller="authentication" action="login"/>">Login</a>
				</sec:ifNotLoggedIn>
			</div>
			<div class="basic-data">
				<g:layoutBody />
			</div>
		</div>
		<div class="basic-footer">
			<hr color="green" size="3px" />
		</div>
	</div>
</body>
</html>