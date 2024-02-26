package com.siyamuddin.blog.blogappapis.Controllers;

import com.siyamuddin.blog.blogappapis.Payloads.ApiResponse;
import com.siyamuddin.blog.blogappapis.Payloads.UserDto;
import com.siyamuddin.blog.blogappapis.Services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/")
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto)
    {
        UserDto createdUserDto= this.userService.createUser(userDto);
        return new ResponseEntity<>(createdUserDto, HttpStatus.CREATED);
    }
    @PutMapping("/{userId}")
    public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto userDto,@PathVariable Integer userId)
    {
        UserDto updatedUserDto=this.userService.updateUser(userDto,userId);
        return ResponseEntity.ok(updatedUserDto);
    }
   @DeleteMapping("/{userId}")
   public ResponseEntity<ApiResponse> deleteUser(@PathVariable Integer userId)
   {
       this.userService.deleteUser(userId);
       return new ResponseEntity(new ApiResponse("User deleted successfully",true),HttpStatus.OK);
   }

  @GetMapping("/{userId}")
    public ResponseEntity<UserDto> getUser(@PathVariable Integer userId)
  {
      UserDto userDto=this.userService.getUserById(userId);
      return new ResponseEntity<>(userDto,HttpStatus.OK);
  }
  @GetMapping("/")
  public ResponseEntity<List<UserDto>> getAllUser()
  {

      return new ResponseEntity<>(this.userService.getAllUser(),HttpStatus.OK);
  }

}
