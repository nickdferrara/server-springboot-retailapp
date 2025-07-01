package com.nickdferrara.serverspringbootretail.orders.controllers

import com.nickdferrara.serverspringbootretail.orders.dtos.CreateOrderRequest
import com.nickdferrara.serverspringbootretail.orders.entities.Order
import com.nickdferrara.serverspringbootretail.orders.entities.OrderItem
import com.nickdferrara.serverspringbootretail.orders.services.OrderService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/api/orders")
class OrderController(
    private val orderService: OrderService
) {
    
    @PostMapping
    fun createOrder(@RequestBody createOrderRequest: CreateOrderRequest): ResponseEntity<Order> {
        val order = orderService.createOrder(
            createOrderRequest.customerId,
            createOrderRequest.items
        )
        return ResponseEntity.ok(order)
    }
}
