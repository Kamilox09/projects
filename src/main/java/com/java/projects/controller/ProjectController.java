package com.java.projects.controller;

import com.java.projects.dto.ProjectDto;
import com.java.projects.model.Project;
import com.java.projects.service.ProjectService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
public class ProjectController {

    private final ProjectService projectService;

    public ProjectController(ProjectService projectService){
        this.projectService = projectService;
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
}
