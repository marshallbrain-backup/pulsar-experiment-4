package com.marshalldbrain.pulsar.colony.construction

import com.marshalldbrain.ion.sequences.randomIntRange
import com.marshalldbrain.pulsar.resources.Resource
import com.marshalldbrain.pulsar.resources.ResourceType
import io.kotlintest.matchers.collections.shouldBeEmpty
import io.kotlintest.properties.Gen
import io.kotlintest.properties.assertAll
import io.kotlintest.properties.forAll
import io.kotlintest.shouldBe
import io.kotlintest.specs.FunSpec
import io.mockk.mockk
import kotlin.random.Random

class ConstructionManagerTest : FunSpec({
	
	context("Process time") {
		
		test("Equal time passed") {
		
			val cm = ConstructionManager()
			val task = TaskGen().random().first()
			
			cm.addToQueue(task)
			cm.tick(task.timeRemaining)
			
			cm.constructionQueue.shouldBeEmpty()
			
		}
		
		test("Time rolled over") {
			
			assertAll(RangedInt(min = 1, max = 10)) { a: Int ->
				
				val cm = ConstructionManager()
				val tasks = TaskGen().random().take(a).toList()
				
				val totalTime = tasks.sumBy { it.timeRemaining }
				val time = Random.nextInt(0, totalTime+1)
				
				tasks.forEach { cm.addToQueue(it) }
				cm.tick(time)
				
				val actual = cm.constructionQueue.sumBy { it.timeRemaining } + time
				actual.shouldBe(totalTime)
				
			}
			
		}
		
	}
	
})

private class RangedInt(val min: Int = 0, val max: Int) : Gen<Int> {
	override fun constants(): Iterable<Int> {
		return emptyList()
	}
	
	override fun random(): Sequence<Int> {
		return randomIntRange(min, max)
	}
	
}

private class TaskGen() : Gen<ConstructionTask> {

	override fun constants(): Iterable<ConstructionTask> {
		return emptyList()
	}

	override fun random(): Sequence<ConstructionTask> {
		return generateSequence {
			ConstructionTask(
				Random.Default.nextInt(1, 13),
				Random.Default.nextInt(1, 6),
				listOf()
			)
		}
	}

}