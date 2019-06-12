package com.java.projects.service;

import com.java.projects.model.Category;
import com.java.projects.model.Project;
import com.java.projects.repository.ProjectRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    public Page<Project> getPageOfProjectsByCategoryName(String name, Pageable pageable){
        return projectRepository.getPageByCategoryName(name, pageable);
    }

    public Page<Project> getPageOfProjects(Pageable pageable){
        return projectRepository.findAll(pageable);
    }

    private List<Category> fetchCategories(Project project) {
        return project.getCategories()
                .stream()
                .map(categoryService::getOrCreateCategory)
                .collect(Collectors.toList());
    }
}
