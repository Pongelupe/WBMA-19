package br.com.pucminas.wbma.entity;

/**
 * This interface defines base operations for entities
 * 
 * @author pongelupe
 *
 */
public interface BaseEntity<T> {

	/**
	 * 
	 * Retrieves the entity's id
	 * 
	 * @return
	 */
	T getId();

	/**
	 * 
	 * Sets the entity's id
	 * 
	 * @param id
	 */
	void setId(T id);
}
