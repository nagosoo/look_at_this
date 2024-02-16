package com.eunji.lookatthis.domain.usecase.links

import com.eunji.lookatthis.data.repository.LinkRepository
import com.eunji.lookatthis.domain.UiState
import com.eunji.lookatthis.domain.model.LinkModel
import com.eunji.lookatthis.domain.model.PostLinkReqModel
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