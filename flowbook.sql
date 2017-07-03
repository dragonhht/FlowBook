# 用户表
CREATE TABLE user (
  user_id INT PRIMARY KEY AUTO_INCREMENT COMMENT '用户编号',
  user_name VARCHAR(30) NOT NULL COMMENT '用户名',
  user_age INT comment '用户年龄',
  user_sex VARCHAR(2) CHECK (user_sex IN ('男', '女')) COMMENT '性别',
  user_address TEXT COMMENT '住址',
  user_email VARCHAR(50) COMMENT '电子邮件',
  user_phone VARCHAR(11) NOT NULL COMMENT '电话号码' ,
  user_img TEXT NOT NULL COMMENT '用户头像'
) AUTO_INCREMENT=1000000 DEFAULT CHARSET=utf8;

# 图书表
CREATE TABLE book (
  book_id INT PRIMARY KEY AUTO_INCREMENT COMMENT '图书编号',
  book_name VARCHAR(50) NOT NULL COMMENT '图书名',
  author VARCHAR(30) NOT NULL COMMENT '作者',
  publish VARCHAR(50) NOT NULL COMMENT '出版社',
  introduction TEXT COMMENT '简介',
  book_img TEXT NOT NULL COMMENT '图书图片'
) AUTO_INCREMENT=1000000 DEFAULT CHARSET=utf8;