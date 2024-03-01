&ANALYZE-SUSPEND _VERSION-NUMBER AB_v10r12
&ANALYZE-RESUME
&ANALYZE-SUSPEND _UIB-CODE-BLOCK _CUSTOM _DEFINITIONS Procedure 
/*------------------------------------------------------------------------
    File        : workflowEngineService.p
    Purpose     : respons vel pelos m‚todos de execu‡Æo do workflow

    Syntax      :

    Description :

    Author(s)   : rzo 
    Created     : 3/03/2015
    Notes       :
  ----------------------------------------------------------------------*/
/*          This .W file was created with the Progress AppBuilder.      */
/*----------------------------------------------------------------------*/


BLOCK-LEVEL ON ERROR UNDO, THROW.

USING Progress.Json.ObjectModel.*.

/* ***************************  Definitions  ************************** */
DEFINE VARIABLE hWebService            AS HANDLE  NO-UNDO.
DEFINE VARIABLE hWorkflowEngineService AS HANDLE  NO-UNDO.
DEFINE VARIABLE lOk                    AS LOGICAL NO-UNDO.

/* variveis xml startProcess */
DEFINE VARIABLE hDoc                   AS HANDLE  NO-UNDO.
DEFINE VARIABLE hRoot                  AS HANDLE  NO-UNDO.
DEFINE VARIABLE hRow                   AS HANDLE  NO-UNDO.
DEFINE VARIABLE hField                 AS HANDLE  NO-UNDO.
DEFINE VARIABLE hText                  AS HANDLE  NO-UNDO.
DEFINE VARIABLE hAttach                AS HANDLE  NO-UNDO.

/* variaveis start process */
DEFINE TEMP-TABLE ttappointment NO-UNDO
    NAMESPACE-URI "" 
    FIELD appointmentDate      AS DATETIME-TZ 
    FIELD appointmentSeconds   AS INTEGER 
    FIELD appointmentSequence  AS INTEGER 
    FIELD colleagueId          AS CHARACTER 
    FIELD colleagueName        AS CHARACTER 
    FIELD companyId            AS int64 
    FIELD isNewRecord          AS LOGICAL 
    FIELD movementSequence     AS INTEGER 
    FIELD processInstanceId    AS INTEGER 
    FIELD transferenceSequence AS INTEGER .

DEFINE DATASET appointment NAMESPACE-URI "http://ws.workflow.ecm.technology.totvs.com/" 
    FOR ttappointment.
/* ------------------------------------------------------------------------------- */

{wsfluig/manageErrors.i}
{wsfluig/readXML.i}

/* _UIB-CODE-BLOCK-END */
&ANALYZE-RESUME


&ANALYZE-SUSPEND _UIB-PREPROCESSOR-BLOCK 

/* ********************  Preprocessor Definitions  ******************** */

&Scoped-define PROCEDURE-TYPE Procedure
&Scoped-define DB-AWARE no



/* _UIB-PREPROCESSOR-BLOCK-END */
&ANALYZE-RESUME


/* *********************** Procedure Settings ************************ */

&ANALYZE-SUSPEND _PROCEDURE-SETTINGS
/* Settings for THIS-PROCEDURE
   Type: Procedure
   Allow: 
   Frames: 0
   Add Fields to: Neither
   Other Settings: CODE-ONLY COMPILE
 */
&ANALYZE-RESUME _END-PROCEDURE-SETTINGS

/* *************************  Create Window  ************************** */

&ANALYZE-SUSPEND _CREATE-WINDOW
/* DESIGN Window definition (used by the UIB) 
  CREATE WINDOW Procedure ASSIGN
         HEIGHT             = 8.71
         WIDTH              = 60.
/* END WINDOW DEFINITION */
                                                                        */
&ANALYZE-RESUME

 


&ANALYZE-SUSPEND _UIB-CODE-BLOCK _CUSTOM _MAIN-BLOCK Procedure 


/* ***************************  Main Block  *************************** */

/* _UIB-CODE-BLOCK-END */
&ANALYZE-RESUME


/* **********************  Internal Procedures  *********************** */

&IF DEFINED(EXCLUDE-cancelInstance) = 0 &THEN

