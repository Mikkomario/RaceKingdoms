package music;

/**Plays a MidiMusic and has tools to loop, pause and continue the music.
 * 
 * @author Unto
 *		   Created 10.7.2013
 *
 */
public class MidiMusicPlayer {
	
	// ATTRIBUTES	---------------------------------------------------------
	
	
	
	// CONSTRUCTOR	---------------------------------------------------------
	
	public MidiMusicPlayer(){
		
	}
	
	// METHODS	---------------------------------------------------
	
	/**
	 * @return	Returns the current position the sequencer is in.
	 */
	public long getCurrentPosition(){
		//return this.midiSequencer.getTickPosition();
		return 0;
	}
	
}
