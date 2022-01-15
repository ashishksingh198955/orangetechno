package orangefinalsystemarchitech.com.service;
import java.io.IOException;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import orangefinalsystemarchitech.com.model.User;

public interface UserService {
    User saveUser(User user);

    User findByUsername(String username);

    List<User> findAllUsers();
    
    void senMail(String email) throws AddressException, MessagingException, IOException;		 

	
}
