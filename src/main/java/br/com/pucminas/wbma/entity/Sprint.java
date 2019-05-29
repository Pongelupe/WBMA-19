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

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Data
public class Sprint implements BaseEntity<Integer> {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "name", length = 60)
	private String name;

	@Column(name = "initial_date")
	@Temporal(value = TemporalType.DATE)
	private Date initialDate;

	@Column(name = "end_date")
	@Temporal(value = TemporalType.DATE)
	private Date endDate;

	@ManyToOne(optional = false)
	@JoinColumn(name = "course_id")
	private Course course;

	@Column(name = "course_id", insertable = false, updatable = false)
	private int courseId;

}
