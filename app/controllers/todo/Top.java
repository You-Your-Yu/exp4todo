package controllers.todo;

import models.todo.consts.Consts;
import models.todo.entity.User;
import play.mvc.Controller;
import services.todo.UserService;

public class Top extends Controller {

	/**
	 * TOPページ
	 */
	public static void index() {
		if(!session.contains(Consts.LOGIN)) {
			flash.put(Consts.ERRMSG, "アクセスにはログインが必要です。");
			Login.index();
		}
		String uid = session.get(Consts.LOGIN);
		User user = UserService.findUserByUid(uid);
		render(user);
	}
}