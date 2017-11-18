package models.todo.dto;

/**
 * Taskの画面表示用オブジェクト
 * @author yamada.y.bk
 *
 */
public class TaskDto {
	/**
	 * タスク名
	 */
	public String taskName;
	/**
	 * タスクのid
	 */
	public Long id;
	/**
	 * 依頼者Userのname
	 */
	public String clientName;
	/**
	 * 依頼者Userのuid
	 */
	public String clientUid;
	/**
	 * 担当者Userのname
	 */
	public String picName;
	/**
	 * 担当者Userのuid
	 */
	public String picUid;
	/**
	 * タスクの説明
	 */
	public String description;
	/**
	 * 締め切り日時
	 */
	public String limitTime;
	/**
	 * フォームの初期値設定用
	 */
	public String formatedLimitTime;
	/**
	 * 残り時間
	 */
	public String remainingTime;
	/**
	 * タスクの状態(完了or未完了)
	 */
	public String taskState;
	/**
	 * チームへの公開設定
	 */
	public String taskType;
	/**
	 * タスクの属するチーム
	 */
	public String teamName;
	/**
	 * 更新日時
	 */
	public String updateTime;
}
