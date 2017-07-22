use FlowBook;
-- 用户表
CREATE TABLE user (
  user_id INT PRIMARY KEY AUTO_INCREMENT COMMENT '用户编号',
  user_name VARCHAR(30) NOT NULL COMMENT '用户名',
  user_age INT comment '用户年龄',
  user_sex VARCHAR(2) COMMENT '性别',
  user_address TEXT COMMENT '住址',
  user_email VARCHAR(50) COMMENT '电子邮件',
  user_phone VARCHAR(11) NOT NULL COMMENT '电话号码' ,
  user_img TEXT NOT NULL COMMENT '用户头像',
  user_date DATE NOT NULL COMMENT '用户注册时间',
  password TEXT NOT NULL COMMENT '密码'
  CHECK (user_sex IN ('男', '女'))
) AUTO_INCREMENT=1000000 DEFAULT CHARSET=utf8;

-- 图书表
CREATE TABLE book (
  book_id INT PRIMARY KEY AUTO_INCREMENT COMMENT '图书编号',
  book_name VARCHAR(50) NOT NULL COMMENT '图书名',
  author VARCHAR(30) NOT NULL COMMENT '作者',
  publish VARCHAR(50) NOT NULL COMMENT '出版社',
  introduction TEXT COMMENT '简介',
  book_date DATE NOT NULL COMMENT '上传时间',
  book_start INT DEFAULT 0 COMMENT '评价星级' ,
  book_img TEXT NOT NULL COMMENT '图书图片',
  contributor INT NOT NULL COMMENT '图书贡献者'
) AUTO_INCREMENT=1000000 DEFAULT CHARSET=utf8;

-- 图书评论表
CREATE TABLE comment (
  comment_id INT PRIMARY KEY AUTO_INCREMENT COMMENT '评论编号',
  comment_text TEXT NOT NULL COMMENT '评论内容' ,
  comment_date DATE COMMENT '评论时间',
  user_id INT NOT NULL ,
  book_id INT NOT NULL ,
  FOREIGN KEY (book_id) REFERENCES book(book_id),
  FOREIGN KEY (user_id) REFERENCES user(user_id)
) CHARSET=utf8;

-- 图书路线表
CREATE TABLE book_route (
  route_id INT AUTO_INCREMENT PRIMARY KEY COMMENT '路线编号',
  book_id INT NOT NULL COMMENT '图书编号',
  user_id INT NOT NULL COMMENT '用户编号',
  route_date DATE NOT NULL COMMENT '路线创建时间',
  FOREIGN KEY (book_id) REFERENCES book(book_id),
  FOREIGN KEY (user_id) REFERENCES user(user_id)
) CHARSET = utf8;

-- 公告表
CREATE TABLE notice (
  notice_id INT AUTO_INCREMENT PRIMARY KEY COMMENT '编号',
  notice_date DATE NOT NULL COMMENT '发布时间',
  notice_text TEXT NOT NULL COMMENT '公告内容',
  user_id INT NOT NULL ,
  FOREIGN KEY (user_id) REFERENCES user(user_id)
) CHARSET = utf8;

-- 图书类型表
CREATE TABLE type (
  type_id INT AUTO_INCREMENT PRIMARY KEY COMMENT '编号',
  type_name VARCHAR(30) NOT NULL COMMENT '类型名称'
)