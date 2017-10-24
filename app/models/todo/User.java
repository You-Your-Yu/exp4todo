package models.todo;

import javax.persistence.Entity;

import play.db.jpa.Model;

@Entity
public class User extends Model {
	
	/**
	 * ユーザーID
	 */
	public String uid;
	
	/**
	 * パスワード
	 */
	public String pw;
	
	/**
	 * コンストラクタ
	 */
	public User(String uid, String pw) {
		this.uid = uid;
		this.pw = pw;
	}
}
