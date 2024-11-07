package ru.kata.spring.boot_security.demo.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.provisioning.JdbcUserDetailsManager;

import javax.sql.DataSource;
@Configuration
@EnableWebSecurity
public class UserDetailsService extends WebSecurityConfig {

    private JdbcUserDetailsManager userDetailsManager = new JdbcUserDetailsManager();

    public UserDetailsService(SuccessUserHandler successUserHandler, DataSource dataSource) {
        super(successUserHandler, dataSource);
    }
        userDetailsManager.setDataSource(dataSource);
        userDetailsManager.setUsersByUsernameQuery("select user_name, password, enabled from users_table where user_name=?");
        userDetailsManager.setAuthoritiesByUsernameQuery("SELECT role FROM roles WHERE role = ?");





//    public UserDetailsService userDetailsService() {
//        JdbcUserDetailsManager userDetailsManager = new JdbcUserDetailsManager();
//        userDetailsManager.setDataSource(dataSource);
//        userDetailsManager.setUsersByUsernameQuery("select user_name, password, enabled from users_table where user_name=?");
//        userDetailsManager.setAuthoritiesByUsernameQuery("SELECT role FROM roles WHERE role = ?");
//        return userDetailsManager;
//    }
}
