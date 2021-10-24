package cs3321;

import io.javalin.Javalin;

public class Server {

    private Javalin javalin;

    public static void main(String[] args) {
        //Javalin app = Javalin.create().start(7000);
        //app.get("/", ctx -> ctx.result("OK"));
        new Server().startServer();
    }

    // There needs to be a way for the other classes to access the Javalin server
    public Javalin getApp() {
        return javalin;
    }

    public void startServer() {
        javalin = Javalin.create().start(7000);
        javalin.get("/", ctx -> ctx.result("OK"));
    }


}
