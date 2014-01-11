<%@ page import="pwr.itapp.meetme.auth.User"%>
<g:applyLayout name="event_layout">
	<head>
<title>
	${message(code: 'title.user.editProfile')}
</title>
	</head>
	<content tag="event_content">
	<div id="edit-user" class="content scaffold-edit" role="main">
		<h1>
			${message(code: 'title.user.editProfile')}
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
		<g:hasErrors bean="${userInstance}">
			<ul class="errors" role="alert">
				<g:eachError bean="${userInstance}" var="error">
					<li
						<g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message
							error="${error}" /></li>
				</g:eachError>
			</ul>
		</g:hasErrors>
		<g:form method="post">
			<g:hiddenField name="id" value="${userInstance?.id}" />
			<g:hiddenField name="version" value="${userInstance?.version}" />
			<div class="form">
				<g:render template="form" />
			</div>
			<div class="buttons">
				<g:actionSubmit class="save" action="update" value="Update" />
				<a
					href="<g:createLink controller="user" action="changePassword"/>/${userInstance?.id}">
					${message(code: 'msg.meetme.changePassword')}
				</a>
			</div>
		</g:form>
	</div>
	</content>
</g:applyLayout>
