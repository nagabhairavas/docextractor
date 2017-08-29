package com.doc.word.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.doc.word.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
	public User findById(Integer userId);
	public User findByIdAndPassword(Integer userId, String password);
}