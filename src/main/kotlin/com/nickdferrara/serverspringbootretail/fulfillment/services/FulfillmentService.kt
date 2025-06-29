package com.nickdferrara.serverspringbootretail.fulfillment.services

import com.nickdferrara.serverspringbootretail.fulfillment.entities.Picklist
import com.nickdferrara.serverspringbootretail.fulfillment.entities.PickItem
import com.nickdferrara.serverspringbootretail.fulfillment.enums.FulfillmentStatus
import com.nickdferrara.serverspringbootretail.fulfillment.repositories.PicklistRepository
import com.nickdferrara.serverspringbootretail.orders.events.OrderReceivedEvent
import org.springframework.modulith.ApplicationModuleListener
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.util.*

@Service
class FulfillmentService(
    private val picklistRepository: PicklistRepository
) {
    
    @ApplicationModuleListener
    fun handle(event: OrderReceivedEvent) {
        println("📦 Fulfillment module received order: ${event.orderId}")
        
        val pickItems = event.items.map { orderItem ->
            PickItem(
                productId = orderItem.productId,
                productName = orderItem.productName,
                quantity = orderItem.quantity,
                price = orderItem.price
            )
        }
        
        val picklist = Picklist(
            orderId = event.orderId,
            customerId = event.customerId,
            items = pickItems,
            totalAmount = event.totalAmount
        )
        
        val savedPicklist = picklistRepository.save(picklist)
        println("✅ Created picklist ${savedPicklist.id} for order ${event.orderId}")
    }
    
    fun startPicking(picklistId: UUID): Picklist? {
        val picklist = picklistRepository.findById(picklistId).orElse(null) ?: return null
        val updatedPicklist = picklist.copy(status = FulfillmentStatus.PICKING)
        return picklistRepository.save(updatedPicklist)
    }
    
    fun completePicking(picklistId: UUID): Picklist? {
        val picklist = picklistRepository.findById(picklistId).orElse(null) ?: return null
        val updatedPicklist = picklist.copy(
            status = FulfillmentStatus.PICKED,
            pickedAt = LocalDateTime.now()
        )
        return picklistRepository.save(updatedPicklist)
    }
}