
<%@ page import="pwr.itapp.meetme.Event" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'event.label', default: 'Event')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-event" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-event" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list event">
			
				<g:if test="${eventInstance?.date}">
				<li class="fieldcontain">
					<span id="date-label" class="property-label"><g:message code="event.date.label" default="Date" /></span>
					
						<span class="property-value" aria-labelledby="date-label"><g:fieldValue bean="${eventInstance}" field="date"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${eventInstance?.description}">
				<li class="fieldcontain">
					<span id="description-label" class="property-label"><g:message code="event.description.label" default="Description" /></span>
					
						<span class="property-value" aria-labelledby="description-label"><g:fieldValue bean="${eventInstance}" field="description"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${eventInstance?.invitations}">
				<li class="fieldcontain">
					<span id="invitations-label" class="property-label"><g:message code="event.invitations.label" default="Invitations" /></span>
					
						<g:each in="${eventInstance.invitations}" var="i">
						<span class="property-value" aria-labelledby="invitations-label"><g:link controller="invitation" action="show" id="${i.id}">${i?.encodeAsHTML()}</g:link></span>
						</g:each>
					
				</li>
				</g:if>
			
				<g:if test="${eventInstance?.location}">
				<li class="fieldcontain">
					<span id="location-label" class="property-label"><g:message code="event.location.label" default="Location" /></span>
					
						<span class="property-value" aria-labelledby="location-label"><g:link controller="location" action="show" id="${eventInstance?.location?.id}">${eventInstance?.location?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${eventInstance?.title}">
				<li class="fieldcontain">
					<span id="title-label" class="property-label"><g:message code="event.title.label" default="Title" /></span>
					
						<span class="property-value" aria-labelledby="title-label"><g:fieldValue bean="${eventInstance}" field="title"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${eventInstance?.user}">
				<li class="fieldcontain">
					<span id="user-label" class="property-label"><g:message code="event.user.label" default="User" /></span>
					
						<span class="property-value" aria-labelledby="user-label"><g:link controller="user" action="show" id="${eventInstance?.user?.id}">${eventInstance?.user?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
			</ol>
			<g:form>
				<fieldset class="buttons">
					<g:hiddenField name="id" value="${eventInstance?.id}" />
					<g:link class="edit" action="edit" id="${eventInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
