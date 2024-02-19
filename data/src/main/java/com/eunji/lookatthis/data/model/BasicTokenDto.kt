package com.eunji.lookatthis.data.model

import com.eunji.lookatthis.domain.model.BasicToken
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BasicTokenDto(
    @SerialName("memberBasicToken")
    val basicToken: String
) : DataModelInterface<BasicToken> {
    override fun toDomainModel(): BasicToken {
        return BasicToken(
            basicToken = this.basicToken
        )
    }
}