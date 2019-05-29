package br.com.pucminas.wbma.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RepositoryXContributorId implements Serializable {

	private static final long serialVersionUID = 1953799980386184438L;

	@Column(name = "repository_id")
	private Integer repositoryId;

	@Column(name = "contributor_id")
	private Integer contributorId;

}
