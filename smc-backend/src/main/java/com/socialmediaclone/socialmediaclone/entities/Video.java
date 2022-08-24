package com.socialmediaclone.socialmediaclone.entities;


import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Video {

    @Id
    @SequenceGenerator(name = "video_id_seq", sequenceName = "video_id_seq", allocationSize = 1)
    @GeneratedValue(generator = "video_id_seq", strategy = GenerationType.SEQUENCE)
    private Long id;
    private String path;

    private String uuid;
    public Video(String path) {
        this.path = path;
    }
}
