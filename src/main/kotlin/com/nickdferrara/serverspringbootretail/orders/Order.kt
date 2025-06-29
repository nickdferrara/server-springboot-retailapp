package com.nickdferrara.serverspringbootretail.orders

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

@Entity
@Table(name = "order_items")
data class OrderItem(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    
    @Column(name = "product_id", nullable = false)
    val productId: String,
    
    @Column(name = "product_name", nullable = false)
    val productName: String,
    
    @Column(name = "quantity", nullable = false)
    val quantity: Int,
    
    @Column(name = "price", nullable = false)
    val price: Double,
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    val order: Order? = null
)

enum class OrderStatus {
    PENDING,
    RECEIVED,
    FULFILLED,
    CANCELLED
}