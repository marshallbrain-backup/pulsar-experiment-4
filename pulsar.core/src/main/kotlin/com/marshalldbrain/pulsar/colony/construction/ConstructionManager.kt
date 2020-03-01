package com.marshalldbrain.pulsar.colony.construction

import com.marshalldbrain.ion.collections.queueOf
import com.marshalldbrain.pulsar.resources.ResourceBucket
import com.marshalldbrain.pulsar.resources.ResourceTeller

class ConstructionManager(private val teller: ResourceTeller) {
	
	private val constructionQueue = queueOf<ConstructionTask>()
	private var currentTask: ConstructionTask? = null
	
	var currentTask: ConstructionTask? = null
	
	fun addToQueue(task: ConstructionTask) {
		
		teller.withdrawAll(task.target.cost)
		
		if (task.target.time == 0) {
			task.passTime(0)
		}
		
		if (task.amountRemaining > 0) {
			if (currentTask == null) {
				currentTask = task
			} else {
				constructionQueue.add(task)
			}
		}
		
	}
	
	fun tick(amount: Int) {
		
		var remaining = amount
		
		while (remaining > 0 && currentTask != null) {
				
			remaining = currentTask!!.passTime(remaining)
			if (currentTask!!.amountRemaining <= 0) {
				currentTask = constructionQueue.poll()
			}
			
		}
		
	}
	
}