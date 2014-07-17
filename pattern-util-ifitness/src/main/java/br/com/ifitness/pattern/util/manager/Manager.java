package br.com.ifitness.pattern.util.manager;

import java.io.Serializable;
import java.util.List;

import br.com.ifitness.pattern.util.manager.exception.ManagementException;

public interface Manager<T, PK extends Serializable> {

	public void save(T obj) throws ManagementException;

	public T findById(PK id) throws ManagementException;

	public void update(T obj) throws ManagementException;

	public void delete(T obj) throws ManagementException;

	public List<T> findAll() throws ManagementException;
	
	public List<T> find(Long first, Long last) throws ManagementException;
	
	public Long listCount() throws ManagementException;

}
