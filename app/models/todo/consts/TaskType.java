package models.todo.consts;

public enum TaskType {
	PRIVATE("公開しない"),
	PUBLIC("公開する"),
	;
	private final String text;

	private TaskType(final String text) {
		this.text = text;
	}
	public String getString() {
		return this.text;
	}
}
