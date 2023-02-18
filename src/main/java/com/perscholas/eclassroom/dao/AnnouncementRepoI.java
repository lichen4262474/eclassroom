package com.perscholas.eclassroom.dao;

import com.perscholas.eclassroom.models.Announcement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnnouncementRepoI extends JpaRepository<Announcement,Integer> {
}
