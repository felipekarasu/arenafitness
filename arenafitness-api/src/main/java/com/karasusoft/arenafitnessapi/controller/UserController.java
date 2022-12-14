package com.karasusoft.arenafitnessapi.controller;

import com.karasusoft.arenafitnessapi.constants.PagesConstants;
import com.karasusoft.arenafitnessapi.dto.UserDto;
import com.karasusoft.arenafitnessapi.enums.UserStatus;
import com.karasusoft.arenafitnessapi.model.UserModel;
import com.karasusoft.arenafitnessapi.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/createUser")
    public String createUser() {

        return PagesConstants.CREATE_USER_PAGE;
    }

    @PostMapping("/createUser")
    public String createUser(UserDto userDto){

        return "redirect:/users/createUser";
    }


    /*public ResponseEntity<Object> createUser(@RequestBody @Valid UserDto userDto) {

        var userModel = new UserModel();
        var addressModel = new AddressModel();
        BeanUtils.copyProperties(userDto, userModel);
        BeanUtils.copyProperties(userDto.getAddressDtoList().get(0), addressModel);

        addressModel.setUser(userModel);
        userModel.setCreationDate(LocalDateTime.now(ZoneId.of("UTC")));
        userModel.setUserStatus(UserStatus.CREATED);
        userModel.getAddressModelList().add(addressModel);

        if(userService.existsByDocument(userDto.getDocument())){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("This user is already registered.");
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(userService.save(userModel));
    }*/

   /*@GetMapping("/{id}")
    public ResponseEntity<Object> getUser(@PathVariable(value = "id") String id) {

        Optional<UserModel> optionalUser = userService.findById(id);

        if(!optionalUser.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("This user doesn't exists.");
        }
        return ResponseEntity.status(HttpStatus.OK).body(optionalUser.get());
    }*/

    @GetMapping
    public ResponseEntity<List<UserModel>> getAllUsers() {

        return ResponseEntity.status(HttpStatus.OK).body(userService.findAll());
    }

    @GetMapping("/status")
    public ResponseEntity<List<UserModel>> getUserByStatus(@RequestParam(value = "status") String userStatus) {

        return ResponseEntity.status(HttpStatus.OK).body(userService.findAllByStatus(UserStatus.valueOf(userStatus)));
    }

    @PutMapping
    public ResponseEntity<Object> updateUser(@PathVariable(value = "id") String id,
                                             @RequestBody @Valid UserDto userDto) {

        Optional<UserModel> optionalUser = userService.findById(id);

        if(!optionalUser.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("This user doesn't exists.");
        }

        var userModel = optionalUser.get();
        BeanUtils.copyProperties(userDto, userModel);
        return ResponseEntity.status(HttpStatus.OK).body(userService.save(userModel));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteUser(@PathVariable(value = "id") String id) {

        Optional<UserModel> optionalUser = userService.findById(id);

        if(!optionalUser.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("This user doesn't exists.");
        }

        userService.delete(optionalUser.get());

        return ResponseEntity.status(HttpStatus.OK).body("User removed successfully.");
    }
}
