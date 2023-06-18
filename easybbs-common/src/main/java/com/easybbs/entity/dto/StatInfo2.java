package com.easybbs.entity.dto;

import lombok.Data;

import java.util.List;

@Data
public class StatInfo2 {
    private String title;
    private String unit;
    private String unitColor;
    private List<String> x;
    private List<Integer> y;
}
