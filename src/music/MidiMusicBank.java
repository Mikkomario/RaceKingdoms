package music;

import java.util.HashMap;

/**Creates a HashMap containing all the 'MidiMusics' used in the project.
 * The class also allows access to these objects.
 * 
 * @author Unto
 *		   Created 10.7.2013
 *
 */
public class MidiMusicBank {
	
	// ATTRIBUTES	---------------------------------------------------------
	
	private HashMap<String, MidiMusic> midis;
	
	
	// CONSTRUCTOR	---------------------------------------------------------
	
	public MidiMusicBank(){
		
	}
	
	// METHODS	---------------------------------------------------
	/**Creates and puts a Midi to the 'midis' HashMap.
	 * 
	 * @param midiName	Name of the midi-file.
	 */
	protected void createMidiMusic(String midiName){
		MidiMusic newMidi = new MidiMusic(midiName);
		this.midis.put(midiName, newMidi);
	}
	
}
