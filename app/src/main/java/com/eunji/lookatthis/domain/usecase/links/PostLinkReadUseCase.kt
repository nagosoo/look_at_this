package com.eunji.lookatthis.domain.usecase.links

import com.eunji.lookatthis.data.model.LinkModel
import com.eunji.lookatthis.data.model.ReadReqModel
import com.eunji.lookatthis.data.repository.LinkRepository
import com.eunji.lookatthis.domain.UiState
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PostLinkReadUseCase @Inject constructor(
    private val linkRepository: LinkRepository
) {

    operator fun invoke(linkReadReqModel: ReadReqModel): Flow<UiState<LinkModel?>> =
        linkRepository.postLinkRead(linkReadReqModel)

}