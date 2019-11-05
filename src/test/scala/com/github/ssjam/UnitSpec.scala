package com.github.ssjam

import org.scalatest.{Inside, Inspectors, MustMatchers, OptionValues, WordSpec}

abstract class UnitSpec extends WordSpec with MustMatchers with
  OptionValues with Inside with Inspectors
