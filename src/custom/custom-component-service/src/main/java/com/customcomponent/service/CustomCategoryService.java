package com.customcomponent.service;

import java.util.List;

import javax.ejb.Remote;

import com.fluig.sdk.api.common.SDKException;
import com.customcomponent.entity.CustomCategory;
import com.totvs.technology.foundation.common.exception.FDNCreateException;
import com.totvs.technology.foundation.common.exception.FDNRemoveException;
import com.totvs.technology.foundation.common.exception.FDNUpdateException;

@Remote
public interface CustomCategoryService {

	public static final String JNDI_NAME = "service/custom-category";
	public static final String JNDI_REMOTE_NAME = "java:global/fluig/store/" + JNDI_NAME;
	
	long create(CustomCategory entity) throws FDNCreateException;
	
	CustomCategory get(long id);
	
	void update(CustomCategory entity) throws FDNUpdateException;
	
	void delete(long id) throws FDNRemoveException;
	
	List<CustomCategory> find(String text, int limit, int offset) throws SDKException;
}
