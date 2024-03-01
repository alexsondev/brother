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

/* ***************************  Definitions  ************************** */
DEFINE VARIABLE hWebService AS HANDLE NO-UNDO.
DEFINE VARIABLE hColleagueService AS HANDLE NO-UNDO.
DEFINE VARIABLE lOk AS LOGICAL     NO-UNDO.

{wsfluig/manageErrors.i}

DEFINE TEMP-TABLE ttResult XML-NODE-NAME "result"
    FIELD ACTIVE AS CHARACTER
    FIELD adminUser AS CHARACTER
    FIELD COLleagueId AS CHARACTER
    FIELD COLleagueName AS CHARACTER
    FIELD companyId AS CHARACTER
    FIELD defaultLanguage AS CHARACTER
    FIELD dialectId AS CHARACTER
    FIELD ecmVersion AS CHARACTER
    FIELD emailHtml AS CHARACTER
    FIELD gedUser AS CHARACTER
    FIELD login AS CHARACTER
    FIELD mail AS CHARACTER
    FIELD menuConfig AS CHARACTER
    FIELD passwd AS CHARACTER
    FIELD ROWID AS CHARACTER.

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
         HEIGHT             = 5.04
         WIDTH              = 60.
/* END WINDOW DEFINITION */
                                                                        */
&ANALYZE-RESUME

 


&ANALYZE-SUSPEND _UIB-CODE-BLOCK _CUSTOM _MAIN-BLOCK Procedure 


/* ***************************  Main Block  *************************** */

/* _UIB-CODE-BLOCK-END */
&ANALYZE-RESUME


/* **********************  Internal Procedures  *********************** */

&IF DEFINED(EXCLUDE-getColleaguesMail) = 0 &THEN

&ANALYZE-SUSPEND _UIB-CODE-BLOCK _PROCEDURE getColleaguesMail Procedure 
PROCEDURE getColleaguesMail :
/*------------------------------------------------------------------------------
  Purpose:     
  Parameters:  <none>
  Notes:       
------------------------------------------------------------------------------*/

    /* vars */
    DEFINE VARIABLE cRet AS CHARACTER   NO-UNDO.
    DEFINE VARIABLE lcErro AS LONGCHAR NO-UNDO.
    DEFINE VARIABLE RESULT AS LONGCHAR NO-UNDO.

    /* param */
    DEFINE INPUT PARAMETER companyId AS CHARACTER NO-UNDO.
    DEFINE INPUT PARAMETER username AS CHARACTER NO-UNDO.
    DEFINE INPUT PARAMETER password AS CHARACTER NO-UNDO.
    DEFINE INPUT PARAMETER pCodUsuario AS CHARACTER NO-UNDO.
    DEFINE OUTPUT PARAMETER pResult AS LONGCHAR NO-UNDO.

    /* main */
    DO ON ERROR UNDO,THROW:

        FIND usuar_mestre NO-LOCK
            WHERE usuar_mestre.cod_usuario = pCodUsuario
            NO-ERROR.
        
        RUN getColleaguesMail IN hColleagueService (INPUT username,
                                                    INPUT password,
                                                    INPUT companyId,
                                                    INPUT usuar_mestre.cod_e_mail_local,
                                                    OUTPUT result).

        pResult = result.
    END.

    CATCH mySoapErrorObject AS Progress.Lang.SoapFaultError.
        DEFINE VARIABLE hSoupFault AS HANDLE      NO-UNDO.

        hSoupFault = mySoapErrorObject:SoapFault.
    
        IF VALID-HANDLE(hSoupFault) THEN DO:
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
    {wsfluig/connect.i ECMColleagueService ColleagueService hWebService hColleagueService pURL}

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
    DEFINE VARIABLE lOk AS LOGICAL     NO-UNDO.

    /* param */
    DEFINE INPUT PARAMETER pXml AS LONGCHAR NO-UNDO.
    DEFINE OUTPUT PARAMETER pResult AS HANDLE NO-UNDO.

    /* main */
    lOk = TEMP-TABLE ttResult:READ-XML("LONGCHAR", pXml, "EMPTY", ?, ? ,?).

    pResult = TEMP-TABLE ttResult:HANDLE.

END PROCEDURE.

/* _UIB-CODE-BLOCK-END */
&ANALYZE-RESUME

&ENDIF


