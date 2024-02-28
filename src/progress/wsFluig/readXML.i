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
DEFINE VARIABLE lColuna  AS LOGICAL NO-UNDO.
DEFINE VARIABLE lValores AS LOGICAL NO-UNDO.
DEFINE VARIABLE iSeq     AS INTEGER NO-UNDO.
DEFINE VARIABLE iOrder   AS INTEGER INITIAL 10 NO-UNDO.
DEFINE VARIABLE hasError AS LOGICAL NO-UNDO.

/* temp tables for dataset result */
DEFINE TEMP-TABLE ttColumns
    FIELD NAME AS CHARACTER.

DEFINE TEMP-TABLE ttValues
    FIELD NAME   AS CHARACTER
    FIELD record AS INTEGER.
    
DEFINE TEMP-TABLE xPath NO-UNDO
    FIELD xPath  AS CHARACTER FORMAT "x(60)"
    FIELD xValue AS CHARACTER
    FIELD order  AS INTEGER
    FIELD atrib  AS CHARACTER
    INDEX idx IS PRIMARY order.    

/* _UIB-CODE-BLOCK-END */
&ANALYZE-RESUME


&ANALYZE-SUSPEND _UIB-PREPROCESSOR-BLOCK 

/* ********************  Preprocessor Definitions  ******************** */



/* _UIB-PREPROCESSOR-BLOCK-END */
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
         HEIGHT             = 15
         WIDTH              = 60.
/* END WINDOW DEFINITION */
                                                                        */
&ANALYZE-RESUME

 


&ANALYZE-SUSPEND _UIB-CODE-BLOCK _CUSTOM _MAIN-BLOCK Include 


/* ***************************  Main Block  *************************** */

/* _UIB-CODE-BLOCK-END */
&ANALYZE-RESUME


/* **********************  Internal Procedures  *********************** */


&ANALYZE-SUSPEND _UIB-CODE-BLOCK _PROCEDURE getChild Include
PROCEDURE getChild:
    /*------------------------------------------------------------------------------
     Purpose:
     Notes:
    ------------------------------------------------------------------------------*/
    
    // vars
    DEFINE VARIABLE i        AS INTEGER   NO-UNDO.
    DEFINE VARIABLE h-node   AS HANDLE    NO-UNDO.
    DEFINE VARIABLE h-aux    AS HANDLE    NO-UNDO.
    DEFINE VARIABLE h-aux2   AS HANDLE    NO-UNDO.
    DEFINE VARIABLE c-parent AS CHARACTER NO-UNDO.
    DEFINE VARIABLE c-name   AS CHARACTER NO-UNDO.
    DEFINE VARIABLE c-atrib  AS CHARACTER NO-UNDO.
    DEFINE VARIABLE iCont    AS INTEGER   NO-UNDO.

    // param
    DEFINE INPUT PARAMETER p-parent AS HANDLE NO-UNDO.
    
    // main
    CREATE X-NODEREF h-node.
    CREATE X-NODEREF h-aux.

    IF p-parent:NAME = "#text" THEN 
    DO:

        ASSIGN 
            c-parent = ""
            h-aux2   = p-parent.

        REPEAT:

            h-aux2:GET-PARENT(h-aux).

            c-name = h-aux:NAME NO-ERROR.

            IF ERROR-STATUS:NUM-MESSAGES > 0 THEN
                LEAVE.

                // buscar o atributo nsima do item
            IF c-name = "det" THEN
                c-atrib = STRING( h-aux:GET-ATTRIBUTE ("nItem") ).
                    
                // buscar o atributo id quando CFE
            IF c-name = "Reference" THEN 
                c-atrib = STRING( h-aux:GET-ATTRIBUTE ("URI") ).
                    
            IF c-parent = "" THEN
                c-parent = c-name.
            ELSE
                c-parent = c-name + "/" + c-parent.

            h-aux2 = h-aux.
    
        END. /* REPEAT */

        IF (TRIM(p-parent:NODE-VALUE) <> "") THEN 
        DO:
                
            CREATE xPath.
            ASSIGN 
                xPath.xPath  = c-parent
                xPath.xValue = TRIM(REPLACE(REPLACE(p-parent:NODE-VALUE, CHR(10), ""), CHR(13), ""))
                xPath.order  = iOrder
                xPath.atrib  = c-atrib
                c-atrib      = ""
                iOrder       = iOrder + 10.
                    
        END.

    END. /* IF p-parent:NAME = "#text" */

    DO i = 1 TO p-parent:NUM-CHILDREN:

        p-parent:GET-CHILD(h-node,i).

        RUN getChild (INPUT h-node).

    END. /* DO i = 1 TO p-parent:NUM-CHILDREN */
        
        
    IF VALID-HANDLE(h-node) THEN DELETE OBJECT h-node.
    IF VALID-HANDLE(h-aux) THEN DELETE OBJECT h-aux.
    IF VALID-HANDLE(h-aux2) THEN DELETE OBJECT h-aux2.
        
    RETURN.
        
    CATCH e AS Progress.Lang.Error:
            
        hasError = TRUE.
            
        DO iCont = 1 TO e:NumMessages.
            
            CREATE tt-erro.
            ASSIGN 
                tt-erro.cd-erro  = e:GetMessageNum(iCont)
                tt-erro.mensagem = e:GetMessage(iCont).  
                
        END.
            
    END CATCH.

