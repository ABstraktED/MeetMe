<%@ page import="pwr.itapp.meetme.auth.User" %>
<g:applyLayout name="event_layout">
	<head>
		<title>${message(code: 'title.user.createNewUser')}</title>
	</head>
	<content tag="event_content">
		<div id="create-user" class="content scaffold-create" role="main">
			<h1>${message(code: 'title.user.createNewUser')}</h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<g:hasErrors bean="${userInstance}">
			<ul class="errors" role="alert">
				<g:eachError bean="${userInstance}" var="error">
				<li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
				</g:eachError>
			</ul>
			</g:hasErrors>
			<g:form action="saveFromInvitation" >
				<div class="form">
					<g:render template="createFromInvitationForm"/>
				</div>
				<div class="buttons">
					<g:submitButton name="create" class="save" value="${message(code: 'msg.meetme.create')}" />
				</div>
			</g:form>
		</div>
</content>
</g:applyLayout>