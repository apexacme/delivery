package com.example.template.example;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface SampleUserRepository extends CrudRepository<SampleUser, Long> {
	
	List<SampleUser> findByUsername(@Param("username") String username);
	
	List<SampleUser> findAllByUsername(@Param("username") Iterable<String> username);
	
}
