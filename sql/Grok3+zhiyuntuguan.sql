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




-- 插入类别数据
INSERT INTO book_category (name, create_by, remark) VALUES
('小说', 'admin', '文学类小说'),
('科技', 'admin', '科学技术相关书籍'),
('历史', 'admin', '历史相关书籍'),
('儿童', 'admin', '适合儿童阅读的书籍'),
('艺术', 'admin', '艺术与设计类书籍');

-- 插入区域数据
INSERT INTO book_region (name, create_by, remark) VALUES
('A区', 'admin', '一楼东侧'),
('B区', 'admin', '一楼西侧'),
('C区', 'admin', '二楼东侧'),
('D区', 'admin', '二楼西侧'),
('E区', 'admin', '三楼阅览室');

-- 插入书籍数据
INSERT INTO mng_book (name, cover, author, publisher, isbn, publish_date, category_id, region_id, quantity, status, entry_date, create_by, remark) VALUES
('活着', 'cover1.jpg', '余华', '作家出版社', '9787506365437', '1993-06-01', 1, 1, 5, 1, '2023-01-10', 'admin', '经典文学作品'),
('Python编程', 'cover2.jpg', 'Eric Matthes', '人民邮电出版社', '9787115479983', '2016-11-01', 2, 2, 3, 1, '2023-02-15', 'admin', '编程入门书籍'),
('史记', 'cover3.jpg', '司马迁', '中华书局', '9787101003048', '1959-10-01', 3, 3, 2, 1, '2023-03-20', 'admin', '中国历史经典'),
('哈利波特', 'cover4.jpg', 'J.K.罗琳', '人民文学出版社', '9787020137893', '2000-10-01', 4, 4, 10, 1, '2023-04-01', 'admin', '儿童奇幻小说'),
('素描基础', 'cover5.jpg', '张三', '美术出版社', '9787532298761', '2018-05-01', 5, 5, 4, 1, '2023-05-10', 'admin', '艺术入门教材');

-- 假设 sys_user 表已存在，插入借阅数据
INSERT INTO book_issue (user_id, book_id, issue_date, due_date, return_date, status, create_by, remark) VALUES
(1, 1, '2025-02-01 10:00:00', '2025-03-01 23:59:59', NULL, 0, 'admin', '用户借阅小说'),
(2, 2, '2025-02-05 14:30:00', '2025-03-05 23:59:59', '2025-02-20 09:00:00', 1, 'admin', '用户归还编程书'),
(3, 3, '2025-02-10 09:15:00', '2025-03-10 23:59:59', NULL, 0, 'admin', '历史爱好者借阅'),
(1, 4, '2025-02-15 16:00:00', '2025-03-15 23:59:59', NULL, 0, 'admin', '儿童书籍借阅'),
(4, 5, '2025-02-20 13:45:00', '2025-03-20 23:59:59', '2025-03-01 10:00:00', 1, 'admin', '艺术书籍已归还');

-- 插入留言数据
INSERT INTO lib_comment (user_id, content, comment_time, reply_content, reply_time, reply_by, create_by, remark) VALUES
(1, '这本书真的很棒，推荐大家读！', '2025-02-01 12:00:00', '感谢推荐，我们会考虑增加库存。', '2025-02-02 09:00:00', 'librarian', 'admin', '关于《活着》'),
(2, '借阅流程可以再优化一下吗？', '2025-02-05 15:30:00', '我们会尽快改进，感谢建议！', '2025-02-06 10:00:00', 'librarian', 'admin', '用户反馈'),
(3, '历史类书籍太少了，能多进一些吗？', '2025-02-10 10:00:00', NULL, NULL, NULL, 'admin', '书籍种类建议'),
(4, '图书馆环境很好，谢谢工作人员！', '2025-02-15 09:45:00', '感谢支持，我们会继续努力。', '2025-02-16 11:00:00', 'librarian', 'admin', '用户表扬'),
(1, '《哈利波特》还有库存吗？', '2025-02-20 14:00:00', '目前还有5本可借。', '2025-02-21 08:30:00', 'librarian', 'admin', '库存询问');