END PROCEDURE.
    
/* _UIB-CODE-BLOCK-END */
&ANALYZE-RESUME



&ANALYZE-SUSPEND _UIB-CODE-BLOCK _PROCEDURE getElements Include 
PROCEDURE getElements :
    /*------------------------------------------------------------------------------
      Purpose:     
      Parameters:  <none>
      Notes:       
    ------------------------------------------------------------------------------*/

    /* vars */
    DEFINE VARIABLE iCont  AS INTEGER NO-UNDO.
    DEFINE VARIABLE hChild AS HANDLE  NO-UNDO.

    /* param */
    DEFINE INPUT PARAMETER pParent AS HANDLE NO-UNDO.

    /* main */
    CREATE X-NODEREF hChild.
 
    IF pParent:NUM-CHILDREN <> 0 THEN 
    DO:
        
        DO iCont = 1 TO pParent:NUM-CHILDREN.

            pParent:GET-CHILD(hChild, iCont).

            IF hChild:SUBTYPE = "Element" THEN 
            DO:
                IF hChild:NAME = "columns" THEN
                    ASSIGN 
                        lColuna  = TRUE
                        lValores = FALSE.
                ELSE IF hChild:NAME = "values" THEN
                        iSeq = iSeq + 10.
                    ELSE IF hChild:NAME = "value" THEN
                            ASSIGN 
                                lValores = TRUE
                                lColuna  = FALSE.
                        ELSE 
                        DO:
                            ASSIGN 
                                lColuna  = FALSE
                                lValores = FALSE.
                        END.

                RUN getElements (INPUT hChild).
            END.
            
            IF lColuna THEN 
            DO:
                IF TRIM(hChild:NODE-VALUE) <> "" THEN 
                DO:
                    CREATE ttColumns.
                    ASSIGN 
                        ttColumns.NAME = hChild:NODE-VALUE.
                END.
            END.

            IF lValores THEN 
            DO:
                IF TRIM(hChild:NODE-VALUE) <> "" THEN 
                DO:
                    CREATE ttValues.
                    ASSIGN 
                        ttValues.NAME   = IF TRIM (hChild:NODE-VALUE) <> "" THEN TRIM (hChild:NODE-VALUE) ELSE ""
                        ttValues.record = iSeq.
                END.
            END.
        END.
    END.

END PROCEDURE.

/* _UIB-CODE-BLOCK-END */
&ANALYZE-RESUME

