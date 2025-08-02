package org.endless.ddd.simplified.generator.components.generator.service.sidecar.rest;

import com.alibaba.fastjson2.JSONException;
import org.endless.ddd.simplified.generator.common.model.sidecar.rest.DDDSimplifiedGeneratorRestController;
import org.endless.ddd.simplified.generator.components.generator.service.application.command.transfer.ServiceCreateReqCTransfer;
import org.endless.ddd.simplified.generator.components.generator.service.facade.adapter.ServiceDrivingAdapter;
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
 * ServiceRestController
 * <p>
 * 服务领域Rest控制器
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
@RequestMapping("/generator/service")
public class ServiceRestController implements DDDSimplifiedGeneratorRestController {

    /**
     * 服务领域主动适配器
     */
    private final ServiceDrivingAdapter serviceDrivingAdapter;

    public ServiceRestController(ServiceDrivingAdapter serviceDrivingAdapter) {
        this.serviceDrivingAdapter = serviceDrivingAdapter;
    }

    @PostMapping("/command/create")
    @Log(message = "服务创建", value = "#command")
    public ResponseEntity<RestResponse> create(@RequestBody ServiceCreateReqCTransfer command) {
        Optional.ofNullable(command)
                .map(ServiceCreateReqCTransfer::validate)
                .orElseThrow(() -> new CommandReqTransferNullException("服务创建建参数不能为空"));
        try {
            return response().success("服务创建成功", serviceDrivingAdapter.create(command));
        } catch (JSONException | NullPointerException e) {
            throw new RestErrorException("服务创建失败", e);
        }
    }
}
