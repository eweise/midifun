package eweise.midifun

import javax.sound.midi.{MidiDevice, MidiSystem}

object MidiUtil {

  def getM4DeviceInfo: MidiDevice.Info =
    val deviceInfo = MidiSystem.getMidiDeviceInfo
//    for info <- deviceInfo if info.getName.contains("KeyStep") yield info
      deviceInfo(2)
}
