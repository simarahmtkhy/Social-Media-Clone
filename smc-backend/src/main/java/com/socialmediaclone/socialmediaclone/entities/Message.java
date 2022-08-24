package com.socialmediaclone.socialmediaclone.entities;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Message {

    @Id
    @SequenceGenerator(name = "message_id_seq", sequenceName = "message_id_seq", allocationSize = 1)
    @GeneratedValue(generator = "message_id_seq", strategy = GenerationType.SEQUENCE)
    private Long messageId;
    @ManyToOne
    @JoinColumn(name = "sender_id", referencedColumnName = "userId")
    private User sender;
    @ManyToOne
    @JoinColumn(name = "receiverId", referencedColumnName = "userId")
    private User receiver;
    @OneToOne(cascade = CascadeType.ALL)
    private ContentBody body;
    private LocalDateTime dateTime;

}
