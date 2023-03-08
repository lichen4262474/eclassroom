package com.perscholas.eclassroom.security;

import com.perscholas.eclassroom.models.AuthGroup;
import com.perscholas.eclassroom.models.Student;
import com.perscholas.eclassroom.models.Teacher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class AppUserPrincipal implements UserDetails {


    private Student student;
    private List<AuthGroup> authGroup ;
    private Teacher teacher;
    @Autowired
    public AppUserPrincipal(Student student, List<AuthGroup> authGroup) {
        this.student = student;
        this.authGroup = authGroup;
    }
    @Autowired
    public AppUserPrincipal(Teacher teacher,List<AuthGroup> authGroup) {
        this.teacher = teacher;
        this.authGroup = authGroup;
    }
    @Autowired
    public AppUserPrincipal(Student student, List<AuthGroup> authGroup, Teacher teacher) {
        this.student = student;
        this.authGroup = authGroup;
        this.teacher = teacher;
    }
    @Autowired
    public AppUserPrincipal() {
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        return authGroup.stream().map((auth)->new SimpleGrantedAuthority(auth.getRole())).collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        if(teacher!=null) {
            return teacher.getPassword();
        }
        else{
            return student.getPassword();
        }
    }
    @Override
    public String getUsername() {
        if(teacher!=null) {
            return teacher.getEmail();
        }
        else{
            return student.getEmail();
        }
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
