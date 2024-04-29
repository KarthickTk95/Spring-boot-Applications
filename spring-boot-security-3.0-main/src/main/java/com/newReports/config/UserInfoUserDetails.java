package com.newReports.config;

import com.newReports.entity.Member;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Base64;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static com.newReports.config.SecurityConfig.passwordEncoder;

public class UserInfoUserDetails implements UserDetails {


    private String username;
    private String password;
    private List<GrantedAuthority> authorities;


    public UserInfoUserDetails(Member member) {

        byte[] pass = Base64.getDecoder().decode(member.getPassword());
        String passwordDecrypted = new String(pass);
        System.out.println("decrypted Password-> "+passwordDecrypted);
        passwordDecrypted = passwordEncoder.encode(passwordDecrypted);
        System.out.println("bcrypt Password-> "+passwordDecrypted);
       // String encodedPassword = passwordEncoder.encode(userInfo.get().getPassword());
        username= member.getUsername();
       // password= reportsMember.getPassword();
        password= passwordDecrypted;
        authorities= Arrays.stream(member.getAccountStatus().split(","))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
