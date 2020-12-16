package com.team3.weekday.service;

import com.team3.weekday.IDataBus;
import com.team3.weekday.WeekdayApplication;
import com.team3.weekday.action.ProjectAction;
import com.team3.weekday.db.dao.ProjectRepository;
import com.team3.weekday.db.dao.TaskRepository;
import com.team3.weekday.db.dao.TeamRepository;
import com.team3.weekday.db.entity.Project;
import com.team3.weekday.db.entity.Task;
import com.team3.weekday.db.entity.Team;
import com.team3.weekday.valueobject.Response;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;

/**
 * @author yangke
 * @projectName weekday
 * @date 2020/11/13
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = WeekdayApplication.class)
class ProjectServiceTest {

    @Autowired
    private IDataBus dataBus;

    @Autowired
    private ProjectAction projectAction;

    @MockBean
    private TaskRepository taskRepository;

    @MockBean
    private ProjectRepository projectRepository;

    @MockBean
    private TeamRepository teamRepository;

    @Test
    void projectToJson() {
        Project project1 = new Project(1L, "project1");
        project1.setId(1L);
        Task task1 = new Task("task1", 1L, "content", 1L, 2L,
                null, null, 1, 0L, 2 );
        task1.setId(1L);
        project1.addTask(task1);

        Assert.assertEquals(dataBus.projectService().projectToJson(project1).toJSONString(),
                "{\"teamId\":1,\"name\":\"project1\",\"projectId\":1,\"tasks\":[]}");
        Mockito.when(taskRepository.findById(1L)).thenReturn(Optional.of(task1));
        Assert.assertEquals(dataBus.projectService().projectToJson(project1).toJSONString(),
                "{\"teamId\":1,\"name\":\"project1\",\"projectId\":1,\"tasks\":[" +
                        "{\"content\":\"content\",\"id\":1,\"name\":\"task1\",\"ownerId\":1,\"projectId\":1," +
                        "\"requesterId\":2,\"status\":\"BEFORE\"}]}");
    }

    @Test
    void createProject() throws Exception {
        Team team1 = new Team("team1", 1L);
        team1.setId(1L);

        Project project = new Project(1L, "project1");
        project.setId(1L);
        Mockito.when(teamRepository.findById(1L)).thenReturn(Optional.of(team1));
        Mockito.when(projectRepository.save(any())).thenReturn(project);
        Mockito.when(projectRepository.findById(1L)).thenReturn(Optional.of(project));

        Response response = dataBus.projectService().createProject(1L, 1L, "project1");

        Assert.assertEquals(response.getBody().toString(), "{\"projects\":[{\"teamId\":1,\"name\":" +
                "\"project1\",\"projectId\":1,\"tasks\":[]}],\"teamId\":1,\"members\":[],\"name\":\"team1\"," +
                "\"ownerId\":1}");
    }

    @Test
    void createTask() throws Exception {
        Team team1 = new Team("team1", 1L);
        team1.setId(1L);

        Project project = new Project(1L, "project1");
        project.setId(1L);
        Mockito.when(projectRepository.findById(1L)).thenReturn(Optional.of(project));

        team1.addProject(project);

        Task task = new Task("name", 1L, "content", 1L, 2L,
                new Date(), new Date(), 1, 2L, 3);
        task.setId(1L);
        project.addTask(task);
        Mockito.when(taskRepository.save(any())).thenReturn(task);
        Mockito.when(taskRepository.findById(1L)).thenReturn(Optional.of(task));

        Response response = dataBus.projectService().createTask(1L,"name","content",
                1L,2L, task.getStart(), task.getEnd(),2L,1);
        Assert.assertEquals(response.getBody().toString(), "{\"teamId\":1,\"name\":\"project1\"," +
                "\"projectId\":1,\"tasks\":[{\"content\":\"content\",\"end\":"+ task.getEnd().getTime() +
                ",\"id\":1,\"name\":\"name\",\"ownerId\":1,\"projectId\":1,\"requesterId\":2,\"start\":"
                + task.getStart().getTime() + ",\"status\":\"BEFORE\"},{\"$ref\":\"$.tasks[0]\"}]}");
    }
}