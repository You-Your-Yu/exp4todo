package controllers;

import controllers.todo.Index;
import play.mvc.Controller;

public class Application extends Controller {

    public static void index() {
        render();
    }

    public static void todo() {
    		Index.index();
    }

}