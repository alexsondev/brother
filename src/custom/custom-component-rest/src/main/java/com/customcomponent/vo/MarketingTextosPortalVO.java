package com.customcomponent.vo;

import java.util.Objects;
import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.gson.Gson;

/**
 * Objeto de com os status de pagamento do fluxo de Marketing
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class MarketingTextosPortalVO implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private String portalDadosTexto;
    private String portalEvidenciasTexto;
    private String portalNDTexto;
    private String portalPagamentoTexto;

    public MarketingTextosPortalVO() {
    }

    @JsonIgnoreProperties(ignoreUnknown = true)

    public MarketingTextosPortalVO(String portalDadosTexto, String portalEvidenciasTexto,
            String portalNDTexto, String portalPagamentoTexto) {
        this.portalDadosTexto = portalDadosTexto;
        this.portalEvidenciasTexto = portalEvidenciasTexto;
        this.portalNDTexto = portalNDTexto;
        this.portalPagamentoTexto = portalPagamentoTexto;
    }

    public String getPortalDadosTexto() {
        return this.portalDadosTexto;
    }

    public void setPortalDadosTexto(String portalDadosTexto) {
        this.portalDadosTexto = portalDadosTexto;
    }

    public String getPortalEvidenciasTexto() {
        return this.portalEvidenciasTexto;
    }

    public void setPortalEvidenciasTexto(String portalEvidenciasTexto) {
        this.portalEvidenciasTexto = portalEvidenciasTexto;
    }

    public String getPortalNDTexto() {
        return this.portalNDTexto;
    }

    public void setPortalNDTexto(String portalNDTexto) {
        this.portalNDTexto = portalNDTexto;
    }

    public String getPortalPagamentoTexto() {
        return this.portalPagamentoTexto;
    }

    public void setPortalPagamentoTexto(String portalPagamentoTexto) {
        this.portalPagamentoTexto = portalPagamentoTexto;
    }

    public MarketingTextosPortalVO portalDadosTexto(String portalDadosTexto) {
        this.portalDadosTexto = portalDadosTexto;
        return this;
    }

    public MarketingTextosPortalVO portalEvidenciasTexto(String portalEvidenciasTexto) {
        this.portalEvidenciasTexto = portalEvidenciasTexto;
        return this;
    }

    public MarketingTextosPortalVO portalNDTexto(String portalNDTexto) {
        this.portalNDTexto = portalNDTexto;
        return this;
    }

    public MarketingTextosPortalVO portalPagamentoTexto(String portalPagamentoTexto) {
        this.portalPagamentoTexto = portalPagamentoTexto;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof MarketingTextosPortalVO)) {
            return false;
        }
        MarketingTextosPortalVO marketingTextosPortalVO = (MarketingTextosPortalVO) o;
        return Objects.equals(portalDadosTexto, marketingTextosPortalVO.portalDadosTexto)
                && Objects.equals(portalEvidenciasTexto,
                        marketingTextosPortalVO.portalEvidenciasTexto)
                && Objects.equals(portalNDTexto, marketingTextosPortalVO.portalNDTexto) && Objects
                        .equals(portalPagamentoTexto, marketingTextosPortalVO.portalPagamentoTexto);
    }

    @Override
    public int hashCode() {
        return Objects.hash(portalDadosTexto, portalEvidenciasTexto, portalNDTexto,
                portalPagamentoTexto);
    }

    @Override
    public String toString() {
      Gson gson = new Gson();
    return gson.toJson(this);

        // return "{" + " portalDadosTexto='" + getPortalDadosTexto() + "'"
        //         + ", portalEvidenciasTexto='" + getPortalEvidenciasTexto() + "'"
        //         + ", portalNDTexto='" + getPortalNDTexto() + "'" + ", portalPagamentoTexto='"
        //         + getPortalPagamentoTexto() + "'" + "}";
    }


}
