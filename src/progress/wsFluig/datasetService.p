&ANALYZE-SUSPEND _VERSION-NUMBER AB_v10r12
&ANALYZE-RESUME
&ANALYZE-SUSPEND _UIB-CODE-BLOCK _CUSTOM _DEFINITIONS Procedure 
/*------------------------------------------------------------------------
    File        : 
    Purpose     :

    Syntax      :

    Description :

    Author(s)   :
    Created     :
    Notes       :
  ----------------------------------------------------------------------*/
/*          This .W file was created with the Progress AppBuilder.      */
/*----------------------------------------------------------------------*/
&ANALYZE-SUSPEND _UIB-CODE-BLOCK _CUSTOM _DECLARATIONS Procedure
USING Progress.Json.*.
USING Progress.Json.ObjectModel.* FROM PROPATH.
&ANALYZE-RESUME

/* ***************************  Definitions  ************************** */
DEFINE VARIABLE hWebService     AS HANDLE  NO-UNDO.
DEFINE VARIABLE hDatasetService AS HANDLE  NO-UNDO.
DEFINE VARIABLE lOk             AS LOGICAL NO-UNDO.

DEFINE TEMP-TABLE ttItem NO-UNDO
    NAMESPACE-URI "" 
    XML-NODE-NAME "item"
    FIELD contraintType AS CHARACTER 
    FIELD fieldName     AS CHARACTER 
    FIELD finalValue    AS CHARACTER 
    FIELD initialValue  AS CHARACTER 
    FIELD clike         AS CHARACTER XML-NODE-NAME "likeSearch".

DEFINE DATASET constraints NAMESPACE-URI "http://ws.dataservice.ecm.technology.totvs.com/" 
    FOR ttItem.

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
         HEIGHT             = 5.17
         WIDTH              = 60.
/* END WINDOW DEFINITION */
                                                                        */
&ANALYZE-RESUME

 


&ANALYZE-SUSPEND _UIB-CODE-BLOCK _CUSTOM _MAIN-BLOCK Procedure 


/* ***************************  Main Block  *************************** */

/* _UIB-CODE-BLOCK-END */
&ANALYZE-RESUME


/* **********************  Internal Procedures  *********************** */

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
    {wsfluig/connect.i ECMDatasetService DatasetService hWebService hDatasetService pURL}

END PROCEDURE.

/* _UIB-CODE-BLOCK-END */
&ANALYZE-RESUME

&ENDIF

&IF DEFINED(EXCLUDE-getDataset) = 0 &THEN

&ANALYZE-SUSPEND _UIB-CODE-BLOCK _PROCEDURE getDataset Procedure 
PROCEDURE getDataset :
    /*------------------------------------------------------------------------------
      Purpose:     
      Parameters:  <none>
      Notes:       
    ------------------------------------------------------------------------------*/
    /* vars */
    DEFINE VARIABLE lRet        AS CHARACTER  NO-UNDO.
    DEFINE VARIABLE hSoapFault  AS HANDLE     NO-UNDO.
    DEFINE VARIABLE lcErro      AS LONGCHAR   NO-UNDO.
    DEFINE VARIABLE jConstraint AS JsonObject NO-UNDO.
    
    /* param */
    DEFINE INPUT PARAMETER pCompanyId AS INTEGER NO-UNDO.
    DEFINE INPUT PARAMETER pUserName AS CHARACTER NO-UNDO.
    DEFINE INPUT PARAMETER pPass AS CHARACTER NO-UNDO.
    DEFINE INPUT PARAMETER pName AS CHARACTER NO-UNDO.
    DEFINE INPUT PARAMETER pCampos AS CHARACTER   NO-UNDO.
    DEFINE INPUT PARAMETER pConstraint AS JsonArray NO-UNDO. 
    DEFINE INPUT PARAMETER pOrdem AS CHARACTER   NO-UNDO.
    DEFINE OUTPUT PARAMETER dataset1 AS LONGCHAR NO-UNDO.

    /* main */  
    DO ON ERROR UNDO, THROW:
        
        IF VALID-OBJECT (pConstraint) THEN 
        DO:
            DO iCont = 1 TO pConstraint:LENGTH.
            
                jConstraint = pConstraint:GetJsonObject(icont).
            
                CREATE ttItem.
                ASSIGN 
                    ttItem.contraintType = jConstraint:GetCharacter("constraintType")
                    ttItem.fieldName     = jConstraint:GetCharacter("fieldName")
                    ttItem.initialValue  = jConstraint:GetCharacter("initialValue")
                    ttItem.finalValue    = jConstraint:GetCharacter("finalValue")
                    ttitem.clike         = jConstraint:GetCharacter("likeSearch").
                
            END.        
        END.
        

        RUN getDataset IN hDatasetService (
            INPUT pCompanyId, 
            INPUT pUserName, 
            INPUT pPass, 
            INPUT pName, 
            INPUT pCampos, 
            INPUT DATASET constraints, 
            INPUT pOrdem, 
            OUTPUT dataset1).

        //COPY-LOB dataset1 TO FILE "c:\temp\xml.xml".
        

        IF TRIM(STRING(dataset1)) = "" THEN 
        DO:
            lRet = "NOK":U.
            RUN generateError (INPUT "17006",
                INPUT "Seleá∆o n∆o retornou registros").    
        END.
        ELSE
            lRet = "OK":U.
    END.

    CATCH mySoapErrorObject AS Progress.Lang.SoapFaultError.

        hSoapFault = mySoapErrorObject:SoapFault.

        IF VALID-HANDLE(hSoapFault:SOAP-FAULT-DETAIL) THEN 
        DO:
            lcErro = hSoapFault:SOAP-FAULT-DETAIL:GET-SERIALIZED().
            RUN generateErrorXML(INPUT lcErro).
        END.

        lRet = "NOK":U.
        DELETE OBJECT mySoapErrorObject.

    END CATCH.

    CATCH mySystemErrorObject AS Progress.Lang.Error.
            
        RUN generateError (INPUT STRING(mySystemErrorObject:getMessageNum(1)),
            INPUT mySoapErrorObject:getMessage(1))    

            lRet = "NOK":U.
        DELETE OBJECT mySystemErrorObject.
    END CATCH.

    FINALLY:
        RETURN lRet.
    END FINALLY.

