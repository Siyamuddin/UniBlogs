package com.siyamuddin.blog.blogappapis.Services.Impl;

import com.siyamuddin.blog.blogappapis.Entity.User;
import com.siyamuddin.blog.blogappapis.Exceptions.ResourceNotFoundException;
import com.siyamuddin.blog.blogappapis.Payloads.UserDto;
import com.siyamuddin.blog.blogappapis.Repository.UserRepo;
import com.siyamuddin.blog.blogappapis.Services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private UserRepo userRepo;
    @Override
    public UserDto createUser(UserDto userDto) {
    User user=this.userDtoToUser(userDto);
    User savedUser=this.userRepo.save(user);
    return this.userToUserDto(savedUser);
    }

    @Override
    public UserDto updateUser(UserDto userDto, Integer userId) {
        User user=this.userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User","ID",userId));

        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user.setEmail(userDto.getEmail());
        user.setAbout(userDto.getAbout());
        User user1=this.userRepo.save(user);
        UserDto userDto1=this.userToUserDto(user1);
        return userDto1;
    }

    @Override
    public UserDto getUserById(Integer userId) {
        User user=userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User","ID",userId));
        UserDto userDto=userToUserDto(user);
        return userDto;
    }

    @Override
    public List<UserDto> getAllUser() {
        List<User> users=userRepo.findAll();
        List<UserDto> userDtos=new ArrayList<>();
       for(int i=0;i<users.size();i++)
       {
           userDtos.add(userToUserDto(users.get(i)));
       }

        return userDtos;
    }

    @Override
    public void deleteUser(Integer userId) {
        User user=userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User","ID",userId));
        userRepo.deleteById(userId);

    }

    private User userDtoToUser(UserDto userDto)
    {
        User user=this.modelMapper.map(userDto,User.class);
//        user.setId(userDto.getId());
//        user.setName(userDto.getName());
//        user.setEmail(userDto.getEmail());
//        user.setPassword(userDto.getPassword());
//        user.setAbout(userDto.getAbout());
        return user;
    }

    private UserDto userToUserDto(User user)
    {
        UserDto userDto=this.modelMapper.map(user,UserDto.class);
//        userDto.setId(user.getId());
//        userDto.setName(user.getName());
//        userDto.setEmail(user.getEmail());
//        userDto.setPassword(user.getPassword());
//        userDto.setAbout(user.getAbout());
        return userDto;
    }
}
