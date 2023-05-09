package com.brother.component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.brother.vo.*;
import com.brother.util.*;
import com.brother.oauth.*;
import org.json.*;

public class DocumentComponent {

    private static Logger log = LoggerFactory.getLogger(UserComponent.class);

    public static DocumentVO createDocument(String fileName, String parentDocumentId,
            Boolean inheritSecurity, Boolean internalVisualizer) throws Exception {
        try {

            log.info(String
                    .format("=*=*=*=*= brother-component | DocumentComponent | createDocument"));

            String json = "{" + "\"documentDescription\":\"" + fileName + "\","
                    + "\"parentDocumentId\":\"" + parentDocumentId + "\"," + "\"inheritSecurity\":"
                    + inheritSecurity + "," + "\"internalVisualizer\":" + internalVisualizer + "}";

            JSONObject documentObject =
                    OAuthRequest.request("/api/public/2.0/documents/createDocument",
                            RestConstant.REQUEST_METHOD_POST, json);
            JSONObject contentObject = documentObject.getJSONObject("content");

            Long documentid = contentObject.optLong("documentId");
            String version = contentObject.optString("version");

            String url = getDownloadURL(documentid);

            return new DocumentVO(documentid, fileName, url, fileName, version);

        } catch (Exception e) {
            throw e;
        }
    }

    public static String getDownloadURL(Long documentid) throws Exception {
        try {
            JSONObject urlObject = OAuthRequest.request(
                    "/api/public/2.0/documents/getDownloadURL/" + String.valueOf(documentid),
                    RestConstant.REQUEST_METHOD_GET, null);

            return urlObject.getString("content");

        } catch (Exception e) {
            throw e;
        }

    }

}
