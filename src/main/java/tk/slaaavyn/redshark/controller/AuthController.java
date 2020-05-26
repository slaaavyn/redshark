package tk.slaaavyn.redshark.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import tk.slaaavyn.redshark.config.EndpointConstants;
import tk.slaaavyn.redshark.dto.auth.AuthRequestDto;
import tk.slaaavyn.redshark.dto.auth.AuthResponseDto;
import tk.slaaavyn.redshark.dto.user.UpdatePasswordDto;
import tk.slaaavyn.redshark.dto.user.UserResponseDto;
import tk.slaaavyn.redshark.model.User;
import tk.slaaavyn.redshark.security.SecurityConstants;
import tk.slaaavyn.redshark.security.jwt.JwtTokenProvider;
import tk.slaaavyn.redshark.security.jwt.JwtUser;
import tk.slaaavyn.redshark.service.UserService;

import java.util.Collections;
import java.util.Date;

@RestController
@RequestMapping(value = EndpointConstants.AUTH_ENDPOINT)
public class AuthController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;

    public AuthController(UserService userService, AuthenticationManager authenticationManager,
                          JwtTokenProvider jwtTokenProvider) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @PostMapping
    @ApiOperation(value = "User authorization with username/password (token generation)")
    public ResponseEntity<AuthResponseDto> login(@RequestBody AuthRequestDto requestDto) {
        if(requestDto == null || requestDto.getUsername() == null || requestDto.getPassword() == null) {
            return ResponseEntity.badRequest().build();
        }

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(requestDto.getUsername(), requestDto.getPassword()));

        User user = userService.getByUsername(requestDto.getUsername());
        if (user == null) {
            throw new UsernameNotFoundException("User with username: " + requestDto.getUsername() + " not found");
        }

        return ResponseEntity.ok(generateTokenResponse(user));
    }

    @PutMapping("/update-password/{id}")
    @ApiOperation(value = "Update user password")
    public ResponseEntity<Object> updatePassword(@PathVariable(name = "id") Long id,
                                                 @RequestBody UpdatePasswordDto passwordDto) {

        JwtUser jwtUser = ((JwtUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        if (JwtUser.userHasAuthority(jwtUser.getAuthorities(), SecurityConstants.ROLE_USER) && !jwtUser.getId().equals(id)) {
            return ResponseEntity.badRequest().build();
        }

        boolean isSuccess = userService.updatePassword(id, passwordDto);

        if (!isSuccess) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok().build();
    }

    private AuthResponseDto generateTokenResponse(User user) {
        Date tokenExpired = new Date(new Date().getTime() + 86400000);

        String token = SecurityConstants.TOKEN_PREFIX +
                jwtTokenProvider.createToken(user.getUsername(), Collections.singletonList(user.getRole().getName()));

        return AuthResponseDto.toDto(UserResponseDto.toDto(user), token, tokenExpired);
    }
}
