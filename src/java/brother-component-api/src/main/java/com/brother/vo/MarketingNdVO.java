package com.brother.vo;

import java.util.Objects;
import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.gson.Gson;

/**
 * Objeto de com os dados dos arquivos de evidência do fluxo de Marketing
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class MarketingNdVO implements Serializable {

  /**
   *
   */
  private static final long serialVersionUID = 1L;

  private Long documentid;
  private String nome;
  private String tipo;
  private String version;
  private String numero;
  private String url;
  private String descricao;
  private Boolean aceito;
  private String motivoRecusa;
  private Boolean removed;

  public MarketingNdVO() {}

  @JsonIgnoreProperties(ignoreUnknown = true)

  public MarketingNdVO(Long documentid, String nome, String tipo, String version, String numero,
      String url, String descricao, Boolean aceito, String motivoRecusa, Boolean removed) {
    this.documentid = documentid;
    this.nome = nome;
    this.tipo = tipo;
    this.version = version;
    this.numero = numero;
    this.url = url;
    this.descricao = descricao;
    this.aceito = aceito;
    this.motivoRecusa = motivoRecusa;
    this.removed = removed;
  }

  public Long getDocumentid() {
    return this.documentid;
  }

  public void setDocumentid(Long documentid) {
    this.documentid = documentid;
  }

  public String getNome() {
    return this.nome;
  }

  public void setNome(String nome) {
    this.nome = nome;
  }

  public String getTipo() {
    return this.tipo;
  }

  public void setTipo(String tipo) {
    this.tipo = tipo;
  }

  public String getVersion() {
    return this.version;
  }

  public void setVersion(String version) {
    this.version = version;
  }

  public String getNumero() {
    return this.numero;
  }

  public void setNumero(String numero) {
    this.numero = numero;
  }

  public String getUrl() {
    return this.url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public String getDescricao() {
    return this.descricao;
  }

  public void setDescricao(String descricao) {
    this.descricao = descricao;
  }

  public Boolean isAceito() {
    return this.aceito;
  }

  public Boolean getAceito() {
    return this.aceito;
  }

  public void setAceito(Boolean aceito) {
    this.aceito = aceito;
  }

  public String getMotivoRecusa() {
    return this.motivoRecusa;
  }

  public void setMotivoRecusa(String motivoRecusa) {
    this.motivoRecusa = motivoRecusa;
  }

  public Boolean isRemoved() {
    return this.removed;
  }

  public Boolean getRemoved() {
    return this.removed;
  }

  public void setRemoved(Boolean removed) {
    this.removed = removed;
  }

  public MarketingNdVO documentid(Long documentid) {
    this.documentid = documentid;
    return this;
  }

  public MarketingNdVO nome(String nome) {
    this.nome = nome;
    return this;
  }

  public MarketingNdVO tipo(String tipo) {
    this.tipo = tipo;
    return this;
  }

  public MarketingNdVO version(String version) {
    this.version = version;
    return this;
  }

  public MarketingNdVO numero(String numero) {
    this.numero = numero;
    return this;
  }

  public MarketingNdVO url(String url) {
    this.url = url;
    return this;
  }

  public MarketingNdVO descricao(String descricao) {
    this.descricao = descricao;
    return this;
  }

  public MarketingNdVO aceito(Boolean aceito) {
    this.aceito = aceito;
    return this;
  }

  public MarketingNdVO motivoRecusa(String motivoRecusa) {
    this.motivoRecusa = motivoRecusa;
    return this;
  }

  public MarketingNdVO removed(Boolean removed) {
    this.removed = removed;
    return this;
  }

  @Override
  public boolean equals(Object o) {
    if (o == this)
      return true;
    if (!(o instanceof MarketingNdVO)) {
      return false;
    }
    MarketingNdVO marketingNdVO = (MarketingNdVO) o;
    return Objects.equals(documentid, marketingNdVO.documentid)
        && Objects.equals(nome, marketingNdVO.nome) && Objects.equals(tipo, marketingNdVO.tipo)
        && Objects.equals(version, marketingNdVO.version)
        && Objects.equals(numero, marketingNdVO.numero) && Objects.equals(url, marketingNdVO.url)
        && Objects.equals(descricao, marketingNdVO.descricao)
        && Objects.equals(aceito, marketingNdVO.aceito)
        && Objects.equals(motivoRecusa, marketingNdVO.motivoRecusa)
        && Objects.equals(removed, marketingNdVO.removed);
  }

  @Override
  public int hashCode() {
    return Objects.hash(documentid, nome, tipo, version, numero, url, descricao, aceito,
        motivoRecusa, removed);
  }

  @Override
  public String toString() {
    Gson gson = new Gson();
    return gson.toJson(this);

    // return "{" +
    // " documentid='" + getDocumentid() + "'" +
    // ", nome='" + getNome() + "'" +
    // ", tipo='" + getTipo() + "'" +
    // ", version='" + getVersion() + "'" +
    // ", numero='" + getNumero() + "'" +
    // ", url='" + getUrl() + "'" +
    // ", descricao='" + getDescricao() + "'" +
    // ", aceito='" + isAceito() + "'" +
    // ", motivoRecusa='" + getMotivoRecusa() + "'" +
    // ", removed='" + isRemoved() + "'" +
    // "}";
  }

}
