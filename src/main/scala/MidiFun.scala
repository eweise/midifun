package eweise.midifun

import javax.sound.midi._
import java.util._
import javax.sound.midi._
import java.util._
// Java program showing how to change the instrument type


object MyMidiPlayer1 {
  def main(args: Array[String]): Unit = {
    val player = new MyMidiPlayer1
//    val in = new Scanner(System.in)
//    System.out.println("Enter the instrument to be played")
//    val instrument = in.nextInt
//    System.out.println("Enter the note to be played")
//    val note = in.nextInt
    player.setUpPlayer(103, 50)
  }
}

class MyMidiPlayer1 {
  def setUpPlayer(instrument: Int, note: Int): Unit = {
    try {
      val m4Info = MidiUtil.getM4DeviceInfo
      val device = MidiSystem.getMidiDevice(m4Info)
      device.open()
      val sequencer = MidiSystem.getSequencer
      sequencer.open()
      val receiver = device.getReceiver
      val transmitters = sequencer.getTransmitters
      transmitters.forEach(_.setReceiver(receiver))
      val sequence = new Sequence(Sequence.PPQ, 4)
      val track = sequence.createTrack
      for( x <- 1 to 127) {
        track.add(makeEvent(192, 1, x, 0, x*2))
        // Add a note on event with specified note
        track.add(makeEvent(144, 1, x, 100, x*2))
        // Add a note off event with specified note
        track.add(makeEvent(128, 1, x, 100, (x*2) + 4))
      }
      sequencer.setSequence(sequence)
      sequencer.start()
      while ( {
        true
      }) if (!sequencer.isRunning) {
        sequencer.close()
        System.exit(1)
      }
    } catch {
      case ex: Exception =>
        ex.printStackTrace()
    }
  }

  def makeEvent(command: Int, channel: Int, note: Int, velocity: Int, tick: Int): MidiEvent = {
    var event:MidiEvent = null
    try {
      val a = new ShortMessage
      a.setMessage(command, channel, note, velocity)
      event = new MidiEvent(a, tick)
    } catch {
      case ex: Exception =>
        ex.printStackTrace()
    }
    event
  }

}

