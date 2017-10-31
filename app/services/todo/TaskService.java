package services.todo;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import models.todo.consts.Consts;
import models.todo.consts.TaskState;
import models.todo.consts.TaskType;
import models.todo.dto.TaskDto;
import models.todo.entity.Task;
import models.todo.entity.Team;
import models.todo.entity.User;

public class TaskService {
	/**
	 * idでTaskを検索する
	 * @param id
	 * @return
	 */
	public static Task findTaskById(Long id) {
		return Task.find("id = ?1", id).first();
	}

	/**
	 * uidでTaskを検索する
	 * @param uid
	 * @return
	 */
	public static List<Task> findListTaskByUid(String uid) {
		return Task.find("clientUid = ?1", uid).fetch();
	}

	public static Task registerTask(String name, String description, String tid, String clientUid, String picUid, TaskType taskType, Timestamp limitTime) {
		Task task = new Task(name, description, tid, clientUid, picUid, taskType, limitTime);
		task.save();
		return task;
	}
	
	/**
	 * taskから画面表示用オブジェクトtaskDtoを設定する
	 * @param task
	 * @return
	 */
	public static TaskDto initTaskDto(Task task) {
		TaskDto taskDto = new TaskDto();
		taskDto.taskName = task.name;
		taskDto.id = task.id;
		User client = UserService.findUserByUid(task.clientUid);
		if(client != null) {
			taskDto.clientName = client.name;
			taskDto.clientUid = client.name;
		}
		else {
			taskDto.clientName = Consts.NONE;
			taskDto.clientUid = Consts.NONE;
		}
		User pic = UserService.findUserByUid(task.picUid);
		if(pic != null) {
			taskDto.picName = pic.name;
			taskDto.clientName = pic.uid;
		}
		else {
			taskDto.picName = Consts.UNDECIDED;
			taskDto.picUid = Consts.UNDECIDED;
		}
		taskDto.description = task.description;
		taskDto.limitTime = new SimpleDateFormat("yyyy年MM月dd日(E) HH時mm分").format(task.limitTime);
		taskDto.taskState = task.taskState.getString();
		Team team = TeamService.findByTid(task.tid);
		if(team != null) {
			taskDto.teamName = TeamService.findByTid(task.tid).name;
		}
		taskDto.teamName = Consts.NONE;
		
		return taskDto;
	}
	
	/**
	 * taskのリストからtaskDtoのリストを取得する
	 * @param listTask
	 * @return
	 */
	public static List<TaskDto> initListTaskDto(List<Task> listTask) {
		List<TaskDto> listTaskDto = new ArrayList<>();
		for(Task task : listTask) {
			listTaskDto.add(initTaskDto(task));
		}
		
		return listTaskDto;
	}
}
