package br.com.pucminas.wbma.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Data
public class Repository implements BaseEntity<Integer> {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "name_tool", length = 60)
	private String toolName;

	@Column(name = "name", length = 60)
	private String name;

	@Column(name = "url", length = 120)
	private String url;

	@Column(name = "truck_factor")
	private int truckFactor;
	
	@ManyToOne(optional = false)
	@JoinColumn(name = "course_id")
	private Course course;
	
	@Column(name = "course_id", insertable = false, updatable = false)
	private Integer courseId;

}
