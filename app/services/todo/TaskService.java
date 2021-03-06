package services.todo;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import models.todo.consts.Consts;
import models.todo.consts.TaskState;
import models.todo.consts.TaskType;
import models.todo.dto.TaskDto;
import models.todo.entity.Task;
import models.todo.entity.Team;
import models.todo.entity.User;

public class TaskService {
	/**
	 * idでTaskを検索する
	 * @param id
	 * @return
	 */
	public static Task findTaskById(Long id) {
		return Task.find("id = ?1", id).first();
	}

	/**
	 * uidでTaskを検索する
	 * @param uid
	 * @return
	 */
	public static List<Task> findListTaskByUid(String uid) {
		return Task.find("clientUid = ?1", uid).fetch();
	}
	/**
	 * tidでTaskを検索する
	 * @return
	 */
	public static List<Task> findListTaskByTid() {
		return Task.find("tid = ?1").fetch();
	}
	/**
	 * uidでtidでTaskを
	 */
	public static List<Task> findListTaskByUidAndTid(String uid, String tid) {
		return Task.find("(clientUid = ?1 AND taskType = ?2) OR (tid = ?3 AND taskType = ?4)", uid, TaskType.PRIVATE, tid, TaskType.PUBLIC).fetch();
	}

	/**
	 * タスクを登録する
	 * @param name
	 * @param description
	 * @param tid
	 * @param clientUid
	 * @param picUid
	 * @param taskType
	 * @param limitTime
	 * @return
	 */
	public static Task registerTask(String name, String description, String tid, String clientUid, TaskType taskType, Timestamp limitTime) {
		Task task = new Task(name, description, tid, clientUid, taskType, limitTime);
		task.save();
		return task;
	}
	/**
	 * タスクを完了する
	 * @param task
	 * @return
	 */
	public static Task completeTask(Task task) {
		task.taskState = TaskState.COMPLETED;
		task.save();
		return task;
	}

	public static Task incompleteTask(Task task) {
		task.taskState = TaskState.INCOMPLETED;
		task.save();
		return task;
	}


	/**
	 * タスクを削除する
	 * @param task
	 * @return
	 */
	public static Task deleteTask(Task task) {
		task.delete();
		return task;
	}

	/**
	 * タスクを編集する
	 * @param task
	 * @param name
	 * @param description
	 * @param picUid
	 * @param taskType
	 * @param limitTime
	 * @return
	 */
	public static Task updateTask(Task task, String name, String description,
			TaskType taskType, String tid, Timestamp limitTime) {
		task.name = name;
		task.description = description;
		task.taskType = taskType;
		if(taskType == TaskType.PUBLIC) {
			task.tid = tid;
		}
		else {
			task.tid = null;
		}
		task.limitTime = limitTime;
		task.updateTime = new Timestamp(System.currentTimeMillis());
		task.save();
		return task;
	}

	public static Task renameTask(Task task, String newName) {
		task.name = newName;
		task.save();
		return task;
	}

	/**
	 * taskから画面表示用オブジェクトtaskDtoを設定する
	 * @param task
	 * @return
	 */
	public static TaskDto initTaskDto(Task task) {
		TaskDto taskDto = new TaskDto();
		taskDto.taskName = task.name;
		taskDto.id = task.id;
		User client = UserService.findUserByUid(task.clientUid);
		if(client != null) {
			taskDto.clientName = client.name;
			taskDto.clientUid = client.name;
		}
		else {
			taskDto.clientName = Consts.NONE;
			taskDto.clientUid = Consts.NONE;
		}
		taskDto.description = task.description;
		taskDto.limitTime = new SimpleDateFormat("yyyy年MM月dd日(E) HH時mm分", Locale.JAPAN).format(task.limitTime);
		taskDto.formatedLimitTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(task.limitTime).replace(" ", "T");
		taskDto.remainingTime = getFormatedRemainingTime(task);
		taskDto.taskState = task.taskState.getString();
		taskDto.taskType = task.taskType.getString();
		Team team = TeamService.findByTid(task.tid);
		if(team != null) {
			taskDto.teamName = TeamService.findByTid(task.tid).name;
		}
		taskDto.teamName = Consts.NONE;
		taskDto.updateTime = new SimpleDateFormat("yyyy年MM月dd日(E) HH時mm分", Locale.JAPAN).format(task.updateTime);

		return taskDto;
	}
	/**
	 * 残り時間として表示する文字列を返す
	 * @param task
	 * @return
	 */
	private static String getFormatedRemainingTime(Task task) {
		long now = System.currentTimeMillis();
		long limit = task.limitTime.getTime();
		int dt = (int)(limit - now);
		int second = dt / 1000;
		int minute = second / 60;
		int hour = minute / 60;
		int day = hour / 24;

		if(day >= 1) {
			return day + "日";
		}
		if(hour >= 1) {
			minute %= 60;
			return hour + "時間" + minute + "分";
		}
		if(minute >= 1) {
			return minute + "分";
		}
		return "期限切れ";
	}

	/**
	 * taskのリストからtaskDtoのリストを取得する
	 * @param listTask
	 * @return
	 */
	public static List<TaskDto> initListTaskDto(List<Task> listTask) {
		List<TaskDto> listTaskDto = new ArrayList<>();
		for(Task task : listTask) {
			listTaskDto.add(initTaskDto(task));
		}
		return listTaskDto;
	}

	public static int deleteTaskByClientUid(String uid) {
		return Task.delete("clientUid = ?1", uid);
	}

	public static boolean hasAuthority(User user, Task task) {
		if(task.taskType == TaskType.PRIVATE) {
			return user.uid.equals(task.clientUid);
		}
		else {
			return user.tid != null && user.tid.equals(task.tid);
		}
	}
}
