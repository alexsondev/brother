package com.brother.component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.brother.vo.*;
import com.brother.util.*;
import com.brother.oauth.*;
import org.json.*;

public class TenantComponent {

    private static Logger log = LoggerFactory.getLogger(TenantComponent.class);

    public static TenantVO getTenantData() throws Exception {
        try {

            log.info(
                    String.format("=*=*=*=*= brother-component | TenantComponent | getTenantData"));

            JSONObject tenantObject = OAuthRequest.request("/api/public/admin/tenant",
                    RestConstant.REQUEST_METHOD_GET, null);

            Long id = tenantObject.getJSONObject("content").optLong("id");
            String code = tenantObject.getJSONObject("content").optString("code");
            String federalId = tenantObject.getJSONObject("content").optString("federalId");
            String description = tenantObject.getJSONObject("content").optString("description");
            String uuid = tenantObject.getJSONObject("content").optString("uuid");

            JSONArray dataVOListArray =
                    tenantObject.getJSONObject("content").optJSONArray("dataVOList");

            TenantDataVO[] dataVOList = new TenantDataVO[dataVOListArray.length()];

            for (int i = 0; i < dataVOListArray.length(); i++) {

                String key = dataVOListArray.getJSONObject(i).optString("key");
                String value = dataVOListArray.getJSONObject(i).optString("value");

                dataVOList[i] = new TenantDataVO(key, value);
            }

            return new TenantVO(id, code, federalId, description, uuid, dataVOList);

        } catch (Exception e) {
            throw e;
        }

    }

}
