package com.ijkalra.secureapis.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;

@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    DataSource dataSource;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication()
                .dataSource(dataSource)
                // these 2 queries are anyways default... it shows that we can edit
                //queries to suit our own schema
                .usersByUsernameQuery("select username, password, enabled " +
                        "from users " +
                        "where username = ?")
                .authoritiesByUsernameQuery("select username, authority " +
                        "from authorities " +
                        "where username = ?");
    }

    // Default schema with H2... With this we do not need data.sql and schema.sql for initializing the data
    /*@Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication()
                .dataSource(dataSource)
                .withDefaultSchema() // this creates user and authority tables automatically
                .withUser(
                        User.withUsername("jass")
                        .password("jass")
                        .roles("USER")
                )
                .withUser(
                        User.withUsername("inder")
                        .password("inder")
                        .roles("ADMIN")
                );
    }*/

    // In Memory Authentication example
    /*
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("Jass")
                .password("jass")
                .roles("USER")
                .and()
                .withUser("inder")
                .password("inder")
                .roles("ADMIN");
    }
    */
    @Bean
    PasswordEncoder getPasswordEncoder(){
        return NoOpPasswordEncoder.getInstance();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/main").permitAll()
                .antMatchers("/main/user").hasAnyRole("USER", "ADMIN")
                .antMatchers("/main/admin").hasRole("ADMIN")
                .and().formLogin();
    }

    // to disable security on H2. H2 has its own username password
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/h2/**");
    }
}
