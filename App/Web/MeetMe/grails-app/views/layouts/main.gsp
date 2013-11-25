<!doctype html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
	<link rel="stylesheet" href="${resource(dir: 'css', file: 'basic.css')}" type="text/css">
	<title><g:layoutTitle default="Meet Me"/></title>
	<g:layoutHead/>
</head>

<body>
	<div class="basic-main" >
		<div class="basic-main">
			<div class="basic-menu">
				<a href="<g:createLink controller="event" action="created"/>">My Events</a>
				<br/>
				<a href="<g:createLink controller="event" action="newEvent"/>">Create Event</a>
				<br/>
				<a href="<g:createLink controller="event" action="list"/>">Find Events</a>
			</div>
			<div class="basic-data" >
				<g:layoutBody/>
			</div>
		</div>
		<div class="basic-footer">
			<hr color="green" size="3px" />
		</div>
	</div>
</body>
</html>