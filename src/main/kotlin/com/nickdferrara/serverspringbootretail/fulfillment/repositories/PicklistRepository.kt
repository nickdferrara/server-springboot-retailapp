package com.nickdferrara.serverspringbootretail.fulfillment.repositories

import com.nickdferrara.serverspringbootretail.fulfillment.entities.Picklist
import com.nickdferrara.serverspringbootretail.fulfillment.enums.FulfillmentStatus
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface PicklistRepository : JpaRepository<Picklist, UUID> {
    
    fun findByOrderId(orderId: UUID): Picklist?
    
    fun findByStatus(status: FulfillmentStatus): List<Picklist>
    
    fun findByCustomerId(customerId: String): List<Picklist>
}