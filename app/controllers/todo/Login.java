package controllers.todo;

import javassist.tools.reflect.CannotCreateException;
import play.mvc.Controller;

public class Login extends Controller {
	
	public static void index() {
		// todo.Login/index.htmlに記述されたビューを描画
		render();
	}
	
	public static void registerUser() {
		//todo.Login/registerUser.htmlに記述されたビューを描画
		render();
	}
}
