package com.perscholas.eclassroom.security;

import com.perscholas.eclassroom.models.AuthGroup;
import com.perscholas.eclassroom.repo.AuthGroupRepoI;
import com.perscholas.eclassroom.repo.StudentRepoI;
import com.perscholas.eclassroom.repo.TeacherRepoI;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class AppUserDetailService implements UserDetailsService {

    TeacherRepoI teacherRepoI;
    StudentRepoI studentRepoI;
    AuthGroupRepoI authGroupRepoI;

    @Autowired
    public AppUserDetailService(TeacherRepoI teacherRepoI, StudentRepoI studentRepoI, AuthGroupRepoI authGroupRepoI) {
        this.teacherRepoI = teacherRepoI;
        this.studentRepoI = studentRepoI;
        this.authGroupRepoI = authGroupRepoI;
    }

    public AppUserDetailService() {
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        List<AuthGroup> authGroupList = authGroupRepoI.findByEmail(email);
        log.warn("authgroup looking for email start");
        if (studentRepoI.existByEmail(email)>0){
            return new AppUserPrincipal(studentRepoI.findByEmail(email), authGroupList);
        }else if (teacherRepoI.existByEmail(email)>0){
        return new AppUserPrincipal(teacherRepoI.findByEmail(email), authGroupList);}
        throw new UsernameNotFoundException("user name not found");
    }
}
