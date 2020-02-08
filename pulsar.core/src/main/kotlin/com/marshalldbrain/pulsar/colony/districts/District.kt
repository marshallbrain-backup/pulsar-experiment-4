package com.marshalldbrain.pulsar.colony.districts

import com.marshalldbrain.pulsar.colony.construction.Constructable
import com.marshalldbrain.pulsar.resources.Resource

data class District (
	var type: DistrictType = DistrictType.emptyDistrict,
	var amount: Int = 0
) : Constructable {
	
	override val time: Int
		get() = type.time
	override val cost: List<Resource>
		get() = type.cost
	override val name: String
		get() = "${type.id} District"
	
}