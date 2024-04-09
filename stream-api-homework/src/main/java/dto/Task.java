package dto;

import dto.enums.TaskState;
import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Task {
    private Long id;
    private String name;
    private TaskState state;
}
