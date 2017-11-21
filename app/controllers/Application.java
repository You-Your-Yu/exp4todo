package controllers;

import controllers.todo.Index;
import play.mvc.Controller;

public class Application extends Controller {

    public static void index() {
    	Index.index();
    }

    public static void todo() {
    	Index.index();
    }

}