&ANALYZE-SUSPEND _UIB-CODE-BLOCK _PROCEDURE cancelInstance Procedure 
PROCEDURE cancelInstance :
    /*------------------------------------------------------------------------------
      Purpose:     
      Parameters:  <none>
      Notes:       
    ------------------------------------------------------------------------------*/

    /* vars */
    DEFINE VARIABLE processInstanceId AS INTEGER   NO-UNDO.
    DEFINE VARIABLE userId1           AS CHARACTER NO-UNDO.
    DEFINE VARIABLE cancelText        AS CHARACTER NO-UNDO.
    DEFINE VARIABLE cRet              AS CHARACTER NO-UNDO.
    DEFINE VARIABLE lcErro            AS LONGCHAR  NO-UNDO.

    /* param */
    DEFINE INPUT PARAMETER companyId AS CHARACTER NO-UNDO.
    DEFINE INPUT PARAMETER username AS CHARACTER NO-UNDO.
    DEFINE INPUT PARAMETER password AS CHARACTER NO-UNDO.
    DEFINE INPUT PARAMETER pProcessIdIntance AS INTEGER NO-UNDO.
    DEFINE INPUT PARAMETER pUser AS CHARACTER NO-UNDO.
    DEFINE INPUT PARAMETER pText AS CHARACTER NO-UNDO.
    DEFINE OUTPUT PARAMETER pResult AS LONGCHAR NO-UNDO.

    /* main */
    DO ON ERROR UNDO,THROW:
        ASSIGN 
            processInstanceId = pProcessIdIntance
            userId1           = pUser
            cancelText        = pText.

        RUN cancelInstance IN hWorkflowEngineService (INPUT username, 
            INPUT password, 
            INPUT companyId, 
            INPUT processInstanceId, 
            INPUT userId1, 
            INPUT cancelText, 
            OUTPUT pResult).    
        cRet = "OK:U".
    END.

    CATCH mySoapErrorObject AS Progress.Lang.SoapFaultError.
        DEFINE VARIABLE hSoupFault AS HANDLE NO-UNDO.

        hSoupFault = mySoapErrorObject:SoapFault.
    
        IF VALID-HANDLE(hSoupFault) THEN 
        DO:
            lcErro = hSoupFault:GET-SERIALIZED().
            RUN generateErrorXML (lcErro).
        END.

        cRet = "NOK":U.
    END CATCH.

    CATCH myAnyError AS Progress.Lang.Error.
        RUN generateError(INPUT STRING(myAnyError:getMessageNum(1)),
            INPUT myAnyError:getMessage(1)).

        cRet = "NOK":U.
    END CATCH.

    FINALLY:
        RETURN cRet.
    END FINALLY.
END PROCEDURE.

/* _UIB-CODE-BLOCK-END */
&ANALYZE-RESUME

&ENDIF

&IF DEFINED(EXCLUDE-connectWS) = 0 &THEN

&ANALYZE-SUSPEND _UIB-CODE-BLOCK _PROCEDURE connectWS Procedure 
PROCEDURE connectWS :
    /*------------------------------------------------------------------------------
      Purpose:     
      Parameters:  <none>
      Notes:       
    ------------------------------------------------------------------------------*/

    /* param */
    DEFINE INPUT PARAMETER pUrl AS CHARACTER NO-UNDO.

    /* main */
    {wsfluig/connect.i ECMWorkflowEngineService WorkflowEngineService hWebService hWorkflowEngineService pURL}

END PROCEDURE.

/* _UIB-CODE-BLOCK-END */
&ANALYZE-RESUME

&ENDIF


&IF DEFINED(EXCLUDE-getTableResult) = 0 &THEN

&ANALYZE-SUSPEND _UIB-CODE-BLOCK _PROCEDURE getTableResult Procedure 
PROCEDURE getTableResult :
    /*------------------------------------------------------------------------------
      Purpose:     
      Parameters:  <none>
      Notes:       
    ------------------------------------------------------------------------------*/

    /* vars */
    DEFINE VARIABLE hDoc  AS HANDLE NO-UNDO.
    DEFINE VARIABLE hRoot AS HANDLE NO-UNDO.

    /* param */
    DEFINE INPUT PARAMETER pXml AS LONGCHAR NO-UNDO.
    DEFINE OUTPUT PARAMETER pResult AS HANDLE NO-UNDO.

    /* main */
    CREATE X-DOCUMENT hDoc.
    CREATE X-NODEREF hRoot.
    
    hDoc:LOAD("longchar", pXml, FALSE).
    hDoc:GET-DOCUMENT-ELEMENT(hRoot).
    
    RUN getElementsArray (INPUT hRoot, INPUT TRUE).
    
    RUN mountTempTable (INPUT "result",
        OUTPUT pResult).

