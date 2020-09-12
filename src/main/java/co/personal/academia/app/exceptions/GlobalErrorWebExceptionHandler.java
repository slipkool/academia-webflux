package co.personal.academia.app.exceptions;

import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.autoconfigure.web.ResourceProperties;
import org.springframework.boot.autoconfigure.web.reactive.error.AbstractErrorWebExceptionHandler;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.reactive.error.ErrorAttributes;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import reactor.core.publisher.Mono;

@Component
@Order(-1)
public class GlobalErrorWebExceptionHandler extends AbstractErrorWebExceptionHandler {

    public GlobalErrorWebExceptionHandler(ErrorAttributes errorAttributes, ResourceProperties resourceProperties,
            ApplicationContext applicationContext, ServerCodecConfigurer configurer) {
        super(errorAttributes, resourceProperties, applicationContext);
        this.setMessageWriters(configurer.getWriters());
    }

    @Override
    protected RouterFunction<ServerResponse> getRoutingFunction(ErrorAttributes errorAttributes) {
        return RouterFunctions.route(RequestPredicates.all(), this::renderErrorResponse);
    }

    private Mono<ServerResponse> renderErrorResponse(ServerRequest request) {
        Map<String, Object> errorPropertiesMap = getErrorAttributes(request, ErrorAttributeOptions.defaults());
        final Map<String, Object> mapException = new HashMap<>();
        Throwable ex = getError(request);

        var httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        String statusCode = String.valueOf(errorPropertiesMap.get("status"));

        switch (statusCode) {
        case "500":
            mapException.put("error", "500");
            mapException.put("excepcion", "Error Interno: ".concat(ex.getMessage()));
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
            break;
        case "400":
            mapException.put("error", "400");
            mapException.put("excepcion", "Petición incorrecta: ".concat(ex.getMessage()));
            httpStatus = HttpStatus.BAD_REQUEST;
            break;
        default:
            mapException.put("error", "XYZ");
            mapException.put("excepcion", "Otro error:".concat(ex.getMessage()));
            httpStatus = HttpStatus.CONFLICT;
            break;
        }

        return ServerResponse.status(httpStatus).contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(mapException));

    }
}
