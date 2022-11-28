package cts.mfpe.authorization.services;

import java.util.ArrayList;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import cts.mfpe.authorization.entities.User;
import cts.mfpe.authorization.exceptions.UserAlredyExistsException;
import cts.mfpe.authorization.repos.UserRepository;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class JwtUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private PasswordEncoder bcryptEncoder;
	
	@Override
	public UserDetails loadUserByUsername(String userEmail) throws UsernameNotFoundException {
		User user = userRepo.findByUserEmail(userEmail);
		if (user == null) {
			throw new UsernameNotFoundException("User not found with useremail: " + userEmail);
		}
		log.info("User found");
		log.info("user successfully located");
		return new org.springframework.security.core.userdetails.User(user.getUserEmail(), user.getUserPassword(), new ArrayList<>());
	}
	
	@Transactional
	public void save(User user) throws Exception{
		if(CheckIfUserAlreadyExists(user.getFirstName())) {
			throw new UserAlredyExistsException("User with user name "+user.getFirstName()+" already exists");
		}
		User newUser = new User();
		//newUser.setUsername(user.getUsername());
		newUser.setFirstName(user.getFirstName());
		newUser.setLastName(user.getLastName());
		newUser.setUserEmail(user.getUserEmail());
		newUser.setUserPassword(bcryptEncoder.encode(user.getUserPassword()));
		newUser.setUserAddress(user.getUserAddress());
		newUser.setUserPhone(user.getUserPhone());


		userRepo.save(newUser);
		log.info("user successfully saved!");
	}
	
	public User getUserByUserEmail(String useremail) {
		log.info("User found with username {}" + useremail);
		return userRepo.findByUserEmail(useremail);
	}
	
	public boolean CheckIfUserAlreadyExists(String useremail) {
		return userRepo.findAll().stream().anyMatch(u -> u.getUserEmail().equals(useremail));
	}


}
