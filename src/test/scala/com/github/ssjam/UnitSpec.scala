package com.github.ssjam

import org.scalatest.{WordSpec, Inside, Inspectors, MustMatchers, OptionValues}

abstract class UnitSpec extends WordSpec with MustMatchers with
  OptionValues with Inside with Inspectors
