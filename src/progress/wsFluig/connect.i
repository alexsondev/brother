&ANALYZE-SUSPEND _VERSION-NUMBER AB_v10r12
&ANALYZE-RESUME
&ANALYZE-SUSPEND _UIB-CODE-BLOCK _CUSTOM _DEFINITIONS Include 
/*------------------------------------------------------------------------
    File        : wsfluig/connect.i
    Purpose     : fonte padr∆o de conex∆o WebService

    Syntax      : wsfluig/connect.1 ECMDatasetService DatasetService hWebService hDataset 

    Description :

    Author(s)   : rzo
    Created     : 27/03/2015
    Notes       :
  ----------------------------------------------------------------------*/
/*          This .W file was created with the Progress AppBuilder.      */
/*----------------------------------------------------------------------*/

/* ***************************  Definitions  ************************** */
DEFINE VARIABLE strConexao AS CHARACTER NO-UNDO.
DEFINE VARIABLE hSoapFault AS HANDLE.
DEFINE VARIABLE hSoapFaultDetail AS HANDLE.
DEFINE VARIABLE lRet AS CHARACTER NO-UNDO.

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
         HEIGHT             = 5.38
         WIDTH              = 60.
/* END WINDOW DEFINITION */
                                                                        */
&ANALYZE-RESUME

 


&ANALYZE-SUSPEND _UIB-CODE-BLOCK _CUSTOM _MAIN-BLOCK Include 


/* ***************************  Main Block  *************************** */
    strConexao = "-WSDL '" + {5} + "/{1}?wsdl'".

    DO ON ERROR UNDO, THROW:

        CREATE SERVER {3}.
        lOk = {3}:CONNECT(strConexao).

        IF NOT lOk THEN DO:
            RUN criarErro (INPUT 17006,
                           INPUT "Erro conex∆o com servidor: " + RETURN-VALUE).
            RETURN "NOK":U.
        END.
        
        RUN {2} SET {4} ON {3}.
        lRet = "OK":U.
    END.

        /* This CATCH handles SoapFaultErrors and ignores all other system errors.*/
    CATCH mySoapErrorObject AS Progress.Lang.SoapFaultError:

        IF VALID-HANDLE(mySoapErrorObject:SoapFault:SOAP-FAULT-DETAIL) THEN DO:
            ASSIGN lcResult = mySoapErrorObject:SoapFault:SOAP-FAULT-DETAIL:GET-SERIALIZED().
            RUN generateErrorXML(INPUT lcResult).
        END.

        lRet = "NOK":U.

        DELETE OBJECT mySoapErrorObject.
        DELETE PROCEDURE {4} NO-ERROR.
        {3}:DISCONNECT( ) NO-ERROR.

    END CATCH.

    CATCH mySystemErrorObject AS Progress.Lang.SysError:
        lRet = "NOK":U.

        RUN generateError (INPUT STRING(mySystemErrorObject:getMessageNum(1)),
                           INPUT mySystemErrorObject:getMessage(1)).

        DELETE OBJECT mySystemErrorObject.
        DELETE PROCEDURE {4} NO-ERROR.
        {3}:DISCONNECT( ) NO-ERROR.

    END CATCH.

    FINALLY:
        RETURN lRet.
    END FINALLY.

/* _UIB-CODE-BLOCK-END */
&ANALYZE-RESUME


