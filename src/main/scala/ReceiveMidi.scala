package eweise.midifun

import javax.sound.midi.{MidiSystem, MidiUnavailableException, ShortMessage}

object ReceiveMidi {
  def main(args: Array[String]): Unit = {
    MidiSystem.getMidiDeviceInfo.foreach { deviceInfo =>
      val device = MidiSystem.getMidiDevice(deviceInfo)
      device.open()

      val realReceiver = MidiSystem.getReceiver
      
      val loggingReceiver = LoggingReceiver(realReceiver)
      device.getTransmitters.forEach{transmitter =>
        transmitter.setReceiver(loggingReceiver)
      }
      try {
        device.getTransmitter.setReceiver(loggingReceiver)
      }
      catch
        case ex: MidiUnavailableException => println("midi unavailable for " + deviceInfo)

      device.open()
      println("opening device " + deviceInfo)
    }
    println("finished opening devices")
  }
}
