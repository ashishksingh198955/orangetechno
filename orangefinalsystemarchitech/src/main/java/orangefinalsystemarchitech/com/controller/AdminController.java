package orangefinalsystemarchitech.com.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import orangefinalsystemarchitech.com.model.User;
import orangefinalsystemarchitech.com.service.UserService;



@RestController
public class AdminController {

    @Autowired
    private UserService userService;

    @GetMapping("/api/admin/all")
    public ResponseEntity<?> findAllUsers(){
    	List<User>userList=userService.findAllUsers();
    	if(userList.isEmpty()|| userList ==null){
    		String errorMessage = "Token has been expired";
    	    return new ResponseEntity<>(errorMessage,HttpStatus.UNAUTHORIZED);
    	}
//        return ResponseEntity.ok(userService.findAllUsers());
    return new ResponseEntity<>(userList, HttpStatus.OK);

    }
    
//    @Autowired
//    CommonService commonService;
//    
//    @Autowired
//    InstituteRepository instituteRepository;
//
//    @PostMapping("/api/admin/saveinstitute")
//    public String save(@RequestBody Institute inst) {
//        inst.getContact().setName(inst.getName());
//        inst.setDoe(new Date());
//        commonService.saveInstitute(inst);
//       
//        return "Data Saved Succefully";
//    }
}
