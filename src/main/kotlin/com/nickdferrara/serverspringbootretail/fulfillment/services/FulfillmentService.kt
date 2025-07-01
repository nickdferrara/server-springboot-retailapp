package com.nickdferrara.serverspringbootretail.fulfillment.services

import com.nickdferrara.serverspringbootretail.fulfillment.entities.Picklist
import com.nickdferrara.serverspringbootretail.fulfillment.enums.FulfillmentStatus
import com.nickdferrara.serverspringbootretail.fulfillment.repositories.PicklistRepository
import com.nickdferrara.serverspringbootretail.fulfillment.FulfillmentApi
import org.springframework.stereotype.Service
import java.util.*

@Service
class FulfillmentService(
    private val pickingService: PickingService
) : FulfillmentApi {
    
    override fun canOrderBeUpdated(orderId: UUID): Boolean {
        val picklist = pickingService.findByOrderId(orderId) ?: return true
        return picklist.status == FulfillmentStatus.READY_FOR_PICKING
    }
}