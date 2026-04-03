package online_food_order_management_system.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import online_food_order_management_system.model.entity.Order;

@RepositoryRestResource
public interface OrderRepository extends JpaRepository<Order, Integer>{
	

}
