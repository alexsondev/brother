package com.customcomponent.vo;

import java.util.Objects;
import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.gson.Gson;

/**
 * Objeto de com os dados da API /ecm/api/rest/ecm/workflowView/send
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ECMFormDataVO implements Serializable {

  /**
   *
   */
  private static final long serialVersionUID = 1L;
  private String name;
  private String value;

  public ECMFormDataVO() {}

  @JsonIgnoreProperties(ignoreUnknown = true)

  public ECMFormDataVO(String name, String value) {
    this.name = name;
    this.value = value;
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getValue() {
    return this.value;
  }

  public void setValue(String value) {
    this.value = value;
  }

  public ECMFormDataVO name(String name) {
    setName(name);
    return this;
  }

  public ECMFormDataVO value(String value) {
    setValue(value);
    return this;
  }

  @Override
  public boolean equals(Object o) {
    if (o == this)
      return true;
    if (!(o instanceof ECMFormDataVO)) {
      return false;
    }
    ECMFormDataVO eCMFormDataVO = (ECMFormDataVO) o;
    return Objects.equals(name, eCMFormDataVO.name);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, value);
  }

  @Override
  public String toString() {
    Gson gson = new Gson();
    return gson.toJson(this);

    // return "{" + " name='" + getName() + "'" + ", value='" + getValue() + "'" + "}";
  }

}
