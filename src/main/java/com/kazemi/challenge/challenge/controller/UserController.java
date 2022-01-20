package com.kazemi.challenge.challenge.controller;

import com.kazemi.challenge.challenge.BaseResponseDto;
import com.kazemi.challenge.challenge.dto.UserResponseDto;
import com.kazemi.challenge.challenge.dto.UserRequestDto;
import com.kazemi.challenge.challenge.dto.UserContactUpdateRequestDto;
import com.kazemi.challenge.challenge.service.UserService;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.annotations.ApiOperation;

import javax.validation.constraints.NotEmpty;
import java.util.List;

import static io.undertow.util.StatusCodes.*;

@RestController
@RequestMapping({"/api/v1/users"})
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @ApiOperation(value = "find user by userId")
    @GetMapping("/findById/{id}")
    @ApiResponses(value = {
            @ApiResponse(code = OK, message = "Successfully perform the operation"),
            @ApiResponse(code = BAD_REQUEST, message = "The Request property was not provide correctly"),
            @ApiResponse(code = INTERNAL_SERVER_ERROR, message = "Internal server error.")
    })
    public ResponseEntity<BaseResponseDto<UserResponseDto>> findUserById(@PathVariable("id") @NonNull Long id){
        return ResponseEntity.ok(BaseResponseDto.of(userService.findUserById(id)));
    }

    @ApiOperation(value = "find user by userName")
    @GetMapping("/findByName/{name}")
    @ApiResponses(value = {
            @ApiResponse(code = OK, message = "Successfully perform the operation"),
            @ApiResponse(code = BAD_REQUEST, message = "The Request property was not provide correctly"),
            @ApiResponse(code = INTERNAL_SERVER_ERROR, message = "Internal server error.")
    })
    public ResponseEntity<BaseResponseDto<List<UserResponseDto>>> findUserByName(@PathVariable("name")  @NotEmpty String name){
        return ResponseEntity.ok(BaseResponseDto.of(userService.findByName(name)));
    }

    @ApiOperation(value = "create new user with contact information")
    @PostMapping("/new")
    @ApiResponses(value = {
            @ApiResponse(code = OK, message = "Successfully perform the operation"),
            @ApiResponse(code = BAD_REQUEST, message = "The Request property was not provide correctly"),
            @ApiResponse(code = INTERNAL_SERVER_ERROR, message = "Internal server error.")
    })
    public ResponseEntity<BaseResponseDto<UserResponseDto>> createNewUser(@RequestBody UserRequestDto request){
        return ResponseEntity.ok(BaseResponseDto.of(userService.createNewUser(request)));
    }

    @ApiOperation(value = "delete a user by userId")
    @PostMapping("/delete/{id}")
    @ApiResponses(value = {
            @ApiResponse(code = OK, message = "Successfully perform the operation"),
            @ApiResponse(code = BAD_REQUEST, message = "The Request property was not provide correctly"),
            @ApiResponse(code = INTERNAL_SERVER_ERROR, message = "Internal server error.")
    })
    public ResponseEntity<BaseResponseDto<Void>> deleteUser(@PathVariable("id") @NonNull Long id){
        userService.deleteById(id);
        return ResponseEntity.ok(BaseResponseDto.ok());
    }

    @ApiOperation(value = "add new contact information")
    @PostMapping("/add-contact")
    @ApiResponses(value = {
            @ApiResponse(code = OK, message = "Successfully perform the operation"),
            @ApiResponse(code = BAD_REQUEST, message = "The Request property was not provide correctly, error code 1005 = both mobile and nationalId are null"),
            @ApiResponse(code = INTERNAL_SERVER_ERROR, message = "Internal server error.")
    })
    public ResponseEntity<BaseResponseDto<Void>> addContactData(@RequestBody UserRequestDto request){
        userService.addContactData(request);
        return ResponseEntity.ok(BaseResponseDto.ok());
    }

    @ApiOperation(value = "update contact information")
    @PostMapping("/update-contact")
    @ApiResponses(value = {
            @ApiResponse(code = OK, message = "Successfully perform the operation"),
            @ApiResponse(code = BAD_REQUEST, message = "The Request property was not provide correctly, error code 1005 = both mobile and nationalId are null"),
            @ApiResponse(code = INTERNAL_SERVER_ERROR, message = "Internal server error.")
    })
    public ResponseEntity<BaseResponseDto<Void>> updateContactData(@RequestBody UserContactUpdateRequestDto request){
        userService.updateContactData(request);
        return ResponseEntity.ok(BaseResponseDto.ok());
    }
}
