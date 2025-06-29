package com.nickdferrara.serverspringbootretail.fulfillment

import com.nickdferrara.serverspringbootretail.orders.OrderReceivedEvent
import org.springframework.modulith.ApplicationModuleListener
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.util.*
import java.util.concurrent.ConcurrentHashMap

@Service
class FulfillmentService {
    private val fulfillmentRequests = ConcurrentHashMap<UUID, FulfillmentRequest>()
    
    @ApplicationModuleListener
    fun handle(event: OrderReceivedEvent) {
        println("📦 Fulfillment module received order: ${event.orderId}")
        
        val fulfillmentItems = event.items.map { orderItem ->
            FulfillmentItem(
                productId = orderItem.productId,
                productName = orderItem.productName,
                quantity = orderItem.quantity,
                price = orderItem.price
            )
        }
        
        val fulfillmentRequest = FulfillmentRequest(
            orderId = event.orderId,
            customerId = event.customerId,
            items = fulfillmentItems,
            totalAmount = event.totalAmount
        )
        
        fulfillmentRequests[fulfillmentRequest.id] = fulfillmentRequest
        println("✅ Created fulfillment request ${fulfillmentRequest.id} for order ${event.orderId}")
    }
    
    fun startPicking(fulfillmentId: UUID): FulfillmentRequest? {
        val request = fulfillmentRequests[fulfillmentId] ?: return null
        val updatedRequest = request.copy(status = FulfillmentStatus.PICKING)
        fulfillmentRequests[fulfillmentId] = updatedRequest
        return updatedRequest
    }
    
    fun completePicking(fulfillmentId: UUID): FulfillmentRequest? {
        val request = fulfillmentRequests[fulfillmentId] ?: return null
        val updatedRequest = request.copy(
            status = FulfillmentStatus.PICKED,
            pickedAt = LocalDateTime.now()
        )
        fulfillmentRequests[fulfillmentId] = updatedRequest
        return updatedRequest
    }
    
    fun getFulfillmentRequest(fulfillmentId: UUID): FulfillmentRequest? = fulfillmentRequests[fulfillmentId]
    
    fun getAllFulfillmentRequests(): List<FulfillmentRequest> = fulfillmentRequests.values.toList()
    
    fun getFulfillmentsByOrderId(orderId: UUID): List<FulfillmentRequest> =
        fulfillmentRequests.values.filter { it.orderId == orderId }
}