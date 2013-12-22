
<%@ page import="pwr.itapp.meetme.auth.User"%>
<g:applyLayout name="event_layout">
	<head>
<g:set var="entityName"
	value="${message(code: 'user.label', default: 'User')}" />
<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<content tag="event_content">
	<div id="list-user" class="content scaffold-list" role="main">
		<h1>User management console</h1>
		<g:if test="${flash.message}">
			<div class="message" role="status">
				${flash.message}
			</div>
		</g:if>
		<table>
			<thead>
				<tr>
					<g:sortableColumn property="name"
						title="${message(code: 'user.name.label', default: 'Name')}" />

					<g:sortableColumn property="email"
						title="${message(code: 'user.email.label', default: 'Email')}" />

					<g:sortableColumn property="username"
						title="${message(code: 'user.username.label', default: 'Username')}" />

					<g:sortableColumn property="phone"
						title="${message(code: 'user.phone.label', default: 'Phone')}" />

					<td>Status</td>
					<td>Lock</td>
					<td>Expire</td>
					<td>Enable</td>
					<td>Actions</td>
				</tr>
			</thead>
			<tbody>
				<g:each in="${userInstanceList}" status="i" var="userInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">

						<td>
							${fieldValue(bean: userInstance, field: "name")}
						</td>
						<td>
							${fieldValue(bean: userInstance, field: "email")}
						</td>
						<td>
							${fieldValue(bean: userInstance, field: "username")}
						</td>
						<td>
							${fieldValue(bean: userInstance, field: "phone")}
						</td>

						<td><g:if test="${!userInstance.status}">
							Deactivate
						</g:if> <g:if test="${userInstance.status}">
							Activate
						</g:if></td>
						<td><g:if test="${userInstance.accountLocked}">
							Locked
						</g:if> <g:if test="${!userInstance.accountLocked}">
							Unlocked
						</g:if></td>
						<td><g:if test="${userInstance.accountExpired}">
							Expired
						</g:if> <g:if test="${!userInstance.accountExpired}">
							OK
						</g:if></td>
						<td><g:if test="${!userInstance.enabled}">
							Disabled
						</g:if> <g:if test="${userInstance.enabled}">
							Enabled
						</g:if></td>






						<td><g:link action="show" id="${userInstance.id}">
								Display
							</g:link></td>

					</tr>
				</g:each>
			</tbody>
		</table>
		<div class="pagination">
			<g:paginate total="${userInstanceTotal}" />
		</div>
	</div>
	</content>
</g:applyLayout>
