package dto.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@ToString
public enum TaskState {
    NEW("Новая"),
    IN_WORK("В работе"),
    CLOSE("Закрыта");

    private String title;

}
