package org.endless.ddd.simplified.generator.components.generator.domain.sidecar.rest;

import com.alibaba.fastjson2.JSONException;
import org.endless.ddd.simplified.generator.common.model.sidecar.rest.DDDSimplifiedGeneratorRestController;
import org.endless.ddd.simplified.generator.components.generator.domain.application.command.transfer.DomainCreateReqCTransfer;
import org.endless.ddd.simplified.generator.components.generator.domain.facade.adapter.DomainDrivingAdapter;
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
 * DomainRestController
 * <p>
 * 聚合领域Rest控制器
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
@RequestMapping("/generator/aggregate")
public class DomainRestController implements DDDSimplifiedGeneratorRestController {

    /**
     * 聚合领域主动适配器
     */
    private final DomainDrivingAdapter aggregateDrivingAdapter;

    public DomainRestController(DomainDrivingAdapter aggregateDrivingAdapter) {
        this.aggregateDrivingAdapter = aggregateDrivingAdapter;
    }

    @PostMapping("/command/create")
    @Log(message = "聚合创建", value = "#command")
    public ResponseEntity<RestResponse> create(@RequestBody DomainCreateReqCTransfer command) {
        Optional.ofNullable(command)
                .map(DomainCreateReqCTransfer::validate)
                .orElseThrow(() -> new CommandReqTransferNullException("聚合创建参数不能为空"));
        try {
            aggregateDrivingAdapter.create(command);
            return response().success("聚合创建成功");
        } catch (JSONException | NullPointerException e) {
            throw new RestErrorException("聚合创建失败", e);
        }
    }
}
