package com.eunji.lookatthis.data.model

interface DataModelInterface<T> {
    fun toDomainModel(): T
}