&ANALYZE-SUSPEND _UIB-CODE-BLOCK _PROCEDURE getElementsArray Include 
PROCEDURE getElementsArray :
    /*------------------------------------------------------------------------------
      Purpose:     
      Parameters:  <none>
      Notes:       
    ------------------------------------------------------------------------------*/

    /* vars */
    DEFINE VARIABLE iCont  AS INTEGER NO-UNDO.
    DEFINE VARIABLE hChild AS HANDLE  NO-UNDO.

    /* param */
    DEFINE INPUT PARAMETER pParent AS HANDLE NO-UNDO.
    DEFINE INPUT PARAMETER pInicio AS LOGICAL NO-UNDO.

    /* main */
    CREATE X-NODEREF hChild.

    IF pParent:NUM-CHILDREN <> 0 THEN 
    DO:
        
        DO iCont = 1 TO pParent:NUM-CHILDREN.

            pParent:GET-CHILD(hChild, iCont).
            
            IF hChild:SUBTYPE = "Element" THEN 
            DO:
                RUN getElementsArray (INPUT hChild, INPUT FALSE).
            END.
            ELSE 
            DO:
                IF pInicio THEN
                    ASSIGN lColuna  = TRUE
                        lValores = FALSE
                        iSeq     = 1.
                ELSE 
                DO:
                    IF lColuna THEN
                        ASSIGN lColuna  = FALSE
                            lValores = TRUE.
                    ELSE
                        ASSIGN lColuna  = TRUE
                            lValores = FALSE
                            /*iSeq = iSeq + 1  - comentei pq estava dando erro no resulto do workflow */.
                END.

                /* valores */
                IF lValores THEN 
                DO:
                    IF TRIM(hChild:NODE-VALUE) <> "" THEN 
                    DO:
                        
                        CREATE ttValues.
                        ASSIGN 
                            ttValues.NAME   = TRIM(hChild:NODE-VALUE)
                            ttValues.record = iSeq.

                    END.
                END.

                /* cabecalho */
                IF lColuna THEN 
                DO:
                    IF TRIM(hChild:NODE-VALUE) <> "" THEN 
                    DO:
    
                        CREATE ttColumns.
                        ASSIGN 
                            ttColumns.NAME = hChild:NODE-VALUE.

                    END.
                END.
            END.
        END.
    END.

END PROCEDURE.

/* _UIB-CODE-BLOCK-END */
&ANALYZE-RESUME



&ANALYZE-SUSPEND _UIB-CODE-BLOCK _PROCEDURE getXPath Include
PROCEDURE getXPath:
/*------------------------------------------------------------------------------
 Purpose:
 Notes:
------------------------------------------------------------------------------*/

    // param
    DEFINE OUTPUT PARAMETER table FOR xPath.

END PROCEDURE.
    
/* _UIB-CODE-BLOCK-END */
&ANALYZE-RESUME



