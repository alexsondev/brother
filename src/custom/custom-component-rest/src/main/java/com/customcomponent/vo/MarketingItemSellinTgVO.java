package com.customcomponent.vo;

import java.util.Objects;
import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.gson.Gson;

/**
 * Objeto de com os dados da API /api/public/admin/tenant Pode também ser chamado de DTO
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class MarketingItemSellinTgVO implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private String descricao;
    private double valorUnit;
    private double qtde;
    private double valorTotal;

    private double qtdEvidencia;
    private double valEvidencia;
    private double totEvidencia;

    public MarketingItemSellinTgVO() {
    }

    @JsonIgnoreProperties(ignoreUnknown = true)

    public MarketingItemSellinTgVO(String descricao, double valorUnit, double qtde,
            double valorTotal, double qtdEvidencia, double valEvidencia, double totEvidencia) {
        this.descricao = descricao;
        this.valorUnit = valorUnit;
        this.qtde = qtde;
        this.valorTotal = valorTotal;
        this.qtdEvidencia = qtdEvidencia;
        this.valEvidencia = valEvidencia;
        this.totEvidencia = totEvidencia;
    }

    public String getDescricao() {
        return this.descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
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

    public MarketingItemSellinTgVO descricao(String descricao) {
        this.descricao = descricao;
        return this;
    }

    public MarketingItemSellinTgVO valorUnit(double valorUnit) {
        this.valorUnit = valorUnit;
        return this;
    }

    public MarketingItemSellinTgVO qtde(double qtde) {
        this.qtde = qtde;
        return this;
    }

    public MarketingItemSellinTgVO valorTotal(double valorTotal) {
        this.valorTotal = valorTotal;
        return this;
    }

    public MarketingItemSellinTgVO qtdEvidencia(double qtdEvidencia) {
        this.qtdEvidencia = qtdEvidencia;
        return this;
    }

    public MarketingItemSellinTgVO valEvidencia(double valEvidencia) {
        this.valEvidencia = valEvidencia;
        return this;
    }

    public MarketingItemSellinTgVO totEvidencia(double totEvidencia) {
        this.totEvidencia = totEvidencia;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof MarketingItemSellinTgVO)) {
            return false;
        }
        MarketingItemSellinTgVO marketingItemSellinTgVO = (MarketingItemSellinTgVO) o;
        return Objects.equals(descricao, marketingItemSellinTgVO.descricao)
                && valorUnit == marketingItemSellinTgVO.valorUnit
                && qtde == marketingItemSellinTgVO.qtde
                && valorTotal == marketingItemSellinTgVO.valorTotal
                && qtdEvidencia == marketingItemSellinTgVO.qtdEvidencia
                && valEvidencia == marketingItemSellinTgVO.valEvidencia
                && totEvidencia == marketingItemSellinTgVO.totEvidencia;
    }

    @Override
    public int hashCode() {
        return Objects.hash(descricao, valorUnit, qtde, valorTotal, qtdEvidencia, valEvidencia,
                totEvidencia);
    }

    @Override
    public String toString() {
      Gson gson = new Gson();
    return gson.toJson(this);

        // return "{" + " descricao='" + getDescricao() + "'" + ", valorUnit='" + getValorUnit() + "'"
        //         + ", qtde='" + getQtde() + "'" + ", valorTotal='" + getValorTotal() + "'"
        //         + ", qtdEvidencia='" + getQtdEvidencia() + "'" + ", valEvidencia='"
        //         + getValEvidencia() + "'" + ", totEvidencia='" + getTotEvidencia() + "'" + "}";
    }

}
