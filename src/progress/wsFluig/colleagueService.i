/*
    Programa: wsFluig/colleagueService.i
   Descri‡Æo: Defini‡Æo de temp-tables
  Data/Autor: 18/10/2017 - TOTVS IBIRAPUERA
*/
/* definicao de temp-table */
DEFINE TEMP-TABLE ttColleagues NO-UNDO  NAMESPACE-URI "" XML-NODE-NAME "item"
	FIELD active AS LOGICAL 
	FIELD adminUser AS LOGICAL 
	FIELD area1Id AS INTEGER 
	FIELD area2Id AS INTEGER 
	FIELD area3Id AS INTEGER 
	FIELD area4Id AS INTEGER 
	FIELD area5Id AS INTEGER 
	FIELD colleagueId AS CHARACTER 
	FIELD colleagueName AS CHARACTER 
	FIELD colleaguebackground AS CHARACTER 
	FIELD companyId AS INT64 
	FIELD currentProject AS CHARACTER 
	FIELD defaultLanguage AS CHARACTER 
	FIELD dialectId AS CHARACTER 
	FIELD ecmVersion AS CHARACTER 
	FIELD emailHtml AS LOGICAL 
	FIELD especializationArea AS CHARACTER 
	FIELD extensionNr AS CHARACTER 
	FIELD gedUser AS LOGICAL 
	FIELD groupId AS CHARACTER 
	FIELD guestUser AS LOGICAL 
	FIELD homePage AS CHARACTER 
	FIELD login AS CHARACTER 
	FIELD mail AS CHARACTER 
	FIELD maxPrivateSize AS CHARACTER XML-DATA-TYPE "float" 
	FIELD menuConfig AS INTEGER 
	FIELD nominalUser AS LOGICAL 
	FIELD passwd AS CHARACTER 
	FIELD photoPath AS CHARACTER 
	FIELD rowId AS INTEGER 
	FIELD sessionId AS CHARACTER 
	FIELD usedSpace AS CHARACTER XML-DATA-TYPE "float" 
	FIELD volumeId AS CHARACTER .

/* definicao de dataset */
DEFINE DATASET dsColleagues NAMESPACE-URI "http://ws.foundation.ecm.technology.totvs.com/" FOR ttColleagues.
