package com.customcomponent.vo;

import java.util.Objects;
import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.gson.Gson;

/**
 * Objeto de com os dados da API /api/public/admin/tenant
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class TenantVO implements Serializable {

  /**
   *
   */
  private static final long serialVersionUID = 1L;

  private Long id;
  private String code;
  private String federalId;
  private String description;
  private String uuid;
  private TenantDataVO[] dataVOList;

  @JsonIgnoreProperties(ignoreUnknown = true)

  public TenantVO() {}

  public TenantVO(Long id, String code, String federalId, String description, String uuid,
      TenantDataVO[] dataVOList) {
    this.id = id;
    this.code = code;
    this.federalId = federalId;
    this.description = description;
    this.uuid = uuid;
    this.dataVOList = dataVOList;
  }

  public Long getId() {
    return this.id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getCode() {
    return this.code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public String getFederalId() {
    return this.federalId;
  }

  public void setFederalId(String federalId) {
    this.federalId = federalId;
  }

  public String getDescription() {
    return this.description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getUuid() {
    return this.uuid;
  }

  public void setUuid(String uuid) {
    this.uuid = uuid;
  }

  public TenantDataVO[] getDataVOList() {
    return this.dataVOList;
  }

  public void setDataVOList(TenantDataVO[] dataVOList) {
    this.dataVOList = dataVOList;
  }

  public TenantVO id(Long id) {
    this.id = id;
    return this;
  }

  public TenantVO code(String code) {
    this.code = code;
    return this;
  }

  public TenantVO federalId(String federalId) {
    this.federalId = federalId;
    return this;
  }

  public TenantVO description(String description) {
    this.description = description;
    return this;
  }

  public TenantVO uuid(String uuid) {
    this.uuid = uuid;
    return this;
  }

  public TenantVO dataVOList(TenantDataVO[] dataVOList) {
    this.dataVOList = dataVOList;
    return this;
  }

  public String getData(String key) {
    String value = "";
    for (int i = 0; i < this.dataVOList.length; i++) {
      if (this.dataVOList[i].getKey().equals(key)) {
        value = this.dataVOList[i].getValue();
      }
    }

    return value;
  }

  @Override
  public boolean equals(Object o) {
    if (o == this)
      return true;
    if (!(o instanceof TenantVO)) {
      return false;
    }
    TenantVO tenantVO = (TenantVO) o;
    return Objects.equals(id, tenantVO.id) && Objects.equals(code, tenantVO.code)
        && Objects.equals(federalId, tenantVO.federalId)
        && Objects.equals(description, tenantVO.description) && Objects.equals(uuid, tenantVO.uuid)
        && Objects.equals(dataVOList, tenantVO.dataVOList);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, code, federalId, description, uuid, dataVOList);
  }

  @Override
  public String toString() {
    Gson gson = new Gson();
    return gson.toJson(this);

    // return "{" + " id='" + getId() + "'" + ", code='" + getCode() + "'" + ", federalId='"
    // + getFederalId() + "'" + ", description='" + getDescription() + "'" + ", uuid='"
    // + getUuid() + "'" + ", dataVOList='" + getDataVOList() + "'" + "}";
  }

}
