package com.nickdferrara.serverspringbootretail.orders.dtos

import com.nickdferrara.serverspringbootretail.orders.entities.OrderItem

data class UpdateOrderRequest(
    val customerId: String?,
    val items: List<OrderItem>?
)