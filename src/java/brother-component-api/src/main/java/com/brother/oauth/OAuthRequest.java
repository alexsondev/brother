package com.brother.oauth;

import java.net.HttpURLConnection;
import java.net.URL;

import java.io.*;

import com.fluig.customappkey.Keyring;
import com.fluig.sdk.api.customappkey.KeyVO;
import oauth.signpost.OAuthConsumer;
import com.brother.util.RestConstant;

import org.json.*;

public class OAuthRequest {

    public static JSONObject request(String path, String method, String json) throws Exception {
        try {
            KeyVO key = Keyring.getKeys(1L, RestConstant.APP_KEY);
            OAuthConsumer consumer = OAuthConfig.config(key);

            String url = key.getDomainUrl();
            url = url.replace(":-1", "");

            URL urlProvisioningTenant = new URL(url + path);
            HttpURLConnection conn = (HttpURLConnection) urlProvisioningTenant.openConnection();
            conn.setRequestProperty("Accept-Charset", "UTF-8");
            conn.setRequestProperty("Content-Type", "application/json; charset=utf-8");

            conn.setRequestMethod(method);
            conn.setDoInput(true);
            conn.setDoOutput(true);

            consumer.sign(conn);

            if (json != null) {
                OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
                wr.write(json);
                wr.flush();
                wr.close();
            }

            // Autentica a conexão

            conn.connect();
            Reader input = new BufferedReader(
                    new InputStreamReader(conn.getInputStream(), RestConstant.UTF_8_ENCODE));
            String ret = "";
            for (int c = input.read(); c != -1; c = input.read()) {
                ret += (char) c;
            }
            int responseCode = conn.getResponseCode();
            System.out.println(String.format("RESPONSE: %d - %s: data: %s", responseCode,
                    conn.getResponseMessage(), ret));
            conn.disconnect();

            return new JSONObject(ret);

        } catch (Exception e) {
            throw e;
        }
    }
}
