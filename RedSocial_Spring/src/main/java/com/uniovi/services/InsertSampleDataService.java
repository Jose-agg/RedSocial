package com.uniovi.services;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uniovi.entities.User;

@Service
public class InsertSampleDataService {

	@Autowired
	private UsersService usersService;

	@Autowired
	private RolesService rolesService;

	@PostConstruct
	public void init() {

		User user1 = new User("Jose", "jose@hotmail.es");
		user1.setPassword("123");
		user1.setRole(rolesService.getRoles()[0]);

		User user2 = new User("Hugo", "hugo@hotmail.com");
		user2.setPassword("123");
		user2.setRole(rolesService.getRoles()[0]);

		User user3 = new User("Pelayo", "pelayo@yahoo.com");
		user3.setPassword("123");
		user3.setRole(rolesService.getRoles()[0]);

		User user4 = new User("Cristina", "cristina@gmail.com");
		user4.setPassword("123");
		user4.setRole(rolesService.getRoles()[0]);

		User user5 = new User("Sofia", "sofia@hotmail.es");
		user5.setPassword("123");
		user5.setRole(rolesService.getRoles()[0]);

		User user6 = new User("Antonio", "antonio@hotmail.com");
		user6.setPassword("123");
		user6.setRole(rolesService.getRoles()[0]);

		User user7 = new User("Mirza", "mirza@yahoo.com");
		user7.setPassword("123");
		user7.setRole(rolesService.getRoles()[0]);

		User user8 = new User("Paloma", "paloma@gmail.com");
		user8.setPassword("123");
		user8.setRole(rolesService.getRoles()[0]);

		User user9 = new User("Jesus", "jesus@hotmail.es");
		user9.setPassword("123");
		user9.setRole(rolesService.getRoles()[0]);

		User user10 = new User("Alejandro", "alejandro@hotmail.com");
		user10.setPassword("123");
		user10.setRole(rolesService.getRoles()[0]);

		User user11 = new User("Ines", "ines@yahoo.com");
		user11.setPassword("123");
		user11.setRole(rolesService.getRoles()[0]);

		User user12 = new User("Sara", "sara@gmail.com");
		user12.setPassword("123");
		user12.setRole(rolesService.getRoles()[0]);

		usersService.addUser(user1);
		usersService.addUser(user2);
		usersService.addUser(user3);
		usersService.addUser(user4);
		usersService.addUser(user5);
		usersService.addUser(user6);
		usersService.addUser(user7);
		usersService.addUser(user8);
		usersService.addUser(user9);
		usersService.addUser(user10);
		usersService.addUser(user11);
		usersService.addUser(user12);

	}

}
