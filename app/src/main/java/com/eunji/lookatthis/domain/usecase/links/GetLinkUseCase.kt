package com.eunji.lookatthis.domain.usecase.links

import androidx.paging.PagingData
import com.eunji.lookatthis.data.repository.LinkRepository
import com.eunji.lookatthis.domain.model.LinkModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetLinkUseCase @Inject constructor(
    private val linkRepository: LinkRepository
) {

    operator fun invoke(): Flow<PagingData<LinkModel>> = linkRepository.getLinks()

}