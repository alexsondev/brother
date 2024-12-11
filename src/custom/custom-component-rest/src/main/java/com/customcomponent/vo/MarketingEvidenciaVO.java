package com.customcomponent.vo;

import java.util.Objects;
import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.gson.Gson;

/**
 * Objeto de com os dados dos arquivos de evidência do fluxo de Marketing
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class MarketingEvidenciaVO implements Serializable {

  /**
   *
   */
  private static final long serialVersionUID = 1L;

  private Long documentid;
  private String nome;
  private String tipo;
  private String version;

  private String url;
  private String descricao;
  private Boolean aceito;
  private String motivoRecusa;
  private Boolean removed;

  public MarketingEvidenciaVO() {}

  @JsonIgnoreProperties(ignoreUnknown = true)

  public MarketingEvidenciaVO(Long documentid, String nome, String tipo, String version, String url,
      String descricao, Boolean aceito, String motivoRecusa, Boolean removed) {
    this.documentid = documentid;
    this.nome = nome;
    this.tipo = tipo;
    this.version = version;
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

  public MarketingEvidenciaVO documentid(Long documentid) {
    this.documentid = documentid;
    return this;
  }

  public MarketingEvidenciaVO nome(String nome) {
    this.nome = nome;
    return this;
  }

  public MarketingEvidenciaVO tipo(String tipo) {
    this.tipo = tipo;
    return this;
  }

  public MarketingEvidenciaVO version(String version) {
    this.version = version;
    return this;
  }

  public MarketingEvidenciaVO url(String url) {
    this.url = url;
    return this;
  }

  public MarketingEvidenciaVO descricao(String descricao) {
    this.descricao = descricao;
    return this;
  }

  public MarketingEvidenciaVO aceito(Boolean aceito) {
    this.aceito = aceito;
    return this;
  }

  public MarketingEvidenciaVO motivoRecusa(String motivoRecusa) {
    this.motivoRecusa = motivoRecusa;
    return this;
  }

  public MarketingEvidenciaVO removed(Boolean removed) {
    this.removed = removed;
    return this;
  }

  @Override
  public boolean equals(Object o) {
    if (o == this)
      return true;
    if (!(o instanceof MarketingEvidenciaVO)) {
      return false;
    }
    MarketingEvidenciaVO marketingEvidenciaVO = (MarketingEvidenciaVO) o;
    return Objects.equals(documentid, marketingEvidenciaVO.documentid)
        && Objects.equals(nome, marketingEvidenciaVO.nome)
        && Objects.equals(tipo, marketingEvidenciaVO.tipo)
        && Objects.equals(version, marketingEvidenciaVO.version)
        && Objects.equals(url, marketingEvidenciaVO.url)
        && Objects.equals(descricao, marketingEvidenciaVO.descricao)
        && Objects.equals(aceito, marketingEvidenciaVO.aceito)
        && Objects.equals(motivoRecusa, marketingEvidenciaVO.motivoRecusa)
        && Objects.equals(removed, marketingEvidenciaVO.removed);
  }

  @Override
  public int hashCode() {
    return Objects.hash(documentid, nome, tipo, version, url, descricao, aceito, motivoRecusa,
        removed);
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
    // ", url='" + getUrl() + "'" +
    // ", descricao='" + getDescricao() + "'" +
    // ", aceito='" + isAceito() + "'" +
    // ", motivoRecusa='" + getMotivoRecusa() + "'" +
    // ", removed='" + isRemoved() + "'" +
    // "}";
  }

}
