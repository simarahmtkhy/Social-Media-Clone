package com.socialmediaclone.socialmediaclone.entities;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Image {

    @Id
    @SequenceGenerator(name = "image_id_seq", sequenceName = "image_id_seq", allocationSize = 1)
    @GeneratedValue(generator = "image_id_seq", strategy = GenerationType.SEQUENCE)
    private Long id;
    private String path;

    private String uuid;

    public Image(String path) {
        this.path = path;
    }
}
