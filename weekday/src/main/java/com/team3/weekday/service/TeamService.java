package com.team3.weekday.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.team3.weekday.IDataBus;
import com.team3.weekday.constant.CommonConstant;
import com.team3.weekday.constant.NotifyConstant;
import com.team3.weekday.db.entity.Project;
import com.team3.weekday.db.entity.Team;
import com.team3.weekday.db.entity.User;
import com.team3.weekday.service.interf.ITeamService;
import com.team3.weekday.utils.ResponseUtil;
import com.team3.weekday.valueobject.Response;
import com.team3.weekday.valueobject.ResponseState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author yangke
 * @projectName weekday
 * @date 2020-10-21
 */

@Service
public class TeamService implements ITeamService {

    @Autowired
    private IDataBus dataBus;

    @Override
    public JSONObject teamToJson(Team team) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("teamId", team.getId());
        jsonObject.put("name", team.getName());
        jsonObject.put("ownerId", team.getOwnerId());

        JSONArray members = new JSONArray();
        if (team.getMemberList() != null) {
            for (Long memberId : team.getMemberList()) {
                Optional<User> user = dataBus.userService().getUserById(memberId);
                if (user.isPresent()) {
                    members.add(user);
                }
            }
        }
        jsonObject.put("members", members);

        JSONArray projects = new JSONArray();
        if (team.getProjectList() != null) {
            for (Long projectId : team.getProjectList()) {
                Optional<Project> project = dataBus.projectRepository().findById(projectId);
                if (project.isPresent()) {
                    projects.add(dataBus.projectService().projectToJson(project.get()));
                }
            }
        }
        jsonObject.put("projects", projects);

