<%@ page import="pwr.itapp.meetme.auth.User" %>

<div class="fieldcontain ${hasErrors(bean: userInstance, field: 'username', 'error')} required">
	<label for="username">
		${message(code: 'lbl.user.username')}
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="username" required="" value="${userInstance?.username}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: userInstance, field: 'email', 'error')} ">
	<label for="email">
		${message(code: 'lbl.user.email')}		
	</label>
	<g:field type="email" name="email" value="${userInstance?.email}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: userInstance, field: 'name', 'error')} ">
	<label for="name">
		${message(code: 'lbl.user.name')}	
	</label>
	<g:textField name="name" maxlength="30" value="${userInstance?.name}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: userInstance, field: 'phone', 'error')} ">
	<label for="phone">
		${message(code: 'lbl.user.phone')}	
	</label>
	<g:textField name="phone" value="${userInstance?.phone}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: userInstance, field: 'image', 'error')} ">
	<label for="image">
		${message(code: 'lbl.user.image')}	
	</label>
	<g:textField name="image" value="${userInstance?.image}"/>
	<input name="fileToUpload" type="file" />
</div>

