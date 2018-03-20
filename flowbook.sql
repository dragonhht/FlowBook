use FlowBook;
-- 用户表
CREATE TABLE user (
  user_id VARCHAR(11) PRIMARY KEY COMMENT '用户编号',
  user_name VARCHAR(30) NOT NULL COMMENT '用户名',
  user_email VARCHAR(50) COMMENT '电子邮件',
  user_phone VARCHAR(11) NOT NULL COMMENT '电话号码' ,
  user_img TEXT NOT NULL COMMENT '用户头像',
  user_date TIMESTAMP NOT NULL COMMENT '用户注册时间',
  contribute_num INT COMMENT '贡献度',
  credit INT COMMENT '信用度',
  identity INT COMMENT '身份， 1 为管理员， 0 为普通用户',
  password TEXT NOT NULL COMMENT '密码'
) DEFAULT CHARSET=utf8;

-- 图书表
CREATE TABLE book (
  book_id INT PRIMARY KEY AUTO_INCREMENT COMMENT '图书编号',
  book_name VARCHAR(50) NOT NULL COMMENT '图书名',
  author VARCHAR(30) NOT NULL COMMENT '作者',
  publish VARCHAR(50) NOT NULL COMMENT '出版社',
  introduction TEXT COMMENT '简介',
  book_date TIMESTAMP NOT NULL COMMENT '上传时间',
  book_start INT DEFAULT 0 COMMENT '评价星级' ,
  book_img TEXT NOT NULL COMMENT '图书图片'
) AUTO_INCREMENT=1000000 DEFAULT CHARSET=utf8;

-- 图书评论表
CREATE TABLE comment (
  comment_id INT PRIMARY KEY AUTO_INCREMENT COMMENT '评论编号',
  comment_text TEXT NOT NULL COMMENT '评论内容' ,
  comment_date TIMESTAMP COMMENT '评论时间',
  user_id VARCHAR(11) NOT NULL ,
  book_id INT NOT NULL ,
  FOREIGN KEY (user_id) REFERENCES user(user_id) ,
  FOREIGN KEY (book_id) REFERENCES book(book_id) ON DELETE CASCADE
) CHARSET=utf8;

-- 图书路线表
CREATE TABLE book_route (
  route_id INT AUTO_INCREMENT PRIMARY KEY COMMENT '路线编号',
  book_id INT NOT NULL COMMENT '图书编号',
  user_id VARCHAR(11) NOT NULL COMMENT '用户编号',
  route_date TIMESTAMP NOT NULL COMMENT '路线创建时间',
  FOREIGN KEY (user_id) REFERENCES user(user_id) ,
  FOREIGN KEY (book_id) REFERENCES book(book_id) ON DELETE CASCADE
) CHARSET = utf8;

-- 公告表
CREATE TABLE notice (
  notice_id INT AUTO_INCREMENT PRIMARY KEY COMMENT '编号',
  notice_date TIMESTAMP NOT NULL COMMENT '发布时间',
  notice_text TEXT NOT NULL COMMENT '公告内容',
  user_id VARCHAR(11) NOT NULL ,
  FOREIGN KEY (user_id) REFERENCES user(user_id)
) CHARSET = utf8;

-- 图书类型表
CREATE TABLE type (
  type_id INT AUTO_INCREMENT PRIMARY KEY COMMENT '编号',
  type_name VARCHAR(30) NOT NULL COMMENT '类型名称'
) CHARSET = utf8;

-- 图书类型对应表
CREATE TABLE book_type (
  type_id INT COMMENT '类型编号',
  book_id INT COMMENT '图书编号',
  FOREIGN KEY (type_id) REFERENCES type(type_id),
  FOREIGN KEY (book_id) REFERENCES book(book_id) ON DELETE CASCADE
) CHARSET = utf8;

-- 借阅记录表
CREATE TABLE loan_record (
  record_id INT AUTO_INCREMENT PRIMARY KEY COMMENT '记录编号',
  is_out BOOLEAN COMMENT '是否借出标志',
  record_date TIMESTAMP COMMENT '借阅时间',
  book_id INT NOT NULL COMMENT '借阅书籍',
  user_id VARCHAR(11) NOT NULL COMMENT '借阅人',
  FOREIGN KEY (user_id) REFERENCES user(user_id),
  FOREIGN KEY (book_id) REFERENCES book(book_id) ON DELETE CASCADE
) CHARSET = utf8;

