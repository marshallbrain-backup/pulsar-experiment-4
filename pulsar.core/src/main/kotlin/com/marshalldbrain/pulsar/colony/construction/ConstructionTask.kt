package com.marshalldbrain.pulsar.colony.construction

import com.marshalldbrain.pulsar.colony.districts.District
import com.marshalldbrain.pulsar.resources.Resource
import kotlin.math.absoluteValue

class ConstructionTask(
	val time: Int,
	val amount: Int,
	val cost: List<Resource>,
	private val onStart: () -> Unit = {},
	private val onCancel: () -> Unit = {},
	private val onFinnish: () -> Unit = {},
	private val whileConstruction: () -> Unit = {}
) {
	
	//"Project"
	// "Amount\nRemaining"
	// " % of \n Capacity "
	// "Production\nRate"
	// "Time\nRemaining"
	// "Estimated Completion\nDate"
	
	constructor(district: District, amount: Int) : this(district.type.time, amount, district.type.cost)
	
	var timeRemaining: Int = amount * time
		private set
	val amountRemaining: Int
		get() = timeRemaining / time
	
	fun passTime(timePass: Int): Int {
		val remaining = timeRemaining - timePass
		if (remaining <= 0) {
			timeRemaining = 0
			return remaining.absoluteValue
		}
		timeRemaining = remaining
		return -1
	}
	
	fun onStart() {
		onStart.invoke()
	}
	
	fun onCancel() {
		onCancel.invoke()
	}
	
	fun onFinnish() {
		onFinnish.invoke()
	}
	
	fun whileConstructing() {
		whileConstruction.invoke()
	}

}