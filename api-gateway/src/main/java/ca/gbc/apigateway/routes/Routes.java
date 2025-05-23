package ca.gbc.apigateway.routes;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.server.mvc.filter.CircuitBreakerFilterFunctions;
import org.springframework.cloud.gateway.server.mvc.handler.GatewayRouterFunctions;
import org.springframework.cloud.gateway.server.mvc.handler.HandlerFunctions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.function.RequestPredicates;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;

import java.net.URI;

import static org.springframework.cloud.gateway.server.mvc.filter.FilterFunctions.setPath;
import static org.springframework.cloud.gateway.server.mvc.handler.GatewayRouterFunctions.route;

@Configuration
@Slf4j
public class Routes {
    @Value("${services.room.url}")
    private String roomServiceUrl;

    @Value("${services.booking.url}")
    private String bookingServiceUrl;

    @Value("${services.user.url}")
    private String userServiceUrl;

    @Value("${services.event.url}")
    private String eventServiceUrl;

    @Value("${services.approval.url}")
    private String approvalServiceUrl;

    @Bean
    public RouterFunction<ServerResponse> roomServiceRoute(){
        log.info("Initializing product service route with URL: {}", roomServiceUrl);

        return GatewayRouterFunctions.route("room_service")
                .route(RequestPredicates.path("/api/room"), request -> {
                    log.info("Received request for product service: {}", request.uri());
                    try{
                        ServerResponse response = HandlerFunctions.http(roomServiceUrl).handle(request);
                        log.info("Response status: {}", response.statusCode());
                        return response;
                    }catch(Exception e){
                        log.error("Error occurred while routing request: {}", e.getMessage());
                        return ServerResponse.status(500).body("An error occurred routing");
                    }
                })
                .route(RequestPredicates.path("/api/room/{id}"), request -> {
                    log.info("Received request for product service: {}", request.uri());
                    try{
                        ServerResponse response = HandlerFunctions.http(roomServiceUrl).handle(request);
                        log.info("Response status: {}", response.statusCode());
                        return response;
                    }catch(Exception e){
                        log.error("Error occurred while routing request: {}", e.getMessage());
                        return ServerResponse.status(500).body("An error occurred routing");
                    }
                })
                .route(RequestPredicates.path("/api/room/available"), request -> {
                    log.info("Received request for product service: {}", request.uri());
                    try{
                        ServerResponse response = HandlerFunctions.http(roomServiceUrl).handle(request);
                        log.info("Response status: {}", response.statusCode());
                        return response;
                    }catch(Exception e){
                        log.error("Error occurred while routing request: {}", e.getMessage());
                        return ServerResponse.status(500).body("An error occurred routing");
                    }
                })
                .route(RequestPredicates.path("/api/room/available/{id}"), request -> {
                    log.info("Received request for product service: {}", request.uri());
                    try{
                        ServerResponse response = HandlerFunctions.http(roomServiceUrl).handle(request);
                        log.info("Response status: {}", response.statusCode());
                        return response;
                    }catch(Exception e){
                        log.error("Error occurred while routing request: {}", e.getMessage());
                        return ServerResponse.status(500).body("An error occurred routing");
                    }
                })
                .route(RequestPredicates.path("/api/room/deleteeverything"), request -> {
                    log.info("Received request for product service: {}", request.uri());
                    try{
                        ServerResponse response = HandlerFunctions.http(roomServiceUrl).handle(request);
                        log.info("Response status: {}", response.statusCode());
                        return response;
                    }catch(Exception e){
                        log.error("Error occurred while routing request: {}", e.getMessage());
                        return ServerResponse.status(500).body("An error occurred routing");
                    }
                })
                .filter(CircuitBreakerFilterFunctions
                        .circuitBreaker("roomServiceCircuitBreaker", URI.create("forward:/fallbackRoute")))
                .build();

    }

