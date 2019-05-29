package br.com.pucminas.wbma.entity;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

@Entity
public class RepositoryXContributor {

	@EmbeddedId
	private RepositoryXContributorId id;

	@Column(name = "key_contributor")
	private Boolean keyContributor;

}
