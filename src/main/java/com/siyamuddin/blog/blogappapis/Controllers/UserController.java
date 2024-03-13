package com.siyamuddin.blog.blogappapis.Controllers;

import com.siyamuddin.blog.blogappapis.Config.AppConstants;
import com.siyamuddin.blog.blogappapis.Payloads.ApiResponse;
import com.siyamuddin.blog.blogappapis.Payloads.UserDto;
import com.siyamuddin.blog.blogappapis.Services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
  public ResponseEntity<List<UserDto>> getAllUser(@RequestParam(value = "pageNumber",defaultValue = AppConstants.PAGE_NUMBER,required = false) Integer pageNumber,
                                                  @RequestParam(value = "pageSize",defaultValue = AppConstants.PAGE_SIZE,required = false) Integer pageSize,
                                                  @RequestParam(value = "sortBy",defaultValue = AppConstants.SORT_BY,required = false) String sortBy,
                                                  @RequestParam(value = "sortDirec",defaultValue = AppConstants.SORT_DIREC,required = false) String sortDirec)
  {
      List<UserDto> userDtos=this.userService.getAllUser(pageNumber,pageSize,sortBy,sortDirec);
      return new ResponseEntity<List<UserDto>>(userDtos,HttpStatus.OK);
  }

@GetMapping("/search/{keywords}")
    public ResponseEntity<List<UserDto>> searchUserByName(@PathVariable("keywords") String keywords)
{
    List<UserDto> userDtos=this.userService.searchUserByName(keywords);
    return new ResponseEntity<List<UserDto>>(userDtos,HttpStatus.OK);
}
}
