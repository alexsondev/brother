package com.brother.vo;

import java.util.Objects;
import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.gson.Gson;

/**
 * Objeto de com os dados da API /api/public/admin/tenant Pode também ser chamado de DTO
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ItemEvidenciaVO implements Serializable {

  /**
   *
   */
  private static final long serialVersionUID = 1L;

  private String tablename;
  private String descricao;
  private String index;
  private double valorTotal;

  public ItemEvidenciaVO() {}

  @JsonIgnoreProperties(ignoreUnknown = true)

  public ItemEvidenciaVO(String tablename, String descricao, String index, double valorTotal) {
    this.tablename = tablename;
    this.descricao = descricao;
    this.index = index;
    this.valorTotal = valorTotal;
  }

  public String getTablename() {
    return this.tablename;
  }

  public void setTablename(String tablename) {
    this.tablename = tablename;
  }

  public String getDescricao() {
    return this.descricao;
  }

  public void setDescricao(String descricao) {
    this.descricao = descricao;
  }

  public String getIndex() {
    return this.index;
  }

  public void setIndex(String index) {
    this.index = index;
  }

  public double getValorTotal() {
    return this.valorTotal;
  }

  public void setValorTotal(double valorTotal) {
    this.valorTotal = valorTotal;
  }

  public ItemEvidenciaVO tablename(String tablename) {
    this.tablename = tablename;
    return this;
  }

  public ItemEvidenciaVO descricao(String descricao) {
    this.descricao = descricao;
    return this;
  }

  public ItemEvidenciaVO index(String index) {
    this.index = index;
    return this;
  }

  public ItemEvidenciaVO valorTotal(double valorTotal) {
    this.valorTotal = valorTotal;
    return this;
  }

  @Override
  public boolean equals(Object o) {
    if (o == this)
      return true;
    if (!(o instanceof ItemEvidenciaVO)) {
      return false;
    }
    ItemEvidenciaVO itemEvidenciaVO = (ItemEvidenciaVO) o;
    return Objects.equals(tablename, itemEvidenciaVO.tablename)
        && Objects.equals(descricao, itemEvidenciaVO.descricao)
        && Objects.equals(index, itemEvidenciaVO.index) && valorTotal == itemEvidenciaVO.valorTotal;
  }

  @Override
  public int hashCode() {
    return Objects.hash(tablename, descricao, index, valorTotal);
  }

  @Override
  public String toString() {
    Gson gson = new Gson();
    return gson.toJson(this);
    // return "{" + " tablename='" + getTablename() + "'" + ", descricao='" + getDescricao() + "'"
    //     + ", index='" + getIndex() + "'" + ", valorTotal='" + getValorTotal() + "'" + "}";
  }


}
