package com.customcomponent.vo;

import java.util.Objects;
import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.gson.Gson;

/**
 * Objeto de com os dados da API /api/public/admin/tenant Pode também ser chamado de DTO
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class MarketingItemSpiffItVO implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private ItemVO item;
    private double valorUnit;
    private double qtde;
    private double valorTotal;

    private double qtdEvidencia;
    private double valEvidencia;
    private double totEvidencia;

    public MarketingItemSpiffItVO() {
    }

    @JsonIgnoreProperties(ignoreUnknown = true)

    public MarketingItemSpiffItVO(ItemVO item, double valorUnit, double qtde, double valorTotal,
            double qtdEvidencia, double valEvidencia, double totEvidencia) {
        this.item = item;
        this.valorUnit = valorUnit;
        this.qtde = qtde;
        this.valorTotal = valorTotal;
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

    public MarketingItemSpiffItVO item(ItemVO item) {
        this.item = item;
        return this;
    }

    public MarketingItemSpiffItVO valorUnit(double valorUnit) {
        this.valorUnit = valorUnit;
        return this;
    }

    public MarketingItemSpiffItVO qtde(double qtde) {
        this.qtde = qtde;
        return this;
    }

    public MarketingItemSpiffItVO valorTotal(double valorTotal) {
        this.valorTotal = valorTotal;
        return this;
    }

    public MarketingItemSpiffItVO qtdEvidencia(double qtdEvidencia) {
        this.qtdEvidencia = qtdEvidencia;
        return this;
    }

    public MarketingItemSpiffItVO valEvidencia(double valEvidencia) {
        this.valEvidencia = valEvidencia;
        return this;
    }

    public MarketingItemSpiffItVO totEvidencia(double totEvidencia) {
        this.totEvidencia = totEvidencia;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof MarketingItemSpiffItVO)) {
            return false;
        }
        MarketingItemSpiffItVO marketingItemSpiffItVO = (MarketingItemSpiffItVO) o;
        return Objects.equals(item, marketingItemSpiffItVO.item)
                && valorUnit == marketingItemSpiffItVO.valorUnit
                && qtde == marketingItemSpiffItVO.qtde
                && valorTotal == marketingItemSpiffItVO.valorTotal
                && qtdEvidencia == marketingItemSpiffItVO.qtdEvidencia
                && valEvidencia == marketingItemSpiffItVO.valEvidencia
                && totEvidencia == marketingItemSpiffItVO.totEvidencia;
    }

    @Override
    public int hashCode() {
        return Objects.hash(item, valorUnit, qtde, valorTotal, qtdEvidencia, valEvidencia,
                totEvidencia);
    }

    @Override
    public String toString() {
      Gson gson = new Gson();
    return gson.toJson(this);

        // return "{" + " item='" + getItem() + "'" + ", valorUnit='" + getValorUnit() + "'"
        //         + ", qtde='" + getQtde() + "'" + ", valorTotal='" + getValorTotal() + "'"
        //         + ", qtdEvidencia='" + getQtdEvidencia() + "'" + ", valEvidencia='"
        //         + getValEvidencia() + "'" + ", totEvidencia='" + getTotEvidencia() + "'" + "}";
    }

}
