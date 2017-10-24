package controllers.todo;

import javassist.tools.reflect.CannotCreateException;
import play.mvc.Controller;

public class Login extends Controller {
	
	public static void index() {
		// todo.Login/index.htmlに記述されたビューを描画
		System.out.println(" >> index");
		render();
	}
	
	public static void login() {
		// 入力されたユーザーデータをDBと照合
		System.out.println(" >> login");
	}
	
	public static void register() {
		// todo.Login/registerUser.htmlに記述されたビューを描画
		System.out.println(" >> register");
		render();
	}
}
