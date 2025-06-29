package com.nickdferrara.serverspringbootretail.fulfillment.listeners

import com.nickdferrara.serverspringbootretail.fulfillment.entities.PickItem
import com.nickdferrara.serverspringbootretail.fulfillment.entities.Picklist
import com.nickdferrara.serverspringbootretail.fulfillment.services.FulfillmentService
import com.nickdferrara.serverspringbootretail.orders.events.OrderReceivedEvent
import org.springframework.stereotype.Component
import org.springframework.transaction.event.TransactionalEventListener

@Component
class OrderReceivedEventListener(
    private val fulfillmentService: FulfillmentService
) {
    
    @TransactionalEventListener
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

        fulfillmentService.save(picklist)
    }
}