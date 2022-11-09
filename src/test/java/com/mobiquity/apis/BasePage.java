package com.mobiquity.apis;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import net.serenitybdd.core.pages.PageObject;
import utils.ForceStringDeserializer;

public class BasePage extends PageObject {

    public final ObjectMapper mapper = new ObjectMapper().registerModule(
            new SimpleModule().addDeserializer(String.class, new ForceStringDeserializer())
    );

    public String getBaseURL() {
        String env = System.getenv("ENV");
        if (env == null || env.isEmpty()) {
            return "https://jsonplaceholder.typicode.com/";
        } else {
            switch (env){
                case "localhost":
                    return "https://localhost:8080";
                case "test":
                    return "URL FOR TEST";
                case "test-staging":
                    return "URL FOR STAGING";
                case "prod-live":
                    return "https://jsonplaceholder.typicode.com/";
                case "mock":
                    return "URL FOR MOCK";
                default:
                    return "default URL";
            }
        }
    }

}
