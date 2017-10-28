package models.todo.entity;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Entity;

import models.todo.consts.TaskState;
import models.todo.consts.TaskType;
import play.db.jpa.Model;

@Entity
public class Task extends Model {
	/**
	 * タスク名
	 */
	public String name;
	/**
	 * タスクの説明
	 */
	public String description;
	/**
	 * 属するチーム
	 */
	public Team team;
	/**
	 * 依頼者
	 */
	public User client;
	/**
	 * 担当者(people in charge)
	 */
	public List<User> pic;
	/**
	 * タスクの種類
	 */
	public TaskType taskType;
	/**
	 * タスクの状態
	 */
	public TaskState taskState;
	/**
	 * 登録日時
	 */
	public Timestamp registerTime;
	/**
	 * 更新日時
	 */
	public Timestamp updateTime;

	public Task(String name, User client, TaskType taskType) {
		this.name = name;
		this.client = client;
		this.taskType = taskType;
		this.taskState = TaskState.INCOMPLETED;
		this.registerTime = new Timestamp(System.currentTimeMillis());
		this.updateTime = new Timestamp(System.currentTimeMillis());
	}
}
