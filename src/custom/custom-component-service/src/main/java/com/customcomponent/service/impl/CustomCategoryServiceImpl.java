package com.customcomponent.service.impl;

import com.fluig.sdk.api.common.SDKException;
import com.fluig.sdk.service.SecurityService;
import com.fluig.sdk.service.UserService;
import com.customcomponent.dao.CustomCategoryDAO;
import com.customcomponent.entity.CustomCategory;
import com.customcomponent.service.CustomCategoryService;
import com.totvs.technology.foundation.common.exception.FDNCreateException;
import com.totvs.technology.foundation.common.exception.FDNRemoveException;
import com.totvs.technology.foundation.common.exception.FDNUpdateException;

import javax.ejb.*;
import java.util.List;
import java.util.Optional;

@Remote
@Stateless(name = CustomCategoryService.JNDI_NAME, mappedName = CustomCategoryService.JNDI_NAME)
public class CustomCategoryServiceImpl implements CustomCategoryService {

	@EJB
	private CustomCategoryDAO dao;

	@EJB(lookup = SecurityService.JNDI_REMOTE_NAME)
	private SecurityService securityService;
	
	@EJB(lookup = UserService.JNDI_REMOTE_NAME)
	private UserService userService;

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public long create(CustomCategory category) throws FDNCreateException {
		try {
			/**
			 * check permission if needed 
			 */
			category.setTenantId(securityService.getCurrentTenantId());
			Optional<CustomCategory> categoryOptional = Optional.ofNullable(dao.create(category));
			return (categoryOptional.isPresent() ? categoryOptional.get().getId() : null);
		} catch (FDNCreateException | SDKException e) {
            throw new FDNCreateException(e.getMessage(), e);
		}
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public CustomCategory get(long id) {
		Optional<CustomCategory> customCategory = Optional.ofNullable(dao.find(id));
		return (customCategory.isPresent() ? customCategory.get() : null);
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void update(CustomCategory customCategory) throws FDNUpdateException {
		/**
		 * check permission if needed
		 */
		Optional<CustomCategory> customCategory1 = Optional.ofNullable(dao.find(customCategory.getId()));
		if (!customCategory1.isPresent())
			throw new FDNUpdateException("No Category found for ID: " + customCategory.getId());
		customCategory.setTenantId(customCategory1.get().getTenantId());
		dao.edit(customCategory);
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public void delete(long id) throws FDNRemoveException {
		/**
		 * check permission if needed
		 */
		Optional<CustomCategory> customCategory = Optional.ofNullable(dao.find(id));
		if (!customCategory.isPresent())
			throw new FDNRemoveException("No Category found for ID: " + id);
		dao.remove(customCategory.get());
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<CustomCategory> find(String text, int limit, int offset) throws SDKException {
		return dao.findCategories(securityService.getCurrentTenantId(), text, limit, offset);
	}

}
