package com.customcomponent.vo;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.gson.Gson;

/**
 * Objeto de com os dados da API /ecm/api/rest/ecm/workflowView/send
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ECMWorkflowVO implements Serializable {

  /**
   *
   */
  private static final long serialVersionUID = 1L;

  private final String processId;
  private final Integer processInstanceId;
  private final Integer currentMovto; // 30
  private final Integer currentState; // 133
  private final Integer selectedState;
  private final String taskUserId;
  private final String comments;
  private final List<String> selectedColleague;
  private final List<ECMFormDataVO> formData;
  private final Boolean completeTask;
  private final Integer version;
  private final Integer versionDoc;
  private List<String> internalFields = new ArrayList<>();
  private List<String> newObservations = new ArrayList<>();
  private List<ECMAppointmentVO> appointments = new ArrayList<>();
  private List<ECMAttachmentVO> attachments = new ArrayList<>();;
  private String subProcessId;
  private String pass;
  private Boolean digitalSignature = false;
  private Boolean isDigitalSigned = false;
  private Boolean isLinkReturn = false;
  private Boolean managerMode = false;
  private Integer conditionAfterAutomatic = -1;
  private Integer selectedDestinyAfterAutomatic = -1;
  private Integer subProcessSequence;


  @JsonIgnoreProperties(ignoreUnknown = true)

  public ECMWorkflowVO(String processId, Integer processInstanceId, Integer currentMovto,
      Integer currentState, Integer selectedState, String taskUserId, String comments,
      List<String> selectedColleague, List<ECMFormDataVO> formData, Boolean completeTask,
      Integer version, Integer versionDoc) {
    this.processId = processId;
    this.processInstanceId = processInstanceId;
    this.currentMovto = currentMovto;
    this.currentState = currentState;
    this.selectedState = selectedState;
    this.taskUserId = taskUserId;
    this.comments = comments;
    this.selectedColleague = selectedColleague;
    this.formData = formData;
    this.completeTask = completeTask;
    this.version = version;
    this.versionDoc = versionDoc;
  }


  public ECMWorkflowVO(String processId, Integer processInstanceId, Integer currentMovto,
      Integer currentState, Integer selectedState, String taskUserId, String comments,
      List<String> selectedColleague, List<ECMFormDataVO> formData, Boolean completeTask,
      List<String> internalFields, List<String> newObservations,
      List<ECMAppointmentVO> appointments, List<ECMAttachmentVO> attachments, String subProcessId,
      String pass, Boolean digitalSignature, Boolean isDigitalSigned, Boolean isLinkReturn,
      Boolean managerMode, Integer conditionAfterAutomatic, Integer selectedDestinyAfterAutomatic,
      Integer subProcessSequence, Integer version, Integer versionDoc) {
    this.processId = processId;
    this.processInstanceId = processInstanceId;
    this.currentMovto = currentMovto;
    this.currentState = currentState;
    this.selectedState = selectedState;
    this.taskUserId = taskUserId;
    this.comments = comments;
    this.selectedColleague = selectedColleague;
    this.formData = formData;
    this.completeTask = completeTask;
    this.internalFields = internalFields;
    this.newObservations = newObservations;
    this.appointments = appointments;
    this.attachments = attachments;
    this.subProcessId = subProcessId;
    this.pass = pass;
    this.digitalSignature = digitalSignature;
    this.isDigitalSigned = isDigitalSigned;
    this.isLinkReturn = isLinkReturn;
    this.managerMode = managerMode;
    this.conditionAfterAutomatic = conditionAfterAutomatic;
    this.selectedDestinyAfterAutomatic = selectedDestinyAfterAutomatic;
    this.subProcessSequence = subProcessSequence;
    this.version = version;
    this.versionDoc = versionDoc;
  }

  public String getProcessId() {
    return this.processId;
  }


  public Integer getProcessInstanceId() {
    return this.processInstanceId;
  }


  public Integer getCurrentMovto() {
    return this.currentMovto;
  }


  public Integer getCurrentState() {
    return this.currentState;
  }


  public Integer getSelectedState() {
    return this.selectedState;
  }


  public String getTaskUserId() {
    return this.taskUserId;
  }


  public String getComments() {
    return this.comments;
  }


  public List<String> getSelectedColleague() {
    return this.selectedColleague;
  }


  public List<ECMFormDataVO> getFormData() {
    return this.formData;
  }


  public Boolean isCompleteTask() {
    return this.completeTask;
  }

  public Boolean getCompleteTask() {
    return this.completeTask;
  }


  public List<String> getInternalFields() {
    return this.internalFields;
  }

  public void setInternalFields(List<String> internalFields) {
    this.internalFields = internalFields;
  }

  public List<String> getNewObservations() {
    return this.newObservations;
  }

  public void setNewObservations(List<String> newObservations) {
    this.newObservations = newObservations;
  }

  public List<ECMAppointmentVO> getAppointments() {
    return this.appointments;
  }

  public void setAppointments(List<ECMAppointmentVO> appointments) {
    this.appointments = appointments;
  }

  public List<ECMAttachmentVO> getAttachments() {
    return this.attachments;
  }

  public void setAttachments(List<ECMAttachmentVO> attachments) {
    this.attachments = attachments;
  }

  public String getSubProcessId() {
    return this.subProcessId;
  }

  public void setSubProcessId(String subProcessId) {
    this.subProcessId = subProcessId;
  }

  public String getPass() {
    return this.pass;
  }

  public void setPass(String pass) {
    this.pass = pass;
  }

  public Boolean isDigitalSignature() {
    return this.digitalSignature;
  }

  public Boolean getDigitalSignature() {
    return this.digitalSignature;
  }

  public void setDigitalSignature(Boolean digitalSignature) {
    this.digitalSignature = digitalSignature;
  }

  public Boolean isIsDigitalSigned() {
    return this.isDigitalSigned;
  }

  public Boolean getIsDigitalSigned() {
    return this.isDigitalSigned;
  }

  public void setIsDigitalSigned(Boolean isDigitalSigned) {
    this.isDigitalSigned = isDigitalSigned;
  }

  public Boolean isIsLinkReturn() {
    return this.isLinkReturn;
  }

  public Boolean getIsLinkReturn() {
    return this.isLinkReturn;
  }

  public void setIsLinkReturn(Boolean isLinkReturn) {
    this.isLinkReturn = isLinkReturn;
  }

  public Boolean isManagerMode() {
    return this.managerMode;
  }

  public Boolean getManagerMode() {
    return this.managerMode;
  }

  public void setManagerMode(Boolean managerMode) {
    this.managerMode = managerMode;
  }

  public Integer getConditionAfterAutomatic() {
    return this.conditionAfterAutomatic;
  }

  public void setConditionAfterAutomatic(Integer conditionAfterAutomatic) {
    this.conditionAfterAutomatic = conditionAfterAutomatic;
  }

  public Integer getSelectedDestinyAfterAutomatic() {
    return this.selectedDestinyAfterAutomatic;
  }

  public void setSelectedDestinyAfterAutomatic(Integer selectedDestinyAfterAutomatic) {
    this.selectedDestinyAfterAutomatic = selectedDestinyAfterAutomatic;
  }

  public Integer getSubProcessSequence() {
    return this.subProcessSequence;
  }

  public void setSubProcessSequence(Integer subProcessSequence) {
    this.subProcessSequence = subProcessSequence;
  }

  public Integer getVersion() {
    return this.version;
  }

  public Integer getVersionDoc() {
    return this.versionDoc;
  }

  public ECMWorkflowVO internalFields(List<String> internalFields) {
    setInternalFields(internalFields);
    return this;
  }

  public ECMWorkflowVO newObservations(List<String> newObservations) {
    setNewObservations(newObservations);
    return this;
  }

  public ECMWorkflowVO appointments(List<ECMAppointmentVO> appointments) {
    setAppointments(appointments);
    return this;
  }

  public ECMWorkflowVO attachments(List<ECMAttachmentVO> attachments) {
    setAttachments(attachments);
    return this;
  }

  public ECMWorkflowVO subProcessId(String subProcessId) {
    setSubProcessId(subProcessId);
    return this;
  }

  public ECMWorkflowVO pass(String pass) {
    setPass(pass);
    return this;
  }

  public ECMWorkflowVO digitalSignature(Boolean digitalSignature) {
    setDigitalSignature(digitalSignature);
    return this;
  }

  public ECMWorkflowVO isDigitalSigned(Boolean isDigitalSigned) {
    setIsDigitalSigned(isDigitalSigned);
    return this;
  }

  public ECMWorkflowVO isLinkReturn(Boolean isLinkReturn) {
    setIsLinkReturn(isLinkReturn);
    return this;
  }

  public ECMWorkflowVO managerMode(Boolean managerMode) {
    setManagerMode(managerMode);
    return this;
  }

  public ECMWorkflowVO conditionAfterAutomatic(Integer conditionAfterAutomatic) {
    setConditionAfterAutomatic(conditionAfterAutomatic);
    return this;
  }

  public ECMWorkflowVO selectedDestinyAfterAutomatic(Integer selectedDestinyAfterAutomatic) {
    setSelectedDestinyAfterAutomatic(selectedDestinyAfterAutomatic);
    return this;
  }

  public ECMWorkflowVO subProcessSequence(Integer subProcessSequence) {
    setSubProcessSequence(subProcessSequence);
    return this;
  }

  @Override
  public boolean equals(Object o) {
    if (o == this)
      return true;
    if (!(o instanceof ECMWorkflowVO)) {
      return false;
    }
    ECMWorkflowVO eCMWorkflowVO = (ECMWorkflowVO) o;
    return Objects.equals(processId, eCMWorkflowVO.processId)
        && Objects.equals(processInstanceId, eCMWorkflowVO.processInstanceId)
        && Objects.equals(currentMovto, eCMWorkflowVO.currentMovto)
        && Objects.equals(currentState, eCMWorkflowVO.currentState)
        && Objects.equals(selectedState, eCMWorkflowVO.selectedState)
        && Objects.equals(taskUserId, eCMWorkflowVO.taskUserId)
        && Objects.equals(comments, eCMWorkflowVO.comments)
        && Objects.equals(selectedColleague, eCMWorkflowVO.selectedColleague)
        && Objects.equals(formData, eCMWorkflowVO.formData)
        && Objects.equals(completeTask, eCMWorkflowVO.completeTask)
        && Objects.equals(internalFields, eCMWorkflowVO.internalFields)
        && Objects.equals(newObservations, eCMWorkflowVO.newObservations)
        && Objects.equals(appointments, eCMWorkflowVO.appointments)
        && Objects.equals(attachments, eCMWorkflowVO.attachments)
        && Objects.equals(subProcessId, eCMWorkflowVO.subProcessId)
        && Objects.equals(pass, eCMWorkflowVO.pass)
        && Objects.equals(digitalSignature, eCMWorkflowVO.digitalSignature)
        && Objects.equals(isDigitalSigned, eCMWorkflowVO.isDigitalSigned)
        && Objects.equals(isLinkReturn, eCMWorkflowVO.isLinkReturn)
        && Objects.equals(managerMode, eCMWorkflowVO.managerMode)
        && Objects.equals(conditionAfterAutomatic, eCMWorkflowVO.conditionAfterAutomatic)
        && Objects.equals(selectedDestinyAfterAutomatic,
            eCMWorkflowVO.selectedDestinyAfterAutomatic)
        && Objects.equals(subProcessSequence, eCMWorkflowVO.subProcessSequence)
        && Objects.equals(version, eCMWorkflowVO.version)
        && Objects.equals(versionDoc, eCMWorkflowVO.versionDoc);
  }

  @Override
  public int hashCode() {
    return Objects.hash(processId, processInstanceId, currentMovto, currentState, selectedState,
        taskUserId, comments, selectedColleague, formData, completeTask, internalFields,
        newObservations, appointments, attachments, subProcessId, pass, digitalSignature,
        isDigitalSigned, isLinkReturn, managerMode, conditionAfterAutomatic,
        selectedDestinyAfterAutomatic, subProcessSequence, version, versionDoc);
  }

  @Override
  public String toString() {
    Gson gson = new Gson();
    return gson.toJson(this);
    
    // return "{" + " processId='" + getProcessId() + "'" + ", processInstanceId='"
    //     + getProcessInstanceId() + "'" + ", currentMovto='" + getCurrentMovto() + "'"
    //     + ", currentState='" + getCurrentState() + "'" + ", selectedState='" + getSelectedState()
    //     + "'" + ", taskUserId='" + getTaskUserId() + "'" + ", comments='" + getComments() + "'"
    //     + ", selectedColleague='" + getSelectedColleague() + "'" + ", formData='" + getFormData()
    //     + "'" + ", completeTask='" + isCompleteTask() + "'" + ", internalFields='"
    //     + getInternalFields() + "'" + ", newObservations='" + getNewObservations() + "'"
    //     + ", appointments='" + getAppointments() + "'" + ", attachments='" + getAttachments() + "'"
    //     + ", subProcessId='" + getSubProcessId() + "'" + ", pass='" + getPass() + "'"
    //     + ", digitalSignature='" + isDigitalSignature() + "'" + ", isDigitalSigned='"
    //     + isIsDigitalSigned() + "'" + ", isLinkReturn='" + isIsLinkReturn() + "'"
    //     + ", managerMode='" + isManagerMode() + "'" + ", conditionAfterAutomatic='"
    //     + getConditionAfterAutomatic() + "'" + ", selectedDestinyAfterAutomatic='"
    //     + getSelectedDestinyAfterAutomatic() + "'" + ", subProcessSequence='"
    //     + getSubProcessSequence() + "'" + ", version='" + getVersion() + "'" + ", versionDoc='"
    //     + getVersionDoc() + "'" + "}";
  }

}
