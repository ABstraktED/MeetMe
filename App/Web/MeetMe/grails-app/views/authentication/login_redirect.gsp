<%@ page contentType="text/html;charset=UTF-8"%>
<html>
<g:javascript src="jquery-1.10.2.min.js" />
<script type="text/javascript">
	$(document).ready(function() {
		$('#autoLoginForm').submit();
	})
</script>
<body>
	Redirecting...
	<form action='${postUrl}' method='POST' id="autoLoginForm" name="autoLoginForm">
		
		<g:hiddenField name="j_username" value="${j_username}"/>
		<g:hiddenField name="j_password" value="${j_password}"/>
		<g:hiddenField name="${rememberMeParameter}" value="${j_rememberMe}" />
	</form>
</body>
</html>