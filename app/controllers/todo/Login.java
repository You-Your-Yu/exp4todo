package controllers.todo;

import com.sun.xml.internal.bind.v2.runtime.Name;

import javassist.tools.reflect.CannotCreateException;
import models.todo.User;
import play.mvc.Controller;

public class Login extends Controller {
	
	public static void index() {
		// todo.Login/index.htmlに記述されたビューを描画
		render();
	}
	
	public static void login() {
		// 入力されたユーザーデータをDBと照合
	}
	
	public static void register() {
		String uid = params.get("uid");
		String pw = params.get("pw");
		String name = params.get("name");
		User user = User.find("uid = ?1", uid).first();
		// ユーザーIDの重複を禁止
		if(user != null) {
			renderArgs.put("message", "既に存在するユーザーIDです。");
			index();
		}
		// ユーザーIDに空の文字列を禁止
		if(uid == null || uid.isEmpty()) {
			renderArgs.put("message", "空の文字列をユーザーIDとして登録することはできません。");
			index();
		}
		// パスワードには空文字を許可
		if(pw == null || pw.isEmpty()) {
			pw = "";
		}
		// 名前に空のの文字列を禁止
		if(name == null || name.isEmpty()) {
			renderArgs.put("message", "空の文字列を名前として登録することはできません。");
		}
		// ユーザー登録処理
		user = new User(uid, pw, name);
		user.save();
		// ログイン処理
		session.put(uid, user);
		renderArgs.put("user", user);
		render();
	}
}
