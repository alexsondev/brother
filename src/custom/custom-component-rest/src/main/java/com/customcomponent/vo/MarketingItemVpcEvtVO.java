package com.customcomponent.vo;

import java.util.Objects;
import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.gson.Gson;

/**
 * Objeto de com os dados da API /api/public/admin/tenant Pode também ser chamado de DTO
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class MarketingItemVpcEvtVO implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private String nomeEvento;
    private String finalidade;
    private Long inicio;
    private Long termino;
    private double perc;
    private double valorTotal;

    private double qtdEvidencia;
    private double valEvidencia;
    private double totEvidencia;

    public MarketingItemVpcEvtVO() {
    }

    @JsonIgnoreProperties(ignoreUnknown = true)

    public MarketingItemVpcEvtVO(String nomeEvento, String finalidade, Long inicio, Long termino,
            double perc, double valorTotal, double qtdEvidencia, double valEvidencia,
            double totEvidencia) {
        this.nomeEvento = nomeEvento;
        this.finalidade = finalidade;
        this.inicio = inicio;
        this.termino = termino;
        this.perc = perc;
        this.valorTotal = valorTotal;
        this.qtdEvidencia = qtdEvidencia;
        this.valEvidencia = valEvidencia;
        this.totEvidencia = totEvidencia;
    }

    public String getNomeEvento() {
        return this.nomeEvento;
    }

    public void setNomeEvento(String nomeEvento) {
        this.nomeEvento = nomeEvento;
    }

    public String getFinalidade() {
        return this.finalidade;
    }

    public void setFinalidade(String finalidade) {
        this.finalidade = finalidade;
    }

    public Long getInicio() {
        return this.inicio;
    }

    public void setInicio(Long inicio) {
        this.inicio = inicio;
    }

    public Long getTermino() {
        return this.termino;
    }

    public void setTermino(Long termino) {
        this.termino = termino;
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

    public MarketingItemVpcEvtVO nomeEvento(String nomeEvento) {
        this.nomeEvento = nomeEvento;
        return this;
    }

    public MarketingItemVpcEvtVO finalidade(String finalidade) {
        this.finalidade = finalidade;
        return this;
    }

    public MarketingItemVpcEvtVO inicio(Long inicio) {
        this.inicio = inicio;
        return this;
    }

    public MarketingItemVpcEvtVO termino(Long termino) {
        this.termino = termino;
        return this;
    }

    public MarketingItemVpcEvtVO perc(double perc) {
        this.perc = perc;
        return this;
    }

    public MarketingItemVpcEvtVO valorTotal(double valorTotal) {
        this.valorTotal = valorTotal;
        return this;
    }

    public MarketingItemVpcEvtVO qtdEvidencia(double qtdEvidencia) {
        this.qtdEvidencia = qtdEvidencia;
        return this;
    }

    public MarketingItemVpcEvtVO valEvidencia(double valEvidencia) {
        this.valEvidencia = valEvidencia;
        return this;
    }

    public MarketingItemVpcEvtVO totEvidencia(double totEvidencia) {
        this.totEvidencia = totEvidencia;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof MarketingItemVpcEvtVO)) {
            return false;
        }
        MarketingItemVpcEvtVO marketingItemVpcEvtVO = (MarketingItemVpcEvtVO) o;
        return Objects.equals(nomeEvento, marketingItemVpcEvtVO.nomeEvento)
                && Objects.equals(finalidade, marketingItemVpcEvtVO.finalidade)
                && Objects.equals(inicio, marketingItemVpcEvtVO.inicio)
                && Objects.equals(termino, marketingItemVpcEvtVO.termino)
                && perc == marketingItemVpcEvtVO.perc
                && valorTotal == marketingItemVpcEvtVO.valorTotal
                && qtdEvidencia == marketingItemVpcEvtVO.qtdEvidencia
                && valEvidencia == marketingItemVpcEvtVO.valEvidencia
                && totEvidencia == marketingItemVpcEvtVO.totEvidencia;
    }

    @Override
    public int hashCode() {
        return Objects.hash(nomeEvento, finalidade, inicio, termino, perc, valorTotal, qtdEvidencia,
                valEvidencia, totEvidencia);
    }

    @Override
    public String toString() {
      Gson gson = new Gson();
    return gson.toJson(this);

        // return "{" + " nomeEvento='" + getNomeEvento() + "'" + ", finalidade='" + getFinalidade()
        //         + "'" + ", inicio='" + getInicio() + "'" + ", termino='" + getTermino() + "'"
        //         + ", perc='" + getPerc() + "'" + ", valorTotal='" + getValorTotal() + "'"
        //         + ", qtdEvidencia='" + getQtdEvidencia() + "'" + ", valEvidencia='"
        //         + getValEvidencia() + "'" + ", totEvidencia='" + getTotEvidencia() + "'" + "}";
    }

}
