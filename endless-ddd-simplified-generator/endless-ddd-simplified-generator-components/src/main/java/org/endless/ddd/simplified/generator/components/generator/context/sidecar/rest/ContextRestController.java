package org.endless.ddd.simplified.generator.components.generator.context.sidecar.rest;

import com.alibaba.fastjson2.JSONException;
import org.endless.ddd.simplified.generator.common.model.sidecar.rest.DDDSimplifiedGeneratorRestController;
import org.endless.ddd.simplified.generator.components.generator.context.application.command.transfer.ContextCreateReqCTransfer;
import org.endless.ddd.simplified.generator.components.generator.context.facade.adapter.ContextDrivingAdapter;
import org.endless.ddd.simplified.starter.common.config.log.annotation.Log;
import org.endless.ddd.simplified.starter.common.exception.model.application.command.transfer.CommandReqTransferNullException;
import org.endless.ddd.simplified.starter.common.exception.model.sidecar.rest.RestErrorException;
import org.endless.ddd.simplified.starter.common.model.sidecar.rest.RestResponse;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

/**
 * ContextRestController
 * <p>
 * 上下文领域Rest控制器
 * <p>
 * create 2025/07/29 16:16
 * <p>
 * update 2025/07/29 16:16
 *
 * @author Deng Haozhi
 * @see DDDSimplifiedGeneratorRestController
 * @since 0.0.1
 */
@Lazy
@RestController
@RequestMapping("/generator/context")
public class ContextRestController implements DDDSimplifiedGeneratorRestController {

    /**
     * 上下文领域主动适配器
     */
    private final ContextDrivingAdapter contextDrivingAdapter;

    public ContextRestController(ContextDrivingAdapter contextDrivingAdapter) {
        this.contextDrivingAdapter = contextDrivingAdapter;
    }

    @PostMapping("/command/create")
    @Log(message = "上下文创建", value = "#command")
    public ResponseEntity<RestResponse> create(@RequestBody ContextCreateReqCTransfer command) {
        Optional.ofNullable(command)
                .map(ContextCreateReqCTransfer::validate)
                .orElseThrow(() -> new CommandReqTransferNullException("上下文创建参数不能为空"));
        try {
            contextDrivingAdapter.create(command);
            return response().success("上下文创建成功");
        } catch (JSONException | NullPointerException e) {
            throw new RestErrorException("上下文创建失败", e);
        }
    }
}