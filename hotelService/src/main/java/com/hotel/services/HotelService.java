package com.hotel.services;

import java.util.List;

import com.hotel.entities.Hotel;

public interface HotelService {

	//create Hotel
	Hotel create(Hotel hotel);
	
	//get all hotels
	List<Hotel> getAllHotels();
	
	//get single
	Hotel get(String id);
}
