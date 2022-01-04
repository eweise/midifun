package eweise.midifun

import javax.sound.midi.{MidiMessage, Receiver}
import javax.sound.midi.MidiSystem
import javax.sound.midi.Receiver
import javax.sound.midi.ShortMessage

class LoggingReceiver(val delegate: Receiver) extends Receiver {

  def send(message: MidiMessage, timeStamp: Long): Unit = {
    println("received message " + message)

    delegate.send(message, timeStamp)

    message match
      case fsm: ShortMessage =>
        val command = fsm.getCommand

        val myMsg = new ShortMessage
        val note = fsm.getData1
        command match
          case ShortMessage.NOTE_ON =>
            myMsg.setMessage(ShortMessage.NOTE_ON, 0, note + 4, fsm.getData2)
            delegate.send(myMsg, -1)
            myMsg.setMessage(ShortMessage.NOTE_ON, 0, note + 7, fsm.getData2)
            delegate.send(myMsg, -1)
          case ShortMessage.NOTE_OFF =>
            myMsg.setMessage(ShortMessage.NOTE_OFF, 0, note + 4, fsm.getData2)
            delegate.send(myMsg, -1)
            myMsg.setMessage(ShortMessage.NOTE_OFF, 0, note + 7, fsm.getData2)
            delegate.send(myMsg, -1)
  }

  override def close(): Unit = {}
}

