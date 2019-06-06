package br.com.pucminas.wbma.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Type;

import br.com.pucminas.wbma.dtos.DiffDTO;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Table(name = "commit")
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
	
	@Column(name = "files_altered")
	private Integer filesAltered;

	@Column(name = "insertions")
	private Integer insertions;

	@Column(name = "deletions")
	private Integer deletions;

	@Column(name = "modifications")
	private Integer modifications;
	

	public Commit(Date commitedAt, String author, String message, DiffDTO diff) {
		this.commitedAt = commitedAt;
		this.author = author;
		this.message = message;
		
		this.insertions = (int) diff.getInsertions();
		this.deletions = (int) diff.getDeletions();
		this.modifications = (int) diff.getModifications();
		this.filesAltered = (int) diff.getFilesAltered();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getCommitedAt() {
		return commitedAt;
	}

	public void setCommitedAt(Date commitedAt) {
		this.commitedAt = commitedAt;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Repository getRepository() {
		return repository;
	}

	public void setRepository(Repository repository) {
		this.repository = repository;
	}

	public Integer getRepositoryId() {
		return repositoryId;
	}

	public void setRepositoryId(Integer repositoryId) {
		this.repositoryId = repositoryId;
	}

	public Contributor getContributor() {
		return contributor;
	}

	public void setContributor(Contributor contributor) {
		this.contributor = contributor;
	}

	public Integer getContributorId() {
		return contributorId;
	}

	public void setContributorId(Integer contributorId) {
		this.contributorId = contributorId;
	}

}
