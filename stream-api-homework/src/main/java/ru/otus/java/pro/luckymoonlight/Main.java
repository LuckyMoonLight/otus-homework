package ru.otus.java.pro.luckymoonlight;

import dto.Task;
import dto.enums.TaskState;
import service.TaskService;

import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        List<Task> tasks = Arrays.asList(
                Task.builder()
                        .id(1L)
                        .name("Задача 1")
                        .state(TaskState.IN_WORK)
                        .build(),
                Task.builder()
                        .id(2L)
                        .name("Задача 2")
                        .state(TaskState.NEW)
                        .build(),
                Task.builder()
                        .id(3L)
                        .name("Задача 3")
                        .state(TaskState.NEW)
                        .build(),
                Task.builder()
                        .id(4L)
                        .name("Задача 4")
                        .state(TaskState.CLOSE)
                        .build(),
                Task.builder()
                        .id(5L)
                        .name("Очень важная задача 1")
                        .state(TaskState.CLOSE)
                        .build(),
                Task.builder()
                        .id(6L)
                        .name("Очень важная задача 2")
                        .state(TaskState.CLOSE)
                        .build(),
                Task.builder()
                        .id(7L)
                        .name("Очень важная задача 3")
                        .state(TaskState.NEW)
                        .build());

        TaskService taskService = new TaskService(tasks);
        System.out.println("Задачи в статусе новая:");
        System.out.println(taskService.getTasksByState(TaskState.NEW));

        if(taskService.hasTaskById(1L))
            System.out.println(taskService.getTaskById(1L).get());
        if(taskService.hasTaskById(100L))
            System.out.println(taskService.getTaskById(100L).get());

        System.out.println("Отсортированные по статусу задачи: ");
        System.out.println(taskService.getSortedTasks());

        System.out.println("Количество закрытых задач: " + taskService.getCountByState(TaskState.CLOSE));

        System.out.println("Сгруппированные по статусу задачи: ");
        System.out.println(taskService.getGroupMapByTaskState());

    }
}