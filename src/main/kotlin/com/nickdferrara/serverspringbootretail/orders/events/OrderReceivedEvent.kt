package com.nickdferrara.serverspringbootretail.orders.events

import com.nickdferrara.serverspringbootretail.orders.entities.OrderItem
import java.time.LocalDateTime
import java.util.*

data class OrderReceivedEvent(
    val orderId: UUID,
    val customerId: String,
    val totalAmount: Double,
    val items: List<OrderItem>,
    val receivedAt: LocalDateTime = LocalDateTime.now()
)