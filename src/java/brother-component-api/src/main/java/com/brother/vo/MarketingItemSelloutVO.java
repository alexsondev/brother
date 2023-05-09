package com.brother.vo;

import java.util.Objects;
import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.gson.Gson;

/**
 * Objeto de com os dados da API /api/public/admin/tenant Pode também ser chamado de DTO
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class MarketingItemSelloutVO implements Serializable {

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

    public MarketingItemSelloutVO() {
    }

    @JsonIgnoreProperties(ignoreUnknown = true)

    public MarketingItemSelloutVO(ItemVO item, String finalidade, String target, double rebateUnit,
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

    public MarketingItemSelloutVO item(ItemVO item) {
        setItem(item);
        return this;
    }

    public MarketingItemSelloutVO finalidade(String finalidade) {
        setFinalidade(finalidade);
        return this;
    }

    public MarketingItemSelloutVO target(String target) {
        setTarget(target);
        return this;
    }

    public MarketingItemSelloutVO rebateUnit(double rebateUnit) {
        setRebateUnit(rebateUnit);
        return this;
    }

    public MarketingItemSelloutVO qtde(double qtde) {
        setQtde(qtde);
        return this;
    }

    public MarketingItemSelloutVO rebateTotal(double rebateTotal) {
        setRebateTotal(rebateTotal);
        return this;
    }

    public MarketingItemSelloutVO qtdEvidencia(double qtdEvidencia) {
        setQtdEvidencia(qtdEvidencia);
        return this;
    }

    public MarketingItemSelloutVO valEvidencia(double valEvidencia) {
        setValEvidencia(valEvidencia);
        return this;
    }

    public MarketingItemSelloutVO totEvidencia(double totEvidencia) {
        setTotEvidencia(totEvidencia);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof MarketingItemSelloutVO)) {
            return false;
        }
        MarketingItemSelloutVO marketingItemSelloutVO = (MarketingItemSelloutVO) o;
        return Objects.equals(item, marketingItemSelloutVO.item)
                && Objects.equals(finalidade, marketingItemSelloutVO.finalidade)
                && Objects.equals(target, marketingItemSelloutVO.target)
                && rebateUnit == marketingItemSelloutVO.rebateUnit
                && qtde == marketingItemSelloutVO.qtde
                && rebateTotal == marketingItemSelloutVO.rebateTotal
                && qtdEvidencia == marketingItemSelloutVO.qtdEvidencia
                && valEvidencia == marketingItemSelloutVO.valEvidencia
                && totEvidencia == marketingItemSelloutVO.totEvidencia;
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
