package com.user.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.apache.commons.logging.LogFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.user.entities.Hotel;
import com.user.entities.Rating;
import com.user.entities.User;
import com.user.exceptions.ResourceNotFoundException;
import com.user.repositories.UserRepository;

@Service
public class UserServiceImpl implements UserService{
	private Logger logger=LoggerFactory.getLogger(UserServiceImpl.class);
	
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private RestTemplate restTemplate;
	@Override
	public User saveUser(User user) {
		String randomUserId=UUID.randomUUID().toString();
		user.setUserId(randomUserId);
		return userRepository.save(user);
	}

	@Override
	public List<User> getAllUser() {
		// TODO Auto-generated method stub
		return userRepository.findAll();
	}

	@Override
	public User getUser(String userId) {
		// TODO Auto-generated method stub
		User  user= userRepository.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User with given Id in not found on server!!"+userId));
		//call ratingService by using RestTemplate
	   Rating[] ratingsOfUser=	restTemplate.getForObject("http://RATINGSERVICE/rating/users/"+user.getUserId(), Rating[].class);
	  
	   logger.info("Rating data in User",ratingsOfUser);
	   //System.out.println(forObject);
	   List<Rating> ratings = Arrays.stream(ratingsOfUser).toList();
       List<Rating> ratingList = ratings.stream().map(rating -> {
           //api call to hotel service to get the hotel
           //http://localhost:8082/hotels/1cbaf36d-0b28-4173-b5ea-f1cb0bc0a791
           ResponseEntity<Hotel> forEntity = restTemplate.getForEntity("http://HOTELSERVICE/hotels/"+rating.getHotelId(), Hotel.class);
           //Hotel hotel = hotelService.getHotel(rating.getHotelId());
           // logger.info("response status code: {} ",forEntity.getStatusCode());
           Hotel hotel=forEntity.getBody();
           //set the hotel to rating
           rating.setHotel(hotel);
           //return the rating
           return rating;
       }).collect(Collectors.toList());
	   user.setRatings(ratingList);
		return user;
	}

	@Override
	public void deleteUser(User user) {
		// TODO Auto-generated method stub
		 userRepository.delete(user);
	}

	@Override
	public void updateUser(User user) {
		// TODO Auto-generated method stub
		
	}

}