END PROCEDURE.

/* _UIB-CODE-BLOCK-END */
&ANALYZE-RESUME

&ENDIF


&IF DEFINED(EXCLUDE-saveAndSendTask) = 0 &THEN

&ANALYZE-SUSPEND _UIB-CODE-BLOCK _PROCEDURE saveAndSendTask Procedure
PROCEDURE saveAndSendTask:
    /*------------------------------------------------------------------------------
     Purpose:
     Notes:
    ------------------------------------------------------------------------------*/

    // param 
    DEFINE INPUT PARAMETER companyId AS INTEGER NO-UNDO.
    DEFINE INPUT PARAMETER username AS CHARACTER NO-UNDO.
    DEFINE INPUT PARAMETER password AS CHARACTER NO-UNDO.
    DEFINE INPUT PARAMETER pProcessId AS INTEGER NO-UNDO.
    DEFINE INPUT PARAMETER pChoosedState AS INTEGER NO-UNDO.
    DEFINE INPUT PARAMETER pColleagueIds AS LONGCHAR NO-UNDO.
    DEFINE INPUT PARAMETER pMsg AS CHARACTER NO-UNDO.
    DEFINE INPUT PARAMETER pUserId AS CHARACTER  NO-UNDO.
    DEFINE INPUT PARAMETER pComplete AS LOGICAL NO-UNDO.
    DEFINE INPUT PARAMETER plAttachment AS LONGCHAR NO-UNDO.
    DEFINE INPUT PARAMETER plCardData AS LONGCHAR NO-UNDO.
    DEFINE INPUT PARAMETER plAppointment AS LONGCHAR NO-UNDO.
    DEFINE INPUT PARAMETER pManager AS LOGICAL NO-UNDO.
    DEFINE INPUT PARAMETER pThreadSequence AS INTEGER NO-UNDO.
    DEFINE OUTPUT PARAMETER pResult AS CHARACTER NO-UNDO.
    
    // main
    RUN saveAndSendTask IN hWorkflowEngineService(
        INPUT username, 
        INPUT password, 
        INPUT companyId, 
        INPUT pProcessId,
        INPUT pChoosedState, //choosed state
        INPUT pColleagueIds, //colleagueIds 
        INPUT pMsg, // comments
        INPUT pUserId, 
        INPUT pComplete, // ccompleteTask
        INPUT plAttachment, // anexo
        INPUT plCardData, // carddata
        INPUT plAppointment, // apontamento
        INPUT pManager, //managerMode
        INPUT pThreadSequence, //threadSequence
        OUTPUT presult).

    RETURN "OK":U.
    
    CATCH mySoapErrorObject AS Progress.Lang.SoapFaultError.
        DEFINE VARIABLE hSoupFault AS HANDLE NO-UNDO.

        hSoupFault = mySoapErrorObject:SoapFault.
    
        IF VALID-HANDLE(hSoupFault) THEN 
        DO:
            RUN generateErrorXML (hSoupFault:GET-SERIALIZED()).
        END.
        
        RETURN "NOK":U.
    END CATCH.

    CATCH myAnyError AS Progress.Lang.Error.
        RUN generateError(INPUT STRING(myAnyError:getMessageNum(1)),
            INPUT myAnyError:getMessage(1)).

        RETURN "NOK":U.
    END CATCH.

END PROCEDURE.
    
/* _UIB-CODE-BLOCK-END */
&ANALYZE-RESUME


&ENDIF


&IF DEFINED(EXCLUDE-saveAndSendTaskByReplacement) = 0 &THEN

