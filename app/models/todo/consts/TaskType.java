package models.todo.consts;

public enum TaskType {
	PRIVATE("個人"),
	PUBLIC("チーム"),
	;
	private final String text;

	private TaskType(final String text) {
		this.text = text;
	}
	public String getString() {
		return this.text;
	}
}
