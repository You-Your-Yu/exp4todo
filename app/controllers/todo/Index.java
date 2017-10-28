package controllers.todo;

import models.todo.consts.Consts;
import play.mvc.Controller;

public class Index extends Controller {

	public static void index() {
		if(session.contains(Consts.LOGIN)) {
			Top.index();
		}
		else {
			Login.index();
		}
	}
}
