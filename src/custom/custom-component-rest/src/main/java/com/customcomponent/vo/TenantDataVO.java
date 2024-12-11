package com.customcomponent.vo;

import java.util.Objects;
import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.gson.Gson;

/**
 * Objeto de com os dados da API /api/public/admin/tenant Pode também ser chamado de DTO
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class TenantDataVO implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private String key;
    private String value;

    public TenantDataVO() {
    }


    public TenantDataVO(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return this.value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public TenantDataVO key(String key) {
        this.key = key;
        return this;
    }

    public TenantDataVO value(String value) {
        this.value = value;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof TenantDataVO)) {
            return false;
        }
        TenantDataVO tenantDataVO = (TenantDataVO) o;
        return Objects.equals(key, tenantDataVO.key) && Objects.equals(value, tenantDataVO.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(key, value);
    }

    @Override
    public String toString() {
      Gson gson = new Gson();
    return gson.toJson(this);
    
        // return "{" +
        //     " key='" + getKey() + "'" +
        //     ", value='" + getValue() + "'" +
        //     "}";
    }
    

}
