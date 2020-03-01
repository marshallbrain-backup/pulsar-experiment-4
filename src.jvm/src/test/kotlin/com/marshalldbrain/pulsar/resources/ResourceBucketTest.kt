package com.marshalldbrain.pulsar.resources

import io.kotlintest.DisplayName
import io.kotlintest.matchers.maps.shouldContainExactly
import io.kotlintest.specs.FunSpec
import io.kotlintest.shouldBe

class ResourceBucketTest : FunSpec({
	
	test("Process income") {
		
		val bucket = ResourceBucket()
		val income = ResourceIncome(bucket.incomeUpdater)
		income.update(mapOf(ResourceType("1") to 5))
		
		bucket.bucket.isEmpty()
		bucket.processIncome()
		bucket.bucket.shouldContainExactly(mapOf(ResourceType("1") to Pair(5, 5)))
		bucket.processIncome()
		bucket.bucket.shouldContainExactly(mapOf(ResourceType("1") to Pair(10, 5)))
		
	}
	
})