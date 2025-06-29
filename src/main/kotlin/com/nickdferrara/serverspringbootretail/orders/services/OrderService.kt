package com.nickdferrara.serverspringbootretail.orders.services

import com.nickdferrara.serverspringbootretail.orders.entities.Order
import com.nickdferrara.serverspringbootretail.orders.entities.OrderItem
import com.nickdferrara.serverspringbootretail.orders.enums.OrderStatus
import com.nickdferrara.serverspringbootretail.orders.events.OrderReceivedEvent
import com.nickdferrara.serverspringbootretail.orders.repositories.OrderRepository
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service
import java.util.*

@Service
class OrderService(
    private val orderRepository: OrderRepository,
    private val eventPublisher: ApplicationEventPublisher
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

        eventPublisher.publishEvent(
            OrderReceivedEvent(
                orderId = savedOrder.id,
                customerId = savedOrder.customerId,
                totalAmount = savedOrder.totalAmount,
                items = savedOrder.items
            )
        )
        
        return savedOrder
    }
    
    fun getOrder(orderId: UUID): Order? = orderRepository.findById(orderId).orElse(null)
}