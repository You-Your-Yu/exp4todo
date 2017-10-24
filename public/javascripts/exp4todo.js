/* exp4todo.js */
/* -*- coding: utf-8 -*- */

/* login */
$(function() {
	$('.login').on('click', function() {
		$('#loginForm').action = '/login';
	});
	$('.register').on('click', function() {
		$('#loginForm').action = '/register.html';
	});
});


