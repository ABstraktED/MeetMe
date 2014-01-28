<%@ page import="pwr.itapp.meetme.auth.User"%>
<g:applyLayout name="contact_layout">
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
							${fieldValue(bean: contactInstance, field: "email")}
						</td>
						<td><g:set var="emailValue"
								value="${fieldValue(bean: contactInstance, field: "email")}" />
							<g:if test="${emailValue != '---'}">
								<a
									href="<g:createLink action="insertContact" controller="contact"/>?email=${fieldValue(bean: contactInstance, field: "email")}"
									onclick="return confirm('Are you sure you want do add this contact?')"><g:message
										code='msg.meetme.addContact' /></a>
							</g:if></td>
					</tr>
				</g:each>

			</tbody>
		</table>
		<div class="pagination">
			<g:paginate controller="contact" action="displayGoogleContact"
				total="${contactsNumber}" />
		</div>
		<a
			href="<g:createLink action="contactFromGoogleContactsDone" controller="contact"/>"><g:message
				code='msg.meetme.done' /></a>
	</div>
	</content>
</g:applyLayout>
