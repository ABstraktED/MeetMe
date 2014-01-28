<g:applyLayout name="contact_layout">
	<head>
<title><g:message code='title.contact.displayGoogleContact' /></title>
	</head>
	<content tag="event_content">
	<div>
		<form action="${postUrl}">
			<h1>
				<g:message code='title.contact.displayGoogleContact' />
			</h1>
			<g:if test="${flash.message}">
				<div class="message" role="status">
					${flash.message}
				</div>
			</g:if>
			<g:if test="${flash.error}">
				<div class="alert alert-error" style="display: block">
					${flash.error}
				</div>
			</g:if>
			<div>
				<g:message code='msg.meetme.googleAccountLogin' />
			</div>
			<div>
				<label><g:message code='lbl.user.email' /></label> <input
					type="text" name="username" /><span>@gmail.com</span> <br />
				<div>
					<label><g:message code='lbl.user.password' /></label> <input
						type="password" name="password" />
				</div>
				<br /> <input type="submit" />
			</div>
			<span>
				<g:message code='msg.invitation.portalNotStoresPrivateData' />
			</span>
		</form>
	</div>
	</content>
</g:applyLayout>