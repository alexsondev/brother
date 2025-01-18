package com.customcomponent.dao;

import com.customcomponent.entity.CustomCategory;
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
 * Aqui a sugestão é herdar a classe abstrata AbstractDAO e passar a entidade CustomCategory como sendo o objeto genérico desse DAO.
 * Além disso, alguns serviços de CRUD já são herdados.
 */
@Stateless(name = "dao/CustomCategory", mappedName = "dao/CustomCategory")
public class CustomCategoryDAO extends AbstractDAO<CustomCategory> {

	/**
	 * Construtor para gerencia a entidade {@link CustomCategory}
	 */
	public CustomCategoryDAO() {
		super(CustomCategory.class);
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
    public List<CustomCategory> findCategories(Long tenantId, String text, int limit, int offset) throws FDNRuntimeException {
        try {
            TypedQuery<CustomCategory> query = getEntityManager().createNamedQuery(CustomCategory.FIND_BY_NAME, CustomCategory.class);
            query.setParameter("tenantId", tenantId);
            query.setParameter("name", "%"+text.toLowerCase()+"%");
            query.setFirstResult(offset);
            query.setMaxResults(limit);
            
            return query.getResultList();
        } catch (FDNRuntimeException e) {
            log.error(e.getMessage(), e);
            throw new FDNRuntimeException(e.getMessage(), e.getCause());
        }
    }

}
