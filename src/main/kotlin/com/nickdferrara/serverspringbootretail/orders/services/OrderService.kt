package com.nickdferrara.serverspringbootretail.orders.services

import com.nickdferrara.serverspringbootretail.orders.dtos.UpdateOrderRequest
import com.nickdferrara.serverspringbootretail.orders.entities.Order
import com.nickdferrara.serverspringbootretail.orders.entities.OrderItem
import com.nickdferrara.serverspringbootretail.orders.enums.OrderStatus
import com.nickdferrara.serverspringbootretail.orders.repositories.OrderRepository
import com.nickdferrara.serverspringbootretail.orders.validators.OrderUpdateValidator
import org.springframework.stereotype.Service
import java.util.*

@Service
class OrderService(
    private val orderRepository: OrderRepository,
    private val orderUpdateValidator: OrderUpdateValidator
) {

    fun createOrder(customerId: String, items: List<OrderItem>): Order {
        val totalAmount = items.sumOf { it.price * it.quantity }
        val order = Order(
            customerId = customerId,
            items = items,
            totalAmount = totalAmount,
            status = OrderStatus.RECEIVED
        )

        val savedOrder = orderRepository.save(order)
        return savedOrder
    }

    fun updateOrder(orderId: UUID, updateRequest: UpdateOrderRequest): Order? {
        orderUpdateValidator.validateOrderCanBeUpdated(orderId)

        val existingOrder = orderRepository.findById(orderId).orElse(null) ?: return null
        
        val newItems = updateRequest.items ?: existingOrder.items
        val newTotalAmount = newItems.sumOf { it.price * it.quantity }
        
        val updatedOrder = Order(
            id = existingOrder.id,
            customerId = updateRequest.customerId ?: existingOrder.customerId,
            items = newItems,
            totalAmount = newTotalAmount,
            status = existingOrder.status,
            createdAt = existingOrder.createdAt
        )
        
        return orderRepository.save(updatedOrder)
    }
}