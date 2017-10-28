package models.todo.entity;

import javax.persistence.Entity;

import play.db.jpa.Model;
import sample.DigestGenerator;
import sample.RandomGenerator;

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
	 * fixed salt
	 */
	public final String fixedSalt;
	/**
	 * 所属チーム
	 */
	public Team team;

	public User(String uid, String pw, String name) {
		this.fixedSalt = RandomGenerator.generateRandomId();
		this.uid = uid;
		this.pw = DigestGenerator.getSHA256(pw + uid + this.fixedSalt);
		this.name = name;
	}
}
