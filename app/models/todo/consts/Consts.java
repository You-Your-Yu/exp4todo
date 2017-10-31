package models.todo.consts;

public class Consts {
	/**
	 * ログインチェックに使用。
	 * sessionスコープにkey=Consts.LOGIN, value=uidとして保存する。
	 */
	public static final String LOGIN = "LOGIN";

	/**
	 * エラー発生時に使用。
	 * flashスコープにkey=Consts.ERRMSG, value=(エラーメッセージ)として保存する。
	 */
	public static final String ERRMSG = "ERRMSG";
	
	/**
	 * タスクの画面表示にて、担当者が決まっていないタスクの担当者欄に表示する文字列。
	 */
	public static final String UNDECIDED = "未定";
	/**
	 * タスクの画面表示にて、タスクがチームに属さない時に表示する文字列。
	 */
	public static final String NONE = "なし";
	
}
