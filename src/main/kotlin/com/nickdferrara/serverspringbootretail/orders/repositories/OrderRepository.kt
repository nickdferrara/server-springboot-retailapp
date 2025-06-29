package com.nickdferrara.serverspringbootretail.orders.repositories

import com.nickdferrara.serverspringbootretail.orders.entities.Order
import com.nickdferrara.serverspringbootretail.orders.enums.OrderStatus
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface OrderRepository : JpaRepository<Order, UUID> {
    
    fun findByCustomerId(customerId: String): List<Order>
    
    fun findByStatus(status: OrderStatus): List<Order>
    
    fun findByCustomerIdAndStatus(customerId: String, status: OrderStatus): List<Order>
}