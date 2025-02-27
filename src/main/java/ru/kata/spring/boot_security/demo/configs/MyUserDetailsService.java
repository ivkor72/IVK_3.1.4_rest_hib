package ru.kata.spring.boot_security.demo.configs;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;

@Configuration
@EnableWebSecurity
public class MyUserDetailsService implements UserDetailsService {

    @Autowired private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        User myUser = userService.findByUsername(userName);
        if (myUser == null) {
            throw new UsernameNotFoundException("Unknown user: " + userName);
        }
        UserDetails user = User.builder()
                .id(myUser.getId())
                .username(myUser.getUsername())
                .password(myUser.getPassword())
                .enabled(myUser.getEnabled())
                .build();

        return user;
   }
}
