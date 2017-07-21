package book.flow.service;

import book.flow.enity.User;

/**
 * 用户服务层接口.
 * User: huang
 * Date: 17-7-21
 */
public interface UserService {

    /**
     * 用户登录.
     * @param user 用户信息
     * @return 数据库中的用户信息
     */
    User login(User user);
}
