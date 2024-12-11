package com.customcomponent.vo;

import java.util.Objects;
import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.gson.Gson;

/**
 * Objeto de com os dados da API /api/public/admin/tenant Pode também ser chamado de DTO
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ResumoVerbasVO implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private Long documentid;
    private String solicitacao;
    private String nomeAcao;
    private Long prazoVencto;
    private String tipoAcaoDescricao;
    private String produtosCodigos;
    private String clienteCodigo;
    private String clienteNome;
    private Long inicioAcao;
    private Long terminoAcao;
    private String status;
    private String guid;

    public ResumoVerbasVO() {
    }

    public ResumoVerbasVO(Long documentid, String solicitacao, String nomeAcao, Long prazoVencto, String tipoAcaoDescricao, String produtosCodigos, String clienteCodigo, String clienteNome, Long inicioAcao, Long terminoAcao, String status, String guid) {
        this.documentid = documentid;
        this.solicitacao = solicitacao;
        this.nomeAcao = nomeAcao;
        this.prazoVencto = prazoVencto;
        this.tipoAcaoDescricao = tipoAcaoDescricao;
        this.produtosCodigos = produtosCodigos;
        this.clienteCodigo = clienteCodigo;
        this.clienteNome = clienteNome;
        this.inicioAcao = inicioAcao;
        this.terminoAcao = terminoAcao;
        this.status = status;
        this.guid = guid;
    }

    public Long getDocumentid() {
        return this.documentid;
    }

    public void setDocumentid(Long documentid) {
        this.documentid = documentid;
    }

    public String getSolicitacao() {
        return this.solicitacao;
    }

    public void setSolicitacao(String solicitacao) {
        this.solicitacao = solicitacao;
    }

    public String getNomeAcao() {
        return this.nomeAcao;
    }

    public void setNomeAcao(String nomeAcao) {
        this.nomeAcao = nomeAcao;
    }

    public Long getPrazoVencto() {
        return this.prazoVencto;
    }

    public void setPrazoVencto(Long prazoVencto) {
        this.prazoVencto = prazoVencto;
    }

    public String getTipoAcaoDescricao() {
        return this.tipoAcaoDescricao;
    }

    public void setTipoAcaoDescricao(String tipoAcaoDescricao) {
        this.tipoAcaoDescricao = tipoAcaoDescricao;
    }

    public String getProdutosCodigos() {
        return this.produtosCodigos;
    }

    public void setProdutosCodigos(String produtosCodigos) {
        this.produtosCodigos = produtosCodigos;
    }

    public String getClienteCodigo() {
        return this.clienteCodigo;
    }

    public void setClienteCodigo(String clienteCodigo) {
        this.clienteCodigo = clienteCodigo;
    }

    public String getClienteNome() {
        return this.clienteNome;
    }

    public void setClienteNome(String clienteNome) {
        this.clienteNome = clienteNome;
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

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getGuid() {
        return this.guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public ResumoVerbasVO documentid(Long documentid) {
        setDocumentid(documentid);
        return this;
    }

    public ResumoVerbasVO solicitacao(String solicitacao) {
        setSolicitacao(solicitacao);
        return this;
    }

    public ResumoVerbasVO nomeAcao(String nomeAcao) {
        setNomeAcao(nomeAcao);
        return this;
    }

    public ResumoVerbasVO prazoVencto(Long prazoVencto) {
        setPrazoVencto(prazoVencto);
        return this;
    }

    public ResumoVerbasVO tipoAcaoDescricao(String tipoAcaoDescricao) {
        setTipoAcaoDescricao(tipoAcaoDescricao);
        return this;
    }

    public ResumoVerbasVO produtosCodigos(String produtosCodigos) {
        setProdutosCodigos(produtosCodigos);
        return this;
    }

    public ResumoVerbasVO clienteCodigo(String clienteCodigo) {
        setClienteCodigo(clienteCodigo);
        return this;
    }

    public ResumoVerbasVO clienteNome(String clienteNome) {
        setClienteNome(clienteNome);
        return this;
    }

    public ResumoVerbasVO inicioAcao(Long inicioAcao) {
        setInicioAcao(inicioAcao);
        return this;
    }

    public ResumoVerbasVO terminoAcao(Long terminoAcao) {
        setTerminoAcao(terminoAcao);
        return this;
    }

    public ResumoVerbasVO status(String status) {
        setStatus(status);
        return this;
    }

    public ResumoVerbasVO guid(String guid) {
        setGuid(guid);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof ResumoVerbasVO)) {
            return false;
        }
        ResumoVerbasVO resumoVerbasVO = (ResumoVerbasVO) o;
        return Objects.equals(documentid, resumoVerbasVO.documentid) && Objects.equals(solicitacao, resumoVerbasVO.solicitacao) && Objects.equals(nomeAcao, resumoVerbasVO.nomeAcao) && Objects.equals(prazoVencto, resumoVerbasVO.prazoVencto) && Objects.equals(tipoAcaoDescricao, resumoVerbasVO.tipoAcaoDescricao) && Objects.equals(produtosCodigos, resumoVerbasVO.produtosCodigos) && Objects.equals(clienteCodigo, resumoVerbasVO.clienteCodigo) && Objects.equals(clienteNome, resumoVerbasVO.clienteNome) && Objects.equals(inicioAcao, resumoVerbasVO.inicioAcao) && Objects.equals(terminoAcao, resumoVerbasVO.terminoAcao) && Objects.equals(status, resumoVerbasVO.status) && Objects.equals(guid, resumoVerbasVO.guid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(documentid, solicitacao, nomeAcao, prazoVencto, tipoAcaoDescricao, produtosCodigos, clienteCodigo, clienteNome, inicioAcao, terminoAcao, status, guid);
    }

    @Override
    public String toString() {
      Gson gson = new Gson();
    return gson.toJson(this);

        // return "{" +
        //     " documentid='" + getDocumentid() + "'" +
        //     ", solicitacao='" + getSolicitacao() + "'" +
        //     ", nomeAcao='" + getNomeAcao() + "'" +
        //     ", prazoVencto='" + getPrazoVencto() + "'" +
        //     ", tipoAcaoDescricao='" + getTipoAcaoDescricao() + "'" +
        //     ", produtosCodigos='" + getProdutosCodigos() + "'" +
        //     ", clienteCodigo='" + getClienteCodigo() + "'" +
        //     ", clienteNome='" + getClienteNome() + "'" +
        //     ", inicioAcao='" + getInicioAcao() + "'" +
        //     ", terminoAcao='" + getTerminoAcao() + "'" +
        //     ", status='" + getStatus() + "'" +
        //     ", guid='" + getGuid() + "'" +
        //     "}";
    }

}
