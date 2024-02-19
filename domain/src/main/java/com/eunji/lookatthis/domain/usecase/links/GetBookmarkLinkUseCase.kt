package com.eunji.lookatthis.domain.usecase.links

import androidx.paging.PagingData
import com.eunji.lookatthis.domain.model.Link
import com.eunji.lookatthis.domain.repository.LinkRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetBookmarkLinkUseCase @Inject constructor(
    private val linkRepository: LinkRepository
) {

    operator fun invoke(): Flow<PagingData<Link>> = linkRepository.getBookmarkLinks()

}