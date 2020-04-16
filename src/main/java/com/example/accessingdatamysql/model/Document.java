package com.example.accessingdatamysql.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.Entity;
import javax.persistence.FetchType;
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
@Entity(name = "document") // This tells Hibernate to make a table out of this class
public class Document {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;

	@NotNull
	@Size(max = 256)
    private String name;

    private Date uploadedDate;

	private Date lastModified;

//	@ManyToOne(fetch = FetchType.LAZY, optional = false)
//	@JoinColumn(name = "student_id", nullable = false)
//	@OnDelete(action = OnDeleteAction.CASCADE)
//	@JsonIdentityInfo(generator= ObjectIdGenerators.PropertyGenerator.class, property="id")
//	@JsonIdentityReference(alwaysAsId=true)
	@ManyToOne
	@JoinColumn(name = "student_id", referencedColumnName = "id")
	private User student;

//	@ManyToOne(fetch = FetchType.LAZY, optional = false)
//	@JoinColumn(name = "tutor_id", nullable = false)
//	@OnDelete(action = OnDeleteAction.CASCADE)
//	@JsonIdentityInfo(generator= ObjectIdGenerators.PropertyGenerator.class, property="id")
//	@JsonIdentityReference(alwaysAsId=true)
//	@JsonProperty("tutor_id")
	@ManyToOne
	@JoinColumn(name = "tutor_id", referencedColumnName = "id")
	private User tutor;

	private String fileDownloadUri;
	private String fileType;
	private long size;
}