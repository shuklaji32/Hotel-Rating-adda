package com.hotel.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hotel.entities.Hotel;
import com.hotel.services.HotelService;

@RestController
@RequestMapping("/hotels")
public class HotelController {
	
	@Autowired
	private HotelService hotelService;
	
	@PostMapping
	public ResponseEntity<Hotel> createHotel(@RequestBody Hotel Hotel){
		Hotel Hotel1=hotelService.create(Hotel);
		return ResponseEntity.status(HttpStatus.CREATED).body(Hotel1);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Hotel> getHotel(@PathVariable String id){
		Hotel Hotel=hotelService.get(id);
		return ResponseEntity.ok(Hotel);
	}
	
	@GetMapping
	public ResponseEntity<List<Hotel>> getAllHotel(){
		List<Hotel> Hotels=hotelService.getAllHotels();
		return ResponseEntity.ok(Hotels);
	}

}
