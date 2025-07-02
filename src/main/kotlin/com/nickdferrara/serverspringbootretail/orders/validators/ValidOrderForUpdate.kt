package com.nickdferrara.serverspringbootretail.orders.validators

import jakarta.validation.Constraint
import jakarta.validation.Payload
import kotlin.reflect.KClass

@Target(AnnotationTarget.VALUE_PARAMETER)
@Retention(AnnotationRetention.RUNTIME)
@Constraint(validatedBy = [OrderUpdateConstraintValidator::class])
annotation class ValidOrderForUpdate(
    val message: String = "Order cannot be updated as fulfillment has already started",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<out Payload>> = []
)