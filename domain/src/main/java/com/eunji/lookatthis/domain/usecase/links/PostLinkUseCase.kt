package com.eunji.lookatthis.domain.usecase.links

import com.eunji.lookatthis.domain.UiState
import com.eunji.lookatthis.domain.model.Link
import com.eunji.lookatthis.domain.repository.LinkRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PostLinkUseCase @Inject constructor(
    private val linkRepository: LinkRepository
) {

    operator fun invoke(linkUrl: String, linkMemo: String?): Flow<UiState<Link?>> =
        linkRepository.postLink(linkUrl, linkMemo)

}