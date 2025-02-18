package org.endless.ddd.simplified.starter.common.config.data.persistence.mybatis.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.endless.ddd.simplified.starter.common.utils.model.time.TimeStamp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * MybatisPlusMetaObjectHandler
 * <p>Mybatis Plus 拦截器，用于处理自动填充字段
 * <p>
 * create 2024/09/20 15:04
 * <p>
 * update 2024/11/17 16:01
 *
 * @author Deng Haozhi
 * @see MetaObjectHandler
 * @since 1.0.0
 */
public class MybatisPlusMetaObjectHandler implements MetaObjectHandler {


    @Override
    public void insertFill(MetaObject metaObject) {
        Long now = TimeStamp.now();
        this.strictInsertFill(metaObject, "createAt", Long.class, now);
        this.strictInsertFill(metaObject, "modifyAt", Long.class, now);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        Long now = TimeStamp.now();
        this.strictUpdateFill(metaObject, "modifyAt", Long.class, now);
    }
}
