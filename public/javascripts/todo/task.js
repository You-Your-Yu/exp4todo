
/*
 * タスクテーブルの描画
 */
function renderTaskTable(data) {
	// テーブルとページャーのリセット
	var $table = $('#task-table');
	$table.find('tr').remove(':not(:first-child)');
	var $pager = $('.pager');
	$pager.children().remove();

	$(data).each(function() {
		var task = this;
		var $taskNameAnchor = $('<a></a>').text(task.taskName)
		.attr('href', '#')
		.addClass('task-name-anchor');
		var $form = $('<form></form>')
		.attr('action', 'taskDetail')
		.attr('method', 'post');
		$hidden = $('<input>')
		.attr('name', 'taskId')
		.attr('type', 'hidden')
		.attr('value', task.id);
		$form
		.append($hidden)
		.append($taskNameAnchor);
		var $taskName = $('<td></td>').append($form);
		var $picName = $('<td></td>').text(task.picName);
		var $taskState = $('<td></td>').text(task.taskState);
		var $limitTime = $('<td></td>').text(task.limitTime);
		$('<tr></tr>')
		.addClass('pagination-data')
		.append($taskName)
		.append($picName)
		.append($taskState)
		.append($limitTime)
		.appendTo($table);
	});
}

function filterData(data, filterNames) {
	console.dir(data);
	var newData = new Set();
	$(filterNames).each(function() {
		var filterName = this.toString();
		switch(filterName) {
		case 'incompleted':
			console.log('case incompleted:');
			$(data).each(function() {
				var currentData = this;
				if(currentData.taskState == '未完了') {
					newData.add(currentData);
				}
			});
			break;
		case 'completed':
			console.log('case completed:');
			$(data).each(function() {
				var currentData = this;
				if(currentData.taskState == '完了済み') {
					newData.add(currentData);
				}
			});
			break;
		case 'all':
			console.log('case all:');
			$(data).each(function() {
				var currentData = this;
				newData.add(currentData);
			});
			break;
		default:
			console.log('default');
			break;
		}
	});
	console.dir(newData);
	return Array.from(newData);
}
/*
 * 有効なフィルタ名の配列を取得する
 */
function getValidFilterNames() {
	var ret = [];
	$('ul.filter-btn').children().each(function() {
		var $child = this;
		if($($child).hasClass('on')) {
			ret.push($($child).attr('id'));
		}
	});
	console.dir(ret);
	return ret;
}