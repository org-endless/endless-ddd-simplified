package org.endless.ddd.simplified.starter.common.model.sidecar.rest;

import io.swagger.v3.oas.annotations.media.Schema;
import org.endless.ddd.simplified.starter.common.handler.result.type.ErrorCode;
import org.endless.ddd.simplified.starter.common.model.common.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.endless.ddd.simplified.starter.common.utils.model.string.StringTools.addBrackets;


/**
 * RestResponse
 * <p>Service Mesh Sidecar
 * <p>Rest通讯响应信息模版
 * <p>
 * create 2024/09/06 13:02
 * <p>
 * update 2024/09/06 13:04
 *
 * @author Deng Haozhi
 * @see Response
 * @since 1.0.0
 */
@Schema(description = "通用的响应格式", name = "Response", implementation = AbstractRestResponse.class)
public interface RestResponse extends Response {

    RestResponse createInstance(String status, String errorCode, String message, Object data);

    String getStatus();

    String getErrorCode();

    String getMessage();

    Object getData();

    String getServiceDescription();

    default ResponseEntity<RestResponse> response(String status, String errorCode, String message, Object data) {
        message = addBrackets(message);
        return new ResponseEntity<>(createInstance(status, errorCode, message, data), HttpStatus.valueOf(Integer.parseInt(status)));
    }

    default ResponseEntity<RestResponse> success() {
        String message = "[" + ErrorCode.SUCCESS.getDescription() + "]";
        return response(String.valueOf(HttpStatus.OK.value()), ErrorCode.SUCCESS.getCode(), message, null);
    }

    default ResponseEntity<RestResponse> success(String message) {
        message = "[" + ErrorCode.SUCCESS.getDescription() + "]" + addBrackets(message);
        return response(String.valueOf(HttpStatus.OK.value()), ErrorCode.SUCCESS.getCode(), message, null);
    }

    default ResponseEntity<RestResponse> success(Object data) {
        String message = "[" + ErrorCode.SUCCESS.getDescription() + "]";
        return response(String.valueOf(HttpStatus.OK.value()), ErrorCode.SUCCESS.getCode(), message, data);
    }

    default ResponseEntity<RestResponse> success(String message, Object data) {
        message = "[" + ErrorCode.SUCCESS.getDescription() + "]" + addBrackets(message);
        return response(String.valueOf(HttpStatus.OK.value()), ErrorCode.SUCCESS.getCode(), message, data);
    }

