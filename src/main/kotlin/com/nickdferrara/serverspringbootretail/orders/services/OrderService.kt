package com.nickdferrara.serverspringbootretail.orders.services

import com.nickdferrara.serverspringbootretail.orders.entities.Order
import com.nickdferrara.serverspringbootretail.orders.entities.OrderItem
import com.nickdferrara.serverspringbootretail.orders.enums.OrderStatus
import com.nickdferrara.serverspringbootretail.orders.events.OrderReceivedEvent
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service
import java.util.*
import java.util.concurrent.ConcurrentHashMap

@Service
class OrderService(
    private val eventPublisher: ApplicationEventPublisher
) {
    private val orders = ConcurrentHashMap<UUID, Order>()
    
    fun createOrder(customerId: String, items: List<OrderItem>): Order {
        val totalAmount = items.sumOf { it.price * it.quantity }
        val order = Order(
            customerId = customerId,
            items = items,
            totalAmount = totalAmount
        )
        
        orders[order.id] = order
        return order
    }
    
    fun receiveOrder(orderId: UUID): Order? {
        val order = orders[orderId] ?: return null
        val receivedOrder = order.copy(status = OrderStatus.RECEIVED)
        orders[orderId] = receivedOrder
        
        // Publish event to fulfillment module
        eventPublisher.publishEvent(
            OrderReceivedEvent(
                orderId = receivedOrder.id,
                customerId = receivedOrder.customerId,
                totalAmount = receivedOrder.totalAmount,
                items = receivedOrder.items
            )
        )
        
        return receivedOrder
    }
    
    fun getOrder(orderId: UUID): Order? = orders[orderId]
    
    fun getAllOrders(): List<Order> = orders.values.toList()
}