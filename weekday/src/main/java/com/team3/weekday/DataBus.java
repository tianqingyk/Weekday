package com.team3.weekday;

import com.team3.weekday.db.dao.*;
import com.team3.weekday.service.interf.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author yangke
 * @title: DataBus
 * @projectName weekday
 * @date 2020-09-16
 */

@Component
public class DataBus implements IDataBus{

    @Autowired
    private IUserService userService;

    @Autowired
    private ILoginService loginService;

    @Autowired
    private IFriendService friendService;

    @Autowired
    private ITeamService teamService;

    @Autowired
    private  IProjectService projectService;

    @Autowired
    private FriendApplyRepository friendApplyRepository;

    @Autowired
    private FriendListRepository friendListRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private IChatService chatService;

    @Autowired
    private IEmailSenderService emailSenderService;

    @Autowired
    private ChatGroupRepository chatGroupRepository;

    @Autowired
    private IAuthorityService githubAuthorityService;

    @Autowired
    private IAuthorityService googleAuthorityService;

    @Autowired
    private TaskCommentRepository taskCommentRepository;

    @Override
    public IUserService userService(){
        return userService;
    }

    @Override
    public ILoginService loginService() {
        return loginService;
    }

    @Override
    public IFriendService friendService() { return friendService; }

    @Override
    public ITeamService teamService() {
        return teamService;
    }

    @Override
    public IProjectService projectService() { return projectService; }

    @Override
    public IChatService chatService() {
        return chatService;
    }

    @Override
    public IEmailSenderService emailSenderService() { return emailSenderService; }

    @Override
    public IAuthorityService githubAuthorityService() {
        return githubAuthorityService;
    }

    @Override
    public IAuthorityService googleAuthorityService() {
        return googleAuthorityService;
    }

    @Override
    public FriendApplyRepository friendApplyRepository() {
        return friendApplyRepository;
    }

    @Override
    public FriendListRepository friendListRepository() {
        return friendListRepository;
    }

    @Override
    public ProjectRepository projectRepository() {
        return projectRepository;
    }

    @Override
    public TaskRepository taskRepository() {
        return taskRepository;
    }

    @Override
    public TeamRepository teamRepository() {
        return teamRepository;
    }

    @Override
    public UserRepository userRepository() {
        return userRepository;
    }

    @Override
    public ChatGroupRepository chatGroupRepository() {
        return chatGroupRepository;
    }

    @Override
    public TaskCommentRepository taskCommentRepository() {
        return taskCommentRepository;
    }

}
