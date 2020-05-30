package tk.slaaavyn.redshark.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import tk.slaaavyn.redshark.security.jwt.JwtConfigurer;
import tk.slaaavyn.redshark.security.jwt.JwtTokenProvider;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtTokenProvider jwtTokenProvider;

    public SecurityConfig(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.httpBasic().disable()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()

                /* AUTH */
                .antMatchers(EndpointConstants.AUTH_ENDPOINT + "/**").permitAll()

                /* USER */
                .antMatchers(EndpointConstants.USER_ENDPOINT + "/**").hasRole("ADMIN")

                /* DEVICE */
                .antMatchers(EndpointConstants.DEVICE_ENDPOINT + "/**").authenticated()

                /* FILES */
                .antMatchers(EndpointConstants.FILES_ENDPOINT + "/**").authenticated()

                /* TASK */
                .antMatchers(HttpMethod.GET,EndpointConstants.TASK_ENDPOINT + "/get-all").hasRole("ADMIN")
                .antMatchers(EndpointConstants.TASK_ENDPOINT + "/**").authenticated()

                /* SWAGGER */
                .antMatchers("/webjars/**", "/v2/api-docs/**", "/configuration/ui/**", "/swagger-resources/**",
                        "/configuration/security/**", "/swagger-ui.html/**", "/swagger-ui.html#/**").permitAll()


                .anyRequest().authenticated()
                .and()
                .apply(new JwtConfigurer(jwtTokenProvider));
    }
}
