
/*------------------------------------------------------------------------
    File        : buscaProcessosDataset.p
    Purpose     : 

    Syntax      :

    Description :         

    Author(s)   : ronil
    Created     : Sun Feb 25 13:51:22 BRT 2024
    Notes       :
  ----------------------------------------------------------------------*/

/* ***************************  Definitions  ************************** */

BLOCK-LEVEL ON ERROR UNDO, THROW.

USING Progress.Json.ObjectModel.* FROM PROPATH.

{utp/ut-glob.i}
{method/dbotterr.i}

DEFINE TEMP-TABLE ext-param-global
    FIELD url      AS CHARACTER
    FIELD usuario  AS CHARACTER
    FIELD senha    AS CHARACTER
    FIELD processo AS CHARACTER
    FIELD tarefa   AS INTEGER.

DEFINE TEMP-TABLE tt-erro
    FIELD i-sequen AS INTEGER
    FIELD cd-erro  AS INTEGER
    FIELD mensagem AS CHARACTER.

DEFINE TEMP-TABLE xPath NO-UNDO
    FIELD xPath  AS CHARACTER FORMAT "x(60)"
    FIELD xValue AS CHARACTER
    FIELD order  AS INTEGER
    FIELD atrib  AS CHARACTER
    INDEX idx IS PRIMARY order.    

/* ********************  Preprocessor Definitions  ******************** */


/* ***************************  Main Block  *************************** */

CREATE ext-param-global.
ASSIGN
    ext-param-global.url      = "https://fluigteste.brother.com.br/webdesk"
    ext-param-global.usuario  = "csilva"
    ext-param-global.senha    = "br123456"
    ext-param-global.processo = "ds_workflow_tasks" 
    ext-param-global.tarefa   = 62.


RUN pi-buscar.

FOR EACH rowErrors.
    MESSAGE errorDescription
        VIEW-AS ALERT-BOX.
END.    




/* **********************  Internal Procedures  *********************** */

PROCEDURE pi-buscar:
    /*------------------------------------------------------------------------------
     Purpose:
     Notes:
    ------------------------------------------------------------------------------*/
    /* vars */
    DEFINE VARIABLE hFluig     AS HANDLE    NO-UNDO.
    DEFINE VARIABLE lOk        AS LOGICAL   NO-UNDO.
    DEFINE VARIABLE cResult    AS LONGCHAR  NO-UNDO.
    DEFINE VARIABLE hResult    AS HANDLE    NO-UNDO.
    DEFINE VARIABLE iErro      AS INTEGER   NO-UNDO.
    DEFINE VARIABLE c-user     AS CHARACTER NO-UNDO.
    DEFINE VARIABLE oJsonArray AS JsonArray NO-UNDO.
    DEFINE VARIABLE lcResult   AS LONGCHAR  NO-UNDO.
    
    /* Workflow Service */
    FIND FIRST ext-param-global NO-LOCK NO-ERROR.
    
    RUN wsFluig/datasetService.p PERSISTENT SET hFluig.
    RUN connectWS IN hFluig (INPUT ext-param-global.url).
    
    IF RETURN-VALUE = "NOK":U THEN 
    DO:
        RUN getTtErrors IN hFluig (OUTPUT TABLE tt-erro).
        
        FOR EACH tt-erro.
            CREATE rowErrors.
            ASSIGN 
                rowErrors.errorNumber      = tt-erro.cd-erro
                rowErrors.errorDescription = tt-erro.mensagem.
        END.
    
        UNDO, THROW NEW Progress.Lang.AppError ("Erro conexao fluig", 17006).
    END.
    
    
    
    oJsonArray = NEW JsonArray().
    
    // rodar dataset para buscar o user id do processo
    RUN getDataset IN hFluig (
        INPUT 1,
        INPUT ext-param-global.usuario,
        INPUT ext-param-global.senha,
        INPUT ext-param-global.processo,
        INPUT "",
        INPUT oJsonArray,
        INPUT "",
        OUTPUT cResult).

    /* testar erro */
    iErro = INDEX(STRING(cResult), "ERRO", 1).
    IF RETURN-VALUE = "NOK":U OR 
        iErro > 0  OR 
        STRING(cResult) = "" OR
        CAN-FIND (FIRST tt-erro) THEN
        UNDO, THROW NEW Progress.Lang.AppError ("Erro ao movimentar tarefa: " + STRING (cResult), 17006).
    
    IF RETURN-VALUE = "NOK":u THEN 
        UNDO, THROW NEW Progress.Lang.AppError("Atividade fluig movimentada, porem nao atualizou o item corrente do contrato", 17006).
        
    //RUN readXML IN hFluig (cResult).
    
    //RUN getXPath IN hFluig (OUTPUT table xPath).
    
    FOR EACH xPath.
        DISPLAY xPath.xPath FORMAT "x(30)"
            xPath.xValue FORMAT "x(30)"
            xPath.order FORMAT "9999"
            xPath.atrib FORMAT "x(10)"
            WITH WIDTH 220.
    END.
    
    RUN mountJsonDataset IN hFluig (cResult, OUTPUT lcResult).
    COPY-LOB lcResult TO FILE SESSION:TEMP-DIR + "dataset.json".
    DOS SILENT START VALUE (SESSION:TEMP-DIR + "dataset.json").
            
    DELETE PROCEDURE hfluig.
    
    /* fim */
    RETURN "OK":U.

    CATCH e AS Progress.Lang.Error:
        
        DEFINE VARIABLE iCont AS INTEGER NO-UNDO.
        DO iCont = 1 TO e:NumMessages.
        
            CREATE rowErrors.
            ASSIGN 
                rowErrors.errorNumber      = e:GetMessageNum(iCont)
                rowErrors.errorDescription = "pi-integrarFluig: " + e:GetMessage(iCont).
        END.
        
        RETURN "NOK":U.
    END CATCH.

END PROCEDURE.
    