package models.entity;

import javax.persistence.Entity;

@Entity
public class User {
	
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
