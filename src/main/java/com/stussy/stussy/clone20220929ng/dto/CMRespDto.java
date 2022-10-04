package com.stussy.stussy.clone20220929ng.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Map;

@AllArgsConstructor
@Data
public class CMRespDto<T> {
    private int code;
    private String msg;
    private T data;
}