    @Bean
    public RouterFunction<ServerResponse> bookingServiceRoute(){
        log.info("Initializing product service route with URL: {}", bookingServiceUrl);

        return GatewayRouterFunctions.route("booking_service")
                .route(RequestPredicates.path("/api/booking"), request -> {
                    log.info("Received request for product service: {}", request.uri());
                    try{
                        ServerResponse response = HandlerFunctions.http(bookingServiceUrl).handle(request);
                        log.info("Response status: {}", response.statusCode());
                        return response;
                    }catch(Exception e){
                        log.error("Error occurred while routing request: {}", e.getMessage());
                        return ServerResponse.status(500).body("An error occurred routing");
                    }

                })
                .route(RequestPredicates.path("/api/booking/{id}"), request -> {
                    log.info("Received request for product service: {}", request.uri());
                    try{
                        ServerResponse response = HandlerFunctions.http(bookingServiceUrl).handle(request);
                        log.info("Response status: {}", response.statusCode());
                        return response;
                    }catch(Exception e){
                        log.error("Error occurred while routing request: {}", e.getMessage());
                        return ServerResponse.status(500).body("An error occurred routing");
                    }

                })
                .filter(CircuitBreakerFilterFunctions
                        .circuitBreaker("bookingServiceCircuitBreaker", URI.create("forward:/fallbackRoute")))
                .build();

    }

    @Bean
    public RouterFunction<ServerResponse> userServiceRoute(){
        log.info("Initializing product service route with URL: {}", userServiceUrl);

        return GatewayRouterFunctions.route("user_service")
                .route(RequestPredicates.path("/api/user"), request -> {
                    log.info("Received request for product service: {}", request.uri());
                    try{
                        ServerResponse response = HandlerFunctions.http(userServiceUrl).handle(request);
                        log.info("Response status: {}", response.statusCode());
                        return response;
                    }catch(Exception e){
                        log.error("Error occurred while routing request: {}", e.getMessage());
                        return ServerResponse.status(500).body("An error occurred routing");
                    }

                })
                .route(RequestPredicates.path("/api/user/{id}"), request -> {
                    log.info("Received request for product service: {}", request.uri());
                    try{
                        ServerResponse response = HandlerFunctions.http(userServiceUrl).handle(request);
                        log.info("Response status: {}", response.statusCode());
                        return response;
                    }catch(Exception e){
                        log.error("Error occurred while routing request: {}", e.getMessage());
                        return ServerResponse.status(500).body("An error occurred routing");
                    }

                })
                .route(RequestPredicates.path("/api/user/approve/{id}"), request -> {
                    log.info("Received request for product service: {}", request.uri());
                    try{
                        ServerResponse response = HandlerFunctions.http(userServiceUrl).handle(request);
                        log.info("Response status: {}", response.statusCode());
                        return response;
                    }catch(Exception e){
                        log.error("Error occurred while routing request: {}", e.getMessage());
                        return ServerResponse.status(500).body("An error occurred routing");
                    }

                })
                .route(RequestPredicates.path("/api/user/deleteeverything"), request -> {
                    log.info("Received request for product service: {}", request.uri());
                    try{
                        ServerResponse response = HandlerFunctions.http(userServiceUrl).handle(request);
                        log.info("Response status: {}", response.statusCode());
                        return response;
                    }catch(Exception e){
                        log.error("Error occurred while routing request: {}", e.getMessage());
                        return ServerResponse.status(500).body("An error occurred routing");
                    }
                })
                .route(RequestPredicates.path("/api/user/**"), request -> {
                    log.info("Received request for product service: {}", request.uri());
                    try{
                        ServerResponse response = HandlerFunctions.http(userServiceUrl).handle(request);
                        log.info("Response status: {}", response.statusCode());
                        return response;
                    }catch(Exception e){
                        log.error("Error occurred while routing request: {}", e.getMessage());
                        return ServerResponse.status(500).body("An error occurred routing");
                    }

                })
                .filter(CircuitBreakerFilterFunctions
                        .circuitBreaker("userServiceCircuitBreaker", URI.create("forward:/fallbackRoute")))
                .build();

    }

    @Bean
    public RouterFunction<ServerResponse> eventServiceRoute(){
        log.info("Initializing product service route with URL: {}", eventServiceUrl);

        return GatewayRouterFunctions.route("event_service")
                .route(RequestPredicates.path("/api/event"), request -> {
                    log.info("Received request for product service: {}", request.uri());
                    try{
                        ServerResponse response = HandlerFunctions.http(eventServiceUrl).handle(request);
                        log.info("Response status: {}", response.statusCode());
                        return response;
                    }catch(Exception e){
                        log.error("Error occurred while routing request: {}", e.getMessage());
                        return ServerResponse.status(500).body("An error occurred routing");
                    }

                })
                .route(RequestPredicates.path("/api/event/{id}"), request -> {
                    log.info("Received request for product service: {}", request.uri());
                    try{
                        ServerResponse response = HandlerFunctions.http(eventServiceUrl).handle(request);
                        log.info("Response status: {}", response.statusCode());
                        return response;
                    }catch(Exception e){
                        log.error("Error occurred while routing request: {}", e.getMessage());
                        return ServerResponse.status(500).body("An error occurred routing");
                    }

                })
                .route(RequestPredicates.path("/api/event/**"), request -> {
                    log.info("Received request for product service: {}", request.uri());
                    try{
                        ServerResponse response = HandlerFunctions.http(eventServiceUrl).handle(request);
                        log.info("Response status: {}", response.statusCode());
                        return response;
                    }catch(Exception e){
                        log.error("Error occurred while routing request: {}", e.getMessage());
                        return ServerResponse.status(500).body("An error occurred routing");
                    }

                })
                .filter(CircuitBreakerFilterFunctions
                        .circuitBreaker("eventServiceCircuitBreaker", URI.create("forward:/fallbackRoute")))
                .build();

    }

