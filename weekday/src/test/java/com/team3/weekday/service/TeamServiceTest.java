package com.team3.weekday.service;

import com.team3.weekday.IDataBus;
import com.team3.weekday.WeekdayApplication;
import com.team3.weekday.db.dao.ProjectRepository;
import com.team3.weekday.db.dao.TaskRepository;
import com.team3.weekday.db.dao.TeamRepository;
import com.team3.weekday.db.dao.UserRepository;
import com.team3.weekday.db.entity.Project;
import com.team3.weekday.db.entity.Task;
import com.team3.weekday.db.entity.Team;
import com.team3.weekday.db.entity.User;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

/**
 * @author yangke
 * @projectName weekday
 * @date 2020/11/13
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = WeekdayApplication.class)
class TeamServiceTest {

    @Autowired
    private IDataBus dataBus;

    @MockBean
    private UserRepository userRepository;
    @MockBean
    private TeamRepository teamRepository;
    @MockBean
    private ProjectRepository projectRepository;

    @Test
    void teamToJson() {
        Team team1 = new Team("team1",1L);
        team1.setId(1L);

        User member1 = new User("user1","1", "user@gmail.com", 136L,null, null);
        member1.setId(2L);
        team1.addMember(member1.getId());

        Project project1 = new Project(1L,"project1");
        project1.setId(1L);
        team1.addProject(project1);

        Assert.assertEquals(dataBus.teamService().teamToJson(team1).toJSONString(),"{\"projects\":[],\"teamId\":1," +
                "\"members\":[],\"name\":\"team1\",\"ownerId\":1}");
        Mockito.when(projectRepository.findById(1L)).thenReturn(Optional.of(project1));
        Mockito.when(userRepository.findById(2L)).thenReturn(Optional.of(member1));
        Assert.assertEquals(dataBus.teamService().teamToJson(team1).toJSONString(),"{\"projects\":[{\"teamId\":1," +
                "\"name\":\"project1\",\"projectId\":1,\"tasks\":[]}],\"teamId\":1,\"members\":[{\"avatar\":\"/static/images/head.png\"," +
                "\"email\":\"user@gmail.com\",\"id\":2,\"name\":\"user1\",\"phonenum\":136,\"teams\":\"\",\"username\":\"user1\"}]," +
                "\"name\":\"team1\",\"ownerId\":1}");
    }

    @Test
    void getTeamInfo() throws Exception {
        User user1 = new User("user1","1", "user@gmail.com", 136L,null, null);
        user1.setId(1L);

        Team team1 = new Team("team1",1L);
        team1.setId(1L);
        User member1 = new User("user2","1", "user2@gmail.com", 136L,null, null);
        member1.setId(2L);
        team1.addMember(1L);
        team1.addMember(2L);
        user1.addTeam(1L);

        Team team2 = new Team("team2",3L);
        team2.setId(2L);
        team2.addMember(1L);
        user1.addTeam(2L);

        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(user1));
        Mockito.when(userRepository.findById(2L)).thenReturn(Optional.of(member1));
        Mockito.when(teamRepository.findById(1L)).thenReturn(Optional.of(team1));
        Mockito.when(teamRepository.findById(2L)).thenReturn(Optional.of(team2));
        Assert.assertEquals(dataBus.teamService().getTeamInfo(1L).getBody().toString(),
                "[{\"projects\":[],\"teamId\":1,\"members\":" +
                        "[{\"avatar\":\"/static/images/head.png\",\"email\":\"user@gmail.com\",\"id\":1,\"name\":" +
                        "\"user1\",\"phonenum\":136,\"teamList\":[1,2],\"teams\":\"1;2\",\"username\":\"user1\"}," +
                        "{\"avatar\":\"/static/images/head.png\",\"email\":\"user2@gmail.com\",\"id\":2,\"name\":" +
                        "\"user2\",\"phonenum\":136,\"teams\":\"\",\"username\":\"user2\"}]," +
                        "\"name\":\"team1\",\"ownerId\":1}," +
                        "{\"projects\":[],\"teamId\":2,\"members\":" +
                        "[{\"$ref\":\"$[0].members.null\"}],\"name\":\"team2\",\"ownerId\":3}]");
    }
}