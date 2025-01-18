package com.customcomponent.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.customcomponent.vo.*;
import com.google.gson.Gson;
import com.customcomponent.util.*;
import com.customcomponent.oauth.*;
import org.json.*;
import java.lang.reflect.Field;
import java.net.*;
import java.util.ArrayList;
import java.util.List;

public class ECMFormDataComponent {

  private static Logger log = LoggerFactory.getLogger(MarketingFlowComponent.class);

  public static List<ECMFormDataVO> getFormData(String dataset, Long documentid, String tables)
      throws Exception {
    try {

      String path =
          "/api/public/ecm/dataset/search?datasetId=fluig_form_data&filterFields=documentid,"
              + documentid + ",dataset," + dataset + ",tables," + tables;

      JSONObject formDataObject = OAuthRequest.request(path, RestConstant.REQUEST_METHOD_GET, null);

      log.info(String
          .format("=*=*=*=*= brother-component | MarketingFlowComponent | getMarketingData 2.2"));


      JSONArray contentArray = formDataObject.getJSONArray("content");

      List<ECMFormDataVO> formData = new ArrayList<>();
      for (int i = 0; i < contentArray.length(); i++) {
        try {
          String name = contentArray.getJSONObject(i).optString("name");
          String value = contentArray.getJSONObject(i).optString("value");

          ECMFormDataVO item = new ECMFormDataVO(name, value);

          formData.add(item);

        } catch (Exception e) {
        }
      }

      return formData;

    } catch (Exception e) {
      throw e;
    }
  }
}
