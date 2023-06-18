package com.easybbs.entity.dto;

import lombok.Data;

import java.util.List;

/**
 * @author lihuanzhi
 * @since 2023/4/21 11:13
 */
@Data
public class PermissionDto {

    private List<PermissionItemDto> allPerm;

    private List<String> havePerm;
}
