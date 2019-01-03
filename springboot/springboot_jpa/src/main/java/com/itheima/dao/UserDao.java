package com.itheima.dao;

import com.itheima.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @Author: Mr.Liu
 * @Date: 2019/1/2 15:35
 * @Location: Shenzhen
 * @Version: 1.1.0
 */
public interface UserDao extends JpaRepository<User,Integer>, JpaSpecificationExecutor<User> {

}
