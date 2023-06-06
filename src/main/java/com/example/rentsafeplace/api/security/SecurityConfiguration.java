package com.example.rentsafeplace.api.security;

import com.example.rentsafeplace.api.model.Company;
import com.example.rentsafeplace.api.model.Realtor;
import com.example.rentsafeplace.api.model.Tenant;
import com.example.rentsafeplace.service.CompanyService;
import com.example.rentsafeplace.service.RealtorService;
import com.example.rentsafeplace.service.TenantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Autowired
    private TenantService tenantService;

    @Autowired
    private RealtorService realtorService;

    @Autowired
    private CompanyService companyService;

    private static final InMemoryUserDetailsManager inMemoryUserDetailsManager = new InMemoryUserDetailsManager();

    public static void addCompanyUser(Company company) {
        PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        UserDetails user = User.withUsername(company.getEmail()).password(passwordEncoder.encode(company.getPassword())).roles("COMPANY").build();
        inMemoryUserDetailsManager.createUser(user);
    }

    public static void addTenantUser(Tenant tenant) {
        PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        UserDetails user = User.withUsername(tenant.getEmail()).password(passwordEncoder.encode(tenant.getPassword())).roles("TENANT").build();
        inMemoryUserDetailsManager.createUser(user);
    }

    public static void addRealtorUser(Realtor realtor) {
        PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        UserDetails user = User.withUsername(realtor.getEmail()).password(passwordEncoder.encode(realtor.getPassword())).roles("REALTOR").build();
        inMemoryUserDetailsManager.createUser(user);
    }

    @Bean
    public InMemoryUserDetailsManager userDetailsService(PasswordEncoder passwordEncoder) {

        List<UserDetails> tenants = tenantService.getAll().stream().map(tenant ->
                User.withUsername(tenant.getEmail()).password(passwordEncoder.encode(tenant.getPassword())).roles("TENANT").build()
        ).toList();

        List<UserDetails> realtors = realtorService.getAll().stream().map(realtor ->
                User.withUsername(realtor.getEmail()).password(passwordEncoder.encode(realtor.getPassword())).roles("REALTOR").build()
        ).toList();

        List<UserDetails> companies = companyService.getAll().stream().map(company ->
                User.withUsername(company.getEmail()).password(passwordEncoder.encode(company.getPassword())).roles("COMPANY").build()
        ).toList();
        for (UserDetails tenant : tenants) {
            inMemoryUserDetailsManager.createUser(tenant);
        }
        for (UserDetails realtor : realtors) {
            inMemoryUserDetailsManager.createUser(realtor);
        }
        for (UserDetails company : companies) {
            inMemoryUserDetailsManager.createUser(company);
        }
        return inMemoryUserDetailsManager;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                    .antMatchers("/companies/register", "/companies/login", "/realtors/login", "/tenants/register",
                        "/tenants/login").permitAll()
                    .antMatchers("/companies/info", "/companies/subscription", "/companies/realtors").hasRole("COMPANY")
                    .antMatchers("/realtors/company", "/realtors/info", "/realtors/tenants", "/realtors/buildings")
                    .hasRole("REALTOR")
                    .antMatchers("/tenants/realtors", "/tenants/info").hasRole("TENANT")
                .anyRequest().permitAll()
                .and()
                .httpBasic()
                .and()
                .rememberMe()
                .userDetailsService(userDetailsService(passwordEncoder()))
                .and()
                .sessionManagement()
                .sessionFixation().none();

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

}
