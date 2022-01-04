package eweise.midifun

import javax.sound.midi.{MidiSystem, MidiUnavailableException, ShortMessage}

object ChordGenerator {
  def main(args: Array[String]): Unit = {
    MidiSystem.getMidiDeviceInfo.foreach { deviceInfo =>
      val device = MidiSystem.getMidiDevice(deviceInfo)

      val realReceiver = MidiSystem.getReceiver

      val loggingReceiver = ChordReceiver(realReceiver)

      device.getTransmitters.forEach { transmitter =>
        transmitter.setReceiver(loggingReceiver)
      }

      try
        device.getTransmitter.setReceiver(loggingReceiver)
      catch
        case ex: MidiUnavailableException => println("midi unavailable for " + deviceInfo)

      device.open()
    }
  }
}
