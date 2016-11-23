CREATE DATABASE fmp DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;
CREATE TABLE
    t00_dict
    (
        disctype VARCHAR(64) NOT NULL COMMENT '码表类型',
        disctypename VARCHAR(128) COMMENT '码表类型名称',
        disckey VARCHAR(32) NOT NULL COMMENT '码值',
        discname VARCHAR(128) COMMENT '码值名称',
        flag CHAR(1) NOT NULL COMMENT '是否有效，0-无效；1-有效',
        seq VARCHAR(64) COMMENT '排序',
        PRIMARY KEY (disctype, disckey)
    )
    ENGINE=InnoDB DEFAULT CHARSET=utf8;
CREATE TABLE
    t00_jcs_sql
    (
        jname VARCHAR(64) NOT NULL COMMENT '缓存名称',
        jsql VARCHAR(4000) NOT NULL COMMENT '缓存SQL',
        jdto VARCHAR(128) COMMENT '实体类',
        flag CHAR(1) COMMENT '是否可用，0-否；1-是',
        PRIMARY KEY (jname)
    )
    ENGINE=InnoDB DEFAULT CHARSET=utf8;
CREATE TABLE
    t00_system_param
    (
        paramkey VARCHAR(64) NOT NULL COMMENT '参数编码',
        paramvalue VARCHAR(64) NOT NULL COMMENT '参数',
        paramdesc VARCHAR(128) NOT NULL COMMENT '描述',
        flag CHAR(1) NOT NULL COMMENT '评论时间',
        PRIMARY KEY (paramkey)
    )
    ENGINE=InnoDB DEFAULT CHARSET=utf8;
CREATE TABLE
    t01_common_interface
    (
        ciid VARCHAR(64) NOT NULL COMMENT '接口编号',
        ci_title VARCHAR(512) NOT NULL COMMENT '接口名称',
        execsql VARCHAR(4000) NOT NULL COMMENT '执行SQL',
        type CHAR(1) NOT NULL COMMENT '接口类型，0-查询；1-更新',
        flag CHAR(1) NOT NULL COMMENT '是否有效，0-无效；1-有效',
        ci_desc VARCHAR(512) COMMENT '接口描述',
        execclass VARCHAR(128) COMMENT '特殊处理实现类，实现cjp.bo.CommonInterfaceService接口',
        PRIMARY KEY (ciid)
    )
    ENGINE=InnoDB DEFAULT CHARSET=utf8;