
<%@ page import="pwr.itapp.meetme.auth.User"%>
<g:applyLayout name="event_layout">
	<head>
<g:set var="entityName"
	value="${message(code: 'user.label', default: 'User')}" />
<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<content tag="event_content">
	<div id="show-user" class="content scaffold-show" role="main">
		<h1>
			<g:message code="default.show.label" args="[entityName]" />
		</h1>
		<g:if test="${flash.message}">
			<div class="message" role="status">
				${flash.message}
			</div>
		</g:if>
		<table>
			<tr>
				<td>User name</td>
				<td><g:if test="${userInstance?.username}">

						<g:fieldValue bean="${userInstance}" field="username" />

					</g:if> <g:if test="${!userInstance?.username}">
					
						---
					
				</g:if></td>
			</tr>


			<tr>
				<td>Email</td>
				<td><g:if test="${userInstance?.email}">
						<g:fieldValue bean="${userInstance}" field="email" />
					</g:if> <g:if test="${!userInstance?.email}">
				---
				</g:if></td>
			</tr>

			<tr>
				<td>Name</td>
				<td><g:if test="${userInstance?.name}">
						<g:fieldValue bean="${userInstance}" field="name" />
					</g:if> <g:if test="${!userInstance?.name}">
			---
		</g:if></td>
			</tr>





			<tr>
				<td>Phone</td>
				<td><g:if test="${userInstance?.phone}">
						<g:fieldValue bean="${userInstance}" field="phone" />
					</g:if> <g:if test="${!userInstance?.phone}">
			---
		</g:if></td>
			</tr>
			<tr>
				<td>Image</td>
				<td><g:if test="${userInstance?.image}">
						<g:fieldValue bean="${userInstance}" field="image" />
					</g:if> <g:if test="${!userInstance?.image}">
			---
		</g:if></td>
			</tr>
		</table>





		<g:form>
			<div class="buttons">
				<g:hiddenField name="id" value="${userInstance?.id}" />
				<g:link class="edit" action="edit" id="${userInstance?.id}">
					<g:message code="default.button.edit.label" default="Edit" />
				</g:link>
			</div>
		</g:form>
	</div>
	</content>
</g:applyLayout>