        return jsonObject;
    }

    @Override
    public Response getTeamInfo(Long userId) throws Exception {
        Optional<User> userOptional = dataBus.userService().getUserById(userId);
        if (userOptional.isEmpty()) {
            return ResponseUtil.send(ResponseState.OK);
        }
        return getTeamInfo(userOptional.get());
    }

    @Override
    public Response getTeamInfo(User user) throws Exception {
        JSONArray jsonArray = new JSONArray();
        if (user.getTeamList() != null) {
            for (Long teamId : user.getTeamList()) {
                Optional<Team> teamOptional = dataBus.teamRepository().findById(teamId);
                if (teamOptional.isPresent()) {
                    jsonArray.add(dataBus.teamService().teamToJson(teamOptional.get()));
                }
            }
        }

        return ResponseUtil.sendBody(ResponseState.OK, jsonArray);
    }

    @Override
    public Response createTeam(User user, String name) throws Exception {
        Long userId = user.getId();
        Team team = new Team(name, userId);
        team.addMember(userId);
        team = dataBus.teamRepository().save(team);
        user.addTeam(team.getId());
        dataBus.userService().updateUser(user);

        notifyUpdateTeamInfo(userId, team);
        return ResponseUtil.send(ResponseState.OK);
    }

    @Override
    public Response deleteWholeTeam(User user, Long teamId) throws Exception {
        Optional<Team> teamOptional = dataBus.teamRepository().findById(teamId);
        if (teamOptional.isEmpty()) {
            //Response response = new Response("")
            return ResponseUtil.sendErrorMessage(CommonConstant.TEAM_NOT_EXIST);
        }
        Team team = teamOptional.get();

        //only the team owner has the permission to delete it
        if (!team.getOwnerId().equals(user.getId())) {
            return ResponseUtil.sendErrorMessage(CommonConstant.PERMISSION_DENIED, "not the team owner");
        }

        //delete this team from each member, at the meanwhile, notify them
        for (Long id : team.getMemberList()) {
            Optional<User> memberOptional = dataBus.userService().getUserById(id);
            if (memberOptional.isEmpty()) {
                return ResponseUtil.sendErrorMessage(CommonConstant.ERROR_MESSAGE, "team doesn't have any member");
            }
            User member = memberOptional.get();
            member.deleteTeam(teamId);
            dataBus.userRepository().save(member);
            ResponseUtil.notifyUserBody(id, NotifyConstant.NOTIFY_DELETE_TEAM, team.getId());
        }

        //delete the team from team table
        dataBus.teamRepository().delete(team);
        return ResponseUtil.send(ResponseState.OK);
    }

    @Override
    public Response addMember(User user, Long memberId, Long teamId) throws Exception {
        /*
         * CAN NOT add null member,  add a member repeatedly, add themselves
         */
        Optional<User> memberOptional = dataBus.userService().getUserById(memberId);
        if (memberOptional.isEmpty()) {
            return ResponseUtil.sendErrorMessage(CommonConstant.MEMBER_NOT_EXIST);
        }

        Optional<Team> teamOptional = dataBus.teamRepository().findById(teamId);
        if (teamOptional.isEmpty()) {
            return ResponseUtil.sendErrorMessage(CommonConstant.TEAM_NOT_EXIST);
        }
        if (memberId.equals(user.getId())) {
            return ResponseUtil.sendErrorMessage(CommonConstant.ERROR_ADD_YOURSELF);
        }
        Team team = teamOptional.get();
        User member = memberOptional.get();
        //check if member already in the team
        if (isMember(member, team)) {
            return ResponseUtil.sendErrorMessage(CommonConstant.ALREADY_BEEN_MEMBER);
        }

        member.addTeam(teamId);
        dataBus.userRepository().save(member);
        team.addMember(memberId);
        dataBus.teamRepository().save(team);

        notifyTeamInfo(team);
//        notifyUpdateTeamInfo(user.getId(), team);
//        notifyUpdateTeamInfo(memberId, team);
        return ResponseUtil.send(ResponseState.OK);
    }

    @Override
    public Response deleteMember(User user, Long memberId, Long teamId) throws Exception {
        Optional<User> memberOptional = dataBus.userService().getUserById(memberId);
        if (memberOptional.isEmpty()) {
            return ResponseUtil.sendErrorMessage(CommonConstant.MEMBER_NOT_EXIST);
        }

        Optional<Team> teamOptional = dataBus.teamRepository().findById(teamId);
        if (teamOptional.isEmpty()) {
            return ResponseUtil.sendErrorMessage(CommonConstant.TEAM_NOT_EXIST);
        }

        Team team = teamOptional.get();
        User member = memberOptional.get();

        //if the user not belongs to the team
        if (!isMember(member, team)) {
            return ResponseUtil.sendErrorMessage(CommonConstant.MEMBER_NOT_EXIST);
        }

        member.deleteTeam(teamId);
        dataBus.userRepository().save(member);
        team.deleteMember(memberId);
        dataBus.teamRepository().save(team);

        notifyUpdateTeamInfo(user.getId(), team);
        ResponseUtil.notifyUserBody(memberId, NotifyConstant.NOTIFY_DELETE_TEAM, team.getId());
        return ResponseUtil.send(ResponseState.OK);
    }

    @Override
    public void notifyTeamInfo(Team team) throws Exception {
        for (Long id : team.getMemberList()) {
            notifyUpdateTeamInfo(id, team);
        }
    }

    @Override
    public Boolean isMember(User member, Team team) {
        if (team.getMembers().contains(member.getId().toString())
                && member.getTeams().contains(team.getId().toString())) {
            return true;
        }
        return false;
    }

    @Override
    public Response changeOwner(User user, Long memberId, Long teamId) throws Exception {
        Optional<User> memberOptional = dataBus.userService().getUserById(memberId);
        if (memberOptional.isEmpty()) {
            return ResponseUtil.sendErrorMessage(CommonConstant.MEMBER_NOT_EXIST);
        }
        Optional<Team> teamOptional = dataBus.teamRepository().findById(teamId);
        if (teamOptional.isEmpty()) {
            return ResponseUtil.sendErrorMessage(CommonConstant.TEAM_NOT_EXIST);
        }
        Team team = teamOptional.get();
        User member = memberOptional.get();

        //only the team owner has the permission to change it
        if (!team.getOwnerId().equals(user.getId())) {
            return ResponseUtil.sendErrorMessage(CommonConstant.PERMISSION_DENIED, "not team owner");
        }
        //if the member not belongs to the team
        //if(!team.getMembers().contains(memberId.toString())||!member.getTeams().contains(teamId.toString())){
        if (!isMember(member, team)) {
            return ResponseUtil.sendErrorMessage(CommonConstant.MEMBER_NOT_EXIST);
        }
        team.setOwnerId(memberId);
        dataBus.teamRepository().save(team);
        notifyTeamInfo(team);
        return ResponseUtil.send(ResponseState.OK);
    }

    private void notifyUpdateTeamInfo(Long userId, Team team) {
        ResponseUtil.notifyUserBody(userId, NotifyConstant.NOTIFY_UPDATE_TEAM_INFO, teamToJson(team));
    }

    @Override
    public void notifyTeam(long teamId, String cmd, Object body) {
        Optional<Team> teamOptional = dataBus.teamRepository().findById(teamId);
        if (teamOptional.isEmpty()) {
            return;
        }
        ResponseUtil.notifyUsersBody(teamOptional.get().getMemberList(), cmd, body);
    }
}
