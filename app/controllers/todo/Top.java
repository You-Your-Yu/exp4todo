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

		render(user);
	}

	/*
	 * タスクのJSONデータを送信
	 */
	public static void getTaskData() {
		String uid = session.get(Consts.LOGIN);
		List<Task> listTask = TaskService.findListTaskByUid(uid);
		List<TaskDto> listTaskDto = TaskService.initListTaskDto(listTask);
		renderJSON(listTaskDto);
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
			flash.put(Consts.ERRMSG, "内部エラー: コンソールを確認してください。");
			e.printStackTrace();
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
		Task task = TaskService.findTaskById(Long.parseLong(params.get("taskId")));
		TaskDto taskDto = TaskService.initTaskDto(task);
		render(user, taskDto);
	}

	/**
	 * タスクを完了する
	 */
	public static void completeTask() {
		Task task = TaskService.findTaskById(Long.parseLong(params.get("taskId")));
		TaskService.completeTask(task);
		index();
	}

	/**
	 * タスクの完了を取り消す
	 */
	public static void incompleteTask() {
		Task task = TaskService.findTaskById(Long.parseLong(params.get("taskId")));
		TaskService.incompleteTask(task);
		index();
	}

	/**
	 * タスクを削除する
	 */
	public static void deleteTask() {
		Task task = TaskService.findTaskById(Long.parseLong(params.get("taskId")));
		TaskService.deleteTask(task);
		index();
	}

	/**
	 * タスク編集画面へ遷移
	 */
	public static void updateTask() {
		String uid = session.get(Consts.LOGIN);
		User user = UserService.findUserByUid(uid);
		if(user == null) {
			flash.put(Consts.ERRMSG, "アクセスにはログインが必要です。");
			Login.index();
		}
		Task task = TaskService.findTaskById(Long.parseLong(params.get("taskId")));
		TaskDto taskDto = TaskService.initTaskDto(task);
		render(user, taskDto);
	}

	/**
	 * タスクを編集する
	 */
	public static void postUpdateTask() {
		try {
			String picUid = null;
			String name = params.get("name");
			String description = params.get("description");
			TaskType taskType = TaskType.valueOf(params.get("taskType"));
			Timestamp limitTime = new Timestamp(new SimpleDateFormat("yyyy-MM-dd HH:mm")
					.parse(params.get("limitTime").replaceAll("T", " ")).getTime());
			Task task = TaskService.findTaskById(Long.parseLong(params.get("taskId")));
			if(task == null) {
				throw new Exception("タスクが見つかりません。");
			}
			TaskService.updateTask(task, name, description, picUid, taskType, limitTime);
		} catch (Exception e) {
			flash.put(Consts.ERRMSG, "内部エラー： コンソールを確認してください。");
			e.printStackTrace();
		}
		index();
	}
	
	/**
	 * 画面遷移なしにタスクを完了する
	 */
	public static void completeTaskWithoutST() {
		Long taskId = Long.parseLong(params.get("taskId"));
		Task task = TaskService.findTaskById(taskId);
		TaskService.completeTask(task);
	}
	/**
	 * 画面遷移なしにタスクの完了を取り消す
	 */
	public static void incompleteTaskWithoutST() {
		Long taskId = Long.parseLong(params.get("taskId"));
		Task task = TaskService.findTaskById(taskId);
		TaskService.incompleteTask(task);
	}
	/**
	 * 画面遷移なしにタスク名を編集する
	 */
	public static void renameTaskWithoutST() {
		Long taskId = Long.parseLong(params.get("taskId"));
		String newName = params.get("newName");
		Task task = TaskService.findTaskById(taskId);
		TaskService.renameTask(task, newName);
	}
	/**
	 * 画面遷移なしにタスクを削除する
	 */
	public static void deleteTaskWithoutST() {
		Long taskId = Long.parseLong(params.get("taskId"));
		Task task = TaskService.findTaskById(taskId);
		TaskService.deleteTask(task);
	}

}
