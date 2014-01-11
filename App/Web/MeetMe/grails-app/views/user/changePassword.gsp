<%@ page import="pwr.itapp.meetme.auth.User"%>
<g:applyLayout name="event_layout">
	<head>
<title>${message(code: 'title.user.changePassword')}</title>
	</head>
	<content tag="event_content">
	<div id="show-user" class="content scaffold-show" role="main">
		<h1>
			${message(code: 'title.user.changePassword')}
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
		<table>
			<tr>
				<td>${message(code: 'lbl.user.currentPassword')}</td>
				<td><input type="password" name="oldPassword" /></td>
			</tr>
<tr>
				<td>${message(code: 'lbl.user.newPassword')}</td>
				<td><input type="password" name="newPassword" /></td>
			</tr><tr>
				<td>${message(code: 'lbl.user.repeatPassword')}</td>
				<td><input type="password" name="newPassword2" /></td>
			</tr>
		</table>





		<g:form>
			<div class="buttons">
				<g:hiddenField name="id" value="${userInstance?.id}" />
				<g:actionSubmit class="save" action="changePasswordPost" value="${message(code: 'msg.meetme.change')}" />
			</div>
		</g:form>
	</div>
	</content>
</g:applyLayout>