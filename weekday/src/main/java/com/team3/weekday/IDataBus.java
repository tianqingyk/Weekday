package com.team3.weekday;

import com.team3.weekday.db.dao.*;
import com.team3.weekday.service.interf.*;

/**
 * @author yangke
 * @title: IDataBus
 * @projectName weekday
 * @date 2020-09-27
 */
public interface IDataBus {
    /***** Service *****/
    IUserService userService();

    ILoginService loginService();

    IFriendService friendService();

    ITeamService teamService();

    IProjectService projectService();

    IChatService chatService();

    IEmailSenderService emailSenderService();

    IAuthorityService githubAuthorityService();

    IAuthorityService googleAuthorityService();

    /***** Repository *****/

    FriendApplyRepository friendApplyRepository();

    FriendListRepository friendListRepository();

    ProjectRepository projectRepository();

    TaskRepository taskRepository();

    TeamRepository teamRepository();

    UserRepository userRepository();

    ChatGroupRepository chatGroupRepository();

    TaskCommentRepository taskCommentRepository();

}
