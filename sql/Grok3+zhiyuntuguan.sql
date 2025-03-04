-- 删除已存在的表
DROP TABLE IF EXISTS lib_comment;
DROP TABLE IF EXISTS book_issue;
DROP TABLE IF EXISTS mng_book;
DROP TABLE IF EXISTS book_region;
DROP TABLE IF EXISTS book_category;

-- 类别表
CREATE TABLE book_category (
    id          BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '类别ID',
    name        VARCHAR(255) NOT NULL UNIQUE COMMENT '类别名称', -- 添加 UNIQUE 约束
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    create_by   VARCHAR(64) DEFAULT '' COMMENT '创建人',
    remark      VARCHAR(500) DEFAULT NULL COMMENT '备注',
    PRIMARY KEY (id)
) ENGINE=InnoDB COMMENT='类别表';

-- 区域表
CREATE TABLE book_region (
    id          BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '区域ID',
    name        VARCHAR(255) NOT NULL UNIQUE COMMENT '区域名称', -- 添加 UNIQUE 约束
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    create_by   VARCHAR(64) DEFAULT '' COMMENT '创建人',
    remark      VARCHAR(500) DEFAULT NULL COMMENT '备注',
    PRIMARY KEY (id)
) ENGINE=InnoDB COMMENT='区域表';

-- 书籍表
CREATE TABLE mng_book (
    id            BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '书籍ID',
    name          VARCHAR(255) NOT NULL COMMENT '书籍名称',
    cover         VARCHAR(500) DEFAULT '' COMMENT '封面',
    author        VARCHAR(255) NOT NULL COMMENT '作者',
    publisher     VARCHAR(255) NOT NULL COMMENT '出版社',
    isbn          VARCHAR(20) UNIQUE NOT NULL COMMENT 'ISBN编号',
    publish_date  DATE NOT NULL COMMENT '出版日期',
    category_id   BIGINT(20) NOT NULL COMMENT '类别ID',
    region_id     BIGINT(20) NOT NULL COMMENT '区域ID',
    quantity      INT(10) NOT NULL DEFAULT 1 CHECK (quantity >= 0) COMMENT '书籍数量', -- 添加非负约束
    status        TINYINT(1) NOT NULL DEFAULT 1 CHECK (status IN (0, 1, 2)) COMMENT '状态（0不可借 1可借 2未到馆）', -- 添加取值约束
    entry_date    DATETIME NOT NULL COMMENT '入馆时间',
    create_time   DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time   DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    create_by     VARCHAR(64) DEFAULT '' COMMENT '创建人',
    remark        VARCHAR(500) DEFAULT NULL COMMENT '备注',
    PRIMARY KEY (id),
    FOREIGN KEY (category_id) REFERENCES book_category(id) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (region_id) REFERENCES book_region(id) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB COMMENT='书籍表';

-- 借阅表
CREATE TABLE book_issue (
    id          BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '借阅ID',
    user_id     BIGINT(20) NOT NULL COMMENT '用户ID',
    book_id     BIGINT(20) NOT NULL COMMENT '书籍ID',
    issue_date  DATETIME NOT NULL COMMENT '借阅日期',
    due_date    DATETIME NOT NULL COMMENT '应还日期', -- 新增应还日期字段
    return_date DATETIME DEFAULT NULL COMMENT '归还日期',
    status      TINYINT(1) NOT NULL DEFAULT 0 CHECK (status IN (0, 1)) COMMENT '状态（0未归还 1已归还）', -- 添加取值约束
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    create_by   VARCHAR(64) DEFAULT '' COMMENT '创建人',
    remark      VARCHAR(500) DEFAULT NULL COMMENT '备注',
    PRIMARY KEY (id),
    FOREIGN KEY (user_id) REFERENCES sys_user(user_id) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (book_id) REFERENCES mng_book(id) ON DELETE CASCADE ON UPDATE CASCADE
    -- 可选：防止重复借阅的约束（视需求而定）
    -- UNIQUE (user_id, book_id, status)
) ENGINE=InnoDB COMMENT='借阅表';

-- 留言表
CREATE TABLE lib_comment (
    id              BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '留言ID',
    user_id         BIGINT(20) NOT NULL COMMENT '用户ID',
    content         TEXT NOT NULL COMMENT '留言内容',
    comment_time    DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '留言时间', -- 保留但添加默认值
    reply_content   TEXT DEFAULT NULL COMMENT '回复内容',
    reply_time      DATETIME DEFAULT NULL COMMENT '回复时间',
    reply_by        VARCHAR(64) DEFAULT NULL COMMENT '回复人', -- 新增回复人字段
    create_time     DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time     DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    create_by       VARCHAR(64) DEFAULT '' COMMENT '创建人',
    remark          VARCHAR(500) DEFAULT NULL COMMENT '备注',
    PRIMARY KEY (id),
    FOREIGN KEY (user_id) REFERENCES sys_user(user_id) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB COMMENT='留言表';