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
	 * 名前
	 */
	public String name;
	
	/**
	 * コンストラクタ
	 */
	public User(String uid, String pw, String name) {
		this.uid = uid;
		this.pw = pw;
		this.name = name;
	}
}
