package controllers.todo;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;

import models.todo.consts.Consts;
import models.todo.consts.TaskType;
import models.todo.dto.TaskDto;
import models.todo.entity.Information;
import models.todo.entity.Task;
import models.todo.entity.User;
import play.mvc.Controller;
import services.todo.InformationService;
import services.todo.TaskService;
import services.todo.UserService;

public class Top extends Controller {

	/**
	 * TOPページ
	 */
	public static void index() {
		String uid = session.get(Consts.LOGIN);
		User user = UserService.findUserByUid(uid);
		// ログインチェック
		if(user == null) {
			flash.put(Consts.ERRMSG, "アクセスにはログインが必要です。");
			Login.index();
		}
		List<Information> listInformation = InformationService.findListInformationByUidOrTidLimitN(uid, user.tid, 20);
		render(user, listInformation);
	}

	/*
	 * タスクのJSONデータを送信
	 */
	public static void getTaskData() {
		String uid = session.get(Consts.LOGIN);
		User user = UserService.findUserByUid(uid);
		// ログインチェック
		if(user == null) {
			forbidden();
		}
		String tid = user.tid;
		List<Task> listTask;
		listTask = TaskService.findListTaskByUidAndTid(uid, tid);
		List<TaskDto> listTaskDto = TaskService.initListTaskDto(listTask);
		renderJSON(listTaskDto);
	}

	/**
	 * タスク登録画面へ遷移
	 */
	public static void registerTask() {
		String uid = session.get(Consts.LOGIN);
		User user = UserService.findUserByUid(uid);
		// ログインチェック
		if(user == null) {
			flash.put(Consts.ERRMSG, "アクセスにはログインが必要です。");
			Login.index();
		}
		List<Information> listInformation = InformationService.findListInformationByUidOrTidLimitN(uid, user.tid, 20);
		render(user, listInformation);
	}

	/**
	 * タスクの登録を行う
	 */
	public static void postRegisterTask() {
		String uid = session.get(Consts.LOGIN);
		User user = UserService.findUserByUid(uid);
		// ログインチェック
		if(user == null) {
			flash.put(Consts.ERRMSG, "アクセスにはログインが必要です。");
			Login.index();
		}
		// パースのチェック
		try {
			String clientUid = uid;
			String tid = user.tid;
			String name = params.get("name");
			String description = params.get("description");
			String strTaskType = params.get("taskType");
			TaskType taskType;
			if(strTaskType == null || strTaskType.isEmpty()) {
				taskType = TaskType.PRIVATE;
			}
			else {
				taskType = TaskType.valueOf(strTaskType);
			}
			Timestamp limitTime = new Timestamp(new SimpleDateFormat("yyyy-MM-dd HH:mm")
					.parse(params.get("limitTime").replaceAll("T", " ")).getTime());
			Task task = TaskService.registerTask(name, description, tid, clientUid, taskType, limitTime);
			InformationService.registerTaskInformation(user, task, "タスク登録", user.name + "さんが「" + name + "」を登録しました。");

		} catch (Exception e) {
			e.printStackTrace();
			forbidden();
		}
		index();
	}
	/**
	 * タスクの詳細ページ
	 */
	public static void taskDetail() {
		String uid = session.get(Consts.LOGIN);
		User user = UserService.findUserByUid(uid);
		// ログインチェック
		if(user == null) {
			flash.put(Consts.ERRMSG, "アクセスにはログインが必要です。");
			Login.index();
		}
		Task task = TaskService.findTaskById(Long.parseLong(params.get("taskId")));
		// 権限のチェック
		if(!TaskService.hasAuthority(user, task)) {
			forbidden();
		}
		TaskDto taskDto = TaskService.initTaskDto(task);
		List<Information> listInformation = InformationService.findListInformationByUidOrTidLimitN(uid, user.tid, 20);
		render(user, taskDto, listInformation);
	}

