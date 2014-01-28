<%@ page import="pwr.itapp.meetme.Contact"%>
<g:applyLayout name="contact_layout">
	<head>
<g:set var="entityName"
	value="${message(code: 'contact.label', default: 'Contact')}" />
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
	</g:if> You have <b> ${contactInstanceTotal}
	</b> contact(s). 
	<g:if test="${contactInstanceTotal > 0 }">
		<div>
			<table style="border-spacing: 20px;">
				<thead>
					<tr>
						<td>Name</td>
						<td>Username</td>
						<td>Email</td>
					</tr>
				</thead>
				<tbody>
					<g:each in="${contactInstanceList}" status="i"
						var="contactInstance">
						<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">

							<td>
								${fieldValue(bean: contactInstance, field: "friend.name")}
							</td>

							<td>
								${fieldValue(bean: contactInstance, field: "friend.username")}
							</td>

							<td>
								${fieldValue(bean: contactInstance, field: "friend.email")}
							</td>
						</tr>
					</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${contactInstanceTotal}" />
			</div>
		</div>
	</g:if> </content>
</g:applyLayout>
