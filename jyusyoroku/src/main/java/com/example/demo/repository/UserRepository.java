package com.example.demo.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.User;

/**
 * ユーザー情報 Repository
 */
@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

	@Query(value = "SELECT * FROM jyusyoroku AS j WHERE j.delete_flg = 0", nativeQuery = true) // SQL
	public Page<User> findAll(Pageable pageable);

	@Query(value = "SELECT * FROM jyusyoroku AS j WHERE j.address LIKE %:SeachName% AND j.delete_flg = 0", nativeQuery = true) // SQL
	public Page<User> findSeachAll(@Param("SeachName") String SeachName,Pageable pageable);
}