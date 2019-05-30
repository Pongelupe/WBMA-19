package br.com.pucminas.wbma.dao;

import java.util.Optional;

import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import br.com.pucminas.wbma.entity.Contributor;
import br.com.pucminas.wbma.entity.Contributor_;

public class ContributorDAO extends BaseDAO<Contributor> {

	public ContributorDAO() {
		super(Contributor.class);
	}

	public Optional<Contributor> findByUsername(String username) {
		CriteriaQuery<Contributor> cq = getCb().createQuery(Contributor.class);
		Root<Contributor> from = cq.from(Contributor.class);

		cq.select(from).where(getCb().equal(getCb().lower(from.get(Contributor_.username)), username));

		try {
			return Optional.ofNullable(em.createQuery(cq).getSingleResult());
		} catch (NoResultException e) {
			return Optional.empty();
		}
	}

	@Override
	public boolean exists(Contributor entity) {
		CriteriaQuery<Integer> cq = getCb().createQuery(Integer.class);
		Root<Contributor> from = cq.from(Contributor.class);

		cq.select(getCb().literal(1)).where(getCb().or(getCb().equal(from.get(Contributor_.id), entity.getId()),
				getCb().equal(getCb().lower(from.get(Contributor_.username)), entity.getUsername())));

		return !em.createQuery(cq).getResultList().isEmpty();
	}

}
