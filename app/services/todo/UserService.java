package services.todo;

import models.todo.dto.UserDto;
import models.todo.entity.Team;
import models.todo.entity.User;
import sample.DigestGenerator;

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
	/**
	 * 指定uidをもつUserを削除する
	 * @param uid
	 * @return
	 */
	public static int deleteUserByUid(String uid) {
		return User.delete("uid = ?1", uid);
	}

	/**
	 * パスワードを変更する
	 * @param user
	 * @param newPW
	 */
	public static void changePW(User user, String newPW) {
		String pw = DigestGenerator.getSHA256(newPW + user.uid + user.fixedSalt);
		user.pw = pw;
		user.save();
	}
	/**
	 * チームを設定する
	 * @param user
	 * @param tid
	 */
	public static void setTeam(User user, String tid) {
		user.tid = tid;
		user.save();
	}

	/**
	 * チームを離脱する
	 * @param user
	 */
	public static void leaveTeam(User user) {
		user.tid = null;
		user.save();
	}

	/**
	 * userから画面表示用オブジェクトUserDtoを設定する
	 * @param user
	 * @return
	 */
	public static UserDto initUserDto(User user) {
		UserDto userDto = new UserDto();
		userDto.uid = user.uid;
		userDto.name = user.name;
		String tid = user.tid;
		Team team = TeamService.findByTid(tid);
		if(team != null) {
			userDto.tid = team.tid;
			userDto.teamName = team.name;
		}

		return userDto;
	}
}
