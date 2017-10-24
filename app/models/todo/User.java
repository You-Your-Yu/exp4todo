package models.todo;

import javax.persistence.Entity;

import play.db.jpa.Model;

@Entity
public class User extends Model {
	
	/**
	 * ユーザーID
	 */
	public String userId;
	
	/**
	 * パスワード
	 */
	public String password;
	
	/**
	 * コンストラクタ
	 */
	public User(String userId, String password) {
		this.userId = userId;
		this.password = password;
	}
}
