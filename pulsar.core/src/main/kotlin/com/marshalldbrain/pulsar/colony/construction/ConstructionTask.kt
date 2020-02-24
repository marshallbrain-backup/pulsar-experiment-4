package com.marshalldbrain.pulsar.colony.construction

import kotlin.math.absoluteValue

class ConstructionTask(
	val type: ConstructionType,
	val target: Constructable,
	val amount: Int,
	private val onComplete: () -> Unit = {}
) {
	
	//"Project"
	// "Amount\nRemaining"
	// " % of \n Capacity "
	// "Production\nRate"
	// "Time\nRemaining"
	// "Estimated Completion\nDate"
	
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
			onComplete.invoke()
			return remaining.absoluteValue
		}
		timeRemaining = remaining
		return -1
	}
	
}