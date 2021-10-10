package com.learning.sociallogin.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.learning.sociallogin.entity.Product;

@RestController
@RequestMapping("/product")
@CrossOrigin
public class ProductController {

	private ArrayList<Product> products = new ArrayList<Product>() {
		{
			add(new Product("T-Shirt", new BigDecimal("10.000")));
			add(new Product("Nike shoes", new BigDecimal("15.000")));
			add(new Product("Addidas shoes", new BigDecimal("20.000")));
			add(new Product("Go pro", new BigDecimal("80.000")));
		}
	};

	@GetMapping
	public ResponseEntity<List<Product>> getProducts() {
		return new ResponseEntity(this.products, HttpStatus.OK);
	}

}
