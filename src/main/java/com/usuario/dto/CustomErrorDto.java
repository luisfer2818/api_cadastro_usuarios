package com.usuario.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.Instant;

@Getter
@AllArgsConstructor
public class CustomErrorDto {

    private Instant timestamp;
    private Integer status;
    private String error;
    private String path;
}
