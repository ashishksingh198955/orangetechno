package orangefinalsystemarchitech.com.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import orangefinalsystemarchitech.com.configuration.JwtTokenProvider;
import orangefinalsystemarchitech.com.model.Role;
import orangefinalsystemarchitech.com.model.User;
import orangefinalsystemarchitech.com.service.UserService;

import java.io.IOException;
import java.security.Principal;

import javax.mail.MessagingException;
import javax.validation.Valid;


@RestController
public class UserController {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private UserService userService;

    @PostMapping("/api/user/registration")
    public ResponseEntity<User> register(@Valid @RequestBody User user){
        if(userService.findByUsername(user.getUsername()) != null){
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        user.setRole(Role.USER);
        return new ResponseEntity<User>(userService.saveUser(user), HttpStatus.CREATED);
    }

    @GetMapping("/api/user/login")
    public ResponseEntity<?> login(Principal principal){
        if(principal == null){
            //This should be ok http status because this will be used for logout path.
            return ResponseEntity.ok(principal);
        }
        UsernamePasswordAuthenticationToken authenticationToken = (UsernamePasswordAuthenticationToken) principal;
        
        User user = userService.findByUsername(authenticationToken.getName());
        
        if(user !=null) {
        	String email=user.getUsername();
        	try {
				userService.senMail(email);
			} catch (MessagingException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
        
        user.setToken(jwtTokenProvider.generateToken(authenticationToken));
        System.out.println("Ashish "+user.getToken());
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
    
    @RequestMapping(value = "/sendemail")
    public String sendEmail() {
    	try {
			userService.senMail(null);
		} catch (MessagingException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
       return "Email sent successfully";
    }  
    
}
