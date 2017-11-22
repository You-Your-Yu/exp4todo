package controllers.todo;

import models.todo.consts.Consts;
import models.todo.entity.Team;
import models.todo.entity.User;
import play.mvc.Controller;
import sample.DigestGenerator;
import services.todo.TaskService;
import services.todo.TeamService;
import services.todo.UserService;

public class MyPage extends Controller {
	/**
	 * マイページ
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

	public static void createTeam() {
		String uid = session.get(Consts.LOGIN);
		User user = UserService.findUserByUid(uid);
		if(user == null) {
			flash.put(Consts.ERRMSG, "アクセスにはログインが必要です。");
			Login.index();
		}

		render(user);
	}

	public static void postCreateTeam() {
		String uid = session.get(Consts.LOGIN);
		User user = UserService.findUserByUid(uid);
		if(user == null) {
			flash.put(Consts.ERRMSG, "アクセスにはログインが必要です。");
			Login.index();
		}
		String tid = params.get("tid");
		String name = params.get("name");
		Team team = TeamService.findByTid(tid);
		if(team != null) {
			flash.put(Consts.ERRMSG, "既に存在するチームIDです。");
			createTeam();
		}
		// チーム作成処理
		TeamService.createTeam(tid, name);
		// ユーザー情報の設定
		UserService.setTeam(user, tid);

		index();
	}

	/**
	 * パスワードを変更する
	 */
	public static void changePW() {
		String ret;
		String currentPW = params.get("currentPW");
		String newPW = params.get("newPW");
		String uid = session.get(Consts.LOGIN);
		User user = UserService.findUserByUid(uid);
		String pw = DigestGenerator.getSHA256(currentPW + uid + user.fixedSalt);
		if(!pw.equals(user.pw)) {
			ret = "{\"msg\": \"パスワードが間違っています。\"}";
		}
		else {
			UserService.changePW(user, newPW);
			ret = "{\"msg\": \"パスワードを変更しました。\"}";
		}
		renderJSON(ret);
	}

	/**
	 * ユーザーの退会処理
	 */
	public static void withdraw() {
		String uid = session.get(Consts.LOGIN);
		TaskService.deleteTaskByClientUid(uid);
		UserService.deleteUserByUid(uid);
		session.remove(Consts.LOGIN);
		Login.index();
	}

}
