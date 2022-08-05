/*
 * @Author: Wonder2020 
 * @Date: 2021-01-22 15:49:18 
 * @Last Modified by: Wonder2020
 * @Last Modified time: 2021-01-29 11:46:12
 */
package top.imwonder.mcauth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import top.imwonder.mcauth.pojo.security.MyPasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    // @Autowired
    // private UserDetailsService wuds;

    // @Autowired
    // private AccessDeniedHandler adh;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new MyPasswordEncoder();
    }

    // @Override
    // protected void configure(AuthenticationManagerBuilder auth) throws Exception
    // {
    // auth.userDetailsService(wuds).passwordEncoder(passwordEncoder());
    // }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // http.exceptionHandling().accessDeniedHandler(adh);
        http.formLogin().loginPage("/space/login").loginProcessingUrl("/uspace/login").defaultSuccessUrl("/space/index")
                .permitAll();
        http.logout().logoutUrl("/space/logout").logoutSuccessUrl("/");
        http.headers().frameOptions().sameOrigin();
        http.authorizeRequests().antMatchers("/space/**").authenticated();
        http.authorizeRequests().antMatchers("/assets/**/admin/**").authenticated();
        http.authorizeRequests().antMatchers("/**").permitAll();
        http.antMatcher("/api/**").csrf().disable();
    }
}