	/**
	 * タスクを完了する
	 */
	public static void completeTask() {
		String uid = session.get(Consts.LOGIN);
		User user = UserService.findUserByUid(uid);
		// ログインチェック
		if(user == null) {
			flash.put(Consts.ERRMSG, "アクセスにはログインが必要です。");
			Login.index();
		}
		Long taskId = null;
		// パースのチェック
		try {
			taskId = Long.parseLong(params.get("taskId"));
		}catch (Exception e) {
			forbidden();
		}
		Task task = TaskService.findTaskById(taskId);
		// 権限のチェック
		if(!TaskService.hasAuthority(user, task)) {
			forbidden();
		}
		TaskService.completeTask(task);
		InformationService.registerTaskInformation(user, task, "タスク完了", user.name + "さんが「" + task.name + "」を完了しました。");

		index();
	}

	/**
	 * タスクの完了を取り消す
	 */
	public static void incompleteTask() {
		String uid = session.get(Consts.LOGIN);
		User user = UserService.findUserByUid(uid);
		// ログインチェック
		if(user == null) {
			flash.put(Consts.ERRMSG, "アクセスにはログインが必要です。");
			Login.index();
		}
		Long taskId = null;
		// パースのチェック
		try {
			taskId = Long.parseLong(params.get("taskId"));
		}catch (Exception e) {
			forbidden();
		}
		Task task = TaskService.findTaskById(taskId);
		// 権限のチェック
		if(!TaskService.hasAuthority(user, task)) {
			forbidden();
		}
		TaskService.incompleteTask(task);
		InformationService.registerTaskInformation(user, task, "タスク完了取り消し", user.name + "さんが「" + task.name + "」の完了を取り消しました。");

		index();
	}

	/**
	 * タスクを削除する
	 */
	public static void deleteTask() {
		String uid = session.get(Consts.LOGIN);
		User user = UserService.findUserByUid(uid);
		// ログインチェック
		if(user == null) {
			flash.put(Consts.ERRMSG, "アクセスにはログインが必要です。");
			Login.index();
		}
		Long taskId = null;
		// パースのチェック
		try {
			taskId = Long.parseLong(params.get("taskId"));
		}catch (Exception e) {
			forbidden();
		}
		Task task = TaskService.findTaskById(taskId);
		// 権限のチェック
		if(!TaskService.hasAuthority(user, task)) {
			forbidden();
		}
		TaskService.deleteTask(task);
		InformationService.registerTaskInformation(user, task, "タスク削除", user.name + "さんが「" + task.name + "」を削除しました。");

		index();
	}

	/**
	 * タスク編集画面へ遷移
	 */
	public static void updateTask() {
		String uid = session.get(Consts.LOGIN);
		User user = UserService.findUserByUid(uid);
		// ログインチェック
		if(user == null) {
			flash.put(Consts.ERRMSG, "アクセスにはログインが必要です。");
			Login.index();
		}
		Long taskId = null;
		// パースのチェック
		try {
			taskId = Long.parseLong(params.get("taskId"));
		}catch (Exception e) {
			forbidden();
		}
		Task task = TaskService.findTaskById(taskId);
		// 権限のチェック
		if(!TaskService.hasAuthority(user, task)) {
			forbidden();
		}
		TaskDto taskDto = TaskService.initTaskDto(task);

		List<Information> listInformation = InformationService.findListInformationByUidOrTidLimitN(uid, user.tid, 20);
		render(user, taskDto, listInformation);
	}

	/**
	 * タスクを編集する
	 */
	public static void postUpdateTask() {
		String uid = session.get(Consts.LOGIN);
		User user = UserService.findUserByUid(uid);
		// ログインチェック
		if(user == null) {
			flash.put(Consts.ERRMSG, "アクセスにはログインが必要です。");
			Login.index();
		}
		// パースのチェック
		try {
			String name = params.get("name");
			String description = params.get("description");
			TaskType taskType = TaskType.valueOf(params.get("taskType"));
			Timestamp limitTime = new Timestamp(new SimpleDateFormat("yyyy-MM-dd HH:mm")
					.parse(params.get("limitTime").replaceAll("T", " ")).getTime());
			Task task = TaskService.findTaskById(Long.parseLong(params.get("taskId")));
			if(task == null) {
				throw new Exception("タスクが見つかりません。");
			}
			// 権限のチェック
			if(!TaskService.hasAuthority(user, task)) {
				forbidden();
			}
			TaskService.updateTask(task, name, description, taskType, user.tid, limitTime);
			InformationService.registerTaskInformation(user, task, "タスク編集", user.name + "さんが「" + task.name + "」を編集しました。");

		} catch (Exception e) {
			forbidden();
		}
		index();
	}

