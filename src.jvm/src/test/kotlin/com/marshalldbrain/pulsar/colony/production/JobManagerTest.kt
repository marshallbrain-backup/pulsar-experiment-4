package com.marshalldbrain.pulsar.colony.production

import com.marshalldbrain.pulsar.resources.ResourceIncome
import com.marshalldbrain.pulsar.resources.ResourceType
import io.kotlintest.matchers.maps.shouldContainExactly
import io.kotlintest.specs.FunSpec
import io.kotlintest.shouldBe
import io.mockk.mockk

class JobManagerTest : FunSpec({
	
	test("Update") {
		
		val manager = JobManager(mockk(relaxUnitFun = true))
		val jobs = mapOf(JobType("1") to 1)
		
		manager.update(jobs)
		
		manager.jobs.shouldContainExactly(jobs)
		
	}
	
	test("Update income") {
		
		val income = ResourceIncome()
		val manager = JobManager(income)
		val jobs = mapOf(
			JobType(
				"1",
				production = mapOf(ResourceType("1") to 4),
				upkeep = mapOf(ResourceType("1") to 1)
			) to 1)
		val expected = mapOf(ResourceType("1") to 3)
		
		manager.update(jobs)
		
		income.income.shouldContainExactly(expected)
		
	}
	
})