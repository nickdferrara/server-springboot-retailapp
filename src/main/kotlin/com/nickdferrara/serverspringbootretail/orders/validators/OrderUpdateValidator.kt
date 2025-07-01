package com.nickdferrara.serverspringbootretail.orders.validators

import com.nickdferrara.serverspringbootretail.fulfillment.FulfillmentApi
import com.nickdferrara.serverspringbootretail.orders.exceptions.OrderUpdateNotAllowedException
import org.springframework.stereotype.Component
import java.util.*

@Component
class OrderUpdateValidator(
    private val fulfillmentApi: FulfillmentApi
) {
    
    fun validateOrderCanBeUpdated(orderId: UUID) {
        if (!fulfillmentApi.canOrderBeUpdated(orderId)) {
            throw OrderUpdateNotAllowedException("Order cannot be updated as fulfillment has already started")
        }
    }
}