package com.brother.vo;

import java.util.Objects;
import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.gson.Gson;

/**
 * Objeto de com os status de pagamento do fluxo de Marketing
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class MarketingDuplicatasVO implements Serializable {

  /**
   *
   */
  private static final long serialVersionUID = 1L;

  private String titulo;
  private double valorAntecipa;
  private String dataVencto;
  private double valorSaldo;
  private double saldoAposAbatimento;
  private String nd;

  public MarketingDuplicatasVO() {}

  @JsonIgnoreProperties(ignoreUnknown = true)

  public MarketingDuplicatasVO(String titulo, double valorAntecipa, String dataVencto,
      double valorSaldo, double saldoAposAbatimento, String nd) {
    this.titulo = titulo;
    this.valorAntecipa = valorAntecipa;
    this.dataVencto = dataVencto;
    this.valorSaldo = valorSaldo;
    this.saldoAposAbatimento = saldoAposAbatimento;
    this.nd = nd;
  }

  public String getTitulo() {
    return this.titulo;
  }

  public void setTitulo(String titulo) {
    this.titulo = titulo;
  }

  public double getValorAntecipa() {
    return this.valorAntecipa;
  }

  public void setValorAntecipa(double valorAntecipa) {
    this.valorAntecipa = valorAntecipa;
  }

  public String getDataVencto() {
    return this.dataVencto;
  }

  public void setDataVencto(String dataVencto) {
    this.dataVencto = dataVencto;
  }

  public double getValorSaldo() {
    return this.valorSaldo;
  }

  public void setValorSaldo(double valorSaldo) {
    this.valorSaldo = valorSaldo;
  }

  public double getSaldoAposAbatimento() {
    return this.saldoAposAbatimento;
  }

  public void setSaldoAposAbatimento(double saldoAposAbatimento) {
    this.saldoAposAbatimento = saldoAposAbatimento;
  }

  public String getNd() {
    return this.nd;
  }

  public void setNd(String nd) {
    this.nd = nd;
  }

  public MarketingDuplicatasVO titulo(String titulo) {
    this.titulo = titulo;
    return this;
  }

  public MarketingDuplicatasVO valorAntecipa(double valorAntecipa) {
    this.valorAntecipa = valorAntecipa;
    return this;
  }

  public MarketingDuplicatasVO dataVencto(String dataVencto) {
    this.dataVencto = dataVencto;
    return this;
  }

  public MarketingDuplicatasVO valorSaldo(double valorSaldo) {
    this.valorSaldo = valorSaldo;
    return this;
  }

  public MarketingDuplicatasVO saldoAposAbatimento(double saldoAposAbatimento) {
    this.saldoAposAbatimento = saldoAposAbatimento;
    return this;
  }

  public MarketingDuplicatasVO nd(String nd) {
    this.nd = nd;
    return this;
  }

  @Override
  public boolean equals(Object o) {
    if (o == this)
      return true;
    if (!(o instanceof MarketingDuplicatasVO)) {
      return false;
    }
    MarketingDuplicatasVO marketingDuplicatasVO = (MarketingDuplicatasVO) o;
    return Objects.equals(titulo, marketingDuplicatasVO.titulo)
        && valorAntecipa == marketingDuplicatasVO.valorAntecipa
        && Objects.equals(dataVencto, marketingDuplicatasVO.dataVencto)
        && valorSaldo == marketingDuplicatasVO.valorSaldo
        && saldoAposAbatimento == marketingDuplicatasVO.saldoAposAbatimento
        && Objects.equals(nd, marketingDuplicatasVO.nd);
  }

  @Override
  public int hashCode() {
    return Objects.hash(titulo, valorAntecipa, dataVencto, valorSaldo, saldoAposAbatimento, nd);
  }

  @Override
  public String toString() {

    Gson gson = new Gson();
    return gson.toJson(this);

    // return "{" + " titulo='" + getTitulo() + "'" + ", valorAntecipa='" + getValorAntecipa() + "'"
    //     + ", dataVencto='" + getDataVencto() + "'" + ", valorSaldo='" + getValorSaldo() + "'"
    //     + ", saldoAposAbatimento='" + getSaldoAposAbatimento() + "'" + ", nd='" + getNd() + "'"
    //     + "}";
  }

}
