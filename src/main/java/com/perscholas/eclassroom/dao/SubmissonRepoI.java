package com.perscholas.eclassroom.dao;

import com.perscholas.eclassroom.models.Submission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubmissonRepoI extends JpaRepository<Submission,Integer> {
}