&ANALYZE-SUSPEND _UIB-CODE-BLOCK _PROCEDURE saveAndSendTaskByReplacement Procedure
PROCEDURE saveAndSendTaskByReplacement:
    /*------------------------------------------------------------------------------
     Purpose:
     Notes: nÆo implementado ainda
    ------------------------------------------------------------------------------*/

    // vars
    
    // param
    DEFINE INPUT PARAMETER companyId AS INTEGER NO-UNDO.
    DEFINE INPUT PARAMETER username AS CHARACTER NO-UNDO.
    DEFINE INPUT PARAMETER password AS CHARACTER NO-UNDO.
    DEFINE INPUT PARAMETER pProcessId AS CHARACTER NO-UNDO.
    DEFINE INPUT PARAMETER pChoosedState AS INTEGER NO-UNDO.
    DEFINE INPUT PARAMETER pUser AS CHARACTER NO-UNDO.
    DEFINE INPUT PARAMETER pMsg AS CHARACTER NO-UNDO.
    
    DEFINE INPUT PARAMETER pUserId AS LONGCHAR NO-UNDO.
    DEFINE INPUT PARAMETER pComplete AS LOGICAL NO-UNDO.
    DEFINE INPUT PARAMETER plAttachment AS LONGCHAR NO-UNDO.
    DEFINE INPUT PARAMETER plCardData AS LONGCHAR NO-UNDO.
    
    DEFINE INPUT PARAMETER pAppointment AS LONGCHAR NO-UNDO.
    
    DEFINE INPUT PARAMETER pManager AS LOGICAL NO-UNDO.
    DEFINE INPUT PARAMETER pThreadSequence AS INTEGER NO-UNDO.
    DEFINE INPUT PARAMETER pReplacementId AS LONGCHAR NO-UNDO.
    DEFINE OUTPUT PARAMETER pResult AS CHARACTER NO-UNDO.
    
    // main
    

END PROCEDURE.
    
/* _UIB-CODE-BLOCK-END */
&ANALYZE-RESUME


&ENDIF


&IF DEFINED(EXCLUDE-simpleStartProcess) = 0 &THEN

&ANALYZE-SUSPEND _UIB-CODE-BLOCK _PROCEDURE simpleStartProcess Procedure
PROCEDURE simpleStartProcess:
    /*------------------------------------------------------------------------------
     Purpose:
     Notes:
    ------------------------------------------------------------------------------*/

    /* param */
    DEFINE INPUT PARAMETER companyId AS INTEGER NO-UNDO.
    DEFINE INPUT PARAMETER username AS CHARACTER NO-UNDO.
    DEFINE INPUT PARAMETER password AS CHARACTER NO-UNDO.
    DEFINE INPUT PARAMETER pProcessId AS CHARACTER NO-UNDO.
    DEFINE INPUT PARAMETER pMsg AS CHARACTER NO-UNDO.
    DEFINE INPUT PARAMETER plAttachment AS LONGCHAR NO-UNDO.
    DEFINE INPUT PARAMETER plCardData AS LONGCHAR NO-UNDO.
    DEFINE OUTPUT PARAMETER pResult AS CHARACTER NO-UNDO.

    // main

    RUN simpleStartProcess IN hWorkflowEngineService(
        INPUT username, 
        INPUT password, 
        INPUT companyId, 
        INPUT pProcessId, 
        INPUT pMsg, 
        INPUT plAttachment, 
        INPUT plCardData, 
        OUTPUT presult) NO-ERROR.
        
    RETURN "OK":U.
    
    CATCH mySoapErrorObject AS Progress.Lang.SoapFaultError.
        DEFINE VARIABLE hSoupFault AS HANDLE NO-UNDO.

        hSoupFault = mySoapErrorObject:SoapFault.
    
        IF VALID-HANDLE(hSoupFault) THEN 
        DO:
            RUN generateErrorXML (hSoupFault:GET-SERIALIZED()).
        END.
        
        RETURN "NOK":U.
    END CATCH.

    CATCH myAnyError AS Progress.Lang.Error.
        RUN generateError(INPUT STRING(myAnyError:getMessageNum(1)),
            INPUT myAnyError:getMessage(1)).

        RETURN "NOK":U.
    END CATCH.

END PROCEDURE.
    
/* _UIB-CODE-BLOCK-END */
&ANALYZE-RESUME


&ENDIF


&IF DEFINED(EXCLUDE-startProcess) = 0 &THEN

