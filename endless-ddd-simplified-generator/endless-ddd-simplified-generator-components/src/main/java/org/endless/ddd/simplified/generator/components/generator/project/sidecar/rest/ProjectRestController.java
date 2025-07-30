package org.endless.ddd.simplified.generator.components.generator.project.sidecar.rest;

import com.alibaba.fastjson2.JSONException;
import org.endless.ddd.simplified.generator.common.model.sidecar.rest.DDDSimplifiedGeneratorRestController;
import org.endless.ddd.simplified.generator.components.generator.project.application.command.transfer.ProjectCreateReqCTransfer;
import org.endless.ddd.simplified.generator.components.generator.project.facade.adapter.ProjectDrivingAdapter;
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
 * ProjectRestController
 * <p>项目领域Rest控制器
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
@RequestMapping("/generator/project")
public class ProjectRestController implements DDDSimplifiedGeneratorRestController {

    /**
     * 项目领域主动适配器
     */
    private final ProjectDrivingAdapter projectDrivingAdapter;

    public ProjectRestController(ProjectDrivingAdapter projectDrivingAdapter) {
        this.projectDrivingAdapter = projectDrivingAdapter;
    }

    @PostMapping("/command/create")
    @Log(message = "项目创建", value = "#command")
    public ResponseEntity<RestResponse> create(@RequestBody ProjectCreateReqCTransfer command) {
        Optional.ofNullable(command)
                .map(ProjectCreateReqCTransfer::validate)
                .orElseThrow(() -> new CommandReqTransferNullException("项目创建参数不能为空"));
        try {
            projectDrivingAdapter.create(command);
            return response().success("项目创建成功");
        } catch (JSONException | NullPointerException e) {
            throw new RestErrorException("项目创建失败", e);
        }
    }
}