	/**
	 * 画面遷移なしにタスクを完了する
	 */
	public static void completeTaskWithoutST() {
		String uid = session.get(Consts.LOGIN);
		User user = UserService.findUserByUid(uid);
		// ログインチェック
		if(user == null) {
			flash.put(Consts.ERRMSG, "アクセスにはログインが必要です。");
			Login.index();
		}
		Long taskId = null;
		// パースのチェック
		try {
			taskId = Long.parseLong(params.get("taskId"));
		}catch (Exception e) {
			forbidden();
		}
		Task task = TaskService.findTaskById(taskId);
		// 権限のチェック
		if(!TaskService.hasAuthority(user, task)) {
			forbidden();
		}

		TaskService.completeTask(task);
		InformationService.registerTaskInformation(user, task, "タスク完了", user.name + "さんが「" + task.name + "」を完了しました。");

	}
	/**
	 * 画面遷移なしにタスクの完了を取り消す
	 */
	public static void incompleteTaskWithoutST() {
		String uid = session.get(Consts.LOGIN);
		User user = UserService.findUserByUid(uid);
		// ログインチェック
		if(user == null) {
			flash.put(Consts.ERRMSG, "アクセスにはログインが必要です。");
			Login.index();
		}
		Long taskId = null;
		// パースのチェック
		try{
			taskId = Long.parseLong(params.get("taskId"));
		}catch (Exception e) {
			forbidden();
		}
		Task task = TaskService.findTaskById(taskId);
		// 権限のチェック
		if(!TaskService.hasAuthority(user, task)) {
			forbidden();
		}

		TaskService.incompleteTask(task);
		InformationService.registerTaskInformation(user, task, "タスク完了取り消し", user.name + "さんが「" + task.name + "」の完了を取り消しました。");

	}
	/**
	 * 画面遷移なしにタスク名を編集する
	 */
	public static void renameTaskWithoutST() {
		String uid = session.get(Consts.LOGIN);
		User user = UserService.findUserByUid(uid);
		// ログインチェック
		if(user == null) {
			flash.put(Consts.ERRMSG, "アクセスにはログインが必要です。");
			Login.index();
		}
		Long taskId = null;
		// パースのチェック
		try {
		 	taskId = Long.parseLong(params.get("taskId"));
		} catch (Exception e) {
			forbidden();
		}
		Task task = TaskService.findTaskById(taskId);
		// 権限のチェック
		if(!TaskService.hasAuthority(user, task)) {
			forbidden();
		}
		String newName = params.get("newName");
		TaskService.renameTask(task, newName);
		InformationService.registerTaskInformation(user, task, "タスク編集", user.name + "さんが「" + task.name + "」を編集しました。");
	}
	/**
	 * 画面遷移なしにタスクを削除する
	 */
	public static void deleteTaskWithoutST() {
		String uid = session.get(Consts.LOGIN);
		User user = UserService.findUserByUid(uid);
		// ログインチェック
		if(user == null) {
			flash.put(Consts.ERRMSG, "アクセスにはログインが必要です。");
			Login.index();
		}
		Long taskId = null;
		// パースのチェック
		try {
			taskId = Long.parseLong(params.get("taskId"));
		}catch (Exception e) {
			forbidden();
		}
		Task task = TaskService.findTaskById(taskId);
		// 権限のチェック
		if(!TaskService.hasAuthority(user, task)) {
			forbidden();
		}

		TaskService.deleteTask(task);
		InformationService.registerTaskInformation(user, task, "タスク削除", user.name + "さんが「" + task.name + "」を削除しました。");
	}

}
