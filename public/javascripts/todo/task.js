
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
		var script = 'javascript:form' + task.id + '.submit()';
		var $taskNameAnchor = $('<a></a>').text(task.taskName)
		.attr('href', script)
		.addClass('task-name-anchor');
		var $form = $('<form></form>')
		.attr('name', 'form' + task.id)
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
		var $btns = createBtns(task.taskState);

		$('<tr></tr>')
		.addClass('pagination-data')
		.append($taskName)
		.append($picName)
		.append($taskState)
		.append($limitTime)
		.append($btns)
		.appendTo($table);
	});
}

function createBtns(taskState) {
	var script1a = 'completeTaskWithoutST()';
	var script1b = 'incompleteTaskWithoutST()';
	var script2 = 'editTaskNameWithoutST()';
	var script3 = 'delteTaskWithoutST()';

	var ret = $('<td></td>');
	var ul = $('<ul></ul>').addClass('btns');
	var li1 = $('<li></li>');
	var btn1 = $('<button></button>').attr('type', 'button');
	if(taskState == '未完了') {
		btn1.text('完了する').addClass('complete-btn').attr('onClick', script1a);
	}
	else {
		btn1.text('完了取消').addClass('incomplete-btn').attr('onClick', script1b);
	}
	var li2 = $('<li></li>');
	var btn2 = $('<button></button>').text('名前変更').addClass('edit-name-btn').attr('type', 'button').attr('onClick', script2);
	var li3 = $('<li></li>');
	var btn3 = $('<button></button>').text('削除する').addClass('delete-btn').attr('type', 'button').attr('onClick', script3);

	li1.append(btn1);
	li2.append(btn2);
	li3.append(btn3);
	ul.append(li1).append(li2).append(li3);
	ret.append(ul);
	return ret;
}

function completeTaskWithoutST() {
	console.log('completeTaskWithoutST');
}
function incompleteTaskWithoutST() {
	console.log('incompleteTaskWithoutST');
}
function editTaskNameWithoutST() {
	console.log('editTaskNameWithoutST');
}
function delteTaskWithoutST() {
	console.log('delteTaskWithoutST');
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