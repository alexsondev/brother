package com.brother.vo;

import java.util.Objects;
import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.gson.Gson;

/**
 * Objeto de com os dados da API /api/public/admin/tenant Pode também ser chamado de DTO
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class MarketingItemPriceVO implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private ItemVO item;
    private String finalidade;
    private String target;
    private double rebateUnit;
    private double qtde;
    private double rebateTotal;

    private double qtdEvidencia;
    private double valEvidencia;
    private double totEvidencia;

    public MarketingItemPriceVO() {
    }

    @JsonIgnoreProperties(ignoreUnknown = true)

    public MarketingItemPriceVO(ItemVO item, String finalidade, String target, double rebateUnit,
            double qtde, double rebateTotal, double qtdEvidencia, double valEvidencia,
            double totEvidencia) {
        this.item = item;
        this.finalidade = finalidade;
        this.target = target;
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

    public String getFinalidade() {
        return this.finalidade;
    }

    public void setFinalidade(String finalidade) {
        this.finalidade = finalidade;
    }

    public String getTarget() {
        return this.target;
    }

    public void setTarget(String target) {
        this.target = target;
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

    public MarketingItemPriceVO item(ItemVO item) {
        setItem(item);
        return this;
    }

    public MarketingItemPriceVO finalidade(String finalidade) {
        setFinalidade(finalidade);
        return this;
    }

    public MarketingItemPriceVO target(String target) {
        setTarget(target);
        return this;
    }

    public MarketingItemPriceVO rebateUnit(double rebateUnit) {
        setRebateUnit(rebateUnit);
        return this;
    }

    public MarketingItemPriceVO qtde(double qtde) {
        setQtde(qtde);
        return this;
    }

    public MarketingItemPriceVO rebateTotal(double rebateTotal) {
        setRebateTotal(rebateTotal);
        return this;
    }

    public MarketingItemPriceVO qtdEvidencia(double qtdEvidencia) {
        setQtdEvidencia(qtdEvidencia);
        return this;
    }

    public MarketingItemPriceVO valEvidencia(double valEvidencia) {
        setValEvidencia(valEvidencia);
        return this;
    }

    public MarketingItemPriceVO totEvidencia(double totEvidencia) {
        setTotEvidencia(totEvidencia);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof MarketingItemPriceVO)) {
            return false;
        }
        MarketingItemPriceVO marketingItemPriceVO = (MarketingItemPriceVO) o;
        return Objects.equals(item, marketingItemPriceVO.item)
                && Objects.equals(finalidade, marketingItemPriceVO.finalidade)
                && Objects.equals(target, marketingItemPriceVO.target)
                && rebateUnit == marketingItemPriceVO.rebateUnit
                && qtde == marketingItemPriceVO.qtde
                && rebateTotal == marketingItemPriceVO.rebateTotal
                && qtdEvidencia == marketingItemPriceVO.qtdEvidencia
                && valEvidencia == marketingItemPriceVO.valEvidencia
                && totEvidencia == marketingItemPriceVO.totEvidencia;
    }

    @Override
    public int hashCode() {
        return Objects.hash(item, finalidade, target, rebateUnit, qtde, rebateTotal, qtdEvidencia,
                valEvidencia, totEvidencia);
    }

    @Override
    public String toString() {
      Gson gson = new Gson();
    return gson.toJson(this);

        // return "{" + " item='" + getItem() + "'" + ", finalidade='" + getFinalidade() + "'"
        //         + ", target='" + getTarget() + "'" + ", rebateUnit='" + getRebateUnit() + "'"
        //         + ", qtde='" + getQtde() + "'" + ", rebateTotal='" + getRebateTotal() + "'"
        //         + ", qtdEvidencia='" + getQtdEvidencia() + "'" + ", valEvidencia='"
        //         + getValEvidencia() + "'" + ", totEvidencia='" + getTotEvidencia() + "'" + "}";
    }

}
