package com.openclassrooms.jwt;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.openclassrooms.model.Role;
import com.openclassrooms.repository.UserRepository;

@Component("userDetailsService")
public class DomainUserDetailsService implements UserDetailsService {

	private final UserRepository userRepository;

	public DomainUserDetailsService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
		com.openclassrooms.model.User user = userRepository.findByName(name)
				.orElseThrow(() -> new UsernameNotFoundException("not found"));
		return new org.springframework.security.core.userdetails.User(user.getName(), user.getPassword(),
				mapRolesToAuthorities(user.getRoles()));
	}

	private Collection<GrantedAuthority> mapRolesToAuthorities(List<Role> roles) {
		return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
	}
}

/*
 * private static final Logger log =
 * LoggerFactory.getLogger(DomainUserDetailsService.class);
 * 
 * @Override
 * 
 * @Transactional public UserDetails loadUserByUsername(final String login) {
 * log.debug("Authenticating {}", login); String lowercaseLogin =
 * login.toLowerCase(Locale.ENGLISH); Optional<User> userByEmailFromDatabase =
 * userRepository.findByName(lowercaseLogin); return
 * userByEmailFromDatabase.map(user -> { try { return
 * createSpringSecurityUser(lowercaseLogin, user); } catch (Exception e) {
 * return null; } }).orElseGet(() -> { Optional<User> userByLoginFromDatabase =
 * userRepository.findByName(lowercaseLogin); return
 * userByLoginFromDatabase.map(user -> { try { return
 * createSpringSecurityUser(lowercaseLogin, user); } catch (Exception e) {
 * return null; } }).orElseThrow(() -> new UsernameNotFoundException( "User " +
 * lowercaseLogin + " was not found in the " + "database")); }); }
 * 
 * private org.springframework.security.core.userdetails.User
 * createSpringSecurityUser(String lowercaseLogin, User user) {
 * 
 * final List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
 * grantedAuthorities.add(new SimpleGrantedAuthority("ok"));
 * 
 * return new org.springframework.security.core.userdetails.User(user.getName(),
 * user.getPassword(), grantedAuthorities); }
 * 
 * }
 */
