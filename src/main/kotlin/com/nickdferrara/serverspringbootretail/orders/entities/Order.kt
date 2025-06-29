package com.nickdferrara.serverspringbootretail.orders.entities

import com.nickdferrara.serverspringbootretail.orders.enums.OrderStatus
import jakarta.persistence.*
import java.time.LocalDateTime
import java.util.*

@Entity
@Table(name = "orders")
data class Order(
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
    val status: OrderStatus = OrderStatus.PENDING,
    
    @Column(name = "created_at", nullable = false)
    val createdAt: LocalDateTime = LocalDateTime.now()
)