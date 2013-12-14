<%@ page import="pwr.itapp.meetme.Contact" %>



<div class="fieldcontain ${hasErrors(bean: contactInstance, field: 'user', 'error')} ">
	<label for="user">
		<g:message code="contact.user.label" default="User" />
		
	</label>
	<g:select id="user" name="user.id" from="${pwr.itapp.meetme.auth.User.list()}" optionKey="id" value="${contactInstance?.user?.id}" class="many-to-one" noSelection="['null': '']"/>
</div>

