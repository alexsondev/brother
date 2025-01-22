&ANALYZE-SUSPEND _VERSION-NUMBER AB_v10r12
&ANALYZE-RESUME
&ANALYZE-SUSPEND _UIB-CODE-BLOCK _CUSTOM _DEFINITIONS Include 
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

/* ***************************  Definitions  ************************** */
DEFINE VARIABLE iContErrors AS INTEGER     NO-UNDO.
DEFINE VARIABLE lcResult AS LONGCHAR      NO-UNDO.
DEFINE VARIABLE ICONt AS INTEGER     NO-UNDO.

DEFINE TEMP-TABLE tt-erro XML-NODE-NAME "Exception"
    FIELD i-sequen AS INTEGER XML-NODE-TYPE "hidden"
    FIELD cd-erro  AS INTEGER XML-NODE-TYPE "hidden"
    FIELD mensagem AS CHARACTER XML-NODE-NAME "message".

/* _UIB-CODE-BLOCK-END */
&ANALYZE-RESUME


&ANALYZE-SUSPEND _UIB-PREPROCESSOR-BLOCK 

/* ********************  Preprocessor Definitions  ******************** */



/* _UIB-PREPROCESSOR-BLOCK-END */
&ANALYZE-RESUME


/* ************************  Function Prototypes ********************** */

&ANALYZE-SUSPEND _UIB-CODE-BLOCK _FUNCTION-FORWARD logMessage Include 
FUNCTION logMessage RETURNS LOGICAL
  ( INPUT pMessage AS CHARACTER )  FORWARD.

/* _UIB-CODE-BLOCK-END */
&ANALYZE-RESUME


/* *********************** Procedure Settings ************************ */

&ANALYZE-SUSPEND _PROCEDURE-SETTINGS
/* Settings for THIS-PROCEDURE
   Type: Include
   Allow: 
   Frames: 0
   Add Fields to: Neither
   Other Settings: INCLUDE-ONLY
 */
&ANALYZE-RESUME _END-PROCEDURE-SETTINGS

/* *************************  Create Window  ************************** */

&ANALYZE-SUSPEND _CREATE-WINDOW
/* DESIGN Window definition (used by the UIB) 
  CREATE WINDOW Include ASSIGN
         HEIGHT             = 5.25
         WIDTH              = 60.
/* END WINDOW DEFINITION */
                                                                        */
&ANALYZE-RESUME

 


&ANALYZE-SUSPEND _UIB-CODE-BLOCK _CUSTOM _MAIN-BLOCK Include 


/* ***************************  Main Block  *************************** */

/* _UIB-CODE-BLOCK-END */
&ANALYZE-RESUME


/* **********************  Internal Procedures  *********************** */

&ANALYZE-SUSPEND _UIB-CODE-BLOCK _PROCEDURE generateError Include 
PROCEDURE generateError :
/*------------------------------------------------------------------------------
  Purpose:     
  Parameters:  <none>
  Notes:       
------------------------------------------------------------------------------*/

    /* param */
    DEFINE INPUT PARAMETER pCodErro AS INTEGER     NO-UNDO.
    DEFINE INPUT PARAMETER pErro AS CHARACTER   NO-UNDO.
    
    /* main */
    CREATE tt-erro.
    ASSIGN iContErrors = iContErrors + 1
           tt-erro.i-sequen = iContErrors
           tt-erro.cd-erro = pCodErro
           tt-erro.mensagem = pErro.

END PROCEDURE.

/* _UIB-CODE-BLOCK-END */
&ANALYZE-RESUME

&ANALYZE-SUSPEND _UIB-CODE-BLOCK _PROCEDURE generateErrorXML Include 
PROCEDURE generateErrorXML :
/*------------------------------------------------------------------------------
  Purpose:     
  Parameters:  <none>
  Notes:       
------------------------------------------------------------------------------*/

    /* vars */
    
    /* param */
    DEFINE INPUT PARAMETER pErro AS LONGCHAR NO-UNDO.

    /* main */
    TEMP-TABLE tt-erro:READ-XML("longchar", pErro, "EMPTY", ?, ?, ?, ?).

    IF NOT CAN-FIND(FIRST tt-erro) THEN DO:
        RUN generateError (17006, "Erro n∆o identificado pelo Servidor").
    END.

    FOR EACH tt-erro.
        IF TRIM(tt-erro.mensagem) = "" THEN
            RUN generateError (17006, "Erro n∆o identificado pelo Servidor").
    END.


END PROCEDURE.

/* _UIB-CODE-BLOCK-END */
&ANALYZE-RESUME

&ANALYZE-SUSPEND _UIB-CODE-BLOCK _PROCEDURE getTtErrors Include 
PROCEDURE getTtErrors :
/*------------------------------------------------------------------------------
  Purpose:     
  Parameters:  <none>
  Notes:       
------------------------------------------------------------------------------*/

    /* param */
    DEFINE OUTPUT PARAMETER TABLE FOR tt-erro.

END PROCEDURE.

/* _UIB-CODE-BLOCK-END */
&ANALYZE-RESUME

/* ************************  Function Implementations ***************** */

&ANALYZE-SUSPEND _UIB-CODE-BLOCK _FUNCTION logMessage Include 
FUNCTION logMessage RETURNS LOGICAL
  ( INPUT pMessage AS CHARACTER ) :
/*------------------------------------------------------------------------------
  Purpose:  
    Notes:  
------------------------------------------------------------------------------*/

    /* main */
    IF LOG-MANAGER:NUM-LOG-FILES <> 0 AND
        LOG-MANAGER:LOGFILE-NAME <> "" THEN
        LOG-MANAGER:WRITE-MESSAGE ("**** - " + pMessage).

    RETURN TRUE.   /* Function return value. */

END FUNCTION.

/* _UIB-CODE-BLOCK-END */
&ANALYZE-RESUME

