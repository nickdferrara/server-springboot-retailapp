package com.nickdferrara.serverspringbootretail.fulfillment.entities

import com.nickdferrara.serverspringbootretail.fulfillment.enums.FulfillmentStatus
import jakarta.persistence.*
import java.time.LocalDateTime
import java.util.*

@Entity
@Table(name = "picklists")
class Picklist(
    @Id
    val id: UUID = UUID.randomUUID(),
    
    @Column(name = "order_id", nullable = false)
    val orderId: UUID,
    
    @Column(name = "customer_id", nullable = false)
    val customerId: String,
    
    @OneToMany(mappedBy = "picklist", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    val items: List<PickItem> = emptyList(),
    
    @Column(name = "total_amount", nullable = false)
    val totalAmount: Double,
    
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    val status: FulfillmentStatus = FulfillmentStatus.READY_FOR_PICKING,
    
    @Column(name = "created_at", nullable = false)
    val createdAt: LocalDateTime = LocalDateTime.now(),
    
    @Column(name = "picked_at")
    val pickedAt: LocalDateTime? = null
) {
    fun copy(
        id: UUID = this.id,
        orderId: UUID = this.orderId,
        customerId: String = this.customerId,
        items: List<PickItem> = this.items,
        totalAmount: Double = this.totalAmount,
        status: FulfillmentStatus = this.status,
        createdAt: LocalDateTime = this.createdAt,
        pickedAt: LocalDateTime? = this.pickedAt
    ): Picklist {
        return Picklist(
            id = id,
            orderId = orderId,
            customerId = customerId,
            items = items,
            totalAmount = totalAmount,
            status = status,
            createdAt = createdAt,
            pickedAt = pickedAt
        )
    }
}