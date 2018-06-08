package com.uniovi.entities;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@Entity
public class User {

	@Id
	@GeneratedValue
	private long id;

	@Column(unique = true)
	private String email;

	private String name;
	private String role;
	private String password;

	@Transient // propiedad que no se almacena e la tabla.
	private String passwordConfirm;

	@ManyToMany
	@JoinTable(name = "friend")
	private Set<User> friends;

	@OneToMany(mappedBy = "sender")
	private Set<Invitation> sendedInvitations;

	@OneToMany(mappedBy = "receiver")
	private Set<Invitation> receivedInvitations;

	public User(String name, String email) {
		super();
		this.name = name;
		this.email = email;
	}

	public User() {
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPasswordConfirm() {
		return passwordConfirm;
	}

	public void setPasswordConfirm(String passwordConfirm) {
		this.passwordConfirm = passwordConfirm;
	}

	public Set<User> getFriends() {
		return friends;
	}

	public Set<Invitation> getSendedInvitations() {
		return sendedInvitations;
	}

	public Set<Invitation> getReceivedInvitations() {
		return receivedInvitations;
	}

	public void setFriends(Set<User> friends) {
		this.friends = friends;
	}

	public void setSendedInvitations(Set<Invitation> sentInvitations) {
		this.sendedInvitations = sentInvitations;
	}

	public void setReceivedInvitations(Set<Invitation> receivedInvitations) {
		this.receivedInvitations = receivedInvitations;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (id != other.id)
			return false;
		return true;
	}

	public void addFriend(User user) {
		friends.add(user);
		user.getFriends().add(this);
	}

	public boolean canInvite(String email) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String activeEmail = auth.getName();
		if (email == activeEmail)
			return false;
		for (User user : friends) {
			if (user.getEmail().equals(activeEmail))
				return false;
		}
		for (Invitation invitation : sendedInvitations) {
			if (invitation.getReceiver().getEmail().equals(activeEmail))
				return false;
		}
		for (Invitation invitation : receivedInvitations) {
			if (invitation.getSender().getEmail().equals(activeEmail))
				return false;
		}
		return true;
	}
}
