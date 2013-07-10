package music;

import java.io.File;
import java.io.IOException;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;

/**Musical objects which can be played.
 * 
 * @author Unto
 *		   Created 10.7.2013
 *
 */
public class MidiMusic {
	
	private String midiName;
	private Sequence midiSequence;
	private Sequencer midiSequencer;
	
	
	public MidiMusic(String midiName){
		this.midiName = midiName;
		//Let's try to create our midiSequence
		try{
			this.midiSequence = MidiSystem.getSequence(new File("midis/"+this.midiName));
		}catch (InvalidMidiDataException e){
			System.err.println("Couldn't find create a midisequence!");
			e.printStackTrace();
		}catch (IOException e){
			System.err.println("IOException whilst creating midisequence!");
			e.printStackTrace();
		}
		//Now let's try and set-up our midiSequencer
		try{
			this.midiSequencer = MidiSystem.getSequencer();
			this.midiSequencer.setSequence(this.midiSequence);
		}catch (MidiUnavailableException e){
			System.err.println("Problems whilst setting up sequencer!");
			e.printStackTrace();
		}catch (InvalidMidiDataException e){
			System.err.println("Midi was invalid!");
			e.printStackTrace();
		}
		
	}
	
	/**
	 * @return	Returns the length of a Midi-sequence in ticks.
	 */
	public long getSequenceLength(){
		return this.midiSequence.getTickLength();
	}
	

}
