package services.todo;

import java.util.List;

import models.todo.entity.Information;

public class InformationService {

	/**
	 * チームIDでお知らせを検索し、最新n件を取得する。
	 * @param tid
	 * @return
	 */
	public static List<Information> findListInformationByUidOrTidLimitN(String uid, String tid, int n) {
		return Information.find("uid = ?1 OR tid = ?2 ORDER BY registerTime DESC", uid, tid).fetch(n);
	}

	/**
	 * お知らせを登録する
	 */
	public static Information registerInformation(String uid, String userName, String tid, String teamName, String title, String description) {
		Information information = new Information(uid, userName, tid, teamName, title, description);
		information.save();
		return information;
	}
}
