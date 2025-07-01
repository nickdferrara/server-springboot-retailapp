package com.nickdferrara.serverspringbootretail.fulfillment

import java.util.*

interface FulfillmentService {
    fun canOrderBeUpdated(orderId: UUID): Boolean
}