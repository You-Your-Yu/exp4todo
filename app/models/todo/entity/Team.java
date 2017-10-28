package models.todo.entity;

import javax.persistence.Entity;

import play.db.jpa.Model;

@Entity
public class Team extends Model {
	/**
	 * チーム名
	 */
	public String name;

	/**
	 * チーム参加のためのパスワード
	 */
	public String pw;

	public Team(String name) {
		this.name = name;
	}
}
