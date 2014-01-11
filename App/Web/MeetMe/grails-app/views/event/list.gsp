
<%@ page import="pwr.itapp.meetme.Event"%>
<g:applyLayout name="event_layout">
	<head>

<g:set var="entityName"
	value="${message(code: 'event.label', default: 'Event')}" />
<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<content tag="event_content"> <b> ${eventInstanceTotal}
	</b> event(s) found. <g:if test="${eventInstanceTotal > 0 }">
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
					<g:each in="${eventInstanceList}" status="i" var="eventInstance">
						<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">

							<td><g:link action="show" id="${eventInstance.id}">
									${fieldValue(bean: eventInstance, field: "title")}
								</g:link></td>

							<td>
								${fieldValue(bean: eventInstance, field: "location.address")}
							</td>

							<td>
								${fieldValue(bean: eventInstance, field: "user.username")}
							</td>
						</tr>
					</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${eventInstanceTotal}" />
			</div>
		</div>
	</g:if> </content>
</g:applyLayout>