    @Bean
    public RouterFunction<ServerResponse> approvalServiceRoute(){
        log.info("Initializing product service route with URL: {}", approvalServiceUrl);

        return GatewayRouterFunctions.route("approval_service")
                .route(RequestPredicates.path("/api/approval"), request -> {
                    log.info("Received request for product service: {}", request.uri());
                    try{
                        ServerResponse response = HandlerFunctions.http(approvalServiceUrl).handle(request);
                        log.info("Response status: {}", response.statusCode());
                        return response;
                    }catch(Exception e){
                        log.error("Error occurred while routing request: {}", e.getMessage());
                        return ServerResponse.status(500).body("An error occurred routing");
                    }
                })
                .route(RequestPredicates.path("/api/approval/{id}"), request -> {
                    log.info("Received request for product service: {}", request.uri());
                    try{
                        ServerResponse response = HandlerFunctions.http(approvalServiceUrl).handle(request);
                        log.info("Response status: {}", response.statusCode());
                        return response;
                    }catch(Exception e){
                        log.error("Error occurred while routing request: {}", e.getMessage());
                        return ServerResponse.status(500).body("An error occurred routing");
                    }
                })
                .route(RequestPredicates.path("/api/approval/approve"), request -> {
                    log.info("Received request for product service: {}", request.uri());
                    try{
                        ServerResponse response = HandlerFunctions.http(approvalServiceUrl).handle(request);
                        log.info("Response status: {}", response.statusCode());
                        return response;
                    }catch(Exception e){
                        log.error("Error occurred while routing request: {}", e.getMessage());
                        return ServerResponse.status(500).body("An error occurred routing");
                    }
                })
                .filter(CircuitBreakerFilterFunctions
                        .circuitBreaker("approvalServiceCircuitBreaker", URI.create("forward:/fallbackRoute")))
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> roomServiceSwaggerRoute(){

        return route("room-service-swagger")
                .route(RequestPredicates.path("/aggregate/room-service/v3/api-docs"),
                        HandlerFunctions.http(roomServiceUrl))
                .filter(setPath("/api-docs"))
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> bookingServiceSwaggerRoute(){

        return route("booking-service-swagger")
                .route(RequestPredicates.path("/aggregate/booking-service/v3/api-docs"),
                        HandlerFunctions.http(bookingServiceUrl))
                .filter(setPath("/api-docs"))
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> eventServiceSwaggerRoute(){

        return route("event-service-swagger")
                .route(RequestPredicates.path("/aggregate/event-service/v3/api-docs"),
                        HandlerFunctions.http(eventServiceUrl))
                .filter(setPath("/api-docs"))
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> approvalServiceSwaggerRoute(){

        return route("approval-service-swagger")
                .route(RequestPredicates.path("/aggregate/approval-service/v3/api-docs"),
                        HandlerFunctions.http(approvalServiceUrl))
                .filter(setPath("/api-docs"))
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> userServiceSwaggerRoute(){

        return route("user-service-swagger")
                .route(RequestPredicates.path("/aggregate/user-service/v3/api-docs"),
                        HandlerFunctions.http(userServiceUrl))
                .filter(setPath("/api-docs"))
                .build();
    }

    //Resilience4j - fallback route
    @Bean
    public RouterFunction<ServerResponse> fallbackRoute() {
        return route( "fallbackRoute")
                .route(RequestPredicates.all(),
                        request -> ServerResponse.status(HttpStatus.SERVICE_UNAVAILABLE)
                                .body("Circuit breaker: Service is Temporarily Unavailable, please try again later"))
                .build();
    }









}
