package ru.otus.java.pro.luckymoonlight.entity;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class Employee {
    private Long id;
    private String name;
    private Long salary;
}
