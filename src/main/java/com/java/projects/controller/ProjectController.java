package com.java.projects.controller;

import com.java.projects.dto.ProjectDto;
import com.java.projects.model.Project;
import com.java.projects.service.ProjectService;
import com.java.projects.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class ProjectController {

    private final ProjectService projectService;
    private final UserService userService;

    public ProjectController(ProjectService projectService,
                             UserService userService){
        this.projectService = projectService;
        this.userService = userService;
    }

    @PostMapping("/project")
    public ResponseEntity<ProjectDto> addProject(@RequestBody @Valid ProjectDto dto){
        Project entity = Mapper.mapToEntity(dto);
        projectService.addProject(entity);
        return new ResponseEntity<>(Mapper.mapToDto(entity), HttpStatus.CREATED);
    }

    @GetMapping("/project")
    public ResponseEntity<Page<ProjectDto>> getPageOfProjects(@RequestParam(value ="category", required = false) String category,
                                                                            Pageable pageable){
        if(category!=null)
            return new ResponseEntity<>(projectService.getPageOfProjectsByCategoryName(category, pageable).map(Mapper::mapToDto), HttpStatus.OK);
        else
            return new ResponseEntity<>(projectService.getPageOfProjects(pageable).map(Mapper::mapToDto), HttpStatus.OK);

    }

    @PutMapping("/project/reserve/{id}")
    public ResponseEntity<ProjectDto> makeReservation(@PathVariable Long id,
                                      Principal principal) throws Exception {
        Project project = projectService.makeReservation(id,
                userService.getUserByUsernameIfNotMadeReservation(principal.getName()));
        return new ResponseEntity<>(Mapper.mapToDto(project), HttpStatus.OK);
    }

    @GetMapping("/project/all")
    public ResponseEntity<List<ProjectDto>> getAllProjects(){
        List<Project> projects = projectService.getAll();
        return new ResponseEntity<>(projects
                .stream()
                .map(Mapper::mapToDto)
        .collect(Collectors.toList()), HttpStatus.OK);
    }

    @GetMapping("/test")
    public String test() {
        return "test";
    }
}
