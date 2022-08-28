package com.payconiq.qa.config;

import org.aeonbits.owner.Config;
import org.aeonbits.owner.Config.LoadPolicy;
import org.aeonbits.owner.Config.LoadType;

@LoadPolicy(LoadType.MERGE)
@Config.Sources({
        "system:properties",
        "classpath:configuration.properties"})
public interface Configuration extends Config {

    @Key("api.base.uri")
    String baseURI();

    @Key("auth.username")
    String userName();

    @Key("auth.passowrd")
    String password();

}