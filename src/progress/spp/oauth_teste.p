
/*------------------------------------------------------------------------
    File        : oauth_teste.p
    Purpose     : 

    Syntax      :

    Description : 

    Author(s)   : ronil
    Created     : Sat Feb 24 14:13:36 BRT 2024
    Notes       :
  ----------------------------------------------------------------------*/

/* ***************************  Definitions  ************************** */

BLOCK-LEVEL ON ERROR UNDO, THROW.

DEFINE VARIABLE cConsumerKey AS CHARACTER NO-UNDO.
DEFINE VARIABLE cConsumerSecret AS CHARACTER NO-UNDO.
DEFINE VARIABLE cAccessToken AS CHARACTER NO-UNDO.
DEFINE VARIABLE cAccessTokenSecret AS CHARACTER NO-UNDO.

/* ********************  Preprocessor Definitions  ******************** */


/* ***************************  Main Block  *************************** */

/* Obtain these credentials from Twitter Developer Portal */
ASSIGN
    cConsumerKey = "apifluig"
    cConsumerSecret = "apifluig"
    cAccessToken = "1400a8e9-cd30-43d8-b66b-3830fd5c40ed"
    cAccessTokenSecret = "87f88064-9186-49b1-bf8f-04df75f9fa03179e656c-640e-4c8c-ac15-23a87a4557a9"
.

/* Construct the OAuth header */
DEFINE VARIABLE cOAuthHeader AS CHARACTER NO-UNDO.
ASSIGN
    cOAuthHeader = CreateOAuthHeader(cConsumerKey, cConsumerSecret, cAccessToken, cAccessTokenSecret)
    .

/* Make an API request (e.g., post a tweet) */
DEFINE VARIABLE cTweet AS CHARACTER NO-UNDO.
ASSIGN
    cTweet = "Hello, Twitter! #OpenEdge"
.

/* Send the tweet using HTTP POST */
DEFINE VARIABLE cResponse AS CHARACTER NO-UNDO.
RUN PostTweet(cTweet, cOAuthHeader, OUTPUT cResponse).

DISPLAY cResponse.
