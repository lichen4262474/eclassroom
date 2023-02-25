package com.perscholas.eclassroom.repo;

import com.perscholas.eclassroom.models.Announcement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface AnnouncementRepoI extends JpaRepository<Announcement,Integer> {

    @Transactional
    @Modifying
    @Query(value="update Announcement set announcement.title = ?1, announcement.content = ?2, where announcement.id = ?3",nativeQuery = true)
    void setAnnouncementInfoById(String title, String content, Integer id);
}
