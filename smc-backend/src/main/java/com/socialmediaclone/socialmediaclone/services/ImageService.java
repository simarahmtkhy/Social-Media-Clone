package com.socialmediaclone.socialmediaclone.services;

import com.socialmediaclone.socialmediaclone.entities.Image;
import com.socialmediaclone.socialmediaclone.respositories.ImageRepository;
import com.socialmediaclone.socialmediaclone.respositories.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class ImageService {


    private final ImageRepository imageRepository;

    @Autowired
    public ImageService(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }


    public Long save(MultipartFile file) throws IOException {
        String uuid = UUID.randomUUID().toString();
        Image image = new Image("" + uuid);
        image.setUuid("assets/" + uuid);
        File imageToSave = new File(image.getPath());
        file.transferTo(imageToSave);
        this.imageRepository.save(image);
        return image.getId();
    }

    public void deleteImage(String path) throws IOException {
        Files.delete(Path.of(path));
    }

    public byte[] getImage(String image_uuid) throws IOException {
        Path path = Paths.get("" + image_uuid);
        return Files.readAllBytes(path);
    }
}
