package com.example.accessingdatamysql.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;


@NoArgsConstructor
@Getter
@Setter
@Entity(name = "chat") // This tells Hibernate to make a table out of this class
public class Chat {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @NotNull
    @Size(max = 256)
    private String text;

    private Date dateChat;

    @ManyToOne
    @JoinColumn(name = "student_ID", referencedColumnName = "id")
    private User student;

    @ManyToOne
    @JoinColumn(name = "tutor_ID", referencedColumnName = "id")
    private User tutor;

}
