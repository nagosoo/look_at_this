package com.eunji.lookatthis.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.eunji.lookatthis.data.datasource.remote.LinkDataSource
import com.eunji.lookatthis.domain.model.Link
import retrofit2.HttpException
import java.io.IOException

class BookmarkLinkPagingSource(
    private val linkDataSource: LinkDataSource
) : PagingSource<Int, Link>() {

    override fun getRefreshKey(state: PagingState<Int, Link>): Int? {
        return null //refresh시 항상 처음부터 로드
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Link> {
        val cursorId = params.key //처음에는 null
        return try {
            val response =
                linkDataSource.getBookmarkLinks(pageSize = params.loadSize, cursorId = cursorId)
            val endOfPagination = response.body()?.hasNext == false
            val repos = response.body()?.values!!.map {linkDto->
                linkDto.toDomainModel()
            }
            val nextKey = if (endOfPagination) {
                null
            } else {
                response.body()!!.nextCursor
            }
            LoadResult.Page(
                data = repos, prevKey = null, nextKey = nextKey
            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }

    companion object {
        const val BOOKMARK_LINK_NETWORK_PAGE_SIZE = 10
    }

}
