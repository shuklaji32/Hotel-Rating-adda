package com.user.Controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.user.entities.User;
import com.user.services.UserService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;

@RestController
@RequestMapping("/users")
public class UserController {
   
	private Logger logger=LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	private UserService userService;
	
	@PostMapping
	public ResponseEntity<User> createUser(@RequestBody User user){
		User user1=userService.saveUser(user);
		return ResponseEntity.status(HttpStatus.CREATED).body(user1);
	}
	int retryCount=0;
	@GetMapping("/{id}")
	//@CircuitBreaker(name="ratingHotelBreaker", fallbackMethod = "ratingHotelFallback")
	//@Retry(name="ratingHotelService", fallbackMethod = "ratingHotelFallback")
	@RateLimiter(name="userRateLimiter", fallbackMethod ="ratingHotelFallback")
	public ResponseEntity<User> getUser(@PathVariable String id){
		logger.info("Get Sindle user handler");
		//retryCount++;
		User user=userService.getUser(id);
		return ResponseEntity.ok(user);
	}
	
	//creating fall back method for circuit breaker
	public ResponseEntity<User> ratingHotelFallback(String userId,Exception ex){
	logger.info("Fallback id executed beacsue service is down:",ex.getMessage());
	User user=	User.builder()
			.email("dummyEmail.com")
			.name("dummy")
			.about("This user is created dummy because some service is down")
			.build();
	return new ResponseEntity<>(user,HttpStatus.OK);
	}
	@GetMapping
	public ResponseEntity<List<User>> getAllUser(){
		List<User> users=userService.getAllUser();
		return ResponseEntity.ok(users);
	}
	
	@DeleteMapping
	public ResponseEntity<String> deleteUser(@RequestBody User user){
		userService.deleteUser(user);
		return ResponseEntity.ok().body("User deleted Successfully");
	}
}
