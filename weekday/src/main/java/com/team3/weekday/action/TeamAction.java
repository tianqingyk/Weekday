package com.team3.weekday.action;

import com.team3.weekday.IDataBus;
import com.team3.weekday.constant.CommonConstant;
import com.team3.weekday.net.ChannelSession;
import com.team3.weekday.net.annotation.Action;
import com.team3.weekday.net.annotation.Command;
import com.team3.weekday.net.annotation.Param;
import com.team3.weekday.utils.ResponseUtil;
import com.team3.weekday.valueobject.Response;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author yangke
 * @projectName weekday
 * @date 2020-10-21
 */
@Action
public class TeamAction {

    @Autowired
    private IDataBus dataBus;

    @Command
    public Response getTeamInfo(ChannelSession session) throws Exception {
        return dataBus.teamService().getTeamInfo(session.getUser());
    }

    @Command
    public Response createTeam(@Param String name, ChannelSession session) throws Exception {
        return dataBus.teamService().createTeam(session.getUser(), name);
    }

    @Command
    public Response deleteTeam(@Param Long teamId, ChannelSession session) throws Exception {
        if (teamId == null){
            return ResponseUtil.sendErrorMessage(CommonConstant.ERROR_MESSAGE, "please enter team ID");
        }

        return dataBus.teamService().deleteWholeTeam(session.getUser(),teamId);
    }
    @Command
    public Response addMember(@Param Long memberId,
                              @Param Long teamId,
                              ChannelSession session) throws Exception{
        if (memberId == null || teamId == null){
            return ResponseUtil.sendErrorMessage(CommonConstant.ERROR_TEAM_ID_EMPTY);
        }

        return dataBus.teamService().addMember(session.getUser(), memberId, teamId);
    }
    @Command
    public Response deleteMember(@Param Long memberId,
                                 @Param Long teamId,
                                 ChannelSession session) throws Exception{
        if (memberId == null || teamId == null){
            return ResponseUtil.sendErrorMessage(CommonConstant.ERROR_TEAM_ID_EMPTY);
        }

        return dataBus.teamService().deleteMember(session.getUser(), memberId, teamId);
    }
    @Command
    public Response changeOwner(@Param Long memberId,
                                @Param Long teamId,
                                ChannelSession session) throws Exception{
        if (memberId == null || teamId == null){
            return ResponseUtil.sendErrorMessage(CommonConstant.ERROR_TEAM_ID_EMPTY);
        }

        return dataBus.teamService().changeOwner(session.getUser(), memberId, teamId);
    }

}
