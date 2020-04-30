package com.example.studentportal.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import org.hibernate.validator.constraints.UniqueElements;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Date;

@Getter
@Setter
@Entity(name = "user") // This tells Hibernate to make a table out of this class
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @NotNull
    @Size(max = 64)
    private String firstName;

    @NonNull
    @Size(max = 64)
    private String lastName;

    @NotNull
    @Size(max = 32)
    @JsonIgnore
    private String password;

    private Date dob;

    @NonNull
    @Size(max = 256)
    private String address;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "tutor_id", referencedColumnName = "id")
    private User tutor;

    private Integer grade;

    private String gender;

    @Email(message = "Please provide a valid email address")
    @Pattern(regexp = ".+@.+\\..+", message = "Please provide a valid email address")
    @NonNull
    @UniqueElements
    private String email;

//	@ManyToOne(fetch = FetchType.LAZY, optional = false)
//	@JoinColumn(name = "role_id", nullable = false)
//	@OnDelete(action = OnDeleteAction.CASCADE)
//	@JsonIdentityInfo(generator= ObjectIdGenerators.PropertyGenerator.class, property="id")
//	@JsonIdentityReference(alwaysAsId=true)
//	@JsonProperty("role_id")
//	private Role role;

    @ManyToOne
    @JoinColumn(name = "role_id", referencedColumnName = "id")
    private Role role;

    private boolean tutorFlag;

    private String avatarDownloadUri;
    private String avatarFilePath;
    private String avatarViewUrl;
}
