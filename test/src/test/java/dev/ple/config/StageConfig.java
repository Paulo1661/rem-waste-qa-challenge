package dev.ple.config;

import io.cucumber.java.Before;
import net.serenitybdd.screenplay.actors.Cast;
import net.serenitybdd.screenplay.actors.OnStage;
import net.serenitybdd.screenplay.actors.OnlineCast;
import net.serenitybdd.screenplay.rest.abilities.CallAnApi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StageConfig {
    private final Logger LOGGER = LoggerFactory.getLogger(StageConfig.class);

    @Before(order=1,value = "@UI")
    public void prepareTheStageForWeb() {
        LOGGER.info("Setting the stage with the Online Cast");
        OnStage.setTheStage(new OnlineCast());
    }

    @Before(order=1,value = "@API")
    public void prepareTheStageForAPI() {
        LOGGER.info("Setting the stage with a Cast where actor can call an api");
        OnStage.setTheStage(Cast.whereEveryoneCan(CallAnApi.at("http://localhost:3000/api")));
    }
}
