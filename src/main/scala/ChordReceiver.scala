package eweise.midifun

import javax.sound.midi.{MidiMessage, Receiver}
import javax.sound.midi.MidiSystem
import javax.sound.midi.Receiver
import javax.sound.midi.ShortMessage

class ChordReceiver(val delegate: Receiver) extends Receiver {

  def send(message: MidiMessage, timeStamp: Long): Unit = {
    delegate.send(message, timeStamp)

    message match
      case shortMsg: ShortMessage =>
        val command = shortMsg.getCommand

        val chordMsg = new ShortMessage
        val note = shortMsg.getData1
        val scale = Major()
        val chord = scale.chord(note, 60)
        val timestamp = -1
        command match
          case ShortMessage.NOTE_ON =>
            chordMsg.setMessage(ShortMessage.NOTE_ON, 0, chord(1), shortMsg.getData2)
            delegate.send(chordMsg, timestamp)
            chordMsg.setMessage(ShortMessage.NOTE_ON, 0, chord(2), shortMsg.getData2)
            delegate.send(chordMsg, timestamp)
          case ShortMessage.NOTE_OFF =>
            chordMsg.setMessage(ShortMessage.NOTE_OFF, 0, chord(1), shortMsg.getData2)
            delegate.send(chordMsg, timestamp)
            chordMsg.setMessage(ShortMessage.NOTE_OFF, 0, chord(2), shortMsg.getData2)
            delegate.send(chordMsg, timestamp)
  }

  override def close(): Unit = {}
}

