package com.nickdferrara.serverspringbootretail.orders.services

import com.nickdferrara.serverspringbootretail.orders.entities.Order
import com.nickdferrara.serverspringbootretail.orders.entities.OrderItem
import com.nickdferrara.serverspringbootretail.orders.enums.OrderStatus
import com.nickdferrara.serverspringbootretail.orders.repositories.OrderRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
class OrderService(
    private val orderRepository: OrderRepository
) {

    fun createOrder(customerId: String, items: List<OrderItem>): Order {
        val totalAmount = items.sumOf { it.price * it.quantity }
        val order = Order(
            customerId = customerId,
            items = items,
            totalAmount = totalAmount,
            status = OrderStatus.RECEIVED
        )

        order.registerOrderReceivedEvent()
        val savedOrder = orderRepository.save(order)

        return savedOrder
    }
}