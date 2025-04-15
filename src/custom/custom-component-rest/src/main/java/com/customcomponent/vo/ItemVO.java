package com.customcomponent.vo;

import java.util.Objects;
import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.gson.Gson;

/**
 * Objeto de com os dados da API /api/public/admin/tenant Pode também ser chamado de DTO
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ItemVO implements Serializable {

  /**
   *
   */
  private static final long serialVersionUID = 1L;

  private String codigo;
  private String categoria;
  private String descricao;
  private String displaykey;
  private String ccusto;

  public ItemVO() {}

  @JsonIgnoreProperties(ignoreUnknown = true)

  public ItemVO(String codigo, String categoria, String descricao, String displaykey,
      String ccusto) {
    this.codigo = codigo;
    this.categoria = categoria;
    this.descricao = descricao;
    this.displaykey = displaykey;
    this.ccusto = ccusto;
  }

  public String getCodigo() {
    return this.codigo;
  }

  public void setCodigo(String codigo) {
    this.codigo = codigo;
  }

  public String getCategoria() {
    return this.categoria;
  }

  public void setCategoria(String categoria) {
    this.categoria = categoria;
  }

  public String getDescricao() {
    return this.descricao;
  }

  public void setDescricao(String descricao) {
    this.descricao = descricao;
  }

  public String getDisplaykey() {
    return this.displaykey;
  }

  public void setDisplaykey(String displaykey) {
    this.displaykey = displaykey;
  }

  public String getCcusto() {
    return this.ccusto;
  }

  public void setCcusto(String ccusto) {
    this.ccusto = ccusto;
  }

  public ItemVO codigo(String codigo) {
    this.codigo = codigo;
    return this;
  }

  public ItemVO categoria(String categoria) {
    this.categoria = categoria;
    return this;
  }

  public ItemVO descricao(String descricao) {
    this.descricao = descricao;
    return this;
  }

  public ItemVO displaykey(String displaykey) {
    this.displaykey = displaykey;
    return this;
  }

  public ItemVO ccusto(String ccusto) {
    this.ccusto = ccusto;
    return this;
  }

  @Override
  public boolean equals(Object o) {
    if (o == this)
      return true;
    if (!(o instanceof ItemVO)) {
      return false;
    }
    ItemVO itemVO = (ItemVO) o;
    return Objects.equals(codigo, itemVO.codigo) && Objects.equals(categoria, itemVO.categoria)
        && Objects.equals(descricao, itemVO.descricao)
        && Objects.equals(displaykey, itemVO.displaykey)
        && Objects.equals(ccusto, itemVO.ccusto);
  }

  @Override
  public int hashCode() {
    return Objects.hash(codigo, categoria, descricao, displaykey, ccusto);
  }

  @Override
  public String toString() {

    Gson gson = new Gson();
    return gson.toJson(this);

    // return "{" + " codigo='" + getCodigo() + "'" + ", categoria='" + getCategoria() + "'"
    // + ", descricao='" + getDescricao() + "'" + "}";
  }

}
