package cs3321;

import Game.Monopoly;
import io.javalin.Javalin;
import org.eclipse.jetty.util.ssl.SslContextFactory;
import org.eclipse.jetty.util.thread.QueuedThreadPool;
import org.eclipse.jetty.server.Server;

import static io.javalin.apibuilder.ApiBuilder.get;
import static io.javalin.apibuilder.ApiBuilder.post;

public class MoServer {

    private Javalin javalin;

    public static void main(String[] args) {
        Monopoly game = new Monopoly();
        QueuedThreadPool queuedThreadPool = new QueuedThreadPool(200, 8, 60000);

        Javalin app = Javalin.create(config ->
                config.server(() ->
                        new Server(queuedThreadPool))).start(7000);

        app.routes(() ->{
            get("game/status", ctx ->{
                ctx.result("OK");
            });
            post("api/diceroll",ctx ->{

            });
        });
    }

    // There needs to be a way for the other classes to access the Javalin server





}
