package imaniprima.meitrack.api.controller;

import com.querydsl.core.BooleanBuilder;
import imaniprima.meitrack.api.domain.ApiResponse;
import imaniprima.meitrack.api.domain.QUsers;
import imaniprima.meitrack.api.domain.Users;
import imaniprima.meitrack.api.dto.ChangePasswordDTO;
import imaniprima.meitrack.api.exception.ResourceNotFoundException;
import imaniprima.meitrack.api.exception.ResponseDuplicateKeyException;
import imaniprima.meitrack.api.payload.JwtAuthenticationResponse;
import imaniprima.meitrack.api.repository.UsersRepository;
import imaniprima.meitrack.api.service.ChangePasswordService;
import imaniprima.meitrack.api.service.JwtService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.util.Optional;

@RestController
@RequestMapping(path = "/users")
@Api(tags = "Users Management")
public class UsersController {
    @Autowired
    private UsersRepository usersRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private ChangePasswordService changePasswordService;

    @GetMapping(path = "getall")
    @ApiOperation("Get All User")
    public Iterable<Users> getAllUsers(
            @RequestHeader("Authorization") String Authorization,
            @RequestParam(name = "userName", required = false) String userName,
            Pageable pageRequest
    ) {
        try {
            QUsers users = QUsers.users;
            BooleanBuilder builder = new BooleanBuilder();

            if (userName != null)
                builder.and(users.username.eq(userName));

            Iterable<Users> response = usersRepo.findAll(builder, pageRequest);

            return response;
        } catch (Exception e) {
            throw e;
        }
    }

    @PostMapping(path = "/add")
    @ApiOperation("Create User")
    public Users createUser(

            @RequestBody Users user) {
        try {
            Optional<Users> resource = usersRepo.findByUsername(user.getUsername());
            if(resource.isPresent()){
                throw new ResponseDuplicateKeyException("User", "Username", user.getUsername());
            }else{
                user.setPassword(passwordEncoder.encode(user.getPassword()));
                Users respUser = usersRepo.save(user);
                return respUser;
            }
        } catch (Exception e) {
            throw e;
        }
    }

    @PutMapping(path = "/{id}")
    @ApiOperation("Update User By Id")
    public Users updateUserById(
            @RequestHeader("Authorization") String Authorization,
            @RequestBody Users user, @PathVariable Long id) {
        Optional<Users> response = usersRepo.findById(id);
        try {
            user.setId(id);



            if(user.getPassword() == null){
                user.setPassword(response.get().getPassword());
            }
//            return userRepo.save(user);
            Users respUser = usersRepo.save(user);
            return respUser;

        } catch (Exception e) {
            throw e;
        }
    }

    @DeleteMapping(path = "/{id}")
    @ApiOperation("Delete User By Id")
    public boolean deleteUserById(
            @RequestHeader("Authorization") String Authorization,
            @PathVariable Long id) {
        try {
            Optional<Users> resource = usersRepo.findById(id);
            if(resource.isPresent()){
                usersRepo.deleteById(id);
                return true;
            }else{
                throw new ResourceNotFoundException("User", "User Id", id);
            }
        } catch (Exception e) {
            throw e;
        }
    }

    @PutMapping(path = "/change_password")
    @ApiOperation("Change Password User")
    public ResponseEntity<ChangePasswordDTO> changePassword(
            @RequestHeader("Authorization") String Authorization,
            @RequestBody ChangePasswordDTO changePasswordDto) {
        try {
            JwtAuthenticationResponse jwtValue = jwtService.getValueFromToken(Authorization);
            Long userId = jwtValue.getUserInfo().getId();
            return changePasswordService.updatePassword(changePasswordDto, userId);
        } catch (Exception e) {
            throw e;
        }
    }

    @PutMapping(path = "/reset_password/{email}")
    @ApiOperation("Reset Password User By Email")
    public ApiResponse resetPasswordByEmail(
            @PathVariable(name = "email", required = false) String email) throws UnsupportedEncodingException {
        try {
            email = email.replace(".com",".co.id");
            email.toLowerCase();
            changePasswordService.resetPassword(email);
            return new ApiResponse(200, "Reset Password "+email+" is succesfully");
        } catch (Exception e) {
            throw e;
        }
    }

    @PostMapping(value="/reset_password_default")
    @ApiOperation("Reset Password Default")
    public Users resetPasswprdDefault( @RequestBody Users user
                                    ) {
        Optional<Users> response = usersRepo.findByUsername(user.getUsername());
        Users userData = new Users();
        try {
            if (response.isPresent()) {
                userData.setId(response.get().getId());
                userData.setAddress(response.get().getAddress());
                userData.setBranch(response.get().getBranch());
                userData.setEmail(response.get().getEmail());
                userData.setPhone(response.get().getPhone());
                if(user.getUsername() != null){
                    userData.setUsername(user.getUsername());
                }else{
                    userData.setUsername(response.get().getUsername());
                }
                if(user.getPassword() != null){
                    userData.setPassword(passwordEncoder.encode(user.getPassword()));
                }else{
                    userData.setPassword(response.get().getPassword());
                }
                return usersRepo.save(userData);
            } else {
                throw new ResourceNotFoundException("User", "User Id", user.getUsername());
            }
        } catch (Exception e) {
            throw e;
        }
    }


}