    default ResponseEntity<RestResponse> error(ErrorCode errorCode) {
        String message = "[" + ErrorCode.FAILURE.getDescription() + "]";
        if (errorCode != ErrorCode.FAILURE) {
            message = message + addBrackets(errorCode.getDescription());
        }
        return response(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()), errorCode.getCode(), message, null);
    }

    default ResponseEntity<RestResponse> error(ErrorCode errorCode, String message) {
        message = "[" + errorCode.getDescription() + "]" + addBrackets(message);
        return response(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()), errorCode.getCode(), message, null);
    }

    default ResponseEntity<RestResponse> error(ErrorCode errorCode, Object data) {
        String message = "[" + ErrorCode.FAILURE.getDescription() + "]";
        if (errorCode != ErrorCode.FAILURE) {
            message = message + addBrackets(errorCode.getDescription());
        }
        return response(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()), errorCode.getCode(), message, data);
    }

    default ResponseEntity<RestResponse> error(ErrorCode errorCode, String message, Object data) {
        message = "[" + errorCode.getDescription() + "]" + addBrackets(message);
        return response(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()), errorCode.getCode(), message, data);
    }

    default ResponseEntity<RestResponse> unavailable(ErrorCode errorCode) {
        String message = "[" + ErrorCode.UNKNOWN.getDescription() + "]";
        if (errorCode != ErrorCode.UNKNOWN) {
            message = message + addBrackets(errorCode.getDescription());
        }
        return response(String.valueOf(HttpStatus.SERVICE_UNAVAILABLE.value()), errorCode.getCode(), message, null);
    }

    default ResponseEntity<RestResponse> unavailable(ErrorCode errorCode, String message) {
        message = "[" + errorCode.getDescription() + "]" + addBrackets(message);
        return response(String.valueOf(HttpStatus.SERVICE_UNAVAILABLE.value()), errorCode.getCode(), message, null);
    }

    default ResponseEntity<RestResponse> unavailable(ErrorCode errorCode, Object data) {
        String message = "[" + ErrorCode.UNKNOWN.getDescription() + "]";
        if (errorCode != ErrorCode.UNKNOWN) {
            message = message + addBrackets(errorCode.getDescription());
        }
        return response(String.valueOf(HttpStatus.SERVICE_UNAVAILABLE.value()), errorCode.getCode(), message, data);
    }

    default ResponseEntity<RestResponse> unavailable(ErrorCode errorCode, String message, Object data) {
        message = "[" + errorCode.getDescription() + "]" + addBrackets(message);
        return response(String.valueOf(HttpStatus.SERVICE_UNAVAILABLE.value()), errorCode.getCode(), message, data);
    }

    default ResponseEntity<RestResponse> unauthorized(ErrorCode errorCode) {
        String message = "[" + ErrorCode.UN_AUTH.getDescription() + "]";
        if (errorCode != ErrorCode.UN_AUTH) {
            message = message + addBrackets(errorCode.getDescription());
        }
        return response(String.valueOf(HttpStatus.UNAUTHORIZED.value()), errorCode.getCode(), message, null);
    }

    default ResponseEntity<RestResponse> unauthorized(ErrorCode errorCode, String message) {
        message = "[" + errorCode.getDescription() + "]" + addBrackets(message);
        return response(String.valueOf(HttpStatus.UNAUTHORIZED.value()), errorCode.getCode(), message, null);
    }

    default ResponseEntity<RestResponse> unauthorized(ErrorCode errorCode, Object data) {
        String message = "[" + ErrorCode.UN_AUTH.getDescription() + "]";
        if (errorCode != ErrorCode.UN_AUTH) {
            message = message + addBrackets(errorCode.getDescription());
        }
        return response(String.valueOf(HttpStatus.UNAUTHORIZED.value()), errorCode.getCode(), message, data);
    }

    default ResponseEntity<RestResponse> unauthorized(ErrorCode errorCode, String message, Object data) {
        message = "[" + errorCode.getDescription() + "]" + addBrackets(message);
        return response(String.valueOf(HttpStatus.UNAUTHORIZED.value()), errorCode.getCode(), message, data);
    }

    default ResponseEntity<RestResponse> badRequest(ErrorCode errorCode) {
        String message = "[" + ErrorCode.BAD_REQ.getDescription() + "]";
        if (errorCode != ErrorCode.BAD_REQ) {
            message = message + addBrackets(errorCode.getDescription());
        }
        return response(String.valueOf(HttpStatus.BAD_REQUEST.value()), errorCode.getCode(), message, null);
    }

    default ResponseEntity<RestResponse> badRequest(ErrorCode errorCode, String message) {
        message = "[" + errorCode.getDescription() + "]" + addBrackets(message);
        return response(String.valueOf(HttpStatus.BAD_REQUEST.value()), errorCode.getCode(), message, null);
    }

    default ResponseEntity<RestResponse> badRequest(ErrorCode errorCode, Object data) {
        String message = "[" + ErrorCode.BAD_REQ.getDescription() + "]";
        if (errorCode != ErrorCode.BAD_REQ) {
            message = message + addBrackets(errorCode.getDescription());
        }
        return response(String.valueOf(HttpStatus.BAD_REQUEST.value()), errorCode.getCode(), message, data);
    }

    default ResponseEntity<RestResponse> badRequest(ErrorCode errorCode, String message, Object data) {
        message = "[" + errorCode.getDescription() + "]" + addBrackets(message);
        return response(String.valueOf(HttpStatus.BAD_REQUEST.value()), errorCode.getCode(), message, data);
    }

    default ResponseEntity<RestResponse> notFound(ErrorCode errorCode) {
        String message = "[" + ErrorCode.NOT_FND.getDescription() + "]";
        if (errorCode != ErrorCode.NOT_FND) {
            message = message + addBrackets(errorCode.getDescription());
        }
        return response(String.valueOf(HttpStatus.NOT_FOUND.value()), errorCode.getCode(), message, null);
    }

    default ResponseEntity<RestResponse> notFound(ErrorCode errorCode, String message) {
        message = "[" + errorCode.getDescription() + "]" + addBrackets(message);
        return response(String.valueOf(HttpStatus.NOT_FOUND.value()), errorCode.getCode(), message, null);
    }

    default ResponseEntity<RestResponse> notFound(ErrorCode errorCode, Object data) {
        String message = "[" + ErrorCode.NOT_FND.getDescription() + "]";
        if (errorCode != ErrorCode.NOT_FND) {
            message = message + addBrackets(errorCode.getDescription());
        }
        return response(String.valueOf(HttpStatus.NOT_FOUND.value()), errorCode.getCode(), message, data);
    }

    default ResponseEntity<RestResponse> notFound(ErrorCode errorCode, String message, Object data) {
        message = "[" + errorCode.getDescription() + "]" + addBrackets(message);
        return response(String.valueOf(HttpStatus.NOT_FOUND.value()), errorCode.getCode(), message, data);
    }
}
