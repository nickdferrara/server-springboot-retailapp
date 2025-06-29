package com.nickdferrara.serverspringbootretail.fulfillment

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/api/fulfillment")
class FulfillmentController(
    private val fulfillmentService: FulfillmentService
) {

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