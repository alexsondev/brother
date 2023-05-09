package com.brother.vo;

import java.util.Objects;
import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.gson.Gson;

/**
 * Objeto de com os dados da API /api/public/admin/tenant Pode também ser chamado de DTO
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class MarketingItemSellinItVO implements Serializable {

  /**
   *
   */
  private static final long serialVersionUID = 1L;

  private ItemVO item;
  private double rebateUnit;
  private double qtde;
  private double rebateTotal;

  private double qtdEvidencia;
  private double valEvidencia;
  private double totEvidencia;

  public MarketingItemSellinItVO() {}

  @JsonIgnoreProperties(ignoreUnknown = true)

  public MarketingItemSellinItVO(ItemVO item, double rebateUnit, double qtde, double rebateTotal,
      double qtdEvidencia, double valEvidencia, double totEvidencia) {
    this.item = item;
    this.rebateUnit = rebateUnit;
    this.qtde = qtde;
    this.rebateTotal = rebateTotal;
    this.qtdEvidencia = qtdEvidencia;
    this.valEvidencia = valEvidencia;
    this.totEvidencia = totEvidencia;
  }

  public ItemVO getItem() {
    return this.item;
  }

  public void setItem(ItemVO item) {
    this.item = item;
  }

  public double getRebateUnit() {
    return this.rebateUnit;
  }

  public void setRebateUnit(double rebateUnit) {
    this.rebateUnit = rebateUnit;
  }

  public double getQtde() {
    return this.qtde;
  }

  public void setQtde(double qtde) {
    this.qtde = qtde;
  }

  public double getRebateTotal() {
    return this.rebateTotal;
  }

  public void setRebateTotal(double rebateTotal) {
    this.rebateTotal = rebateTotal;
  }

  public double getQtdEvidencia() {
    return this.qtdEvidencia;
  }

  public void setQtdEvidencia(double qtdEvidencia) {
    this.qtdEvidencia = qtdEvidencia;
  }

  public double getValEvidencia() {
    return this.valEvidencia;
  }

  public void setValEvidencia(double valEvidencia) {
    this.valEvidencia = valEvidencia;
  }

  public double getTotEvidencia() {
    return this.totEvidencia;
  }

  public void setTotEvidencia(double totEvidencia) {
    this.totEvidencia = totEvidencia;
  }

  public MarketingItemSellinItVO item(ItemVO item) {
    this.item = item;
    return this;
  }

  public MarketingItemSellinItVO rebateUnit(double rebateUnit) {
    this.rebateUnit = rebateUnit;
    return this;
  }

  public MarketingItemSellinItVO qtde(double qtde) {
    this.qtde = qtde;
    return this;
  }

  public MarketingItemSellinItVO rebateTotal(double rebateTotal) {
    this.rebateTotal = rebateTotal;
    return this;
  }

  public MarketingItemSellinItVO qtdEvidencia(double qtdEvidencia) {
    this.qtdEvidencia = qtdEvidencia;
    return this;
  }

  public MarketingItemSellinItVO valEvidencia(double valEvidencia) {
    this.valEvidencia = valEvidencia;
    return this;
  }

  public MarketingItemSellinItVO totEvidencia(double totEvidencia) {
    this.totEvidencia = totEvidencia;
    return this;
  }

  @Override
  public boolean equals(Object o) {
    if (o == this)
      return true;
    if (!(o instanceof MarketingItemSellinItVO)) {
      return false;
    }
    MarketingItemSellinItVO marketingItemSellinItVO = (MarketingItemSellinItVO) o;
    return Objects.equals(item, marketingItemSellinItVO.item)
        && rebateUnit == marketingItemSellinItVO.rebateUnit && qtde == marketingItemSellinItVO.qtde
        && rebateTotal == marketingItemSellinItVO.rebateTotal
        && qtdEvidencia == marketingItemSellinItVO.qtdEvidencia
        && valEvidencia == marketingItemSellinItVO.valEvidencia
        && totEvidencia == marketingItemSellinItVO.totEvidencia;
  }

  @Override
  public int hashCode() {
    return Objects.hash(item, rebateUnit, qtde, rebateTotal, qtdEvidencia, valEvidencia,
        totEvidencia);
  }

  @Override
  public String toString() {
    Gson gson = new Gson();
    return gson.toJson(this);

    // return "{" +
    // " item='" + getItem() + "'" +
    // ", rebateUnit='" + getRebateUnit() + "'" +
    // ", qtde='" + getQtde() + "'" +
    // ", rebateTotal='" + getRebateTotal() + "'" +
    // ", qtdEvidencia='" + getQtdEvidencia() + "'" +
    // ", valEvidencia='" + getValEvidencia() + "'" +
    // ", totEvidencia='" + getTotEvidencia() + "'" +
    // "}";
  }

}
