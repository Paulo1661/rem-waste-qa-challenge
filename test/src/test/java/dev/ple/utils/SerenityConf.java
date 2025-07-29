package dev.ple.utils;


import net.serenitybdd.core.di.SerenityInfrastructure;
import net.serenitybdd.model.environment.EnvironmentSpecificConfiguration;
import net.thucydides.model.util.EnvironmentVariables;

public class SerenityConf {

    private final EnvironmentVariables environmentVariables = SerenityInfrastructure.getEnvironmentVariables();

    public String getVariable(String variableName) {

        if(EnvironmentSpecificConfiguration.from(environmentVariables).getOptionalProperty(variableName).isPresent()) {
            return EnvironmentSpecificConfiguration.from(environmentVariables).getProperty(variableName);
        }
        return "";
    }

    public String getVariable(final Enum<?> variableName) {
        return EnvironmentSpecificConfiguration.from(environmentVariables).getProperty(variableName.toString());
    }

    public static SerenityConf getInstance()
    {
        return SingletonHolder.instance;
    }

    private static class SingletonHolder
    {
        private final static SerenityConf instance = new SerenityConf();
    }
}

