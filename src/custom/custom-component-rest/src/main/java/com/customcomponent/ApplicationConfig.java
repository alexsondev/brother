package com.customcomponent;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

@ApplicationPath("/v1")
public class ApplicationConfig extends Application {
    public ApplicationConfig() {

    }
}
