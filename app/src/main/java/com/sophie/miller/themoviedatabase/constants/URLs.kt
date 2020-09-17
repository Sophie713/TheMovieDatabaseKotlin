package com.sophie.miller.themoviedatabase.constants

import com.sophie.miller.themoviedatabase.constants.Api.MOVIE_DB_API_KEY

object URLs {
    const val URL_BASE = "https://api.themoviedb.org/"
    const val URL_LIST_NO_PARAM = "3/movie/changes"//?api_key=${MOVIE_DB_API_KEY}"
    const val URL_MOVIE_PRE_ID = "/3/movie/{movie_id}?api_key=${MOVIE_DB_API_KEY}"
    //&end_date=2018-06-14&start_date=2018-06-14
}