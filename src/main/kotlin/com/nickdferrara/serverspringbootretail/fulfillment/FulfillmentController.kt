package com.nickdferrara.serverspringbootretail.fulfillment

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/api/fulfillment")
class FulfillmentController(
    private val fulfillmentService: FulfillmentService
) {
    
    @GetMapping
    fun getAllFulfillmentRequests(): ResponseEntity<List<FulfillmentRequest>> {
        return ResponseEntity.ok(fulfillmentService.getAllFulfillmentRequests())
    }
    
    @GetMapping("/{fulfillmentId}")
    fun getFulfillmentRequest(@PathVariable fulfillmentId: UUID): ResponseEntity<FulfillmentRequest> {
        val request = fulfillmentService.getFulfillmentRequest(fulfillmentId)
        return if (request != null) {
            ResponseEntity.ok(request)
        } else {
            ResponseEntity.notFound().build()
        }
    }
    
    @GetMapping("/order/{orderId}")
    fun getFulfillmentsByOrderId(@PathVariable orderId: UUID): ResponseEntity<List<FulfillmentRequest>> {
        return ResponseEntity.ok(fulfillmentService.getFulfillmentsByOrderId(orderId))
    }
    
    @PostMapping("/{fulfillmentId}/start-picking")
    fun startPicking(@PathVariable fulfillmentId: UUID): ResponseEntity<FulfillmentRequest> {
        val request = fulfillmentService.startPicking(fulfillmentId)
        return if (request != null) {
            ResponseEntity.ok(request)
        } else {
            ResponseEntity.notFound().build()
        }
    }
    
    @PostMapping("/{fulfillmentId}/complete-picking")
    fun completePicking(@PathVariable fulfillmentId: UUID): ResponseEntity<FulfillmentRequest> {
        val request = fulfillmentService.completePicking(fulfillmentId)
        return if (request != null) {
            ResponseEntity.ok(request)
        } else {
            ResponseEntity.notFound().build()
        }
    }
}