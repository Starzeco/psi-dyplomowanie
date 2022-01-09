package com.example.dyplomowaniebackend.domain.model.exception

import kotlin.reflect.KClass

class EntityNotFoundException(entity: KClass<*>, id: Long) : RuntimeException("Could not find entity ${entity.simpleName} with id: $id") {
}