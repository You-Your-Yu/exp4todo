package services.todo;

import models.todo.entity.Team;

public class TeamService {
	public static Team findByTid(String tid) {
		return Team.find("tid = ?1", tid).first();
	}
}
