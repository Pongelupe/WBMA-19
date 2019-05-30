package br.com.pucminas.wbma.entity;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "repositoryxcontributor")
@NoArgsConstructor
@AllArgsConstructor
public class RepositoryXContributor implements BaseEntity<RepositoryXContributorId> {

	@EmbeddedId
	private RepositoryXContributorId id;

	@Column(name = "key_contributor")
	private Boolean keyContributor;

	public RepositoryXContributorId getId() {
		return id;
	}

	public void setId(RepositoryXContributorId id) {
		this.id = id;
	}

	public Boolean getKeyContributor() {
		return keyContributor;
	}

	public void setKeyContributor(Boolean keyContributor) {
		this.keyContributor = keyContributor;
	}

}