-- 用户申请表
CREATE TABLE user_apply (
  apply_id INT AUTO_INCREMENT PRIMARY KEY ,
  apply_date TIMESTAMP NOT NULL ,
  apply_text TEXT NOT NULL ,
  user_id VARCHAR(11) NOT NULL,
  book_id INT NOT NULL,
  FOREIGN KEY (user_id) REFERENCES user(user_id),
  FOREIGN KEY (book_id) REFERENCES book(book_id) ON DELETE CASCADE
) AUTO_INCREMENT=1000000 DEFAULT CHARSET=utf8;

-- 图片表
/*CREATE TABLE img (
  img_id INT AUTO_INCREMENT PRIMARY KEY ,
  img_path TEXT NOT NULL ,
  apply_id INT NOT NULL ,
  FOREIGN KEY (apply_id) REFERENCES user_apply(apply_id)
) CHARSET=utf8;*/

-- 聊天记录表
CREATE TABLE chat_record (
  chat_id BIGINT AUTO_INCREMENT PRIMARY KEY ,
  sender VARCHAR(11) NOT NULL ,
  receiver VARCHAR(11) NOT NULL ,
  message TEXT NOT NULL ,
  send_date TIMESTAMP NOT NULL ,
  looked BOOLEAN NOT NULL ,
  FOREIGN KEY (sender) REFERENCES user(user_id),
  FOREIGN KEY (receiver) REFERENCES user(user_id)
) CHARSET=utf8;

-- 图书传阅表
CREATE TABLE flow_apply (
  id INT AUTO_INCREMENT PRIMARY KEY ,
  apply_user VARCHAR(11) NOT NULL ,
  ok_user VARCHAR(11) NOT NULL ,
  book_id INT NOT NULL ,
  want_say TEXT ,
  status INT DEFAULT 0,
  apply_date TIMESTAMP NOT NULL ,
  FOREIGN KEY (apply_user) REFERENCES user(user_id),
  FOREIGN KEY (ok_user) REFERENCES user(user_id),
  FOREIGN KEY (book_id) REFERENCES book(book_id) ON DELETE CASCADE
) CHARSET=utf8;

-- 图书类型
insert INTO type VALUE (1, '文学类');
insert INTO type VALUE (2, '教科书');
insert INTO type VALUE (3, '自然科学');
insert INTO type VALUE (4, '社会科学');
insert INTO type VALUE (5, '刊物');
insert INTO type VALUE (6, '艺术');
insert INTO type VALUE (7, '综合性图书');

-- 举报图片表
CREATE TABLE report_img (
  id INT AUTO_INCREMENT PRIMARY KEY ,
  path TEXT NOT NULL
) CHARSET=utf8;

-- 举报表
CREATE TABLE report (
  report_id INT AUTO_INCREMENT PRIMARY KEY ,
  report VARCHAR(11) NOT NULL ,
  be_report VARCHAR(11) NOT NULL ,
  report_date TIMESTAMP NOT NULL ,
  report_text TEXT,
  status INT,
  FOREIGN KEY (report) REFERENCES user(user_id),
  FOREIGN KEY (be_report) REFERENCES user(user_id)
) CHARSET=utf8;

-- 好友表
CREATE TABLE friends (
  id INT AUTO_INCREMENT PRIMARY KEY ,
  friend_id VARCHAR(11) NOT NULL ,
  user_id VARCHAR(11) NOT NULL ,
  FOREIGN KEY (friend_id) REFERENCES user(user_id),
  FOREIGN KEY (friend_id) REFERENCES user(user_id)
) CHARSET = utf8;

-- 活动表
CREATE TABLE activity (
  active_id INT AUTO_INCREMENT PRIMARY KEY ,
  user_id VARCHAR(11) NOT NULL ,
  active_title VARCHAR(30) NOT NULL ,
  active_date DATE NOT NULL ,
  active_text MEDIUMTEXT,
  status INT NOT NULL COMMENT '是否通过申请, 0 为未处理, 1, 为通过, 2, 为未通过',
  FOREIGN KEY (user_id) REFERENCES user(user_id)
) AUTO_INCREMENT=1000000 DEFAULT CHARSET=utf8;

-- 管理员申请表
CREATE TABLE apply_admin(
  apply_id INT AUTO_INCREMENT PRIMARY KEY ,
  user_id VARCHAR(11) NOT NULL ,
  apply_text TEXT,
  status INT DEFAULT 0 NOT NULL COMMENT '是否通过申请, 0 为未处理, 1, 为通过, 2, 为未通过',
  FOREIGN KEY (user_id) REFERENCES user(user_id)
)DEFAULT CHARSET=utf8;