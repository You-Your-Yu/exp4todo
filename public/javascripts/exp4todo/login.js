/* login */
$(function() {
	$('.login').on('click', function() {
		//alert('login');
		$('#login-form').action = '/login';
	});
	$('.register').on('click', function() {
		//alert('register');
		$('#login-form').action = '/register';
	});
});
