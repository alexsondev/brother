
/*------------------------------------------------------------------------
    File        : enviarTarefa.p
    Purpose     : 

    Syntax      :

    Description : 

    Author(s)   : flavio
    Created     : Sun Feb 25 00:25:27 BRT 2024
    Notes       :
  ----------------------------------------------------------------------*/

/* ***************************  Definitions  ************************** */

BLOCK-LEVEL ON ERROR UNDO, THROW.

USING Progress.Json.ObjectModel.* FROM PROPATH.

DEFINE VARIABLE c-erro AS CHARACTER NO-UNDO.
{utp/ut-glob.i}
{method/dbotterr.i}

DEFINE TEMP-TABLE ext-param-global
    FIELD url      AS CHARACTER
    FIELD usuario  AS CHARACTER
    FIELD senha    AS CHARACTER
    FIELD processo AS integer
    FIELD tarefa AS INTEGER.

DEFINE TEMP-TABLE cardData XML-NODE-NAME "cardData"  
    FIELD codCampo AS CHARACTER XML-NODE-NAME "item"
    FIELD desCampo AS CHARACTER XML-NODE-NAME "item".
    
DEFINE TEMP-TABLE tt-erro
    FIELD i-sequen AS INTEGER
    FIELD cd-erro  AS INTEGER
    FIELD mensagem AS CHARACTER.
    
DEFINE TEMP-TABLE tt-result
    FIELD iTask      AS CHARACTER
    FIELD WDNrVersao AS CHARACTER
    FIELD WDNrDocto  AS CHARACTER
    FIELD iProcess   AS CHARACTER
    FIELD cDestino   AS CHARACTER.
    

/* ********************  Preprocessor Definitions  ******************** */


/* ************************  Function Prototypes ********************** */

/* ***************************  Main Block  *************************** */
CREATE ext-param-global.
ASSIGN
    ext-param-global.url      = "https://fluigteste.brother.com.br/webdesk"
    ext-param-global.usuario  = "csilva"
    ext-param-global.senha    = "br123456"
    ext-param-global.processo = 36726
    ext-param-global.tarefa   = 62.
    
RUN pi-movimentaFluig (output c-erro).

MESSAGE "erro " c-erro
VIEW-AS ALERT-BOX.

for each rowErrors.
    MESSAGE errorDescription
    VIEW-AS ALERT-BOX.
end.    

/* **********************  Internal Procedures  *********************** */

PROCEDURE pi-movimentaFluig:
    /*------------------------------------------------------------------------------
     Purpose:
     Notes:
    ------------------------------------------------------------------------------*/
    /* vars */
    DEFINE VARIABLE hFluig     AS HANDLE    NO-UNDO.
    DEFINE VARIABLE lOk        AS LOGICAL   NO-UNDO.
    DEFINE VARIABLE lcCardData AS LONGCHAR  NO-UNDO.
    DEFINE VARIABLE cResult    AS LONGCHAR  NO-UNDO.
    DEFINE VARIABLE hResult    AS HANDLE    NO-UNDO.
    DEFINE VARIABLE iErro      AS INTEGER   NO-UNDO.
    
    DEFINE VARIABLE c-user     AS CHARACTER NO-UNDO.
    
    // output
    def output param c-erro as char no-undo.
    
    /* Workflow Service */
    FIND FIRST ext-param-global NO-LOCK NO-ERROR.
    
    RUN wsFluig/workflowEngineService.p PERSISTENT SET hFluig.
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
    
    // rodar dataset para buscar o user id do processo
    c-user = "csilva".
    IF c-user = "" THEN 
        UNDO, THROW NEW Progress.Lang.AppError ("Erro enviar tarefa", 17006). 
    
    
    MESSAGE ext-param-global.processo skip ext-param-global.tarefa 
    VIEW-AS ALERT-BOX.
    
    RUN saveAndSendTask IN hFluig (
        INPUT 1,
        INPUT ext-param-global.usuario,
        INPUT ext-param-global.senha,
        INPUT ext-param-global.processo,
        INPUT ext-param-global.tarefa,
        INPUT "",                                   //"Pool:Role:AM-Marketing",
        INPUT "Movimentar automatico",
        INPUT c-user,
        INPUT TRUE, // completar
        INPUT "", // atachment
        INPUT "", // cardData
        INPUT "", // appointment
        INPUT true, // manager
        INPUT 0, // thread
        OUTPUT cResult).

        MESSAGE "retorno: " string (cResult)
        VIEW-AS ALERT-BOX.
        
    /* testar erro */
    iErro = INDEX(STRING(cResult), "ERRO", 1).
    IF RETURN-VALUE = "NOK":U OR 
        iErro > 0  OR 
        STRING(cResult) = "" OR
        CAN-FIND (FIRST tt-erro) THEN
        UNDO, THROW NEW Progress.Lang.AppError ("Erro ao movimentar tarefa: " + STRING (cResult), 17006).
    
    IF RETURN-VALUE = "NOK":u THEN 
        UNDO, THROW NEW Progress.Lang.AppError("Atividade fluig movimentada, porem nao atualizou o item corrente do contrato", 17006).
        
    DELETE PROCEDURE hfluig.
    
    /* fim */
    RETURN "OK":U.

    CATCH e AS Progress.Lang.Error:
        
        DEFINE VARIABLE iCont AS INTEGER NO-UNDO.
        c-Erro = "".
        DO iCont = 1 TO e:NumMessages.
        
            CREATE rowErrors.
            ASSIGN 
                rowErrors.errorNumber      = e:GetMessageNum(iCont)
                rowErrors.errorDescription = "pi-integrarFluig: " + e:GetMessage(iCont).
                
            IF c-Erro = "" THEN 
                c-Erro = e:GetMessage(iCont).
            ELSE
                c-Erro = c-Erro + ", " + e:GetMessage(iCont).
        END.
        
        RETURN "NOK":U.
    END CATCH.

END PROCEDURE.