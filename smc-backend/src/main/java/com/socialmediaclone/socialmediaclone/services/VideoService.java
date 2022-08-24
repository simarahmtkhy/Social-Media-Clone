package com.socialmediaclone.socialmediaclone.services;


import com.socialmediaclone.socialmediaclone.entities.Video;
import com.socialmediaclone.socialmediaclone.respositories.VideoRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class VideoService {

    private final VideoRepository videoRepository;



    public VideoService(VideoRepository videoRepository) {
        this.videoRepository = videoRepository;
    }

    public Long save(MultipartFile file) throws IOException {
        String uuid = UUID.randomUUID().toString();
        Video video = new Video("" + uuid);
        video.setUuid("assets/" + uuid);
        File imageToSave = new File(video.getPath());
        file.transferTo(imageToSave);
        this.videoRepository.save(video);
        return video.getId();
    }

    public void deleteVideo(String path) throws IOException {
        Files.delete(Path.of(path));
    }
    public byte[] getVideo(String video_uuid) throws IOException {
        Path path = Paths.get("" + video_uuid);
        return Files.readAllBytes(path);
    }
}
