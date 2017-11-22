package controllers.todo;

import models.todo.consts.Consts;
import models.todo.entity.User;
import play.mvc.Controller;
import sample.DigestGenerator;
import services.todo.UserService;

public class Login extends Controller {
	/**
	 * デフォルトで実行されるアクション
	 */
	public static void index() {
		if(UserService.findUserByUid(session.get(Consts.LOGIN)) != null) {
			Top.index();
		}
		render();
	}
	/**
	 * ログインフォームの処理
	 */
	public static void postLogin() {
		String uid = params.get("uid");
		String pw = params.get("pw");
		User user = UserService.findUserByUid(uid);
		if(user == null) {
			flash.put(Consts.ERRMSG, "存在しないユーザーIDです。ユーザーID: " + uid);
			index();
		}
		// pwのダイジェスト作成
		pw = DigestGenerator.getSHA256(pw + uid + user.fixedSalt);
		if(!pw.equals(user.pw)) {
			flash.put(Consts.ERRMSG, "パスワードが間違っています。");
			index();
		}
		session.put(Consts.LOGIN, uid);
		Top.index();
	}
	/**
	 * ユーザー登録画面へ遷移
	 */
	public static void registerUser() {
		render();
	}

	public static void postRegisterUser() {
		String uid = params.get("uid");
		String pw = params.get("pw");
		String name = params.get("name");
		User user = UserService.findUserByUid(uid);
		// ユーザーIDの重複を禁止
		if(user != null) {
			flash.put(Consts.ERRMSG, "既に存在するユーザーIDです。ユーザーID: " + uid);
			registerUser();
		}
		// ユーザー登録処理
		UserService.regisgerUser(uid, pw, name);
		// ログイン処理
		session.put(Consts.LOGIN, uid);
		index();
	}

	/**
	 * ログアウト
	 */
	public static void logout() {
		System.out.println(" >> logout");
		session.remove(Consts.LOGIN);
		index();
	}
}
