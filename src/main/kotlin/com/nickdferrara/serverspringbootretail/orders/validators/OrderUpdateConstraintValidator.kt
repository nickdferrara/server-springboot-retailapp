package com.nickdferrara.serverspringbootretail.orders.validators

import com.nickdferrara.serverspringbootretail.fulfillment.FulfillmentApi
import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext
import java.util.*

@Validator
class OrderUpdateConstraintValidator(
    private val fulfillmentApi: FulfillmentApi
) : ConstraintValidator<ValidOrderForUpdate, UUID> {
    
    override fun isValid(orderId: UUID?, context: ConstraintValidatorContext): Boolean {
        return orderId?.let { fulfillmentApi.canOrderBeUpdated(it) } ?: false
    }
}