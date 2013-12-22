
<%@ page import="pwr.itapp.meetme.auth.User"%>
<g:applyLayout name="event_layout">
	<head>
<g:set var="entityName"
	value="${message(code: 'user.label', default: 'User')}" />
<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<content tag="event_content">
	<div id="show-user" class="content scaffold-show" role="main">
		<h1>
			<g:message code="default.show.label" args="[entityName]" />
		</h1>
		<g:if test="${flash.message}">
			<div class="message" role="status">
				${flash.message}
			</div>
		</g:if>
		<table>
			<tr>
				<td>Current password</td>
				<td><input type="password" name="oldPassword" /></td>
			</tr>
<tr>
				<td>New password</td>
				<td><input type="password" name="newPassword" /></td>
			</tr><tr>
				<td>Repeat new password</td>
				<td><input type="password" name="newPassword2" /></td>
			</tr>
		</table>





		<g:form>
			<div class="buttons">
				<g:hiddenField name="id" value="${userInstance?.id}" />
				<g:actionSubmit class="save" action="changePasswordPost" value="Submit" />
			</div>
		</g:form>
	</div>
	</content>
</g:applyLayout>
