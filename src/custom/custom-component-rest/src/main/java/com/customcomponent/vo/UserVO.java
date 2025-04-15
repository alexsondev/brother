package com.customcomponent.vo;

import java.util.Objects;
import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.gson.Gson;

/**
 * Objeto de com os dados da API /api/public/admin/tenant Pode também ser chamado de DTO
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserVO implements Serializable {

  /**
   *
   */
  private static final long serialVersionUID = 1L;

  private Long id;
  private String code;
  private String login;
  private String email;
  private String fullName;

  public UserVO() {}

  @JsonIgnoreProperties(ignoreUnknown = true)

  public UserVO(Long id, String code, String login, String email, String fullName) {
    this.id = id;
    this.code = code;
    this.login = login;
    this.email = email;
    this.fullName = fullName;
  }

  public Long getId() {
    return this.id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getCode() {
    return this.code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public String getLogin() {
    return this.login;
  }

  public void setLogin(String login) {
    this.login = login;
  }

  public String getEmail() {
    return this.email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getFullName() {
    return this.fullName;
  }

  public void setFullName(String fullName) {
    this.fullName = fullName;
  }

  public UserVO id(Long id) {
    this.id = id;
    return this;
  }

  public UserVO code(String code) {
    this.code = code;
    return this;
  }

  public UserVO login(String login) {
    this.login = login;
    return this;
  }

  public UserVO email(String email) {
    this.email = email;
    return this;
  }

  public UserVO fullName(String fullName) {
    this.fullName = fullName;
    return this;
  }

  @Override
  public boolean equals(Object o) {
    if (o == this)
      return true;
    if (!(o instanceof UserVO)) {
      return false;
    }
    UserVO userVO = (UserVO) o;
    return Objects.equals(id, userVO.id) && Objects.equals(code, userVO.code)
        && Objects.equals(login, userVO.login) && Objects.equals(email, userVO.email)
        && Objects.equals(fullName, userVO.fullName);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, code, login, email, fullName);
  }

  @Override
  public String toString() {
    Gson gson = new Gson();
    return gson.toJson(this);

    // return "{" + " id='" + getId() + "'" + ", code='" + getCode() + "'" + ", login='"
    // + getLogin() + "'" + ", email='" + getEmail() + "'" + ", fullName='" + getFullName()
    // + "'" + "}";
  }


}
