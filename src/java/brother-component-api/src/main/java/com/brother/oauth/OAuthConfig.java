package com.brother.oauth;

import com.fluig.sdk.api.customappkey.KeyVO;
import oauth.signpost.OAuthConsumer;
import oauth.signpost.basic.DefaultOAuthConsumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OAuthConfig {

    private static Logger log = LoggerFactory.getLogger(OAuthConfig.class);

    public static OAuthConsumer config(KeyVO key) {
        log.info(String.format(
                "=*=*=*=*= brother-component | OAuthConsumer | config | key.getConsumerKey(): %s",
                key.getConsumerKey()));
        log.info(String.format(
                "=*=*=*=*= brother-component | OAuthConsumer | config | key.getConsumerSecret(): %s",
                key.getConsumerSecret()));
        log.info(String.format(
                "=*=*=*=*= brother-component | OAuthConsumer | config | key.getToken(): %s",
                key.getToken()));
        log.info(String.format(
                "=*=*=*=*= brother-component | OAuthConsumer | config | key.getTokenSecret(): %s",
                key.getTokenSecret()));

        OAuthConsumer consumer =
                new DefaultOAuthConsumer(key.getConsumerKey(), key.getConsumerSecret());
        consumer.setTokenWithSecret(key.getToken(), key.getTokenSecret());
        return consumer;
    }

}
