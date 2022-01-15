package orangefinalsystemarchitech.com.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import orangefinalsystemarchitech.com.dao.UserRepository;
import orangefinalsystemarchitech.com.model.User;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

@Service
@Transactional
public class UserServiceImpl implements UserService {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private PasswordEncoder passwordEncoder;
	
	
	@Override
	public User saveUser(User user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
     return userRepository.save(user);
	}

	@Override
	public User findByUsername(String username) {
		// TODO Auto-generated method stub
		return userRepository.findByUsername(username).orElse(null);
	}

	@Override
	public List<User> findAllUsers() {
		// TODO Auto-generated method stub
		 return userRepository.findAll();
	}

	@Override
	public void senMail(String email) throws AddressException, MessagingException, IOException {
	
			   Properties props = new Properties();
			   props.put("mail.smtp.auth", "true");
			   props.put("mail.smtp.starttls.enable", "true");
			   props.put("mail.smtp.host", "smtp.gmail.com");
			   props.put("mail.smtp.port", "587");
			   
			   Session session = Session.getInstance(props, new javax.mail.Authenticator() {
				   
			      protected PasswordAuthentication getPasswordAuthentication() {
			         return new PasswordAuthentication("ashishkumarsingh296@gmail.com", "9599046971");
			      }
			   });
			   
//			  to verification email code https://stackabuse.com/spring-security-email-verification-registration/
			   Message msg = new MimeMessage(session);
			   msg.setFrom(new InternetAddress("ashishkumarsingh296@gmail.com", false));

			   msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
			   msg.setSubject("Login Success");
			   msg.setContent("Thanks to login with us!", "text/html");
			   msg.setSentDate(new Date());

			   MimeBodyPart messageBodyPart = new MimeBodyPart();
			   messageBodyPart.setContent("Welcome To JKV Solutions I am glad to see you again", "text/html");

			   Multipart multipart = new MimeMultipart();
			   multipart.addBodyPart(messageBodyPart);
			   MimeBodyPart attachPart = new MimeBodyPart();

			   attachPart.attachFile("/var/tmp/image19.png");
//			   multipart.addBodyPart(attachPart);
			   msg.setContent(multipart);
			   Transport.send(msg);   
			}

		
	


}
