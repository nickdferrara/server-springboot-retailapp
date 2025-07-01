package com.nickdferrara.serverspringbootretail.fulfillment.services

import com.nickdferrara.serverspringbootretail.fulfillment.entities.Picklist
import com.nickdferrara.serverspringbootretail.fulfillment.enums.FulfillmentStatus
import com.nickdferrara.serverspringbootretail.fulfillment.repositories.PicklistRepository
import com.nickdferrara.serverspringbootretail.fulfillment.FulfillmentService
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.util.*

@Service
class FulfillmentService(
    private val picklistRepository: PicklistRepository
) : BaseService<Picklist, UUID>(picklistRepository), FulfillmentService {
    
    fun startPicking(picklistId: UUID): Picklist? {
        val picklist = picklistRepository.findById(picklistId).orElse(null) ?: return null
        val updatedPicklist = picklist.copy(status = FulfillmentStatus.PICKING)
        return save(updatedPicklist)
    }
    
    fun completePicking(picklistId: UUID): Picklist? {
        val picklist = picklistRepository.findById(picklistId).orElse(null) ?: return null
        val updatedPicklist = picklist.copy(
            status = FulfillmentStatus.PICKED,
            pickedAt = LocalDateTime.now()
        )
        return save(updatedPicklist)
    }
    
    override fun canOrderBeUpdated(orderId: UUID): Boolean {
        val picklist = picklistRepository.findByOrderId(orderId) ?: return true
        return picklist.status == FulfillmentStatus.READY_FOR_PICKING
    }
}