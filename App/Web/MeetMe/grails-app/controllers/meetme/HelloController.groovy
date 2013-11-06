package meetme

class HelloController {

    def index() {
		def user = new User()
		user.setEmail("email@email.com")
		user.setUsername("UserName")
		user.setPassword("this is a password")
		user.save()
		
		User guser = User.get(1)
		render guser.getUsername()
		render guser.getEmail()
		render "Hello World!"
	}
}
