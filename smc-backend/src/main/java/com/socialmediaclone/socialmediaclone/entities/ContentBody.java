package com.socialmediaclone.socialmediaclone.entities;


import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ContentBody {

    @Id
    @SequenceGenerator(name = "content_id_seq", sequenceName = "content_id_seq", allocationSize = 1)
    @GeneratedValue(generator = "content_id_seq", strategy = GenerationType.SEQUENCE)
    private Long contentId;
    @NotNull
    private String text;
    @OneToOne(cascade = CascadeType.REMOVE)
    private Image image;
    @OneToOne(cascade = CascadeType.REMOVE)
    private Video video;

}
