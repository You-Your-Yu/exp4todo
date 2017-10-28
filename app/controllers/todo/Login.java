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
		if(session.contains(Consts.LOGIN)) {
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
			flash.put(Consts.ERRMSG, "エラー: 存在しないユーザーIDです。ユーザーID: " + uid);
			index();
		}
		// pwのダイジェスト作成
		pw = DigestGenerator.getSHA256(pw + user.name + user.fixedSalt);
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
			flash.put(Consts.ERRMSG, "エラー: 既に存在するユーザーIDです。ユーザーID: " + uid);
			registerUser();
		}
		// ユーザーIDに空の文字列を禁止
		if(uid == null || uid.isEmpty()) {
			flash.put(Consts.ERRMSG, "エラー: 空の文字列をユーザーIDとして登録することはできません。");
			registerUser();
		}
		// パスワードには空文字を許可
		if(pw == null || pw.isEmpty()) {
			pw = "";
		}
		// 名前に空のの文字列を禁止
		if(name == null || name.isEmpty()) {
			flash.put(Consts.ERRMSG, "エラー: 空の文字列をニックネームとして登録することはできません。");
			registerUser();
		}
		// ユーザー登録処理
		UserService.regisgerUser(uid, pw, name);
		// ログイン処理
		session.put(Consts.LOGIN, uid);
		registerUserResult();
	}
	/**
	 * ユーザー登録結果
	 */
	public static void registerUserResult() {
		String uid = session.get(Consts.LOGIN);
		User user = UserService.findUserByUid(uid);
		render(user);
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
