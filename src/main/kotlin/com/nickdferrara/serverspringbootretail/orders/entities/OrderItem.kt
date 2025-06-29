package com.nickdferrara.serverspringbootretail.orders.entities

import jakarta.persistence.*

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