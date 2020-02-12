package com.marshalldbrain.pulsar.colony.districts

import com.marshalldbrain.pulsar.colony.construction.Constructable
import com.marshalldbrain.pulsar.resources.Resource
import com.marshalldbrain.pulsar.resources.ResourceCollection
import com.marshalldbrain.pulsar.resources.times

data class District (
	var type: DistrictType = DistrictType.emptyDistrict,
	var amount: Int = 0
) : Constructable, ResourceCollection {
	
	override val time: Int
		get() = type.time
	override val cost: Set<Resource>
		get() = type.cost
	override val id: String
		get() = "${type.id} District"
	override val income: Set<Resource>
		get() {
			return type.production + type.upkeep * -1
		}
	
}
