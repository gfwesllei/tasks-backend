package br.ce.wcaquino.taskbackend.controller;

import br.ce.wcaquino.taskbackend.model.Task;
import br.ce.wcaquino.taskbackend.repo.TaskRepo;
import br.ce.wcaquino.taskbackend.utils.ValidationException;
import org.dom4j.util.StringUtils;
import org.hamcrest.text.IsEmptyString;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import java.time.LocalDate;

import static org.junit.Assert.*;

public class TaskControllerTest {

    @Mock
    private TaskRepo taskRepo;

    @InjectMocks
    private TaskController taskController;

    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldNotSaveTaskWidhoutDecription() {
        Task task = new Task();
        task.setDueDate(LocalDate.now());
        try {
            taskController.save(task);
            Assert.fail("Should not pass here");
        } catch (ValidationException e) {
            Assert.assertEquals("Fill the task description",e.getMessage());
        }
    }

    @Test
    public void shouldNotSaveTaskWidhEmptyDecription() {
        Task task = new Task();
        task.setDueDate(LocalDate.now());
        task.setTask("");
        try {
            taskController.save(task);
            Assert.fail("Should not pass here");
        } catch (ValidationException e) {
            Assert.assertEquals("Fill the task description",e.getMessage());
        }
    }

    @Test
    public void shouldNotSaveTaskWithoutDate(){
        Task task = new Task();
        task.setTask("Send email");
        try {
            taskController.save(task);
        } catch (ValidationException e) {
            Assert.assertEquals("Fill the due date",e.getMessage());
        }
    }

    @Test
    public void shouldNotSaveTaskWithPastDate(){
        Task task = new Task();
        task.setDueDate(LocalDate.now().plusDays(-1));
        task.setTask("Send email");
        try {
            taskController.save(task);
        } catch (ValidationException e) {
            Assert.assertEquals("Due date must not be in past",e.getMessage());
        }
    }

    @Test
    public void shouldSaveTask() throws ValidationException {
        Task task = new Task();
        task.setDueDate(LocalDate.now().plusDays(1));
        task.setTask("Send email");
        taskController.save(task);
        Mockito.verify(taskRepo).save(task);
    }

    @Test
    public void shouldReturnNotFoundTask(){

        Long idDelete =1L;
        Mockito.when(taskRepo.existsById(idDelete)).thenReturn(false);
        try{
            taskController.delete(idDelete);
        }catch (ValidationException e){
            Assert.assertEquals("Task not found",e.getMessage());
        }
    }


    @Test
    public void shouldDeleteTask(){

        Long idDelete =1L;
        Mockito.when(taskRepo.existsById(idDelete)).thenReturn(true);
        try{
            taskController.delete(idDelete);
        }catch (ValidationException e){
            Assert.assertEquals("Task not found",e.getMessage());
        }
        Mockito.verify(taskRepo).deleteById(idDelete);
    }


}