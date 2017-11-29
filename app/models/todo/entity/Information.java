package models.todo.entity;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Locale;

import javax.persistence.Entity;

import play.db.jpa.Model;

@Entity
public class Information extends Model {
	/**
	 * ユーザーID
	 */
	public String uid;
	/**
	 * ユーザー名
	 */
	public String userName;
	/**
	 * チームID
	 */
	public String tid;
	/**
	 * チーム名
	 */
	public String teamName;
	/**
	 * タイトル
	 */
	public String title;
	/**
	 * 内容
	 */
	public String description;
	/**
	 * 登録日時
	 */
	public Timestamp registerTime;
	/**
	 * 登録日時を画面表示用にフォーマットした文字列
	 */
	public String formatedRegisterTime;

	public Information(String uid, String userName, String tid, String teamName, String title, String description) {
		this.uid = uid;
		this.userName = userName;
		this.tid = tid;
		this.teamName = teamName;
		this.title = title;
		this.description = description;
		this.registerTime = new Timestamp(System.currentTimeMillis());
		try{
			this.formatedRegisterTime = new SimpleDateFormat("yyyy/MM/dd (E) HH:mm", Locale.JAPAN)
										.format(this.registerTime);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

}
