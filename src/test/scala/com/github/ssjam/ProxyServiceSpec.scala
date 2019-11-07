package com.github.ssjam

import scala.io.Source

class ProxyServiceSpec extends UnitSpec {

  "A Proxy Service" when {

    "initializing" should {
      "accept a port number, p to listen for connections" in {
        val a = Array("-p", "12345")
        val ps = new ProxyService(12345)
        ps.status() must equal(12345)
      }
      "set the port number to a default value" in {
        val ps = new ProxyService()
        ps.status() must equal(5978)
      }
    }

    "running" should {
      "accept open connections" in {
        val ps = new ProxyService(9966);
        val res = Source.fromURL("http://localhost:9966/hello")
        res.mkString must equal("<h1>Say hello to akka-http</h1>")
      }
      "accept TLS connections" in {
        fail("not yet implemented")
      }
    }
  }
}
