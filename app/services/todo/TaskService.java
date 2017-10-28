package services.todo;

import java.util.List;

import models.todo.entity.Task;
import models.todo.entity.User;

public class TaskService {

	public static Task findTaskById(Long id) {
		return Task.find("id = ?1", id).first();
	}

	public static List<Task> findListTaskByUid(String uid) {
		return User.find("uid = ?1", uid).fetch();
	}
}
