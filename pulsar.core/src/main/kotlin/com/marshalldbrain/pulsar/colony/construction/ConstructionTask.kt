package com.marshalldbrain.pulsar.colony.construction

import com.marshalldbrain.pulsar.colony.districts.District
import com.marshalldbrain.pulsar.resources.Resource
import kotlin.math.absoluteValue

class ConstructionTask(
	val type: Type,
	val target: Constructable,
	val amount: Int
) {
	
	//TODO move creation to the Constructable
	//TODO add onComplete value
	
	
	var timeRemaining: Int = amount * target.time
		private set
	val amountRemaining: Int
		get() = timeRemaining / target.time + 1
	
	val projectName: String
		get() = type.name(target)
	
	fun passTime(timePass: Int): Int {
		val remaining = timeRemaining - timePass
		if (remaining <= 0) {
			timeRemaining = 0
			return remaining.absoluteValue
		}
		timeRemaining = remaining
		return -1
	}
	
	enum class Type {
		
		BUILD {
			override fun name(target: Constructable): String {
				return "Building ${target.name}"
			}
		};
		
		abstract fun name(target: Constructable): String
		
	}
	
}