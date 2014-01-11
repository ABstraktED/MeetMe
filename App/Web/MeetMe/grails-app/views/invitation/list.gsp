<%@ page import="pwr.itapp.meetme.Invitation"%>
<g:applyLayout name="event_layout">
	<head>
<g:set var="entityName"
	value="${message(code: 'invitation.label', default: 'Invitation')}" />
<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<content tag="event_content"> <g:if test="${flash.message}">
		<div class="message" role="status">
			${flash.message}
		</div>
	</g:if> <g:if test="${flash.error}">
		<div class="alert alert-error" style="display: block">
			${flash.error}
		</div>
	</g:if> You have <b> ${invitationInstanceTotal}
	</b> invitation(s). <g:if test="${invitationInstanceTotal > 0 }">
		<div>
			<table style="border-spacing: 20px;">
				<thead>
					<tr>
						<td>Title</td>
						<td>Location</td>
						<td>Creator</td>
					</tr>
				</thead>
				<tbody>
					<g:each in="${invitationInstanceList}" status="i"
						var="invitationInstance">
						<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">

							<td><g:link controller="event" action="show"
									id="${invitationInstance.event.id}">
									${fieldValue(bean: invitationInstance, field: "event.title")}
								</g:link></td>

							<td>
								${fieldValue(bean: invitationInstance, field: "event.location.address")}
							</td>

							<td>
								${fieldValue(bean: invitationInstance, field: "event.user.username")}
							</td>
						</tr>
					</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${invitationInstanceTotal}" />
			</div>
		</div>
	</g:if> </content>
</g:applyLayout>
