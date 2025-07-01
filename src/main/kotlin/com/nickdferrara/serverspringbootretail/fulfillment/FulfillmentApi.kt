package com.nickdferrara.serverspringbootretail.fulfillment

import java.util.*

interface FulfillmentApi {
    fun canOrderBeUpdated(orderId: UUID): Boolean
}