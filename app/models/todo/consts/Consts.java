package models.todo.consts;

public class Consts {
	/**
	 * ログインチェックに使用。
	 * sessionスコープに
	 * key=Consts.LOGIN
	 * value=uid
	 * として保存する。
	 */
	public static final String LOGIN = "LOGIN";

	/**
	 * エラー発生時に使用。
	 * flashスコープに
	 * key=Consts.ERRMSG
	 * value=(エラーメッセージ)
	 * として保存する。
	 */
	public static final String ERRMSG = "ERRMSG";
}
