package com.vitiello.android.stargazers.network.dto

import com.fasterxml.jackson.annotation.JsonIgnoreProperties


/**
 * Created by Antonio Vitiello
 */
@JsonIgnoreProperties(ignoreUnknown = true)
class StargazerResponse : ArrayList<StargazerResponseItem>()