
<%@ page import="pwr.itapp.meetme.auth.User"%>
<g:applyLayout name="event_layout">
	<head>
<title>${message(code: 'title.user.UserManagementConsole')}</title>
	</head>
	<content tag="event_content">
	<div id="list-user" class="content scaffold-list" role="main">
		<h1>${message(code: 'title.user.UserManagementConsole')}</h1>
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
			<thead>
				<tr>
					<g:sortableColumn property="name"
						title="${message(code: 'lbl.user.name')}" />

					<g:sortableColumn property="email"
						title="${message(code: 'lbl.user.email')}" />

					<g:sortableColumn property="username"
						title="${message(code: 'lbl.user.username', default: 'Username')}" />

					<g:sortableColumn property="phone"
						title="${message(code: 'lbl.user.phone')}" />

					<td>${message(code: 'lbl.user.status')}</td>
					<td>${message(code: 'lbl.user.lock')}</td>
					<td>${message(code: 'lbl.user.expire')}</td>
					<td>${message(code: 'lbl.user.enable')}</td>
					<td>${message(code: 'lbl.user.actions')}</td>
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
						
						<g:link action="activate" id="${userInstance.id}">
								${message(code: 'msg.meetme.activate')}
							</g:link>
						</g:if> <g:if test="${userInstance.status}">
						<g:link action="deactivate" id="${userInstance.id}">
								${message(code: 'msg.meetme.deactivate')}
							</g:link>
						</g:if></td>
						<td><g:if test="${userInstance.accountLocked}">
							<g:link action="unlock" id="${userInstance.id}">
								${message(code: 'msg.meetme.unlock')}
							</g:link>
						</g:if> <g:if test="${!userInstance.accountLocked}">
							<g:link action="lock" id="${userInstance.id}">
								${message(code: 'msg.meetme.lock')}
							</g:link>
						</g:if></td>
						<td><g:if test="${userInstance.accountExpired}">
							<g:link action="extend" id="${userInstance.id}">
								${message(code: 'msg.meetme.extend')}
							</g:link>
						</g:if> <g:if test="${!userInstance.accountExpired}">
							<g:link action="expire" id="${userInstance.id}">
								${message(code: 'msg.meetme.expire')}
							</g:link>
						</g:if></td>
						<td><g:if test="${!userInstance.enabled}">
							<g:link action="enable" id="${userInstance.id}">
								${message(code: 'msg.meetme.enable')}
							</g:link>
						</g:if> <g:if test="${userInstance.enabled}">
							<g:link action="disable" id="${userInstance.id}">
								${message(code: 'msg.meetme.disable')}
							</g:link>
						</g:if></td>






						<td><g:link action="show" id="${userInstance.id}">
								${message(code: 'msg.meetme.display')}
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
