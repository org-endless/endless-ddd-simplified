package org.endless.ddd.simplified.starter.common.handler.exception.rest;

import lombok.extern.slf4j.Slf4j;
import org.endless.ddd.simplified.starter.common.exception.common.FailedException;
import org.endless.ddd.simplified.starter.common.exception.common.UnknownException;
import org.endless.ddd.simplified.starter.common.exception.model.application.command.handler.CommandHandlerNotFoundException;
import org.endless.ddd.simplified.starter.common.exception.model.application.query.handler.QueryHandlerNotFoundException;
import org.endless.ddd.simplified.starter.common.exception.model.infrastructure.data.manager.DataManagerNotFoundException;
import org.endless.ddd.simplified.starter.common.exception.model.infrastructure.data.persistence.mapper.MapperUnknownException;
import org.endless.ddd.simplified.starter.common.exception.model.sidecar.rest.RestBadRequestException;
import org.endless.ddd.simplified.starter.common.exception.model.sidecar.rest.RestNotFoundException;
import org.endless.ddd.simplified.starter.common.handler.result.type.ErrorCode;
import org.endless.ddd.simplified.starter.common.model.sidecar.rest.RestResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.NoHandlerFoundException;

import static org.endless.ddd.simplified.starter.common.utils.model.string.StringTools.addBrackets;

/**
 * AbstractRestAdapterExceptionHandler
 * <p>
 * create 2024/11/02 04:19
 * <p>
 * update 2024/11/02 06:56
 *
 * @author Deng Haozhi
 * @see RestAdapterExceptionHandler
 * @since 1.0.0
 */
@Slf4j
public abstract class AbstractRestAdapterExceptionHandler implements RestAdapterExceptionHandler {

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<RestResponse> handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        String message = addBrackets(e.getMessage());
        log.error("[{}][{}]{}", ErrorCode.BAD_REQ.getCode(), ErrorCode.BAD_REQ.getDescription(), message, e);
        return response().badRequest(ErrorCode.BAD_REQ, message);
    }

    @ExceptionHandler(RestBadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<RestResponse> handleRestBadRequestException(RestBadRequestException e) {
        String message = addBrackets(e.getMessage());
        log.error("[{}][{}]{}", ErrorCode.BAD_REQ.getCode(), ErrorCode.BAD_REQ.getDescription(), message, e);
        return response().badRequest(ErrorCode.BAD_REQ, message);
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<RestResponse> handleNoHandlerFoundException(NoHandlerFoundException e) {
        String message = addBrackets(e.getMessage());
        log.error("[{}][{}]{}", ErrorCode.NOT_FND.getCode(), ErrorCode.NOT_FND.getDescription(), message, e);
        return response().notFound(ErrorCode.NOT_FND, message);
    }

    @ExceptionHandler(DataManagerNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<RestResponse> handleDataManagerNotFoundException(DataManagerNotFoundException e) {
        String message = addBrackets(e.getMessage());
        ErrorCode errorCode = e.getErrorCode();
        log.error("[{}][{}]{}", errorCode.getCode(), errorCode.getDescription(), message, e);
        return response().notFound(errorCode, message);
    }

    @ExceptionHandler(CommandHandlerNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<RestResponse> handleCommandHandlerNotFoundException(CommandHandlerNotFoundException e) {
        String message = addBrackets(e.getMessage());
        ErrorCode errorCode = e.getErrorCode();
        log.error("[{}][{}]{}", errorCode.getCode(), errorCode.getDescription(), message, e);
        return response().notFound(errorCode, message);
    }

    @ExceptionHandler(QueryHandlerNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<RestResponse> handleQueryHandlerNotFoundException(QueryHandlerNotFoundException e) {
        String message = addBrackets(e.getMessage());
        ErrorCode errorCode = e.getErrorCode();
        log.error("[{}][{}]{}", errorCode.getCode(), errorCode.getDescription(), message, e);
        return response().notFound(errorCode, message);
    }

    @ExceptionHandler(RestNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<RestResponse> handleRestNotFoundException(RestNotFoundException e) {
        String message = addBrackets(e.getMessage());
        ErrorCode errorCode = e.getErrorCode();
        log.error("[{}][{}]{}", errorCode.getCode(), errorCode.getDescription(), message, e);
        return response().notFound(errorCode, message);
    }

    @ExceptionHandler(MapperUnknownException.class)
    @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
    public ResponseEntity<RestResponse> handleMapperException(MapperUnknownException e) {
        String message = addBrackets(e.getMessage());
        log.error("[{}]{}", ErrorCode.UNKNOWN.getDescription(), message, e);
        return response().unavailable(ErrorCode.UNKNOWN, message);
    }

    @ExceptionHandler(UnknownException.class)
    @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
    public ResponseEntity<RestResponse> handleUnknownException(UnknownException e) {
        String message = addBrackets(e.getMessage());
        ErrorCode errorCode = e.getErrorCode();
        log.error("[{}][{}]{}", errorCode.getCode(), errorCode.getDescription(), message, e);
        return response().unavailable(errorCode, message);
    }

    @ExceptionHandler(FailedException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<RestResponse> handleFailedException(FailedException e) {
        String message = addBrackets(e.getMessage());
        ErrorCode errorCode = e.getErrorCode();
        log.error("[{}][{}]{}", errorCode.getCode(), errorCode.getDescription(), message, e);
        return response().error(errorCode, message);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<RestResponse> handleException(Exception e) {
        String message = addBrackets(e.getMessage());
        log.error("[{}][{}]{}", ErrorCode.FAILURE.getCode(), ErrorCode.FAILURE.getDescription(), message, e);
        return response().error(ErrorCode.FAILURE, message);
    }
}
