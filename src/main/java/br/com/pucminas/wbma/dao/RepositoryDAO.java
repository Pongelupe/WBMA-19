package br.com.pucminas.wbma.dao;

import java.util.Date;
import java.util.List;

import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;

import br.com.pucminas.wbma.entity.Course;
import br.com.pucminas.wbma.entity.Course_;
import br.com.pucminas.wbma.entity.Repository;
import br.com.pucminas.wbma.entity.Repository_;

public class RepositoryDAO extends BaseDAO<Repository> {

	public RepositoryDAO() {
		super(Repository.class);
	}

	public List<Repository> findAllBySemester(Date semester) {
		CriteriaQuery<Repository> cq = getCb().createQuery(Repository.class);
		Root<Repository> from = cq.from(Repository.class);
		Join<Repository, Course> join = from.join(Repository_.course);

		cq.select(from).where(getCb().equal(join.get(Course_.semester), semester));

		return em.createQuery(cq).getResultList();
	}

}