&ANALYZE-SUSPEND _UIB-CODE-BLOCK _PROCEDURE startProcess Procedure 
PROCEDURE startProcess :
    /*------------------------------------------------------------------------------
      Purpose:     
      Parameters:  <none>
      Notes:       
    ------------------------------------------------------------------------------*/

    /* vars */    
    DEFINE VARIABLE processId    AS CHARACTER NO-UNDO.
    DEFINE VARIABLE choosedState AS INTEGER   NO-UNDO.
    DEFINE VARIABLE colleagueIds AS CHARACTER EXTENT 1 NO-UNDO.
    DEFINE VARIABLE comments     AS CHARACTER NO-UNDO.
    DEFINE VARIABLE userId1      AS CHARACTER NO-UNDO.
    DEFINE VARIABLE completeTask AS LOGICAL   NO-UNDO.
    DEFINE VARIABLE attachments  AS LONGCHAR  NO-UNDO.
    DEFINE VARIABLE cardData     AS LONGCHAR  NO-UNDO.
    DEFINE VARIABLE managerMode  AS LOGICAL   NO-UNDO.

    DEFINE VARIABLE lcErro       AS LONGCHAR  NO-UNDO.
    DEFINE VARIABLE cRet         AS CHARACTER NO-UNDO.
    
    /* param */
    DEFINE INPUT PARAMETER companyId AS INTEGER NO-UNDO.
    DEFINE INPUT PARAMETER username AS CHARACTER NO-UNDO.
    DEFINE INPUT PARAMETER password AS CHARACTER NO-UNDO.
    DEFINE INPUT PARAMETER pProcessId AS CHARACTER NO-UNDO.
    DEFINE INPUT PARAMETER pChoosedState AS INTEGER NO-UNDO.
    DEFINE INPUT PARAMETER pAprovador AS CHARACTER NO-UNDO.
    DEFINE INPUT PARAMETER pMsg AS CHARACTER NO-UNDO.
    DEFINE INPUT PARAMETER pUser AS CHARACTER NO-UNDO.
    DEFINE INPUT PARAMETER pComplete AS LOGICAL NO-UNDO.
    DEFINE INPUT PARAMETER plAttachment AS LONGCHAR NO-UNDO.
    DEFINE INPUT PARAMETER plCardData AS LONGCHAR NO-UNDO.
    DEFINE INPUT PARAMETER pManager AS LOGICAL NO-UNDO.
    DEFINE OUTPUT PARAMETER pResult AS CHARACTER NO-UNDO.
                       
    /* main */
    DO ON ERROR UNDO,THROW:
    
        ASSIGN 
            colleagueIds = pAprovador
            comments     = pMsg
            cardData     = ?
            attachments  = plAttachment
            cardData     = plCardData.

        RUN startProcess IN hWorkflowEngineService(INPUT username, 
            INPUT password, 
            INPUT companyId, 
            INPUT pProcessId, 
            INPUT pChoosedState, 
            INPUT colleagueIds, 
            INPUT comments, 
            INPUT pUser, 
            INPUT pComplete, 
            INPUT attachments, 
            INPUT cardData, 
            INPUT DATASET appointment, 
            INPUT managerMode, 
            OUTPUT presult) NO-ERROR.
        
        cRet = "OK":U.
    END.

    CATCH mySoapErrorObject AS Progress.Lang.SoapFaultError.
        DEFINE VARIABLE hSoupFault AS HANDLE NO-UNDO.

        hSoupFault = mySoapErrorObject:SoapFault.
    
        IF VALID-HANDLE(hSoupFault) THEN 
        DO:
            lcErro = hSoupFault:GET-SERIALIZED().
            RUN generateErrorXML (lcErro).
        END.

        cRet = "NOK":U.
    END CATCH.

    CATCH myAnyError AS Progress.Lang.Error.
        RUN generateError(INPUT STRING(myAnyError:getMessageNum(1)),
            INPUT myAnyError:getMessage(1)).

        cRet = "NOK":U.
    END CATCH.

    FINALLY:
        RETURN cRet.
    END FINALLY.

END PROCEDURE.

/* _UIB-CODE-BLOCK-END */
&ANALYZE-RESUME

&ENDIF

