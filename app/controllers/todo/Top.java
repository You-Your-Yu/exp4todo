package controllers.todo;

import java.util.List;

import models.todo.consts.Consts;
import models.todo.consts.TaskType;
import models.todo.entity.Task;
import models.todo.entity.User;
import play.mvc.Controller;
import services.todo.TaskService;
import services.todo.UserService;

public class Top extends Controller {

	/**
	 * TOPページ
	 */
	public static void index() {
		String uid = session.get(Consts.LOGIN);
		User user = UserService.findUserByUid(uid);
		if(user == null) {
			flash.put(Consts.ERRMSG, "アクセスにはログインが必要です。");
			Login.index();
		}
		List<Task> listTask = TaskService.findListTaskByUid(uid);
		render(user, listTask);
	}

	/**
	 * タスク登録画面へ遷移
	 */
	public static void registerTask() {
		render();
	}

	/**
	 * タスクの登録を行う
	 */
	public static void postRegisterTask() {
		String uid = session.get(Consts.LOGIN);
		User client = UserService.findUserByUid(uid);
		String clientUid = client.uid;
		String picUid = null;
		String tid = client.tid;
		String name = params.get("name");
		String description = params.get("description");
		TaskType taskType = TaskType.valueOf(params.get("taskType"));
		TaskService.registerTask(name, description, tid, clientUid, picUid, taskType);

		index();
	}
}