END PROCEDURE.

/* _UIB-CODE-BLOCK-END */
&ANALYZE-RESUME

&ENDIF

&IF DEFINED(EXCLUDE-getJsonDataset) = 0 &THEN

&ANALYZE-SUSPEND _UIB-CODE-BLOCK _PROCEDURE getJsonDataset Procedure
PROCEDURE getJsonDataset:
/*------------------------------------------------------------------------------
 Purpose:
 Notes:
------------------------------------------------------------------------------*/
    /* vars */
    
    /* param */
    DEFINE INPUT PARAMETER pXml AS LONGCHAR NO-UNDO.
    DEFINE INPUT PARAMETER pName AS CHARACTER NO-UNDO.
    DEFINE OUTPUT PARAMETER pDataset AS LONGCHAR NO-UNDO.

    /* main */
    RUN mountJsonDataset (
        INPUT pXML,
        OUTPUT pDataSet).

    RETURN RETURN-VALUE.


END PROCEDURE.
    
/* _UIB-CODE-BLOCK-END */
&ANALYZE-RESUME


&ENDIF


&IF DEFINED(EXCLUDE-getTableDataset) = 0 &THEN

&ANALYZE-SUSPEND _UIB-CODE-BLOCK _PROCEDURE 1 Procedure 
PROCEDURE getTableDataset :
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
    DEFINE INPUT PARAMETER pName AS CHARACTER NO-UNDO.
    DEFINE OUTPUT PARAMETER pDataset AS HANDLE NO-UNDO.

    /* main */
    CREATE X-DOCUMENT hDoc.
    CREATE X-NODEREF hRoot.
    
    hDoc:LOAD("longchar", pXml, FALSE).
    hDoc:GET-DOCUMENT-ELEMENT(hRoot).
    
    RUN getElements (INPUT hRoot).
    
    RUN mountTempTable (
        INPUT pName,
        OUTPUT pDataSet).

    RETURN RETURN-VALUE.
    
END PROCEDURE.

/* _UIB-CODE-BLOCK-END */
&ANALYZE-RESUME

&ENDIF


&IF DEFINED(EXCLUDE-setConstraints) = 0 &THEN

&ANALYZE-SUSPEND _UIB-CODE-BLOCK _PROCEDURE setConstraints Procedure 
PROCEDURE setConstraints :
    /*------------------------------------------------------------------------------
      Purpose:     
      Parameters:  <none>
      Notes:       
    ------------------------------------------------------------------------------*/

    /* vars */
    /* param */
    DEFINE INPUT PARAMETER pConstType AS LONGCHAR NO-UNDO.
    DEFINE INPUT PARAMETER pField AS CHARACTER NO-UNDO.
    DEFINE INPUT PARAMETER pIni AS CHARACTER NO-UNDO.
    DEFINE INPUT PARAMETER pFim AS CHARACTER NO-UNDO.
    DEFINE INPUT PARAMETER pZera AS LOGICAL NO-UNDO.

    /* main */
    IF pZera THEN 
        EMPTY TEMP-TABLE ttItem.

    CREATE ttItem.
    ASSIGN 
        ttItem.contraintType = pConstType
        ttItem.fieldName     = pField
        ttItem.initialValue  = pIni
        ttItem.finalValue    = pFim.

    
END PROCEDURE.

/* _UIB-CODE-BLOCK-END */
&ANALYZE-RESUME

&ENDIF
