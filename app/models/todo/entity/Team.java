package models.todo.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

import play.db.jpa.Model;

@Entity
public class Team extends Model {

	/**
	 * チームID
	 */
	@Id
	public String tid;

	/**
	 * チーム名
	 */
	public String name;

	public Team(String tid, String name) {
		this.tid = tid;
		this.name = name;
	}
}
