package com.customcomponent.service;

import java.util.List;

import javax.ejb.Remote;

import com.fluig.sdk.api.common.SDKException;
import com.customcomponent.entity.CustomApp;
import com.totvs.technology.foundation.common.exception.FDNCreateException;
import com.totvs.technology.foundation.common.exception.FDNRemoveException;
import com.totvs.technology.foundation.common.exception.FDNUpdateException;

@Remote
public interface CustomAppService {

	public static final String JNDI_NAME = "service/custom-app";
	public static final String JNDI_REMOTE_NAME = "java:global/fluig/store/" + JNDI_NAME;
	
	long create(CustomApp entity) throws FDNCreateException;
	
	CustomApp get(long id);
	
	void update(CustomApp entity) throws FDNUpdateException;
	
	void delete(long id) throws FDNRemoveException;
	
	List<CustomApp> find(String text, int limit, int offset) throws SDKException;
	
	List<CustomApp> findByCategory(Long categoryId, int limit, int offset) throws SDKException;
}
