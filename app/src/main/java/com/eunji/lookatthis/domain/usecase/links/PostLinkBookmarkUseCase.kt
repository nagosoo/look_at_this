package com.eunji.lookatthis.domain.usecase.links

import com.eunji.lookatthis.data.model.BookmarkReqModel
import com.eunji.lookatthis.data.model.LinkModel
import com.eunji.lookatthis.data.repository.LinkRepository
import com.eunji.lookatthis.domain.UiState
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PostLinkBookmarkUseCase @Inject constructor(
    private val linkRepository: LinkRepository
) {

    operator fun invoke(linkBookmarkReqModel: BookmarkReqModel): Flow<UiState<LinkModel?>> =
        linkRepository.postLinkBookmark(linkBookmarkReqModel)

}