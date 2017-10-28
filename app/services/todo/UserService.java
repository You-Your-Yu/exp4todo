package services.todo;

import models.todo.entity.User;

public class UserService {

	public static User findUserByUid(String uid) {
		return User.find("uid = ?1", uid).first();
	}
}
