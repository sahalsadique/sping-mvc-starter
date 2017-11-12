package com.demo.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.demo.model.SimpleInterest;

public interface SimpleInterestRepository extends CrudRepository<SimpleInterest, Long> {
	List<SimpleInterest> findByUserId(Integer userId); 
	List<SimpleInterest> findByUserIdOrderByIdDesc(Integer userId);
}
