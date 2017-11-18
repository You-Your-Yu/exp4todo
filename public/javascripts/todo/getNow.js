$(function() {
		var now = new Date();
		var year = now.getFullYear();
		var month = now.getMonth() + 1;
		var day = now.getDate();
		var hour = now.getHours();
		var formatedNow = year + '-' + month + '-' + day + 'T' + hour +':00:00';
		$('.datetime-local').attr('value', formatedNow);
});