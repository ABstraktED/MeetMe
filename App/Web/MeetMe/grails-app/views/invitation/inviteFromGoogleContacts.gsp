<!DOCTYPE html>
<html>
<head>
<meta name="layout" content="main">
<title>Invite from Google Contacts</title>
</head>
<body>
	<div>
		<form action="${postUrl}">
			<h1>Invite from Google Contacts</h1>
			<g:if test="${flash.message}">
				<div class="message" role="status">
					${flash.message}
				</div>
			</g:if>
			<div>Google account login</div>
			<div>
				<label>Email</label> <input type="text" name="username" /><span>@gmail.com</span>
				<br />
				<div>
					<label>Password</label> <input type="password" name="password" />
				</div><br/>
				<input type="submit"/>
			</div>
			<span>Portal in not storing private data</span>
			<g:hiddenField name="eventId" value="${eventId}"/>
		</form>
	</div>
</body>
</html>


