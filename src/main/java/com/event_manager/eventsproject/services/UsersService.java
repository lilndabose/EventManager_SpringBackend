package com.event_manager.eventsproject.services;

import com.event_manager.eventsproject.dto.LoginInfo;
import com.event_manager.eventsproject.dto.UsersDto;
import com.event_manager.eventsproject.entity.Users;
import com.event_manager.eventsproject.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UsersService {


    @Autowired
    public UsersRepository usersRepository;


    public List<Users> findAll() {
        return usersRepository.findAll();
    }

    public Users findById(Long id) {
        return usersRepository.findById(id)
                .orElseThrow(()-> new IllegalArgumentException("User with id: "+id+" does not exist"));
    }

    public UsersDto save(Users user) {
        Users usernameOrEmailExist  = usersRepository.usernameOrEmailExist(user.getUsername(),user.getEmail());
        if(usernameOrEmailExist != null){
            return null;
        }
        usersRepository.save(user);

        return new UsersDto(user.getId(),user.getEmail(),user.getUsername());
    }

    public UsersDto loginUser(LoginInfo loginInfo){
        Users savedUser =  usersRepository.loginUser(loginInfo.username(), loginInfo.password());
        if(savedUser == null) return null;
        return new UsersDto(savedUser.getId(),savedUser.getEmail(),savedUser.getUsername());
    }

    public void deleteById(Long id) {
        usersRepository.deleteById(id);
    }
}
