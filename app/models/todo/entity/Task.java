package models.todo.entity;

import java.sql.Timestamp;

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
	public String tid;
	/**
	 * 依頼者のuid
	 */
	public String clientUid;
	/**
	 * 担当者(person in charge)のuid
	 */
	public String picUid;
	/**
	 * タスクの種類
	 */
	public TaskType taskType;
	/**
	 * タスクの状態
	 */
	public TaskState taskState;
	/**
	 * 締め切り日時
	 */
	public Timestamp limitTime;
	/**
	 * 登録日時
	 */
	public Timestamp registerTime;
	/**
	 * 更新日時
	 */
	public Timestamp updateTime;

	/**
	 * 最低限必要な項目
	 * @param name
	 * @param client
	 * @param taskType
	 */
	public Task(String name, String description, String tid, String clientUid, String picUid, TaskType taskType, Timestamp limitTime) {
		this.name = name;
		this.description = description;
		this.clientUid = clientUid;
		this.picUid = picUid;
		this.taskType = taskType;
		if(taskType != null) {
			this.tid = tid;
		}
		this.taskState = TaskState.INCOMPLETED;
		this.limitTime = limitTime;
		this.registerTime = new Timestamp(System.currentTimeMillis());
		this.updateTime = new Timestamp(System.currentTimeMillis());
	}
}
