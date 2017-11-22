$(function() {
		var now = new Date();
		var format = 'yyyy-MM-ddTHH:mm:00';
		var format = format.replace(/yyyy/g, now.getFullYear());
		// 0詰めを行うため、左に0連結し、後ろ2文字をsliceする
		var format = format.replace(/MM/, ('0' + (now.getMonth()+1)).slice(-2));
		var format = format.replace(/dd/, ('0' + now.getDate()).slice(-2));
		var format = format.replace(/HH/, ('0' + now.getHours()).slice(-2));
		var format = format.replace(/mm/, ('0' + now.getMinutes()).slice(-2));

		$('.datetime-local').attr('value', format);
});