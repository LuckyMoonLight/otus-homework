package service;

import dto.Task;
import dto.enums.TaskState;
import lombok.AllArgsConstructor;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor
public class TaskService {
    private List<Task> tasks;

    public List<Task> getTasksByState(TaskState taskState) {
        return tasks.stream().filter(t -> t.getState().equals(taskState)).toList();
    }

    public Optional<Task> getTaskById(Long id) {
        return tasks.stream().filter(t -> t.getId().equals(id)).findFirst();
    }

    public boolean hasTaskById(Long id) {
        return tasks.stream().anyMatch(t -> t.getId().equals(id));
    }

    public List<Task> getSortedTasks() {
        return tasks.stream().sorted(Comparator.comparing(t -> t.getState().getTitle())).toList();
    }

    public long getCountByState(TaskState taskState) {
        return tasks.stream().filter(t -> t.getState().equals(taskState)).count();
    }

    public Map<TaskState, List<Task>> getGroupMapByTaskState() {
        return tasks.stream().collect(Collectors.groupingBy(Task::getState));
    }

}
