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

    public static CategoryDto mapToDto(Category category){
        CategoryDto dto = new CategoryDto();
        dto.setId(category.getId());
        dto.setName(category.getName());
        return dto;
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

    public static ProjectDto mapToDto(Project project){
        ProjectDto dto = new ProjectDto();
        dto.setId(project.getId());
        dto.setTitle(project.getTitle());
        dto.setDescription(project.getDescription());

        dto.setCategories(project.getCategories()
        .stream()
        .map(Mapper::mapToDto)
        .collect(Collectors.toList()));

        return dto;
    }
}
