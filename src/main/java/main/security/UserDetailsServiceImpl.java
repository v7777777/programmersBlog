package main.security;

import main.model.User;
import main.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

// этот класс проверяет есть ли емейл и пароль в базе

@Service("userDetailsServiceImpl")
public class UserDetailsServiceImpl implements UserDetailsService {

  private final UserRepository userRepository;

  @Autowired
  public UserDetailsServiceImpl(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

    User user = userRepository.findByEmail(username).orElseThrow(() ->
        new UsernameNotFoundException("user " + username + " not found"));

    UserDetails userDetails = SecurityUser.fromUser(user);

    userDetails.getAuthorities().forEach(a -> System.out.println(a));

    return userDetails;
  }
}
