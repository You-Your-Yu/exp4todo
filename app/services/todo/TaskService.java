package services.todo;

import java.util.List;

import models.todo.consts.TaskType;
import models.todo.entity.Task;

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

	public static Task registerTask(String name, String description, String tid, String clientUid, String picUid, TaskType taskType) {
		Task task = new Task(name, description, tid, clientUid, picUid, taskType);
		task.save();
		return task;
	}
}
