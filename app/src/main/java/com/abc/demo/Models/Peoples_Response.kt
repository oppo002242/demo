package com.abc.demo.Models

import com.google.gson.annotations.SerializedName

data class Api_Response(

	@field:SerializedName("next")
	val next: String? = null,

	@field:SerializedName("previous")
	val previous: Any? = null,

	@field:SerializedName("count")
	val count: Int? = null,

	@field:SerializedName("results")
	val results: List<ResultsItem?>? = null
)

data class ResultsItem(

	@field:SerializedName("films")
	val films: List<String?>? = null,

	@field:SerializedName("homeworld")
	val homeworld: String? = null,

	@field:SerializedName("gender")
	val gender: String? = null,

	@field:SerializedName("skin_color")
	val skinColor: String? = null,

	@field:SerializedName("edited")
	val edited: String? = null,

	@field:SerializedName("created")
	val created: String? = null,

	@field:SerializedName("mass")
	val mass: String? = null,

	@field:SerializedName("vehicles")
	val vehicles: List<String?>? = null,

	@field:SerializedName("url")
	val url: String? = null,

	@field:SerializedName("hair_color")
	val hairColor: String? = null,

	@field:SerializedName("birth_year")
	val birthYear: String? = null,

	@field:SerializedName("eye_color")
	val eyeColor: String? = null,

	@field:SerializedName("species")
	val species: List<Any?>? = null,

	@field:SerializedName("starships")
	val starships: List<String?>? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("height")
	val height: String? = null,

	var isExpanded: Boolean = false,



)
