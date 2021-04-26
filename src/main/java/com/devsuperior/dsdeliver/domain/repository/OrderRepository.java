package com.devsuperior.dsdeliver.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.devsuperior.dsdeliver.domain.model.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {

	@Query("SELECT DISTINCT o FROM Order o JOIN FETCH o.products WHERE o.status = 0 ORDER BY o.moment ASC")
	List<Order> findPendingOrdersWithProductsByOrderByMomentAsc();

}
