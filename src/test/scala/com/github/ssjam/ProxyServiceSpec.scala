package com.github.ssjam

import org.scalatest.AsyncWordSpec

import scala.concurrent.Future
import scala.io.Source

class ProxyServiceSpec extends UnitSpec {

  def proxySoon(port: Int): Future[Int] = Future { new ProxyService(port).test(); 0 }

  "A Proxy Service" when {

    "initializing" should {
      "accept a port number, p to listen for connections" in {
        val a = Array("-p", "12345")
        val ps = new ProxyService(12345)
        ps.status() must equal(12345)
        ps.stop()
        succeed
      }
      "set the port number to a default value" in {
        val ps = new ProxyService()
        ps.status() must equal(5978)
        ps.stop()
        succeed
      }
    }

    "running" should {
      "accept open connections" in {
        val ps: Future[Int] = proxySoon(9966)
        val res = Source.fromURL("http://localhost:9966?q=hello%20world!")
        res.mkString must equal("good by!")
        ps map(s => assert(s == 0))
        succeed
      }
      "accept TLS connections" in {
        fail("not yet implemented")
      }
    }
  }
}
