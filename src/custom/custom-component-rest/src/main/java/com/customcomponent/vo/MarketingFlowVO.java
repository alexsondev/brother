package com.customcomponent.vo;

import java.util.Objects;
import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.gson.Gson;

/**
 * Objeto de com os dados da API /api/public/admin/tenant Pode também ser
 * chamado de DTO
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class MarketingFlowVO implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private Long documentid;
    private int solicitacao;
    private String status;
    private double valorLiberado;
    private double valorTotalVerba;
    private double valorResultado;
    private String descricaoDetalhada;
    private Long inicioAcao;
    private Long terminoAcao;
    private Boolean envioEvidenciasConcluido;
    private Boolean evRecusada;
    private String obsEnvioEvidencias;
    private Boolean envioNDConcluido;
    private Boolean ndRecusada;
    private String obsEnvioND;
    private int currentStepPortal;
    private String motivoCancelamento;
    private String motivoRecusaND;
    private String motivoRecusaEv;
    private String tipoSellout;
    private String tipoPrpro;
    private String tipoQuantidade;
    private Long folderAttach;

    private MarketingItemSelloutVO[] itensSellout;
    // private MarketingItemPrproVO[] itensPrpro;
    private MarketingItemSellinItVO[] itensSellinIt;
    private MarketingItemSellinTgVO[] itensSellinTg;
    private MarketingItemSellinTgAcVO[] itensSellinTgAc;
    private MarketingItemSpiffItVO[] itensSpiffIt;
    private MarketingItemSpiffTgVO[] itensSpiffTg;
    private MarketingItemVpcEvtVO[] itensVpcEvt;
    private MarketingItemVpcOutrosVO[] itensVpcOutros;

    private MarketingEvidenciaVO[] evidencias;
    private MarketingNdVO[] nd;
    private MarketingDuplicatasVO[] duplicatas;

    public MarketingFlowVO() {
    }

    @JsonIgnoreProperties(ignoreUnknown = true)

    public MarketingFlowVO(Long documentid, int solicitacao, String status, double valorLiberado, double valorTotalVerba, double valorResultado, String descricaoDetalhada, Long inicioAcao, Long terminoAcao, Boolean envioEvidenciasConcluido, Boolean evRecusada, String obsEnvioEvidencias, Boolean envioNDConcluido, Boolean ndRecusada, String obsEnvioND, int currentStepPortal, String motivoCancelamento, String motivoRecusaND, String motivoRecusaEv, String tipoSellout, String tipoPrpro, String tipoQuantidade, Long folderAttach, MarketingItemSelloutVO[] itensSellout, MarketingItemSellinItVO[] itensSellinIt, MarketingItemSellinTgVO[] itensSellinTg, MarketingItemSellinTgAcVO[] itensSellinTgAc, MarketingItemSpiffItVO[] itensSpiffIt, MarketingItemSpiffTgVO[] itensSpiffTg, MarketingItemVpcEvtVO[] itensVpcEvt, MarketingItemVpcOutrosVO[] itensVpcOutros, MarketingEvidenciaVO[] evidencias, MarketingNdVO[] nd, MarketingDuplicatasVO[] duplicatas) {
        this.documentid = documentid;
        this.solicitacao = solicitacao;
        this.status = status;
        this.valorLiberado = valorLiberado;
        this.valorTotalVerba = valorTotalVerba;
        this.valorResultado = valorResultado;
        this.descricaoDetalhada = descricaoDetalhada;
        this.inicioAcao = inicioAcao;
        this.terminoAcao = terminoAcao;
        this.envioEvidenciasConcluido = envioEvidenciasConcluido;
        this.evRecusada = evRecusada;
        this.obsEnvioEvidencias = obsEnvioEvidencias;
        this.envioNDConcluido = envioNDConcluido;
        this.ndRecusada = ndRecusada;
        this.obsEnvioND = obsEnvioND;
        this.currentStepPortal = currentStepPortal;
        this.motivoCancelamento = motivoCancelamento;
        this.motivoRecusaND = motivoRecusaND;
        this.motivoRecusaEv = motivoRecusaEv;
        this.tipoSellout = tipoSellout;
        this.tipoPrpro = tipoPrpro;
        this.tipoQuantidade = tipoQuantidade;
        this.folderAttach = folderAttach;
        this.itensSellout = itensSellout;
        this.itensSellinIt = itensSellinIt;
        this.itensSellinTg = itensSellinTg;
        this.itensSellinTgAc = itensSellinTgAc;
        this.itensSpiffIt = itensSpiffIt;
        this.itensSpiffTg = itensSpiffTg;
        this.itensVpcEvt = itensVpcEvt;
        this.itensVpcOutros = itensVpcOutros;
        this.evidencias = evidencias;
        this.nd = nd;
        this.duplicatas = duplicatas;
    }

    public Long getDocumentid() {
        return this.documentid;
    }

    public void setDocumentid(Long documentid) {
        this.documentid = documentid;
    }

    public int getSolicitacao() {
        return this.solicitacao;
    }

    public void setSolicitacao(int solicitacao) {
        this.solicitacao = solicitacao;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getValorLiberado() {
        return this.valorLiberado;
    }

    public void setValorLiberado(double valorLiberado) {
        this.valorLiberado = valorLiberado;
    }

    public double getValorTotalVerba() {
        return this.valorTotalVerba;
    }

    public void setValorTotalVerba(double valorTotalVerba) {
        this.valorTotalVerba = valorTotalVerba;
    }

    public double getValorResultado() {
        return this.valorResultado;
    }

    public void setValorResultado(double valorResultado) {
        this.valorResultado = valorResultado;
    }

    public String getDescricaoDetalhada() {
        return this.descricaoDetalhada;
    }

    public void setDescricaoDetalhada(String descricaoDetalhada) {
        this.descricaoDetalhada = descricaoDetalhada;
    }

    public Long getInicioAcao() {
        return this.inicioAcao;
    }

    public void setInicioAcao(Long inicioAcao) {
        this.inicioAcao = inicioAcao;
    }

    public Long getTerminoAcao() {
        return this.terminoAcao;
    }

    public void setTerminoAcao(Long terminoAcao) {
        this.terminoAcao = terminoAcao;
    }

    public Boolean isEnvioEvidenciasConcluido() {
        return this.envioEvidenciasConcluido;
    }

    public Boolean getEnvioEvidenciasConcluido() {
        return this.envioEvidenciasConcluido;
    }

    public void setEnvioEvidenciasConcluido(Boolean envioEvidenciasConcluido) {
        this.envioEvidenciasConcluido = envioEvidenciasConcluido;
    }

    public Boolean isEvRecusada() {
        return this.evRecusada;
    }

    public Boolean getEvRecusada() {
        return this.evRecusada;
    }

    public void setEvRecusada(Boolean evRecusada) {
        this.evRecusada = evRecusada;
    }

    public String getObsEnvioEvidencias() {
        return this.obsEnvioEvidencias;
    }

    public void setObsEnvioEvidencias(String obsEnvioEvidencias) {
        this.obsEnvioEvidencias = obsEnvioEvidencias;
    }

    public Boolean isEnvioNDConcluido() {
        return this.envioNDConcluido;
    }

    public Boolean getEnvioNDConcluido() {
        return this.envioNDConcluido;
    }

    public void setEnvioNDConcluido(Boolean envioNDConcluido) {
        this.envioNDConcluido = envioNDConcluido;
    }

    public Boolean isNdRecusada() {
        return this.ndRecusada;
    }

    public Boolean getNdRecusada() {
        return this.ndRecusada;
    }

    public void setNdRecusada(Boolean ndRecusada) {
        this.ndRecusada = ndRecusada;
    }

    public String getObsEnvioND() {
        return this.obsEnvioND;
    }

    public void setObsEnvioND(String obsEnvioND) {
        this.obsEnvioND = obsEnvioND;
    }

    public int getCurrentStepPortal() {
        return this.currentStepPortal;
    }

    public void setCurrentStepPortal(int currentStepPortal) {
        this.currentStepPortal = currentStepPortal;
    }

    public String getMotivoCancelamento() {
        return this.motivoCancelamento;
    }

    public void setMotivoCancelamento(String motivoCancelamento) {
        this.motivoCancelamento = motivoCancelamento;
    }

    public String getMotivoRecusaND() {
        return this.motivoRecusaND;
    }

    public void setMotivoRecusaND(String motivoRecusaND) {
        this.motivoRecusaND = motivoRecusaND;
    }

    public String getMotivoRecusaEv() {
        return this.motivoRecusaEv;
    }

    public void setMotivoRecusaEv(String motivoRecusaEv) {
        this.motivoRecusaEv = motivoRecusaEv;
    }

    public String getTipoSellout() {
        return this.tipoSellout;
    }

    public void setTipoSellout(String tipoSellout) {
        this.tipoSellout = tipoSellout;
    }

    public String getTipoPrpro() {
        return this.tipoPrpro;
    }

    public void setTipoPrpro(String tipoPrpro) {
        this.tipoPrpro = tipoPrpro;
    }

    public String getTipoQuantidade() {
        return this.tipoQuantidade;
    }

    public void setTipoQuantidade(String tipoQuantidade) {
        this.tipoQuantidade = tipoQuantidade;
    }

    public Long getFolderAttach() {
        return this.folderAttach;
    }

    public void setFolderAttach(Long folderAttach) {
        this.folderAttach = folderAttach;
    }

    public MarketingItemSelloutVO[] getItensSellout() {
        return this.itensSellout;
    }

    public void setItensSellout(MarketingItemSelloutVO[] itensSellout) {
        this.itensSellout = itensSellout;
    }

    // public MarketingItemPrproVO[] getItensPrpro() {
    //     return this.itensPrpro;
    // }

    // public void setItensPrpro(MarketingItemPrproVO[] itensPrpro) {
    //     this.itensPrpro = itensPrpro;
    // }

    public MarketingItemSellinItVO[] getItensSellinIt() {
        return this.itensSellinIt;
    }

    public void setItensSellinIt(MarketingItemSellinItVO[] itensSellinIt) {
        this.itensSellinIt = itensSellinIt;
    }

    public MarketingItemSellinTgVO[] getItensSellinTg() {
        return this.itensSellinTg;
    }

    public void setItensSellinTg(MarketingItemSellinTgVO[] itensSellinTg) {
        this.itensSellinTg = itensSellinTg;
    }

    public MarketingItemSellinTgAcVO[] getItensSellinTgAc() {
        return this.itensSellinTgAc;
    }

    public void setItensSellinTgAc(MarketingItemSellinTgAcVO[] itensSellinTgAc) {
        this.itensSellinTgAc = itensSellinTgAc;
    }

    public MarketingItemSpiffItVO[] getItensSpiffIt() {
        return this.itensSpiffIt;
    }

    public void setItensSpiffIt(MarketingItemSpiffItVO[] itensSpiffIt) {
        this.itensSpiffIt = itensSpiffIt;
    }

    public MarketingItemSpiffTgVO[] getItensSpiffTg() {
        return this.itensSpiffTg;
    }

    public void setItensSpiffTg(MarketingItemSpiffTgVO[] itensSpiffTg) {
        this.itensSpiffTg = itensSpiffTg;
    }

    public MarketingItemVpcEvtVO[] getItensVpcEvt() {
        return this.itensVpcEvt;
    }

    public void setItensVpcEvt(MarketingItemVpcEvtVO[] itensVpcEvt) {
        this.itensVpcEvt = itensVpcEvt;
    }

    public MarketingItemVpcOutrosVO[] getItensVpcOutros() {
        return this.itensVpcOutros;
    }

    public void setItensVpcOutros(MarketingItemVpcOutrosVO[] itensVpcOutros) {
        this.itensVpcOutros = itensVpcOutros;
    }

    public MarketingEvidenciaVO[] getEvidencias() {
        return this.evidencias;
    }

    public void setEvidencias(MarketingEvidenciaVO[] evidencias) {
        this.evidencias = evidencias;
    }

    public MarketingNdVO[] getNd() {
        return this.nd;
    }

    public void setNd(MarketingNdVO[] nd) {
        this.nd = nd;
    }

    public MarketingDuplicatasVO[] getDuplicatas() {
        return this.duplicatas;
    }

    public void setDuplicatas(MarketingDuplicatasVO[] duplicatas) {
        this.duplicatas = duplicatas;
    }

    public MarketingFlowVO documentid(Long documentid) {
        setDocumentid(documentid);
        return this;
    }

    public MarketingFlowVO solicitacao(int solicitacao) {
        setSolicitacao(solicitacao);
        return this;
    }

    public MarketingFlowVO status(String status) {
        setStatus(status);
        return this;
    }

    public MarketingFlowVO valorLiberado(double valorLiberado) {
        setValorLiberado(valorLiberado);
        return this;
    }

    public MarketingFlowVO valorTotalVerba(double valorTotalVerba) {
        setValorTotalVerba(valorTotalVerba);
        return this;
    }

    public MarketingFlowVO valorResultado(double valorResultado) {
        setValorResultado(valorResultado);
        return this;
    }

    public MarketingFlowVO descricaoDetalhada(String descricaoDetalhada) {
        setDescricaoDetalhada(descricaoDetalhada);
        return this;
    }

    public MarketingFlowVO inicioAcao(Long inicioAcao) {
        setInicioAcao(inicioAcao);
        return this;
    }

    public MarketingFlowVO terminoAcao(Long terminoAcao) {
        setTerminoAcao(terminoAcao);
        return this;
    }

    public MarketingFlowVO envioEvidenciasConcluido(Boolean envioEvidenciasConcluido) {
        setEnvioEvidenciasConcluido(envioEvidenciasConcluido);
        return this;
    }

    public MarketingFlowVO evRecusada(Boolean evRecusada) {
        setEvRecusada(evRecusada);
        return this;
    }

    public MarketingFlowVO obsEnvioEvidencias(String obsEnvioEvidencias) {
        setObsEnvioEvidencias(obsEnvioEvidencias);
        return this;
    }

    public MarketingFlowVO envioNDConcluido(Boolean envioNDConcluido) {
        setEnvioNDConcluido(envioNDConcluido);
        return this;
    }

    public MarketingFlowVO ndRecusada(Boolean ndRecusada) {
        setNdRecusada(ndRecusada);
        return this;
    }

    public MarketingFlowVO obsEnvioND(String obsEnvioND) {
        setObsEnvioND(obsEnvioND);
        return this;
    }

    public MarketingFlowVO currentStepPortal(int currentStepPortal) {
        setCurrentStepPortal(currentStepPortal);
        return this;
    }

    public MarketingFlowVO motivoCancelamento(String motivoCancelamento) {
        setMotivoCancelamento(motivoCancelamento);
        return this;
    }

    public MarketingFlowVO motivoRecusaND(String motivoRecusaND) {
        setMotivoRecusaND(motivoRecusaND);
        return this;
    }

    public MarketingFlowVO motivoRecusaEv(String motivoRecusaEv) {
        setMotivoRecusaEv(motivoRecusaEv);
        return this;
    }

    public MarketingFlowVO tipoSellout(String tipoSellout) {
        setTipoSellout(tipoSellout);
        return this;
    }

    public MarketingFlowVO tipoPrpro(String tipoPrpro) {
        setTipoPrpro(tipoPrpro);
        return this;
    }

    public MarketingFlowVO tipoQuantidade(String tipoQuantidade) {
        setTipoQuantidade(tipoQuantidade);
        return this;
    }

    public MarketingFlowVO folderAttach(Long folderAttach) {
        setFolderAttach(folderAttach);
        return this;
    }

    public MarketingFlowVO itensSellout(MarketingItemSelloutVO[] itensSellout) {
        setItensSellout(itensSellout);
        return this;
    }

    // public MarketingFlowVO itensPrpro(MarketingItemPrproVO[] itensPrpro) {
    //     setItensPrpro(itensPrpro);
    //     return this;
    // }

    public MarketingFlowVO itensSellinIt(MarketingItemSellinItVO[] itensSellinIt) {
        setItensSellinIt(itensSellinIt);
        return this;
    }

    public MarketingFlowVO itensSellinTg(MarketingItemSellinTgVO[] itensSellinTg) {
        setItensSellinTg(itensSellinTg);
        return this;
    }

    public MarketingFlowVO itensSellinTgAc(MarketingItemSellinTgAcVO[] itensSellinTgAc) {
        setItensSellinTgAc(itensSellinTgAc);
        return this;
    }

    public MarketingFlowVO itensSpiffIt(MarketingItemSpiffItVO[] itensSpiffIt) {
        setItensSpiffIt(itensSpiffIt);
        return this;
    }

    public MarketingFlowVO itensSpiffTg(MarketingItemSpiffTgVO[] itensSpiffTg) {
        setItensSpiffTg(itensSpiffTg);
        return this;
    }

    public MarketingFlowVO itensVpcEvt(MarketingItemVpcEvtVO[] itensVpcEvt) {
        setItensVpcEvt(itensVpcEvt);
        return this;
    }

    public MarketingFlowVO itensVpcOutros(MarketingItemVpcOutrosVO[] itensVpcOutros) {
        setItensVpcOutros(itensVpcOutros);
        return this;
    }

    public MarketingFlowVO evidencias(MarketingEvidenciaVO[] evidencias) {
        setEvidencias(evidencias);
        return this;
    }

    public MarketingFlowVO nd(MarketingNdVO[] nd) {
        setNd(nd);
        return this;
    }

    public MarketingFlowVO duplicatas(MarketingDuplicatasVO[] duplicatas) {
        setDuplicatas(duplicatas);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof MarketingFlowVO)) {
            return false;
        }
        MarketingFlowVO marketingFlowVO = (MarketingFlowVO) o;
        return Objects.equals(documentid, marketingFlowVO.documentid) && Objects.equals(solicitacao, marketingFlowVO.solicitacao) && Objects.equals(status, marketingFlowVO.status) && valorLiberado == marketingFlowVO.valorLiberado && valorTotalVerba == marketingFlowVO.valorTotalVerba && valorResultado == marketingFlowVO.valorResultado && Objects.equals(descricaoDetalhada, marketingFlowVO.descricaoDetalhada) && Objects.equals(inicioAcao, marketingFlowVO.inicioAcao) && Objects.equals(terminoAcao, marketingFlowVO.terminoAcao) && Objects.equals(envioEvidenciasConcluido, marketingFlowVO.envioEvidenciasConcluido) && Objects.equals(evRecusada, marketingFlowVO.evRecusada) && Objects.equals(obsEnvioEvidencias, marketingFlowVO.obsEnvioEvidencias) && Objects.equals(envioNDConcluido, marketingFlowVO.envioNDConcluido) && Objects.equals(ndRecusada, marketingFlowVO.ndRecusada) && Objects.equals(obsEnvioND, marketingFlowVO.obsEnvioND) && currentStepPortal == marketingFlowVO.currentStepPortal && Objects.equals(motivoCancelamento, marketingFlowVO.motivoCancelamento) && Objects.equals(motivoRecusaND, marketingFlowVO.motivoRecusaND) && Objects.equals(motivoRecusaEv, marketingFlowVO.motivoRecusaEv) && Objects.equals(tipoSellout, marketingFlowVO.tipoSellout) && 
          Objects.equals(tipoPrpro, marketingFlowVO.tipoPrpro) &&
          Objects.equals(tipoQuantidade, marketingFlowVO.tipoQuantidade) && 
          Objects.equals(folderAttach, marketingFlowVO.folderAttach) && 
          Objects.equals(itensSellout, marketingFlowVO.itensSellout) && 
          // Objects.equals(itensPrpro, marketingFlowVO.itensPrpro) &&
          Objects.equals(itensSellinIt, marketingFlowVO.itensSellinIt) && 
          Objects.equals(itensSellinTg, marketingFlowVO.itensSellinTg) && 
          Objects.equals(itensSellinTgAc, marketingFlowVO.itensSellinTgAc) && 
          Objects.equals(itensSpiffIt, marketingFlowVO.itensSpiffIt) && 
          Objects.equals(itensSpiffTg, marketingFlowVO.itensSpiffTg) && 
          Objects.equals(itensVpcEvt, marketingFlowVO.itensVpcEvt) && 
          Objects.equals(itensVpcOutros, marketingFlowVO.itensVpcOutros) && Objects.equals(evidencias, marketingFlowVO.evidencias) && Objects.equals(nd, marketingFlowVO.nd) && Objects.equals(duplicatas, marketingFlowVO.duplicatas);
    }

    // @Override
    // public int hashCode() {
    //     return Objects.hash(documentid, solicitacao, status, valorLiberado, valorTotalVerba, valorResultado, descricaoDetalhada, inicioAcao, terminoAcao, envioEvidenciasConcluido, evRecusada, obsEnvioEvidencias, envioNDConcluido, ndRecusada, obsEnvioND, currentStepPortal, motivoCancelamento, motivoRecusaND, motivoRecusaEv, tipoSellout, tipoPrpro, tipoQuantidade, folderAttach, itensSellout, itensSellinIt, itensSellinTg, itensSellinTgAc, itensSpiffIt, itensSpiffTg, itensVpcEvt, itensVpcOutros, evidencias, nd, duplicatas);
    // }

    @Override
    public String toString() {
      Gson gson = new Gson();
    return gson.toJson(this);
    
        // return "{" +
        //     " documentid='" + getDocumentid() + "'" +
        //     ", solicitacao='" + getSolicitacao() + "'" +
        //     ", status='" + getStatus() + "'" +
        //     ", valorLiberado='" + getValorLiberado() + "'" +
        //     ", valorTotalVerba='" + getValorTotalVerba() + "'" +
        //     ", valorResultado='" + getValorResultado() + "'" +
        //     ", descricaoDetalhada='" + getDescricaoDetalhada() + "'" +
        //     ", inicioAcao='" + getInicioAcao() + "'" +
        //     ", terminoAcao='" + getTerminoAcao() + "'" +
        //     ", envioEvidenciasConcluido='" + isEnvioEvidenciasConcluido() + "'" +
        //     ", evRecusada='" + isEvRecusada() + "'" +
        //     ", obsEnvioEvidencias='" + getObsEnvioEvidencias() + "'" +
        //     ", envioNDConcluido='" + isEnvioNDConcluido() + "'" +
        //     ", ndRecusada='" + isNdRecusada() + "'" +
        //     ", obsEnvioND='" + getObsEnvioND() + "'" +
        //     ", currentStepPortal='" + getCurrentStepPortal() + "'" +
        //     ", motivoCancelamento='" + getMotivoCancelamento() + "'" +
        //     ", motivoRecusaND='" + getMotivoRecusaND() + "'" +
        //     ", motivoRecusaEv='" + getMotivoRecusaEv() + "'" +
        //     ", tipoSellout='" + getTipoSellout() + "'" +
        //     ", tipoQuantidade='" + getTipoQuantidade() + "'" +
        //     ", folderAttach='" + getFolderAttach() + "'" +
        //     ", itensSellout='" + getItensSellout() + "'" +
        //     ", itensSellinIt='" + getItensSellinIt() + "'" +
        //     ", itensSellinTg='" + getItensSellinTg() + "'" +
        //     ", itensSellinTgAc='" + getItensSellinTgAc() + "'" +
        //     ", itensSpiffIt='" + getItensSpiffIt() + "'" +
        //     ", itensSpiffTg='" + getItensSpiffTg() + "'" +
        //     ", itensVpcEvt='" + getItensVpcEvt() + "'" +
        //     ", itensVpcOutros='" + getItensVpcOutros() + "'" +
        //     ", evidencias='" + getEvidencias() + "'" +
        //     ", nd='" + getNd() + "'" +
        //     ", duplicatas='" + getDuplicatas() + "'" +
        //     "}";
    }

}
