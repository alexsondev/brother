package com.customcomponent.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.customcomponent.vo.*;
import com.customcomponent.util.*;
import com.customcomponent.oauth.*;

import org.json.*;

public class UserComponent {

    private static Logger log = LoggerFactory.getLogger(UserComponent.class);

    public static UserVO getCurrentUser() throws Exception {
        try {

            log.info(String.format("=*=*=*=*= brother-component | UserComponent | getCurrentUser"));

            JSONObject userObject = OAuthRequest.request("/api/public/2.0/users/getCurrent",
                    RestConstant.REQUEST_METHOD_GET, null);

            Long id = userObject.getJSONObject("content").optLong("id");
            String code = userObject.getJSONObject("content").optString("code");
            String login = userObject.getJSONObject("content").optString("login");
            String email = userObject.getJSONObject("content").optString("email");
            String fullName = userObject.getJSONObject("content").optString("fullName");

            return new UserVO(id, code, login, email, fullName);

        } catch (Exception e) {
            throw e;
        }

    }

}
