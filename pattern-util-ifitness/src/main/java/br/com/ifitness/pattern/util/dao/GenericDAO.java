package br.com.ifitness.pattern.util.dao;

import java.io.Serializable;
import java.util.List;

import br.com.ifitness.pattern.util.dao.exception.DAOException;
/**
 * Interface de abstracao do Banco de Dados
 * GenericDAO pattern
 * 
 * http://www.ibm.com/developerworks/java/library/j-genericdao/index.html
 * 
 * @author Tiago F. Valerio
 *
 * @param <T>
 * @param <PK>
 */
public interface GenericDAO <T, PK extends Serializable> {
	/**
	 * Persiste um novo Objeto no banco
	 * @param obj
	 * 				O objeto <code>T</code>
	 * @return
	 * 			O id do objeto
	 */
	void save(T obj) throws DAOException;
	/**
	 * Busca um objeto pelo id
	 * @param id
	 * 			O id do objeto
	 * @return
	 * 			O objeto
	 * @throws DAOException 
	 */
	T findById(PK id) throws DAOException;
	/**
	 * Atualiza um objeto no banco
	 * @param obj
	 * 				O objeto
	 */
	void update(T obj) throws DAOException;
	/**
	 * Remove o objeto do banco
	 * @param obj
	 * 
	 */
	void delete(T obj) throws DAOException;
	/**
	 * Recupera todos os objetos do banco
	 * @return
	 * 			Um <code>java.util.List</code> com os objetos
	 */
	List<T> findAll() throws DAOException;
	
	/**
	 * Recupera last - first elementos começando em 'first' e terminando em 'last'
	 **/
	List<T> find(Long first, Long last) throws DAOException;
	
	/**
	 * Retorna a contagem de registros totais da tabela
	 * @return total de registros
	 * @throws DAOException
	 */
	Long listCount() throws DAOException;
	
	
}
