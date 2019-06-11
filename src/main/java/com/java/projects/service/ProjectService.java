package com.java.projects.service;

import com.java.projects.model.Category;
import com.java.projects.model.Project;
import com.java.projects.repository.ProjectRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final CategoryService categoryService;

    public ProjectService(ProjectRepository projectRepository,
                          CategoryService categoryService){
        this.projectRepository = projectRepository;
        this.categoryService = categoryService;
    }

    public void addProject(Project project){
        List<Category> categoryList = fetchCategories(project);
        project.setCategories(categoryList);
        projectRepository.save(project);

    }

    private List<Category> fetchCategories(Project project) {
        return project.getCategories()
                .stream()
                .map(categoryService::getOrCreateCategory)
                .collect(Collectors.toList());
    }
}
