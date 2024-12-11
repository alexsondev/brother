package com.customcomponent.vo;

import java.util.Objects;
import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.gson.Gson;

/**
 * Objeto de com os dados da API /api/public/admin/tenant Pode também ser chamado de DTO
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class MarketingItemVpcOutrosVO implements Serializable {

  /**
   *
   */
  private static final long serialVersionUID = 1L;

  private String tipo;
  private String finalidade;
  private double qtde;
  private double perc;
  private double valorTotal;

  private double qtdEvidencia;
  private double valEvidencia;
  private double totEvidencia;

  public MarketingItemVpcOutrosVO() {}

  @JsonIgnoreProperties(ignoreUnknown = true)

  public MarketingItemVpcOutrosVO(String tipo, String finalidade, double qtde, double perc,
      double valorTotal, double qtdEvidencia, double valEvidencia, double totEvidencia) {
    this.tipo = tipo;
    this.finalidade = finalidade;
    this.qtde = qtde;
    this.perc = perc;
    this.valorTotal = valorTotal;
    this.qtdEvidencia = qtdEvidencia;
    this.valEvidencia = valEvidencia;
    this.totEvidencia = totEvidencia;
  }

  public String getTipo() {
    return this.tipo;
  }

  public void setTipo(String tipo) {
    this.tipo = tipo;
  }

  public String getFinalidade() {
    return this.finalidade;
  }

  public void setFinalidade(String finalidade) {
    this.finalidade = finalidade;
  }

  public double getQtde() {
    return this.qtde;
  }

  public void setQtde(double qtde) {
    this.qtde = qtde;
  }

  public double getPerc() {
    return this.perc;
  }

  public void setPerc(double perc) {
    this.perc = perc;
  }

  public double getValorTotal() {
    return this.valorTotal;
  }

  public void setValorTotal(double valorTotal) {
    this.valorTotal = valorTotal;
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

  public MarketingItemVpcOutrosVO tipo(String tipo) {
    this.tipo = tipo;
    return this;
  }

  public MarketingItemVpcOutrosVO finalidade(String finalidade) {
    this.finalidade = finalidade;
    return this;
  }

  public MarketingItemVpcOutrosVO qtde(double qtde) {
    this.qtde = qtde;
    return this;
  }

  public MarketingItemVpcOutrosVO perc(double perc) {
    this.perc = perc;
    return this;
  }

  public MarketingItemVpcOutrosVO valorTotal(double valorTotal) {
    this.valorTotal = valorTotal;
    return this;
  }

  public MarketingItemVpcOutrosVO qtdEvidencia(double qtdEvidencia) {
    this.qtdEvidencia = qtdEvidencia;
    return this;
  }

  public MarketingItemVpcOutrosVO valEvidencia(double valEvidencia) {
    this.valEvidencia = valEvidencia;
    return this;
  }

  public MarketingItemVpcOutrosVO totEvidencia(double totEvidencia) {
    this.totEvidencia = totEvidencia;
    return this;
  }

  @Override
  public boolean equals(Object o) {
    if (o == this)
      return true;
    if (!(o instanceof MarketingItemVpcOutrosVO)) {
      return false;
    }
    MarketingItemVpcOutrosVO marketingItemVpcOutrosVO = (MarketingItemVpcOutrosVO) o;
    return Objects.equals(tipo, marketingItemVpcOutrosVO.tipo)
        && Objects.equals(finalidade, marketingItemVpcOutrosVO.finalidade)
        && qtde == marketingItemVpcOutrosVO.qtde && perc == marketingItemVpcOutrosVO.perc
        && valorTotal == marketingItemVpcOutrosVO.valorTotal
        && qtdEvidencia == marketingItemVpcOutrosVO.qtdEvidencia
        && valEvidencia == marketingItemVpcOutrosVO.valEvidencia
        && totEvidencia == marketingItemVpcOutrosVO.totEvidencia;
  }

  @Override
  public int hashCode() {
    return Objects.hash(tipo, finalidade, qtde, perc, valorTotal, qtdEvidencia, valEvidencia,
        totEvidencia);
  }

  @Override
  public String toString() {
    Gson gson = new Gson();
    return gson.toJson(this);

    // return "{" + " tipo='" + getTipo() + "'" + ", finalidade='" + getFinalidade() + "'"
    // + ", qtde='" + getQtde() + "'" + ", perc='" + getPerc() + "'" + ", valorTotal='"
    // + getValorTotal() + "'" + ", qtdEvidencia='" + getQtdEvidencia() + "'"
    // + ", valEvidencia='" + getValEvidencia() + "'" + ", totEvidencia='"
    // + getTotEvidencia() + "'" + "}";
  }

}
