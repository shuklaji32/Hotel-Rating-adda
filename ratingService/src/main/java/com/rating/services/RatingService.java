package com.rating.services;

import java.util.List;

import com.rating.entities.Rating;

public interface RatingService {

	   //create Rating
		Rating create(Rating rating);
		
		//get all Rating
		List<Rating> getAllRatings();
		
		//get all ratings by userId;
		List<Rating> getRatingsByUserId(String userId);
		
		
		//get all ratings by hotelId;
		List<Rating> getRatingsByHotelId(String hotelId);
		
}
