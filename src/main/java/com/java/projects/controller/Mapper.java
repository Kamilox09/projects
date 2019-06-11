package com.java.projects.controller;

import com.java.projects.dto.CategoryDto;
import com.java.projects.dto.ProjectDto;
import com.java.projects.model.Category;
import com.java.projects.model.Project;

import java.util.stream.Collectors;

public class Mapper {

    public static Category mapToEntity(CategoryDto dto){
        Category entity = new Category();
        entity.setId(dto.getId());
        entity.setName(dto.getName());
        return entity;
    }

    public static Project mapToEntity(ProjectDto dto){
        Project entity = new Project();
        entity.setId(dto.getId());
        entity.setTitle(dto.getTitle());
        entity.setDescription(dto.getDescription());

        entity.setCategories(dto.getCategories()
        .stream()
        .map(Mapper::mapToEntity)
        .collect(Collectors.toList()));

        return entity;
    }
}
