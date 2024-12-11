package com.customcomponent.dao;

import com.customcomponent.entity.CustomApp;
import com.totvs.technology.foundation.common.AbstractDAO;
import com.totvs.technology.foundation.common.exception.FDNRuntimeException;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * Aqui a sugestão é herdar a classe abstrata AbstractDAO e passar a entidade
 * CustomApp como sendo o objeto genérico desse DAO. Além disso, alguns serviços
 * de CRUD já são herdados.
 */
@Stateless(name = "dao/CustomApp", mappedName = "dao/CustomApp")
public class CustomAppDAO extends AbstractDAO<CustomApp> {

	/**
	 * Construtor para gerencia a entidade {@link CustomApp}
	 */
	public CustomAppDAO() {
		super(CustomApp.class);
	}

	private EntityManager em;

	@Override
	public EntityManager getEntityManager() {
		return this.em;
	}

	@Override
	// Obrigatório utilizar o DataSource correto: AppDS
	@PersistenceContext(unitName = "AppDS")
	public void setEntityManager(EntityManager em) {
		this.em = em;
	}

	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<CustomApp> findApps(Long tenantId, String text, int limit, int offset) {
		try {
			TypedQuery<CustomApp> query = getEntityManager().createNamedQuery(CustomApp.FIND_BY_NAME_DEV,
					CustomApp.class);
			query.setParameter("tenantId", tenantId);
			query.setParameter("text", "%" + text.toLowerCase() + "%");
			query.setFirstResult(offset);
			query.setMaxResults(limit);

			return query.getResultList();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new FDNRuntimeException(e.getMessage(), e.getCause()); 
		}
	}
	
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<CustomApp> findAppsByCategoryId(Long tenantId, Long categoryId, int limit, int offset) {
		try {
			TypedQuery<CustomApp> query = getEntityManager().createNamedQuery(CustomApp.FIND_BY_CATEGORY,
					CustomApp.class);
			query.setParameter("tenantId", tenantId);
			query.setParameter("categoryId", categoryId);
			query.setFirstResult(offset);
			query.setMaxResults(limit);

			return query.getResultList();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new FDNRuntimeException(e.getMessage(), e.getCause());
		}
	}

}
