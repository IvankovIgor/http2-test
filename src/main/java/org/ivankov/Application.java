package org.ivankov;

import one.nio.http.HttpServer;
import one.nio.http.HttpServerConfig;
import one.nio.http.HttpSession;
import one.nio.http.Path;
import one.nio.http.Request;
import one.nio.http.Response;
import one.nio.server.AcceptorConfig;
import one.nio.util.Utf8;

import java.io.IOException;

//@SpringBootApplication

//    public static void main(String[] args){
//        SpringApplication.run(Application.class, args);
//    }
//    public static void main(final String[] args) {
//        Undertow server = Undertow.builder()
//                .addHttpListener(8080, "localhost")
//                .setHandler(exchange -> {
//                    exchange.getResponseHeaders().put(Headers.CONTENT_TYPE, "text/plain");
//                    exchange.getResponseSender().send("Hello World");
//                }).build();
//        server.start();
//    }
public class Application extends HttpServer {

    public Application(HttpServerConfig config) throws IOException {
        super(config);
    }
    @Path("/simple")
    public Response handleSimple() {
        return Response.ok("Simple");
    }

    @Override
    public void handleRequest(Request request, HttpSession session) throws IOException {
        try {
            super.handleRequest(request, session);
        } catch (RuntimeException e) {
            session.sendError(Response.BAD_REQUEST, e.toString());
        }
    }

    @Override
    public void handleDefault(Request request, HttpSession session) throws IOException {
        Response response = Response.ok(Utf8.toBytes("<html><body><pre>Default</pre></body></html>"));
        response.addHeader("Content-Type: text/html");
        session.sendResponse(response);
    }

    public static void main(String[] args) throws Exception {
        HttpServerConfig config = create(8080);

        Application server = new Application(config);
        server.start();
    }

    public static HttpServerConfig create(int port) {
        AcceptorConfig ac = new AcceptorConfig();
        ac.port = port;

        HttpServerConfig config = new HttpServerConfig();
        config.acceptors = new AcceptorConfig[]{ac};
        return config;
    }
}
