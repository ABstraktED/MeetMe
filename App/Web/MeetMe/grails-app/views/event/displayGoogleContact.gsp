<%@ page import="pwr.itapp.meetme.auth.User" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<title>Invite from Google Contacts</title>
	</head>
	<body>
		<div id="list-user" class="content scaffold-list" role="main">
			<h1>Invite from Google Contacts</h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			
			
			<table style="border-spacing: 20px;">
			<thead>
				<tr>
					<td>Name</td>
					<td>Phone</td>
					<td>Email</td>
					<td> </td>
					<td> </td>
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
									href="<g:createLink action="inviteByEmail" controller="event"/>?email=${fieldValue(bean: contactInstance, field: "email")}&eventId=${eventId}">Invite
									by email</a>
							</g:if></td>
						<td><g:set var="phoneValue"
								value="${fieldValue(bean: contactInstance, field: "phone")}" />
							<g:if test="${phoneValue != '---'}">
								<a
									href="<g:createLink action="inviteByPhone" controller="event"/>?phone=${fieldValue(bean: contactInstance, field: "phone")}">Invite
									by phone</a>
							</g:if></td>
					</tr>
				</g:each>
				
			</tbody>
		</table>
		<a href="<g:createLink action="inviteFromGoogleContactsDone" controller="event"/>">Done</a>
		</div>
	</body>
</html>


		