&ANALYZE-SUSPEND _UIB-CODE-BLOCK _PROCEDURE mountJson Include
PROCEDURE mountJsonDataset:
    /*------------------------------------------------------------------------------
     Purpose:
     Notes:
    ------------------------------------------------------------------------------*/
    
    // var
    DEFINE VARIABLE c-column  AS CHARACTER  NO-UNDO EXTENT.
    DEFINE VARIABLE i-len     AS INTEGER    NO-UNDO.
    DEFINE VARIABLE i-cont    AS INTEGER    NO-UNDO.
    
    DEFINE VARIABLE jRegistro AS JsonObject NO-UNDO.
    DEFINE VARIABLE jTabela   AS JsonArray  NO-UNDO.
    
    DEFINE VARIABLE iCount    AS INTEGER    NO-UNDO.
    DEFINE VARIABLE iCount2   AS INTEGER    NO-UNDO.
    DEFINE VARIABLE hDoc      AS HANDLE     NO-UNDO.
    DEFINE VARIABLE hRoot     AS HANDLE     NO-UNDO.
    DEFINE VARIABLE hValues   AS HANDLE     NO-UNDO.
    DEFINE VARIABLE hEntry    AS HANDLE     NO-UNDO.
    DEFINE VARIABLE hText     AS HANDLE     NO-UNDO.
    DEFINE VARIABLE cValue    AS CHARACTER  NO-UNDO.

    // param
    DEFINE INPUT PARAMETER pXML AS LONGCHAR NO-UNDO.
    DEFINE OUTPUT PARAMETER pRetorno AS LONGCHAR NO-UNDO.
    
    // main
    EMPTY TEMP-TABLE ttColumns.
    EMPTY TEMP-TABLE ttValues.
    
    // ler xml
    CREATE X-DOCUMENT hDoc.
    CREATE X-NODEREF hRoot.
    CREATE X-NODEREF hEntry.
    CREATE X-NODEREF hText.
    CREATE X-NODEREF hValues.
 
    hDoc:LOAD("longchar", pXML, FALSE).
    hDoc:GET-DOCUMENT-ELEMENT(hRoot).
 
    /* Percorre as colunas <columns> */
    DO iCount = 1 TO hRoot:NUM-CHILDREN:
        hRoot:GET-CHILD(hEntry, iCount).
        IF hEntry:NAME <> "columns" THEN
            NEXT.
 
        hEntry:GET-CHILD(hText, 1).
        CREATE ttColumns.
        ASSIGN 
            ttColumns.NAME = hText:NODE-VALUE.
        
    END.
 
    /* Percorre os registros <values> */
    iSeq = 0.
    DO iCount = 1 TO hRoot:NUM-CHILDREN:
        hRoot:GET-CHILD(hValues, iCount).
        
        IF hValues:NAME <> "values" THEN
            NEXT.
            
        iSeq = iSeq + 10.
        
        /* Percorre os campos <value> */
        DO iCount2 = 1 TO hValues:NUM-CHILDREN:
            hValues:GET-CHILD(hEntry, iCount2).
 
            IF hEntry:NUM-CHILDREN = 0 THEN
                cValue = "".
            ELSE 
            DO:
                hEntry:GET-CHILD(hText, 1).
                cValue = hText:NODE-VALUE.
            END.
                
            CREATE ttValues.
            ASSIGN 
                ttValues.NAME   = cValue
                ttValues.record = iSeq.
        END. 
    END.
 
    DELETE OBJECT hValues.
    DELETE OBJECT hText.
    DELETE OBJECT hEntry.
    DELETE OBJECT hRoot.
    DELETE OBJECT hDoc.

    // numero de colunas
    i-len = 0.
    FOR EACH ttColumns.
        i-len = i-len + 1.
    END.
    
    // transformar ttcolumn em array
    EXTENT (c-column) = i-len.
    i-len = 0.
    FOR EACH ttColumns.
        i-len = i-len + 1.
        c-column[i-len] = ttColumns.NAME.
    END. 
   
    jTabela = NEW JsonArray().
    FOR EACH ttValues
        BREAK BY ttValues.record.
        
        // sempre novo record, crir um json object
        IF FIRST-OF (ttValues.record) THEN
        DO: 
            jRegistro = NEW jsonObject().
            i-cont = 0.
        END.
        
        i-cont = i-cont + 1.
        
        jRegistro:ADD (c-column[i-cont], ttValues.NAME).
        
        // sempre fim record, add ao arraay
        IF LAST-OF (ttValues.record) THEN
            jTabela:add(jRegistro).
    END.
    
    jTabela:write(pRetorno).
    
    RETURN "OK":U.
    

END PROCEDURE.
    
/* _UIB-CODE-BLOCK-END */
&ANALYZE-RESUME



