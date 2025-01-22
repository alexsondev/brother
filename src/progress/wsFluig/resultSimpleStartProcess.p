
/*------------------------------------------------------------------------
    File        : resultSimpleStartProcess.p
    Purpose     : 

    Syntax      :

    Description : SAX parse XML SimpleStartProcess

    Author(s)   : TIB
    Created     : Sat Oct 15 00:20:13 BRT 2022
    Notes       :
  ----------------------------------------------------------------------*/

/* ***************************  Definitions  ************************** */

BLOCK-LEVEL ON ERROR UNDO, THROW.

/* estrutura
<result>
    <item>iTask=2</item>
    <item>WDNrVersao=1000</item>
    <item>WDNrDocto=0</item>
    <item>iProcess=28</item>
    <item>cDestino=[adm]</item>
</result>
*/


DEFINE VARIABLE hSax AS HANDLE NO-UNDO.

DEFINE TEMP-TABLE tt-result
    FIELD iTask      AS CHARACTER
    FIELD WDNrVersao AS CHARACTER
    FIELD WDNrDocto  AS CHARACTER
    FIELD iProcess   AS CHARACTER
    FIELD cDestino   AS CHARACTER.

/* ********************  Preprocessor Definitions  ******************** */

/* ********************  Parameter Definitions     ******************** */
DEFINE INPUT PARAMETER pXML AS LONGCHAR NO-UNDO.
DEFINE OUTPUT PARAMETER TABLE FOR tt-result.
DEFINE OUTPUT PARAMETER pErro AS CHARACTER.

/* ***************************  Main Block  *************************** */
CREATE SAX-READER hSax.
hSax:HANDLER = THIS-PROCEDURE.
hSax:SET-INPUT-SOURCE("longchar", pXML).
hSax:SAX-PARSE().



/* **********************  Internal Procedures  *********************** */
/* handling methods - See OpenEdge Development: Working with XML guide for more info. */
/* warnings and errors */
PROCEDURE Warning:
    DEFINE INPUT PARAMETER errMessage AS CHARACTER.
    pErro = errMessage.
END PROCEDURE.

PROCEDURE Error:
    DEFINE INPUT PARAMETER errMessage AS CHARACTER.
    pErro = errMessage.
END PROCEDURE.

PROCEDURE FatalError:
    DEFINE INPUT PARAMETER errMessage AS CHARACTER.
    pErro = errMessage.
END PROCEDURE.


/* the common processing callbacks - these are used to identify where you are in 
     the XML document, and what you're reading. */
PROCEDURE Characters:
   // var
    DEFINE VARIABLE c-campo AS CHARACTER NO-UNDO.
    DEFINE VARIABLE c-valor AS CHARACTER NO-UNDO.
   
   // param
    DEFINE INPUT PARAMETER charData AS LONGCHAR.
    DEFINE INPUT PARAMETER numChars AS INTEGER.

    IF TRIM(chardata) = "" THEN LEAVE. /* discard whitespace used to format the XML */
   
    /* assign to the correct field, depending on node identifier - identifiers are created in StartElement callback */
    c-campo = ENTRY(1, charData, "=").
    c-valor = ENTRY(2, charData, "=").
   
    CASE c-campo:
        /* Assign values from other nodes to the correct fields. 
        Note that currency field is populated in StartElement callback as it needs to be pulled from an attribute */
        WHEN "iTask" THEN 
            tt-result.iTask = c-valor.
        WHEN "WDNrVersao" THEN 
            tt-result.WDNrVersao = c-valor.
        WHEN "WDNrDocto" THEN 
            tt-result.WDNrDocto = c-valor.
        WHEN "iProcess" THEN 
            tt-result.iProcess = c-valor.
        WHEN "cDestino" THEN 
            tt-result.cDestino = c-valor.
       
        OTHERWISE . /* I'm assuming we're not interested in the trace message in the XML... */

    END CASE.

END PROCEDURE.

PROCEDURE StartElement:
    DEFINE INPUT PARAMETER namespaceURI AS CHARACTER.
    DEFINE INPUT PARAMETER localName AS CHARACTER.
    DEFINE INPUT PARAMETER qName AS CHARACTER.
    DEFINE INPUT PARAMETER attributes AS HANDLE.

   //MESSAGE qName VIEW-AS ALERT-BOX INFO BUTTONS OK.
    CASE qName:

        /* New Product element = new record in the temp-table */
        WHEN "result" THEN 
            CREATE tt-result.
       
        /* for any other node, just carry over the nodename */
        OTHERWISE
        . 
       
       // cActiveNode = qName.
    END CASE.

END PROCEDURE.




