package com.project.sample.dto;

import lombok.Data;

import java.time.LocalDate;

//롬복 연결
@Data
public class UserDTO {
    private int no;
    private String name;
    private String email;
    private String pwd;
    private Boolean gender;
    private Boolean del;
    private LocalDate regDate;
}
