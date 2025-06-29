package com.nickdferrara.serverspringbootretail.orders.entities

import com.nickdferrara.serverspringbootretail.orders.enums.OrderStatus
import com.nickdferrara.serverspringbootretail.orders.events.OrderReceivedEvent
import jakarta.persistence.*
import org.springframework.data.domain.AfterDomainEventPublication
import org.springframework.data.domain.DomainEvents
import java.time.LocalDateTime
import java.util.*

@Entity
@Table(name = "orders")
class Order(
    @Id
    val id: UUID = UUID.randomUUID(),
    
    @Column(name = "customer_id", nullable = false)
    val customerId: String,
    
    @OneToMany(mappedBy = "order", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    val items: List<OrderItem> = emptyList(),
    
    @Column(name = "total_amount", nullable = false)
    val totalAmount: Double,
    
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    val status: OrderStatus = OrderStatus.RECEIVED,
    
    @Column(name = "created_at", nullable = false)
    val createdAt: LocalDateTime = LocalDateTime.now()
) {
    @Transient
    private val domainEvents = mutableListOf<Any>()
    
    @DomainEvents
    fun getDomainEvents(): List<Any> {
        return domainEvents.toList()
    }
    
    @AfterDomainEventPublication
    fun clearDomainEvents() {
        domainEvents.clear()
    }
    
    @PostPersist
    private fun onOrderPersisted() {
        registerOrderReceivedEvent()
    }
    
    fun registerOrderReceivedEvent() {
        domainEvents.add(
            OrderReceivedEvent(
                orderId = this.id,
                customerId = this.customerId,
                totalAmount = this.totalAmount,
                items = this.items,
                receivedAt = this.createdAt
            )
        )
    }
}