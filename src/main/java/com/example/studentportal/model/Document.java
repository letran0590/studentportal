package com.example.studentportal.model;

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

	@ManyToOne
	@JoinColumn(name = "student_id", referencedColumnName = "id")
	private User student;

	@ManyToOne
	@JoinColumn(name = "tutor_id", referencedColumnName = "id")
	private User tutor;

	private String fileDownloadUri;
	private String fileType;
	private long size;

	private boolean deletedFlag;

	private String viewDocumentUrl;
}
