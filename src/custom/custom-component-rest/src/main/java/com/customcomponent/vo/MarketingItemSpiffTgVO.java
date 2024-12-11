package com.customcomponent.vo;

import java.util.Objects;
import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.gson.Gson;

/**
 * Objeto de com os dados da API /api/public/admin/tenant Pode também ser chamado de DTO
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class MarketingItemSpiffTgVO implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private String foco;
    private double valorUnit;
    private double qtde;
    private double valorTotal;

    private double qtdEvidencia;
    private double valEvidencia;
    private double totEvidencia;

    public MarketingItemSpiffTgVO() {
    }

    @JsonIgnoreProperties(ignoreUnknown = true)

    public MarketingItemSpiffTgVO(String foco, double valorUnit, double qtde, double valorTotal,
            double qtdEvidencia, double valEvidencia, double totEvidencia) {
        this.foco = foco;
        this.valorUnit = valorUnit;
        this.qtde = qtde;
        this.valorTotal = valorTotal;
        this.qtdEvidencia = qtdEvidencia;
        this.valEvidencia = valEvidencia;
        this.totEvidencia = totEvidencia;
    }

    public String getFoco() {
        return this.foco;
    }

    public void setFoco(String foco) {
        this.foco = foco;
    }

    public double getValorUnit() {
        return this.valorUnit;
    }

    public void setValorUnit(double valorUnit) {
        this.valorUnit = valorUnit;
    }

    public double getQtde() {
        return this.qtde;
    }

    public void setQtde(double qtde) {
        this.qtde = qtde;
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

    public MarketingItemSpiffTgVO foco(String foco) {
        this.foco = foco;
        return this;
    }

    public MarketingItemSpiffTgVO valorUnit(double valorUnit) {
        this.valorUnit = valorUnit;
        return this;
    }

    public MarketingItemSpiffTgVO qtde(double qtde) {
        this.qtde = qtde;
        return this;
    }

    public MarketingItemSpiffTgVO valorTotal(double valorTotal) {
        this.valorTotal = valorTotal;
        return this;
    }

    public MarketingItemSpiffTgVO qtdEvidencia(double qtdEvidencia) {
        this.qtdEvidencia = qtdEvidencia;
        return this;
    }

    public MarketingItemSpiffTgVO valEvidencia(double valEvidencia) {
        this.valEvidencia = valEvidencia;
        return this;
    }

    public MarketingItemSpiffTgVO totEvidencia(double totEvidencia) {
        this.totEvidencia = totEvidencia;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof MarketingItemSpiffTgVO)) {
            return false;
        }
        MarketingItemSpiffTgVO marketingItemSpiffTgVO = (MarketingItemSpiffTgVO) o;
        return Objects.equals(foco, marketingItemSpiffTgVO.foco)
                && valorUnit == marketingItemSpiffTgVO.valorUnit
                && qtde == marketingItemSpiffTgVO.qtde
                && valorTotal == marketingItemSpiffTgVO.valorTotal
                && qtdEvidencia == marketingItemSpiffTgVO.qtdEvidencia
                && valEvidencia == marketingItemSpiffTgVO.valEvidencia
                && totEvidencia == marketingItemSpiffTgVO.totEvidencia;
    }

    @Override
    public int hashCode() {
        return Objects.hash(foco, valorUnit, qtde, valorTotal, qtdEvidencia, valEvidencia,
                totEvidencia);
    }

    @Override
    public String toString() {
      Gson gson = new Gson();
    return gson.toJson(this);
    
        // return "{" + " foco='" + getFoco() + "'" + ", valorUnit='" + getValorUnit() + "'"
        //         + ", qtde='" + getQtde() + "'" + ", valorTotal='" + getValorTotal() + "'"
        //         + ", qtdEvidencia='" + getQtdEvidencia() + "'" + ", valEvidencia='"
        //         + getValEvidencia() + "'" + ", totEvidencia='" + getTotEvidencia() + "'" + "}";
    }

}
