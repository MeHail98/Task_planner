package com.example.task_planner.configuration;

import com.example.task_planner.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    /**
     * тут  В САМОМ КОНЦЕ ВЫЗВАН МЕТОД httpBasic();, БЛАГОДАРЯ ЕМУ БУДЕТ
     * РАБОТАТЬ BASIC AUTH В POSTMAN И В КЛИЕНТЕ КОТОРЫЙ Я САМ НАПИСАЛ.
     * БЕЗ НЕГО НЕ РАБОТАЕТ, ХОТЬ И ТАК КАК БЫ BASIC AUTH, НО НАДО ЯВНО
     * ЭТО ПРОПИСЫВАТЬ
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeHttpRequests( urlConfig -> urlConfig
                        .antMatchers("/registration","/err").permitAll()
                        .anyRequest().authenticated()
                )
                .logout(logout-> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/")
                        .deleteCookies("JSESSIONID"))
                .formLogin(login->login
                        .loginPage("/login")
                        .failureUrl("/err")
                        .defaultSuccessUrl("/getTask")
                        .permitAll()).httpBasic();

    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();

    }
}
