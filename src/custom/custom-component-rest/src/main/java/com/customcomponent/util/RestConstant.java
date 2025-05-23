package com.customcomponent.util;

import java.nio.charset.StandardCharsets;

public class RestConstant {
	
	/**
	 * ATENÇÃO: o valor dessa chave precisa ser o mesmo de Activate.APP_KEY, no pacote custom-component-config
	 */
    public static final String APP_KEY = "1111-5678-9876-9999";
    public static final String UTF_8_ENCODE = StandardCharsets.UTF_8.name();

    /**
     * HTTP Headers.
     */
    public static final String REQUEST_METHOD_GET = "GET";
    public static final String REQUEST_METHOD_POST = "POST";
}