&ANALYZE-SUSPEND _UIB-CODE-BLOCK _PROCEDURE mountTempTable Include 
PROCEDURE mountTempTable :
    /*------------------------------------------------------------------------------
      Purpose:     
      Parameters:  <none>
      Notes:       
    ------------------------------------------------------------------------------*/

    /* vars */
    DEFINE VARIABLE hTable      AS HANDLE    NO-UNDO.
    DEFINE VARIABLE hBuffer     AS HANDLE    NO-UNDO.
    DEFINE VARIABLE hQuery      AS HANDLE    NO-UNDO.
    DEFINE VARIABLE iColumns    AS INTEGER   NO-UNDO.
    DEFINE VARIABLE iColumnsAdd AS INTEGER   NO-UNDO.
    DEFINE VARIABLE iMax        AS INTEGER   NO-UNDO.

    DEFINE VARIABLE cNomeField  AS CHARACTER NO-UNDO.

    /* param */
    DEFINE INPUT PARAMETER pName AS CHARACTER NO-UNDO.
    DEFINE OUTPUT PARAMETER pTable AS HANDLE NO-UNDO.

    /* main */
    IF NOT CAN-FIND(FIRST ttColumns) THEN 
        RETURN "NOK":U.    

    IF NOT CAN-FIND(FIRST ttValues) THEN 
        RETURN "NOK":U.    

    CREATE TEMP-TABLE pTable.

    iColumns = 0.
    FOR EACH ttColumns.
        cNomeField = REPLACE(ttColumns.NAME, ".", "_").
        cNomeField = REPLACE(cNomeField, "#", "__").

        /* logMessage ("Nome DOS campos: " + cNomeField). */

        pTable:ADD-NEW-FIELD(cNomeField, "character", 1, ?, ?, cNomeField, cNomeField).
        iColumns = iColumns + 1.
    END.
    
    /* cria‡Æo dinamica da temp-table */
    pTable:TEMP-TABLE-PREPARE(pName).

    CREATE QUERY hQuery.
    hQuery:SET-BUFFERS(pTable:DEFAULT-BUFFER-HANDLE).
    hQuery:QUERY-PREPARE("for each " + pTable:NAME).
    hQuery:QUERY-OPEN().

    hBuffer = hQuery:GET-BUFFER-HANDLE().

    IF hQuery:IS-OPEN THEN 
    DO:

        FOR EACH ttValues
            BREAK BY ttValues.record.
    
            IF FIRST-OF(ttValues.record) THEN 
            DO:
                ASSIGN 
                    iColumnsAdd = 1.
                hBuffer:BUFFER-CREATE.
            END.

            hBuffer:BUFFER-FIELD(iColumnsAdd):BUFFER-VALUE = ttValues.NAME.
            iColumnsAdd = iColumnsAdd + 1.

        /*             logMessage("ColumnADD : " + STRING(iColumnsAdd - 1) +                        */
        /*                        " Nome: " + hBuffer:BUFFER-FIELD(iColumnsAdd - 1):NAME +          */
        /*                        " Valor: " + hBuffer:BUFFER-FIELD(iColumnsAdd - 1):BUFFER-VALUE). */
        END.
    END.

    /* zerar as tabelas */
    EMPTY TEMP-TABLE ttValues.
    EMPTY TEMP-TABLE ttColumns.

    RETURN "OK":U.

END PROCEDURE.

/* _UIB-CODE-BLOCK-END */
&ANALYZE-RESUME

&ANALYZE-SUSPEND _UIB-CODE-BLOCK _PROCEDURE readXML Include
PROCEDURE readXML:
    /*------------------------------------------------------------------------------
     Purpose:
     Notes:
    ------------------------------------------------------------------------------*/
    
    // vars
    DEFINE VARIABLE lcXML AS LONGCHAR NO-UNDO.
    DEFINE VARIABLE h-doc AS HANDLE   NO-UNDO.
    DEFINE VARIABLE iCont AS INTEGER  NO-UNDO.
    
    // param
    DEFINE INPUT PARAMETER plcXML AS LONGCHAR NO-UNDO.
    
    // main
    FOR EACH xPath:
        DELETE xPath.
    END.

    CREATE X-DOCUMENT h-doc.

    h-doc:LOAD("longchar", plcXML, FALSE).

    RUN getChild(INPUT h-doc).
        
    IF VALID-HANDLE(h-doc) THEN DELETE OBJECT h-doc.
        
    RETURN.
        
    CATCH e AS Progress.Lang.Error:
            
        hasError = TRUE.
            
        DO iCont = 1 TO e:NumMessages.
            
            CREATE tt-erro.
            ASSIGN 
                tt-erro.cd-erro = e:GetMessageNum(iCont)
                tt-erro.mensagem = e:GetMessage(iCont). 
                
        END.
            
    END CATCH.

END PROCEDURE.
    
/* _UIB-CODE-BLOCK-END */
&ANALYZE-RESUME


