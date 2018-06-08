package com.uniovi.services;

import java.util.LinkedList;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.uniovi.entities.User;
import com.uniovi.repositories.UsersRepository;

@Service
public class UsersService {

	@Autowired
	private UsersRepository usersRepository;

	@Autowired
	private InvitationsService invitationsService;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@PostConstruct
	public void init() {
	}

	public User getUser(Long id) {
		return usersRepository.findOne(id);
	}

	public void addUser(User user) {
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		usersRepository.save(user);
		logger.info("Se ha añadido correctamente al usuario:" + user.getName());
	}

	public User getUserByEmail(String name) {
		return usersRepository.findByEmail(name);
	}

	public void deleteUser(Long id) {
		User user = usersRepository.findById(id);
		usersRepository.delete(id);
		logger.info("Se ha borrado correctamente al usuario:" + user.getName());
	}

	public Page<User> getUsers(Pageable pageable) {
		Page<User> users = new PageImpl<User>(new LinkedList<User>());
		users = usersRepository.findAll(pageable);
		return users;
	}

	public Page<User> searchUsersByNameAndEmail(Pageable pageable, String searchText) {
		Page<User> users = new PageImpl<User>(new LinkedList<User>());
		searchText = "%" + searchText + "%";

		users = usersRepository.searchUsersByNameAndEmail(pageable, searchText);

		return users;
	}

	public void makeFriend(User whoSended, User whoReceived) {
		whoSended.addFriend(whoReceived);
		whoReceived.addFriend(whoSended);
		logger.info(
				"Se ha creado una relacion de amistad entre " + whoSended.getEmail() + " y " + whoReceived.getEmail());
		invitationsService.deleteInvitation(whoSended, whoReceived);
		logger.info("Se ha borrado la invitacion entre " + whoSended.getEmail() + " y " + whoReceived.getEmail());
	}

	public Page<User> getFriendsByUser(Pageable pageable, User user) {
		return usersRepository.findAllFriendsByUser(pageable, user);
	}
}
