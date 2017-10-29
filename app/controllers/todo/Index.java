package controllers.todo;

import models.todo.consts.Consts;
import play.mvc.Controller;
import services.todo.UserService;

public class Index extends Controller {

	public static void index() {
		if(UserService.findUserByUid(session.get(Consts.LOGIN)) == null) {
			Login.index();
		}
		else {
			Top.index();
		}
	}
}
