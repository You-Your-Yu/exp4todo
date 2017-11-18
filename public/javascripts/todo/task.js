var tasks;

$.fn.renderTaskTable = function() {
	var dfd = jQuery.Deferred();
	$.get('renderTaskTable', function(data) {
		console.dir(data);
		tasks = data;
		$(data).each(function() {
			var task = this;
			var $taskName = $('<td></td>').text(task.taskName);
			var $picName = $('<td></td>').text(task.picName);
			var $taskState = $('<td></td>').text(task.taskState);
			var $limitTime = $('<td></td>').text(task.limitTime);

			$('<tr></tr>')
			.addClass('pagination-data')
			.append($taskName)
			.append($picName)
			.append($taskState)
			.append($limitTime)
			.appendTo('#task-table');
		});
		dfd.resolve();
	});
	return dfd.promise();
}