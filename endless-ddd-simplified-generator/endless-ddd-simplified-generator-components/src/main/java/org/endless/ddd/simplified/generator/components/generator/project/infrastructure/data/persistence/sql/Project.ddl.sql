DROP TABLE IF EXISTS generator_project;

CREATE TABLE generator_project
(
    project_id            TEXT PRIMARY KEY,  -- 项目ID
    project_artifact_id   TEXT    NOT NULL,  -- 项目构件ID
    group_id              TEXT    NOT NULL,  -- 项目组织ID
    name                  TEXT    NOT NULL,  -- 项目名称
    description           TEXT    NOT NULL,  -- 项目描述
    version               TEXT    NOT NULL,  -- 项目版本号
    author                TEXT    NOT NULL,  -- 项目作者
    root_path             TEXT    NOT NULL,  -- 项目根路径
    base_package          TEXT    NOT NULL,  -- 项目基础包名
    enable_spring_doc     INTEGER NOT NULL,  -- 是否启用Spring Doc（布尔值用0/1表示）
    java_version          TEXT    NOT NULL,  -- 项目Java版本
    logging_framework     TEXT    NOT NULL,  -- 项目日志框架
    persistence_framework TEXT    NOT NULL,  -- 项目持久化框架
    create_user_id        TEXT    NOT NULL,  -- 创建者ID
    modify_user_id        TEXT    NOT NULL,  -- 修改者ID
    is_removed            INTEGER NOT NULL,  -- 是否已删除（布尔值用0/1表示）
    create_at             INTEGER NOT NULL,  -- 创建时间
    modify_at             INTEGER NOT NULL,  -- 修改时间
    remove_at             INTEGER DEFAULT 0, -- 删除时间
    UNIQUE (project_artifact_id, remove_at)
);
