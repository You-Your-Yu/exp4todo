package models.todo.consts;

public enum TaskState {
	COMPLETED("完了済み"),
	INCOMPLETED("未完了"),
	;

	private final String text;

	private TaskState(final String text) {
		this.text = text;
	}
	public String getString() {
		return this.text;
	}
}
