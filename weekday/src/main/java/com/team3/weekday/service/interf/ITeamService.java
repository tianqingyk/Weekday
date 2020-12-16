package com.team3.weekday.service.interf;

import com.alibaba.fastjson.JSONObject;
import com.team3.weekday.db.entity.Team;
import com.team3.weekday.db.entity.User;
import com.team3.weekday.valueobject.Response;

/**
 * @author yangke
 * @projectName weekday
 * @date 2020-10-21
 */
public interface ITeamService {

    JSONObject teamToJson(Team team);

    Response getTeamInfo(User user) throws Exception;

    Response getTeamInfo(Long userId) throws Exception;

    Response createTeam(User user, String name) throws Exception;

    Response addMember(User user, Long memberId, Long teamId) throws Exception;

    Response deleteMember(User user, Long memberId, Long teamId) throws Exception;

    Response deleteWholeTeam(User user, Long teamId) throws Exception;

    void notifyTeamInfo(Team team) throws Exception;

    Response changeOwner(User user, Long memberId, Long teamId) throws Exception;

    Boolean isMember(User member, Team team);

    void notifyTeam(long teamId, String cmd, Object body);
}
