package dev.ple.screenplay.questions;

import net.serenitybdd.core.steps.Instrumented;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.questions.Text;

import java.util.List;

public class TheUserList implements Question<List<String>> {
    protected TheUserList() {}
    public static TheUserList displayed() {
        return Instrumented.instanceOf(TheUserList.class).newInstance();
    }
    @Override
    public List<String> answeredBy(Actor actor) {
        return Text.ofEach(".user-list-container li span").answeredBy(actor).stream().toList();
    }
}
