package controllers.todo;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;

import models.todo.consts.Consts;
import models.todo.consts.TaskType;
import models.todo.dto.TaskDto;
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
		// タスクの一覧表示
		List<Task> listTask = TaskService.findListTaskByUid(uid);
		List<TaskDto> listTaskDto = TaskService.initListTaskDto(listTask);

		render(user, listTaskDto);
	}

	/**
	 * タスク登録画面へ遷移
	 */
	public static void registerTask() {
		String uid = session.get(Consts.LOGIN);
		User user = UserService.findUserByUid(uid);
		render(user);
	}

	/**
	 * タスクの登録を行う
	 */
	public static void postRegisterTask() {
		try {
			String uid = session.get(Consts.LOGIN);
			User client = UserService.findUserByUid(uid);
			String clientUid = client.uid;
			String picUid = null;
			String tid = client.tid;
			String name = params.get("name");
			String description = params.get("description");
			TaskType taskType = TaskType.valueOf(params.get("taskType"));
			Timestamp limitTime = new Timestamp(new SimpleDateFormat("yyyy-MM-dd HH:mm")
					.parse(params.get("limitTime").replaceAll("T", " ")).getTime());
			TaskService.registerTask(name, description, tid, clientUid, picUid, taskType, limitTime);
		} catch (Exception e) {
			flash.put(Consts.ERRMSG, e.getMessage());
		}

		index();
	}
	/**
	 * タスクの詳細ページ
	 */
	public static void taskDetail() {
		String uid = session.get(Consts.LOGIN);
		User user = UserService.findUserByUid(uid);
		if(user == null) {
			flash.put(Consts.ERRMSG, "アクセスにはログインが必要です。");
			Login.index();
		}
		Task task = TaskService.findTaskById(Long.parseLong(request.querystring.split("=")[1]));
		TaskDto taskDto = TaskService.initTaskDto(task);
		render(user, taskDto);
	}
}
