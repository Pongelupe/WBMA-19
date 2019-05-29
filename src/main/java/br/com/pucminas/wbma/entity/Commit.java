package br.com.pucminas.wbma.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Type;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Commit implements BaseEntity<Integer> {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "commited_at")
	@Temporal(value = TemporalType.TIMESTAMP)
	private Date commitedAt;

	@Column(name = "author", length = 60)
	private String author;

	@Column(name = "message")
	@Type(type = "text")
	private String message;

	@ManyToOne(optional = false)
	@JoinColumn(name = "repository_id")
	private Repository repository;

	@Column(name = "repository_id", insertable = false, updatable = false)
	private Integer repositoryId;

	@ManyToOne(optional = false)
	@JoinColumn(name = "contributor_id")
	private Contributor contributor;
	
	@Column(name = "contributor_id", insertable = false, updatable = false)
	private Integer contributorId;

}
