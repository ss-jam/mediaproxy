package com.github.ssjam

class ProxyServiceSpec extends UnitSpec {
  "A Proxy Service" when {

    "initializing" should {
      "accept a port number, p to listen for connections" in {
        val a = Array("-p", "12345")
        val ps = new ProxyServer(12345)
        ps.status() must equal(12345)
      }
      "set the port number to a default value" in {
        val ps2 = new ProxyServer()
        ps2.status() must equal(5978)
      }
    }

    "running" should {
      "accept open connections" in {
        fail("not yet implemented")
      }
      "accept TLS connections" in {
        fail("not yet implemented")
      }
    }
  }
}
