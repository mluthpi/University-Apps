package com.example.myuniversity

import com.google.gson.annotations.SerializedName

data class UniversityResponse(

	@field:SerializedName("UniversityResponse")
	val universityResponse: List<UniversityResponseItem?>? = null
)

data class UniversityResponseItem(

	@field:SerializedName("country")
	val country: String? = null,

	@field:SerializedName("web_pages")
	val webPages: List<String?>? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("domains")
	val domains: List<String?>? = null,

	@field:SerializedName("alpha_two_code")
	val alphaTwoCode: String? = null,

	@field:SerializedName("state-province")
	val stateProvince: Any? = null
)
