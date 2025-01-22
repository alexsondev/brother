
/*------------------------------------------------------------------------
    File        : cardService.p
    Purpose     : 

    Syntax      :

    Description : API integração Card fluig

    Author(s)   : TIB
    Created     : Thu Dec 08 13:42:31 BRST 2022
    Notes       :
  ----------------------------------------------------------------------*/

/* ***************************  Definitions  ************************** */

BLOCK-LEVEL ON ERROR UNDO, THROW.

USING Progress.Json.ObjectModel.*.

{wsfluig/manageErrors.i}
{wsfluig/readXML.i}

DEFINE VARIABLE hWebService      AS HANDLE  NO-UNDO.
DEFINE VARIABLE hCardDataService AS HANDLE  NO-UNDO.
DEFINE VARIABLE lOk              AS LOGICAL NO-UNDO.

DEFINE TEMP-TABLE ttItem NO-UNDO
    XML-NODE-NAME "item"
    FIELD campo AS CHARACTER XML-NODE-NAME "field" 
    FIELD valor AS CHARACTER XML-NODE-NAME "value".

DEFINE DATASET dsItem FOR ttItem.

/* ********************  Preprocessor Definitions  ******************** */


/* ***************************  Main Block  *************************** */



/* **********************  Internal Procedures  *********************** */

PROCEDURE connectWS:
    /*------------------------------------------------------------------------------
     Purpose:
     Notes:
    ------------------------------------------------------------------------------*/

    /* param */
    DEFINE INPUT PARAMETER pUrl AS CHARACTER NO-UNDO.

    /* main */
    {wsfluig/connect.i ECMCardService CardService hWebService hCardDataService pURL}


END PROCEDURE.

PROCEDURE updateCardData:
    /*------------------------------------------------------------------------------
     Purpose:
     Notes:
    ------------------------------------------------------------------------------*/

    // var
    DEFINE VARIABLE i-cont     AS INTEGER  NO-UNDO.
    DEFINE VARIABLE lcCard     AS LONGCHAR NO-UNDO.
    DEFINE VARIABLE lcResult   AS LONGCHAR NO-UNDO.
    
    DEFINE VARIABLE hSoapFault AS HANDLE   NO-UNDO.
    DEFINE VARIABLE lcErro     AS LONGCHAR NO-UNDO.
    
    // param
    DEFINE INPUT PARAMETER pEmpresa AS INTEGER NO-UNDO.
    DEFINE INPUT PARAMETER pUsuario AS CHARACTER NO-UNDO.
    DEFINE INPUT PARAMETER pSenha AS CHARACTER NO-UNDO.
    DEFINE INPUT PARAMETER pCardId AS INTEGER NO-UNDO.
    DEFINE INPUT PARAMETER pCard AS JsonArray NO-UNDO.
    DEFINE OUTPUT PARAMETER pResult AS LONGCHAR NO-UNDO.
    
    // main
    DO i-cont = 1 TO pCard:LENGTH.
    
        CREATE ttItem.
        ASSIGN 
            ttItem.campo = pCard:GetJsonObject(i-cont):GetCharacter("field")
            ttItem.valor = pCard:GetJsonObject(i-cont):GetCharacter("value").
    END.
    
    DATASET dsItem:WRITE-XML ("longchar", lcCard, FALSE).
    
    RUN updateCardData IN hCardDataService (
        INPUT pEmpresa,
        INPUT pUsuario,
        INPUT pSenha,
        INPUT pCardId,
        INPUT lcCard,
        OUTPUT lcResult).
        
    IF TRIM(STRING(lcResult)) = "" THEN
        UNDO, THROW NEW Progress.Lang.AppError("Erro ao atualizar campo", 17006).       
    ELSE
        pResult = lcResult.
        
    RETURN "OK":U.
    
    CATCH mySoapErrorObject AS Progress.Lang.SoapFaultError.

        hSoapFault = mySoapErrorObject:SoapFault.

        IF VALID-HANDLE(hSoapFault:SOAP-FAULT-DETAIL) THEN 
        DO:
            lcErro = hSoapFault:SOAP-FAULT-DETAIL:GET-SERIALIZED().
            RUN generateErrorXML(INPUT lcErro).
        END.

        DELETE OBJECT mySoapErrorObject.
        
        RETURN "NOK":U.

    END CATCH.

    CATCH mySystemErrorObject AS Progress.Lang.Error.
            
        RUN generateError (
            INPUT STRING(mySystemErrorObject:getMessageNum(1)),
            INPUT mySystemErrorObject:getMessage(1)).    

        DELETE OBJECT mySystemErrorObject.
        
        RETURN "NOK":U.
        
    END CATCH.

END PROCEDURE.

