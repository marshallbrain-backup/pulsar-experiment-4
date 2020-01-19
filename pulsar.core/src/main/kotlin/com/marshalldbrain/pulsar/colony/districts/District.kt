package com.marshalldbrain.pulsar.colony.districts

data class District (
	var type: DistrictType = DistrictType.emptyDistrict,
	var amount: Int = 0
) {
}