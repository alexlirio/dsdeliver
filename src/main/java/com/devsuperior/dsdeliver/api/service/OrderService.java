package com.devsuperior.dsdeliver.api.service;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.dsdeliver.api.dto.OrderDTO;
import com.devsuperior.dsdeliver.api.dto.ProductDTO;
import com.devsuperior.dsdeliver.domain.model.Order;
import com.devsuperior.dsdeliver.domain.model.OrderStatus;
import com.devsuperior.dsdeliver.domain.model.Product;
import com.devsuperior.dsdeliver.domain.repository.OrderRepository;
import com.devsuperior.dsdeliver.domain.repository.ProductRepository;

@Service
public class OrderService {

	@Autowired
	OrderRepository repository;

	@Autowired
	ProductRepository productRepository;

	@Transactional(readOnly = true)
	public List<OrderDTO> findAll() {
		List<Order> orders = repository.findPendingOrdersWithProductsByOrderByMomentAsc();
		return orders.stream().map(o -> new OrderDTO(o)).collect(Collectors.toList());
	}

	@Transactional
	public OrderDTO insert(OrderDTO dto) {
		Order order = new Order(null, dto.getAddress(), dto.getLatitude(), dto.getLongitude(), Instant.now(),
				OrderStatus.PENDING);
		for (ProductDTO p : dto.getProducts()) {
			Product product = productRepository.getOne(p.getId());
			order.getProducts().add(product);
		}
		order = repository.save(order);
		return new OrderDTO(order);
	}

	@Transactional
	public OrderDTO setDelivered(Long id) {
		Order order = repository.getOne(id);
		order.setStatus(OrderStatus.DELIVERED);
		order = repository.save(order);
		return new OrderDTO(order);
	}
}
