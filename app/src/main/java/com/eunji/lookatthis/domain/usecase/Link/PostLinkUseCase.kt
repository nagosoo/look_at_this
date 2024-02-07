package com.eunji.lookatthis.domain.usecase.Link

import com.eunji.lookatthis.data.model.LinkModel
import com.eunji.lookatthis.data.model.PostLinkReqModel
import com.eunji.lookatthis.data.repository.LinkRepository
import com.eunji.lookatthis.domain.UiState
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PostLinkUseCase @Inject constructor(
    private val linkRepository: LinkRepository
) {

    operator fun invoke(linkReqModel: PostLinkReqModel): Flow<UiState<LinkModel?>> =
        linkRepository.postLink(linkReqModel)

}