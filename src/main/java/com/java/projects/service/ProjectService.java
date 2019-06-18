package com.java.projects.service;

import com.java.projects.model.Category;
import com.java.projects.model.Project;
import com.java.projects.model.User;
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
    private final UserService userService;

    public ProjectService(ProjectRepository projectRepository,
                          CategoryService categoryService,
                          UserService userService){
        this.projectRepository = projectRepository;
        this.categoryService = categoryService;
        this.userService = userService;
    }

    public void addProject(Project project){
        List<Category> categoryList = fetchCategories(project);
        project.setCategories(categoryList);
        project.setAvailableReservations(5);
        projectRepository.save(project);

    }

    public Page<Project> getPageOfProjectsByCategoryName(String name, Pageable pageable){
        return projectRepository.getPageByCategoryName(name, pageable);
    }

    public Page<Project> getPageOfProjects(Pageable pageable){
        return projectRepository.findAll(pageable);
    }

    public Project getById(Long id) throws Exception {
        return projectRepository.findById(id)
                .orElseThrow(()-> new Exception("Nie znaleziono projektu o id: "+id));
    }

    public Project makeReservation(Long projectId, User user) throws Exception{
        Project project = getById(projectId);
        if(project.getAvailableReservations()==0)
            throw new Exception("Projekt zarezerwowany!");
        project.setAvailableReservations(project.getAvailableReservations()-1);
        user.setProject(project);
        userService.saveUser(user);
        return project;
    }

    public List<Project> getAll() {
        return projectRepository.findAll();
    }

    private List<Category> fetchCategories(Project project) {
        return project.getCategories()
                .stream()
                .map(categoryService::getOrCreateCategory)
                .collect(Collectors.toList());
    }
}
