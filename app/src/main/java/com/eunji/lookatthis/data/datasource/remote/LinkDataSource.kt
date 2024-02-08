package com.eunji.lookatthis.data.datasource.remote

import com.eunji.lookatthis.data.model.LinkModel
import com.eunji.lookatthis.data.model.PaginationModel
import com.eunji.lookatthis.data.model.PostLinkReqModel
import com.eunji.lookatthis.data.model.ResponseModel
import com.eunji.lookatthis.data.retrofit.services.LinkService
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LinkDataSource @Inject constructor(
    private val linkService: LinkService
) {

    suspend fun postLink(linkReqModel: PostLinkReqModel): Response<ResponseModel<LinkModel?>> =
        linkService.postLink(linkReqModel)

    suspend fun getLinks(
        pageSize: Int? = null,
        cursorId: Int? = null
    ): Response<PaginationModel<LinkModel>> = linkService.getLinks(pageSize, cursorId)

}