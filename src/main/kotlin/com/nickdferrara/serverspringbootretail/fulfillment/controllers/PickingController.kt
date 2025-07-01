package com.nickdferrara.serverspringbootretail.fulfillment.controllers

import com.nickdferrara.serverspringbootretail.fulfillment.entities.Picklist
import com.nickdferrara.serverspringbootretail.fulfillment.services.FulfillmentService
import com.nickdferrara.serverspringbootretail.fulfillment.services.PickingService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/api/fulfillment")
class PickingController(
    private val pickingService: PickingService
) {

    @PostMapping("/{picklistId}/start-picking")
    fun startPicking(@PathVariable picklistId: UUID): ResponseEntity<Picklist> {
        val picklist = pickingService.startPicking(picklistId)
        return if (picklist != null) {
            ResponseEntity.ok(picklist)
        } else {
            ResponseEntity.notFound().build()
        }
    }
    
    @PostMapping("/{picklistId}/complete-picking")
    fun completePicking(@PathVariable picklistId: UUID): ResponseEntity<Picklist> {
        val picklist = pickingService.completePicking(picklistId)
        return if (picklist != null) {
            ResponseEntity.ok(picklist)
        } else {
            ResponseEntity.notFound().build()
        }
    }
}