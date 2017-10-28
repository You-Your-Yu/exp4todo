package services.todo;

import models.todo.entity.User;

public class UserService {
	/**
	 * uidでUserを検索する
	 * @param uid
	 * @return
	 */
	public static User findUserByUid(String uid) {
		return User.find("uid = ?1", uid).first();
	}
	/**
	 * Userを新規登録する
	 * @param uid
	 * @param pw
	 * @param name
	 * @return
	 */
	public static User regisgerUser(String uid, String pw, String name) {
		User user = new User(uid, pw, name);
		user.save();
		return user;
	}
}
