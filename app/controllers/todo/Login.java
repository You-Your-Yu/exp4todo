package controllers.todo;

import models.todo.consts.Consts;
import models.todo.entity.User;
import play.mvc.Controller;
import sample.DigestGenerator;

public class Login extends Controller {
	/**
	 * デフォルトで実行されるアクション
	 */
	public static void index() {
		if(session.contains(Consts.LOGIN)) {
			redirect("/todo.Top");
		}
		render();
	}
	/**
	 * ログインフォームの処理
	 */
	public static void postLogin() {
		String uid = params.get("uid");
		String pw = params.get("pw");
		User user = User.find("uid = ?1", uid).first();
		if(user == null) {
			renderArgs.put("message", "存在しないユーザーIDです。");
			index();
		}
		// pwのダイジェスト作成
		pw = DigestGenerator.getSHA256(pw + user.name + user.fixedSalt);
		if(!pw.equals(user.pw)) {
			renderArgs.put("message", "パスワードが間違っています。");
			index();
		}
		response.setCookie("uid", uid);
		redirect("/todo.Top");
	}
	/**
	 * ユーザー登録画面へ遷移
	 */
	public static void register() {
		render();
	}

	public static void postRegister() {
		String uid = params.get("uid");
		String pw = params.get("pw");
		String name = params.get("name");
		User user = User.find("uid = ?1", uid).first();
		// ユーザーIDの重複を禁止
		if(user != null) {
			renderArgs.put("message", "既に存在するユーザーIDです。");
			register();
		}
		// ユーザーIDに空の文字列を禁止
		if(uid == null || uid.isEmpty()) {
			renderArgs.put("message", "空の文字列をユーザーIDとして登録することはできません。");
			register();
		}
		// パスワードには空文字を許可
		if(pw == null || pw.isEmpty()) {
			pw = "";
		}
		// 名前に空のの文字列を禁止
		if(name == null || name.isEmpty()) {
			renderArgs.put("message", "空の文字列をニックネームとして登録することはできません。");
			register();
		}
		// ユーザー登録処理
		user = new User(uid, pw, name);
		user.save();
		// ログイン処理
		session.put(Consts.LOGIN, true);
		registerResult(user);
	}
	/**
	 * ユーザー登録結果
	 */
	public static void registerResult(User user) {
		render(user);
	}
	/**
	 * ログアウト
	 */
	public static void logout() {
		String uid = request.cookies.get("uid").value;
		session.remove(uid);
		index();
	}
}
