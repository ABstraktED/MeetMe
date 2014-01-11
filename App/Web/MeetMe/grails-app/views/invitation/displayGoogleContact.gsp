<%@ page import="pwr.itapp.meetme.auth.User"%>
<g:applyLayout name="event_layout">
	<head>
<title><g:message code='title.invitation.displayGoogleContact' /></title>
	</head>
	<content tag="event_content">
	<div id="list-user" class="content scaffold-list" role="main">
		<h1>
			<g:message code='title.invitation.displayGoogleContact' />
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


		<table style="border-spacing: 20px;">
			<thead>
				<tr>
					<td><g:message code='lbl.user.name' /></td>
					<td><g:message code='lbl.user.phone' /></td>
					<td><g:message code='lbl.user.email' /></td>
					<td></td>
					<td></td>
				</tr>
			</thead>
			<tbody>
				<g:each in="${contacts}" status="i" var="contactInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
						<td>
							${fieldValue(bean: contactInstance, field: "name")}
						</td>
						<td>
							${fieldValue(bean: contactInstance, field: "phone")}
						</td>
						<td>
							${fieldValue(bean: contactInstance, field: "email")}
						</td>
						<td><g:set var="emailValue"
								value="${fieldValue(bean: contactInstance, field: "email")}" />
							<g:if test="${emailValue != '---'}">
								<a
									href="<g:createLink action="inviteByEmail" controller="invitation"/>?email=${fieldValue(bean: contactInstance, field: "email")}&eventId=${eventId}"
									onclick="return confirm('Are you sure you want do invite this contact?')"><g:message
										code='msg.meetme.inviteByEmail' /></a>
							</g:if></td>
						<td><g:set var="phoneValue"
								value="${fieldValue(bean: contactInstance, field: "phone")}" />
							<g:if test="${phoneValue != '---'}">
								<a
									href="<g:createLink action="inviteByPhone" controller="invitation"/>?phone=${fieldValue(bean: contactInstance, field: "phone")}"
									onclick="return confirm('Are you sure you want do invite this contact?')"><g:message
										code='msg.meetme.inviteByPhone' /></a>
							</g:if></td>
					</tr>
				</g:each>

			</tbody>
		</table>
		<div class="pagination">
			<g:paginate controller="invitation" action="displayGoogleContact"
				total="${contactsNumber}" />
		</div>
		<a
			href="<g:createLink action="inviteFromGoogleContactsDone" controller="invitation"/>"><g:message
				code='msg.meetme.done' /></a>
	</div>
	</content>
</g:applyLayout>


