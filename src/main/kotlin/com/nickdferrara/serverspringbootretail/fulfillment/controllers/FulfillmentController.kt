package com.nickdferrara.serverspringbootretail.fulfillment.controllers

import com.nickdferrara.serverspringbootretail.fulfillment.entities.Picklist
import com.nickdferrara.serverspringbootretail.fulfillment.services.FulfillmentService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/api/fulfillment")
class FulfillmentController(
    private val fulfillmentService: FulfillmentService
) {

    @PostMapping("/{picklistId}/start-picking")
    fun startPicking(@PathVariable picklistId: UUID): ResponseEntity<Picklist> {
        val picklist = fulfillmentService.startPicking(picklistId)
        return if (picklist != null) {
            ResponseEntity.ok(picklist)
        } else {
            ResponseEntity.notFound().build()
        }
    }
    
    @PostMapping("/{picklistId}/complete-picking")
    fun completePicking(@PathVariable picklistId: UUID): ResponseEntity<Picklist> {
        val picklist = fulfillmentService.completePicking(picklistId)
        return if (picklist != null) {
            ResponseEntity.ok(picklist)
        } else {
            ResponseEntity.notFound().build()
        }
    }
}