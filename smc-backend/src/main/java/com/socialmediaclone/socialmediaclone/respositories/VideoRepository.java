package com.socialmediaclone.socialmediaclone.respositories;

import com.socialmediaclone.socialmediaclone.entities.Video;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VideoRepository extends JpaRepository<Video, Long> {
}
