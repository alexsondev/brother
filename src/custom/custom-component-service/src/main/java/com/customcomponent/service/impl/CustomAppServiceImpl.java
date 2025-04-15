package com.customcomponent.service.impl;

import com.fluig.sdk.api.common.SDKException;
import com.fluig.sdk.service.SecurityService;
import com.fluig.sdk.service.UserService;
import com.fluig.sdk.tenant.AdminUserVO;
import com.customcomponent.dao.CustomAppDAO;
import com.customcomponent.entity.CustomApp;
import com.customcomponent.entity.CustomCategory;
import com.customcomponent.service.CustomAppService;
import com.customcomponent.service.CustomCategoryService;
import com.totvs.technology.foundation.common.exception.FDNCreateException;
import com.totvs.technology.foundation.common.exception.FDNRemoveException;
import com.totvs.technology.foundation.common.exception.FDNRuntimeException;
import com.totvs.technology.foundation.common.exception.FDNUpdateException;

import javax.ejb.*;
import java.util.List;
import java.util.Optional;

@Remote(CustomAppService.class)
@Stateless(mappedName = CustomAppService.JNDI_NAME, name = CustomAppService.JNDI_NAME)
public class CustomAppServiceImpl implements CustomAppService {

	@EJB
	private CustomAppDAO dao;

	@EJB
	private CustomCategoryService categoryService;

	@EJB(lookup = SecurityService.JNDI_REMOTE_NAME)
	private SecurityService securityService;

	@EJB(lookup = UserService.JNDI_REMOTE_NAME)
	private UserService userService;

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public long create(CustomApp app) throws FDNCreateException {
		try {
			/**
			 * Check permission if needed
			 * DO SOMETHING LIKE THIS
			 */
			if (!isUserLoggedAdmin())
				throw new FDNCreateException("Only admin can create this resource");
			
			CustomCategory category = categoryService.get(app.getCategoryId());
			if(category == null)
				throw new FDNCreateException("No Category found for id: " + app.getCategoryId());
			
			app.setCategory(category);
			app.setTenantId(category.getTenantId());
			Optional<CustomApp> customApp = Optional.ofNullable(dao.create(app));
			return customApp.isPresent() ? customApp.get().getId() : null;
		} catch (FDNCreateException e) {
            throw new FDNCreateException(e.getMessage(), e);
		}
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public CustomApp get(long id) {
		Optional<CustomApp> customApp = Optional.ofNullable(dao.find(id));
		return customApp.isPresent() ? customApp.get() : null;
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void update(CustomApp app) throws FDNUpdateException {
		/**
		 * Check permission if needed
		 */
		Optional<CustomApp> customApp = Optional.ofNullable(dao.find(app.getId()));
		if (!customApp.isPresent())
			throw new FDNUpdateException("No App found for ID: " + app.getId());

		if (customApp.get().getCategory().getId().equals(app.getCategoryId())) {
			app.setCategory(customApp.get().getCategory());
		} else {
			CustomCategory category = categoryService.get(app.getCategoryId());
			if (category == null)
				throw new FDNUpdateException("No Category found for id: " + app.getCategoryId());
			app.setCategory(category);
		}
		app.setTenantId(customApp.get().getTenantId());
		dao.edit(app);
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void delete(long id) throws FDNRemoveException {
		/**
		 * Check permission if needed
		 */
		Optional<CustomApp> customApp = Optional.ofNullable(dao.find(id));
		if (!customApp.isPresent())
			throw new FDNRemoveException("No App found for ID: " + id);
		dao.remove(customApp.get());
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<CustomApp> find(String text, int limit, int offset) throws SDKException{
		try {
			return dao.findApps(securityService.getCurrentTenantId(), text, limit, offset);
		} catch (FDNRuntimeException | SDKException e) {
			throw new FDNRuntimeException(e.getMessage(), e.getCause());
		}
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<CustomApp> findByCategory(Long categoryId, int limit, int offset) throws SDKException {
		try {
			return dao.findAppsByCategoryId(securityService.getCurrentTenantId(), categoryId, limit, offset);
		} catch (FDNRuntimeException | SDKException e) {
			throw new FDNRuntimeException(e.getMessage(), e.getCause());
		}
	}

	private boolean isUserLoggedAdmin() {
		try {
			String login = userService.getCurrent().getLogin();
			List<AdminUserVO> tenantAdmins = securityService.listTenantAdmins(securityService.getCurrentTenantId());
			for (AdminUserVO admin : tenantAdmins)
				if (admin.getLogin().equals(login))
					return true;
			return false;
		} catch (SDKException e) {
			throw new RuntimeException("Can't request tenant admin list");
		}
	}
}
