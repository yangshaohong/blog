package top.crazymad.dao;

import top.crazymad.entity.User;

public interface UserDao {
	User getUserByAccount(String account);
}
