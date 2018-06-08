package com.uniovi.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.uniovi.entities.User;

public interface UsersRepository extends CrudRepository<User, Long> {

	User findByEmail(String email);

	User findById(long id);

	Page<User> findAll(Pageable pageable);

	@Query("SELECT r FROM User r WHERE (LOWER(r.name) LIKE LOWER(?1) OR LOWER(r.email) LIKE LOWER(?1))")
	Page<User> searchUsersByNameAndEmail(Pageable pageable, String seachtext);

	@Query("SELECT r.friends FROM User r WHERE r = ?1")
	Page<User> findAllFriendsByUser(Pageable pageable, User user);
}