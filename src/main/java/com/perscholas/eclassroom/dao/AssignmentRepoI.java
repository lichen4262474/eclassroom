package com.perscholas.eclassroom.dao;

import com.perscholas.eclassroom.models.Assignment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AssignmentRepoI extends JpaRepository<Assignment,Integer> {
}
