package com.socialmediaclone.socialmediaclone.respositories;

import com.socialmediaclone.socialmediaclone.entities.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {
}
