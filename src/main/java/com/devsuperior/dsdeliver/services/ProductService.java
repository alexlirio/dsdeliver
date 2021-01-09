package com.devsuperior.dsdeliver.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.dsdeliver.dto.ProductDTO;
import com.devsuperior.dsdeliver.entities.Product;
import com.devsuperior.dsdeliver.repositories.ProductRepository;

@Service
public class ProductService {

	@Autowired
	ProductRepository repository;

	@Transactional(readOnly = true)
	public List<ProductDTO> findAllByOrderByNameAsc() {
		List<Product> products = repository.findAllByOrderByNameAsc();
		return products.stream().map(o -> new ProductDTO(o)).collect(Collectors.toList());
	}
}
