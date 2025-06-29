package com.nickdferrara.serverspringbootretail.fulfillment

import java.time.LocalDateTime
import java.util.*

data class FulfillmentRequest(
    val id: UUID = UUID.randomUUID(),
    val orderId: UUID,
    val customerId: String,
    val items: List<FulfillmentItem>,
    val totalAmount: Double,
    val status: FulfillmentStatus = FulfillmentStatus.READY_FOR_PICKING,
    val createdAt: LocalDateTime = LocalDateTime.now(),
    val pickedAt: LocalDateTime? = null
)

data class FulfillmentItem(
    val productId: String,
    val productName: String,
    val quantity: Int,
    val price: Double
)

enum class FulfillmentStatus {
    READY_FOR_PICKING,
    PICKING,
    PICKED,
    SHIPPED,
    DELIVERED
}