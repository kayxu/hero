package com.joymeng.web.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.joymeng.web.entity.User;

public interface UserDao extends PagingAndSortingRepository<User, Long> {
	User findByLoginName(String loginName);
}
