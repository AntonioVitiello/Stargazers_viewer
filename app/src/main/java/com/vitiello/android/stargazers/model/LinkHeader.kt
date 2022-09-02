package com.vitiello.android.stargazers.model

import android.net.Uri
import android.os.Parcelable
import com.vitiello.android.stargazers.network.dto.StargazerResponse
import com.vitiello.android.stargazers.tools.safeLet
import kotlinx.android.parcel.Parcelize
import retrofit2.Response

/**
 * Created by Antonio Vitiello
 */
@Parcelize
class LinkHeader constructor() : Parcelable {
    var firstPage = 1
        private set
    var lastPage = 1
        private set
    var nextPage = 1
        private set
    var prevPage = 0
        private set
    var perPage = 30
        private set

    companion object {
        private const val LINK_HEADER = "link"
        private const val FIRST = "first"
        private const val LAST = "last"
        private const val PREV = "prev"
        private const val NEXT = "next"
        private const val PAGE_QUERY_PARAM = "page"
        private const val PERPAGE_QUERY_PARAM = "per_page"
    }

    constructor(response: Response<StargazerResponse>) : this() {
        parseResponse(response)
    }

    fun parseResponse(response: Response<StargazerResponse>) {
        val linkHeader = response.headers().get(LINK_HEADER)
        if (linkHeader != null) {
            val links = linkHeader.split(',')

            // set next and last
            getNextLastOrNull(links)?.let { nextLast ->
                setNextPage(nextLast.first)
                setLastPage(nextLast.second)
            } ?: run {
                nextPage = 0
                lastPage = 0
            }

            // set prev and first
            getPrevFirstOrNull(links)?.let { prevFirst ->
                setPrevPage(prevFirst.first)
                setFirstPage(prevFirst.second)
            } ?: run {
                prevPage = 0
                firstPage = 0
            }

        } else {
            firstPage = 0
            lastPage = 0
            nextPage = 0
            prevPage = 0
            perPage = 0
        }
    }

    private fun setNextPage(next: String?) {
        val nextPagePerpage = getPagesPair(next)
        nextPage = nextPagePerpage.first?.toInt() ?: 0
        perPage = nextPagePerpage.second?.toInt() ?: 0
    }

    private fun setLastPage(last: String?) {
        val lastPagePerpage = getPagesPair(last)
        lastPage = lastPagePerpage.first?.toInt() ?: 0
        perPage = lastPagePerpage.second?.toInt() ?: 0
    }

    private fun setPrevPage(prev: String?) {
        val prevPagePerpage = getPagesPair(prev)
        prevPage = prevPagePerpage.first?.toInt() ?: 0
        perPage = prevPagePerpage.second?.toInt() ?: 30
    }

    private fun setFirstPage(first: String?) {
        val firstPagePerpage = getPagesPair(first)
        firstPage = firstPagePerpage.first?.toInt() ?: 0
        perPage = firstPagePerpage.second?.toInt() ?: 0
    }

    private fun getNextLastOrNull(links: List<String>): Pair<String, String>? {
        var next: String? = null
        var last: String? = null
        links.forEach { link ->
            val split = link.split(';')
            var index = split.indexOfFirst { it.contains(NEXT) }
            if (index != -1) {
                next = split[index - 1].trimEnd('>')
            } else {
                index = split.indexOfFirst { it.contains(LAST) }
                if (index != -1) {
                    last = split[index - 1].trimEnd('>')
                }
            }
        }
        return safeLet(next, last) { sNext, sLast ->
            sNext to sLast
        }
    }

    private fun getPrevFirstOrNull(links: List<String>): Pair<String, String>? {
        var prev: String? = null
        var first: String? = null
        links.forEach { link ->
            val split = link.split(';')
            var index = split.indexOfFirst { it.contains(PREV) }
            if (index != -1) {
                prev = split[index - 1].trimEnd('>')
            } else {
                index = split.indexOfFirst { it.contains(FIRST) }
                if (index != -1) {
                    first = split[index - 1].trimEnd('>')
                }
            }
        }
        return safeLet(prev, first) { sPrev, sFirst ->
            sPrev to sFirst
        }
    }

    private fun getPagesPair(link: String?): Pair<String?, String?> {
        val uri = Uri.parse(link)
        val pageParam = uri.getQueryParameter(PAGE_QUERY_PARAM)
        val perPageParam = uri.getQueryParameter(PERPAGE_QUERY_PARAM)
        return pageParam to perPageParam
    }

    fun resetToFirstPage() {
        firstPage = 1
        lastPage = 1
        nextPage = 1
        prevPage = 0
        perPage = 30
    }

    override fun toString(): String {
        return "LinkHeader(firstPage=$firstPage, lastPage=$lastPage, nextPage=$nextPage, prevPage=$prevPage, perPage=$perPage)"
    }

}