
<%@ page import="pwr.itapp.meetme.auth.User"%>
<g:applyLayout name="event_layout">
	<head>
<title>${message(code: 'title.user.myprofile')}</title>
	</head>
	<content tag="event_content">
	<div id="show-user" class="content scaffold-show" role="main">
		<h1>${message(code: 'title.user.myprofile')}</h1>
		<g:if test="${flash.message}">
			<div class="message" role="status">
				${flash.message}
			</div>
		</g:if>
		<table>
			<tr>
				<td>
					${message(code: 'lbl.user.username')}
				</td>
				<td><g:if test="${userInstance?.username}">
						<g:fieldValue bean="${userInstance}" field="username" />
					</g:if> <g:if test="${!userInstance?.username}">
						${message(code: 'lbl.user.novalue')}
					</g:if></td>
			</tr>
			<tr>
				<td>
					${message(code: 'lbl.user.email')}
				</td>
				<td><g:if test="${userInstance?.email}">
						<g:fieldValue bean="${userInstance}" field="email" />
					</g:if> <g:if test="${!userInstance?.email}">
						${message(code: 'lbl.user.novalue')}
					</g:if></td>
			</tr>
			<tr>
				<td>
					${message(code: 'lbl.user.name')}
				</td>
				<td><g:if test="${userInstance?.name}">
						<g:fieldValue bean="${userInstance}" field="name" />
					</g:if> <g:if test="${!userInstance?.name}">
						${message(code: 'lbl.user.novalue')}
					</g:if></td>
			</tr>
			<tr>
				<td>
					${message(code: 'lbl.user.phone')}
				</td>
				<td><g:if test="${userInstance?.phone}">
						<g:fieldValue bean="${userInstance}" field="phone" />
					</g:if> <g:if test="${!userInstance?.phone}">
						${message(code: 'lbl.user.novalue')}
					</g:if></td>
			</tr>
			<tr>
				<td>
					${message(code: 'lbl.user.image')}
				</td>
				<td><g:if test="${userInstance?.image}">
						<g:fieldValue bean="${userInstance}" field="image" />
					</g:if> <g:if test="${!userInstance?.image}">
						${message(code: 'lbl.user.novalue')}
					</g:if></td>
			</tr>
		</table>





		<g:form>
			<div class="buttons">
				<g:hiddenField name="id" value="${userInstance?.id}" />
				<g:link class="edit" action="edit" id="${userInstance?.id}">
					${message(code: 'msg.meetme.edit')}
				</g:link>
			</div>
		</g:form>
	</div>
	</content>
</g:applyLayout>
