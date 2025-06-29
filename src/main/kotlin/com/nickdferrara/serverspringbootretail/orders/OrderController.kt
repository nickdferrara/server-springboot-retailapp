package com.nickdferrara.serverspringbootretail.orders

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
    
    @PostMapping("/{orderId}/receive")
    fun receiveOrder(@PathVariable orderId: UUID): ResponseEntity<Order> {
        val order = orderService.receiveOrder(orderId)
        return if (order != null) {
            ResponseEntity.ok(order)
        } else {
            ResponseEntity.notFound().build()
        }
    }
    
    @GetMapping("/{orderId}")
    fun getOrder(@PathVariable orderId: UUID): ResponseEntity<Order> {
        val order = orderService.getOrder(orderId)
        return if (order != null) {
            ResponseEntity.ok(order)
        } else {
            ResponseEntity.notFound().build()
        }
    }
    
    @GetMapping
    fun getAllOrders(): ResponseEntity<List<Order>> {
        return ResponseEntity.ok(orderService.getAllOrders())
    }
}

data class CreateOrderRequest(
    val customerId: String,
    val items: List<OrderItem>
)