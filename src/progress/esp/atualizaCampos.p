
/*------------------------------------------------------------------------
    File        : atualizaCampos.p
    Purpose     : 

    Syntax      :

    Description : 

    Author(s)   : flavio
    Created     : Sat Feb 24 23:23:18 BRT 2024
    Notes       :
  ----------------------------------------------------------------------*/

/* ***************************  Definitions  ************************** */

BLOCK-LEVEL ON ERROR UNDO, THROW.

USING Progress.Json.ObjectModel.*.

DEFINE TEMP-TABLE tt-erro
    FIELD i-sequen AS INTEGER
    FIELD cd-erro  AS INTEGER
    FIELD mensagem AS CHARACTER.

DEFINE TEMP-TABLE cardData XML-NODE-NAME "item"  
    FIELD codCampo AS CHARACTER XML-NODE-NAME "item"
    FIELD desCampo AS CHARACTER XML-NODE-NAME "item".

DEFINE TEMP-TABLE updateCardData XML-NODE-NAME "cardData"  
    FIELD codCampo AS CHARACTER XML-NODE-NAME "field"
    FIELD desCampo AS CHARACTER XML-NODE-NAME "value".
  
DEFINE TEMP-TABLE ext-param-global
    FIELD url     AS CHARACTER
    FIELD usuario AS CHARACTER
    FIELD senha   AS CHARACTER.
    
/* ********************  Preprocessor Definitions  ******************** */


/* ***************************  Main Block  *************************** */
CREATE ext-param-global.
ASSIGN
    ext-param-global.url     = "https://fluigteste.brother.com.br/webdesk"
    ext-param-global.usuario = "csilva"
    ext-param-global.senha   = "br123456". 
    
RUN atualizarCard (102416).    

/* **********************  Internal Procedures  *********************** */

PROCEDURE getUpdateCardData:
    /*------------------------------------------------------------------------------
     Purpose:
     Notes:
    ------------------------------------------------------------------------------*/

    // vars
    DEFINE VARIABLE lOK   AS LOGICAL    NO-UNDO.
    DEFINE VARIABLE oJson AS JsonObject NO-UNDO.    
    
    /* param */
    DEFINE OUTPUT PARAMETER pCardData AS JsonArray NO-UNDO.
    
    /* main */
    pCardData = NEW JsonArray().
    
    oJson = NEW JsonObject().
    oJson:add("field", "envioEvidenciasConcluido").
    oJson:add("value", "true").
    pCardData:add(oJson).
    
    oJson = NEW JsonObject().
    oJson:add("field", "itemSellout_qtdEvidencia___1").
    oJson:add("value", "987").
    pCardData:add(oJson).
   
END PROCEDURE.

PROCEDURE pi-erro:
/*------------------------------------------------------------------------------
 Purpose:
 Notes:
------------------------------------------------------------------------------*/


END PROCEDURE.




PROCEDURE atualizarCard.
    /*------------------------------------------------------------------------------
            Purpose:                                                                      
            Notes:                                                                        
    ------------------------------------------------------------------------------*/

    /* vars */
    DEFINE VARIABLE hFluig    AS HANDLE    NO-UNDO.
    DEFINE VARIABLE lOk       AS LOGICAL   NO-UNDO.
    //DEFINE VARIABLE lcCardData AS LONGCHAR  NO-UNDO.
    DEFINE VARIABLE cResult   AS LONGCHAR  NO-UNDO.
    DEFINE VARIABLE hResult   AS HANDLE    NO-UNDO.
    DEFINE VARIABLE cValue    AS CHARACTER NO-UNDO.
    DEFINE VARIABLE oCardData AS JsonArray NO-UNDO.
    
    // param
    DEFINE INPUT PARAMETER iDocto AS INTEGER NO-UNDO.
    
    /* main */
    RUN wsFluig/cardService.p PERSISTENT SET hFluig.
    RUN connectWS IN hFluig (INPUT ext-param-global.url).
    
    MESSAGE "conectou?"
        VIEW-AS ALERT-BOX.
    
    IF RETURN-VALUE = "NOK":U THEN 
    DO:
        RUN getTtErrors IN hFluig (OUTPUT TABLE tt-erro).
    
        RETURN "NOK":U.
    END.
    
    RUN getUpdateCardData (OUTPUT oCardData).
    
    MESSAGE STRING (oCardData:GetJsonText())
        VIEW-AS ALERT-BOX.
    
    /* cardService */
    RUN updateCardData IN hFluig(
        INPUT 1, 
        INPUT ext-param-global.usuario, 
        INPUT ext-param-global.senha, 
        INPUT iDocto, 
        INPUT oCardData, 
        OUTPUT cResult).

    MESSAGE STRING (cResult)
        VIEW-AS ALERT-BOX.
    
    IF cResult <> "" THEN 
    DO: 
        
              
    END.
    ELSE 
    DO:
        MESSAGE "deu erro"
            VIEW-AS ALERT-BOX.        
    END.
    
    DELETE PROCEDURE hfluig.
  
    /* fim */
    RETURN "OK":U.
     
END PROCEDURE.

