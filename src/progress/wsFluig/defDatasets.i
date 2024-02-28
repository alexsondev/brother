    /*------------------------------------------------------------------------
    File        : defDatasets.i
    Purpose     : respons vel pelas defini‡äes de campos  dos datasets

    Syntax      :

    Description :

    Author(s)   : rzo 
    Created     : 3/03/2015
    Notes       :
  ----------------------------------------------------------------------*/
/*          This .W file was created with the Progress AppBuilder.      */
/*----------------------------------------------------------------------*/

/* ***************************  Definitions  ************************** */

/* const */   
&GLOBAL-DEFINE CampoForm  id,companyid,documentid,cardid,version,tableid,Origem,CodMLA
&GLOBAL-DEFINE OrdemForm  id,companyid,documentid,cardid,version,tableid,Origem,CodMLA

&GLOBAL-DEFINE CampoTaskProcess  active,status,choosedSequence,processTaskPK.colleagueId,processTaskPK.companyId,processTaskPK.movementSequence,processTaskPK.processInstanceId,processTaskPK.transferredSequence
&GLOBAL-DEFINE OrdemTaskProcess  active,status,choosedSequence,processTaskPK.colleagueId,processTaskPK.companyId,processTaskPK.movementSequence,processTaskPK.processInstanceId,processTaskPK.transferredSequence

&GLOBAL-DEFINE CampoTaskProcessMed  active,status,choosedSequence,processTaskPK.colleagueId,processTaskPK.companyId,processTaskPK.movementSequence,processTaskPK.processInstanceId,processTaskPK.transferredSequence
&GLOBAL-DEFINE OrdemTaskProcessMed  active,status,choosedSequence,processTaskPK.colleagueId,processTaskPK.companyId,processTaskPK.movementSequence,processTaskPK.processInstanceId,processTaskPK.transferredSequence

DEFINE TEMP-TABLE ttTaskProcess
    FIELD ACTIVE AS CHARACTER
    FIELD companyId AS INTEGER
    FIELD processId AS INTEGER
    FIELD COLleagueId AS CHARACTER
    FIELD movementSequence AS INTEGER 
    FIELD transferSequence AS INTEGER
    FIELD choosedSequence AS INTEGER
    FIELD sit AS CHARACTER
    FIELD origem AS INTEGER
    FIELD aprovador AS LOGICAL .
