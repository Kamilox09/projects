package com.java.projects.repository;

import com.java.projects.model.Project;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
    @Query("select p from Project p join p.categories cat where cat.name = :name")
    Page<Project> getPageByCategoryName(String name, Pageable pageable);
}
