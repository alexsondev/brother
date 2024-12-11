package com.customcomponent.vo;

import java.util.Objects;
import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.gson.Gson;

/**
 * Objeto de com os dados da API /api/public/admin/tenant Pode também ser chamado de DTO
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class DocumentVO implements Serializable {

  /**
   *
   */
  private static final long serialVersionUID = 1L;

  private Long documentid;
  private String description;
  private String url;
  private String filename;
  private String version;

  public DocumentVO() {}

  @JsonIgnoreProperties(ignoreUnknown = true)

  public DocumentVO(Long documentid, String description, String url, String filename,
      String version) {
    this.documentid = documentid;
    this.description = description;
    this.url = url;
    this.filename = filename;
    this.version = version;
  }

  public Long getDocumentid() {
    return this.documentid;
  }

  public void setDocumentid(Long documentid) {
    this.documentid = documentid;
  }

  public String getDescription() {
    return this.description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getUrl() {
    return this.url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public String getFilename() {
    return this.filename;
  }

  public void setFilename(String filename) {
    this.filename = filename;
  }

  public String getVersion() {
    return this.version;
  }

  public void setVersion(String version) {
    this.version = version;
  }

  public DocumentVO documentid(Long documentid) {
    this.documentid = documentid;
    return this;
  }

  public DocumentVO description(String description) {
    this.description = description;
    return this;
  }

  public DocumentVO url(String url) {
    this.url = url;
    return this;
  }

  public DocumentVO filename(String filename) {
    this.filename = filename;
    return this;
  }

  public DocumentVO version(String version) {
    this.version = version;
    return this;
  }

  @Override
  public boolean equals(Object o) {
    if (o == this)
      return true;
    if (!(o instanceof DocumentVO)) {
      return false;
    }
    DocumentVO documentVO = (DocumentVO) o;
    return Objects.equals(documentid, documentVO.documentid)
        && Objects.equals(description, documentVO.description)
        && Objects.equals(url, documentVO.url) && Objects.equals(filename, documentVO.filename)
        && Objects.equals(version, documentVO.version);
  }

  @Override
  public int hashCode() {
    return Objects.hash(documentid, description, url, filename, version);
  }

  @Override
  public String toString() {

    Gson gson = new Gson();
    return gson.toJson(this);

    // return "{" + " documentid='" + getDocumentid() + "'" + ", description='" + getDescription()
    //     + "'" + ", url='" + getUrl() + "'" + ", filename='" + getFilename() + "'" + ", version='"
    //     + getVersion() + "'" + "}";
  }

}
