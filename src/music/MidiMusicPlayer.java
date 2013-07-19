package music;

import javax.sound.midi.MidiUnavailableException;

/**
 * Plays a MidiMusic and has tools to loop, pause and continue the music.
 * 
 * @author Unto Created 10.7.2013
 * 
 */
public class MidiMusicPlayer {

	// ATTRIBUTES ---------------------------------------------------------
	private MidiMusic currentMidi;
	private long currentPosition;

	// CONSTRUCTOR ---------------------------------------------------------
	/**
	 * Creates new MidiMusicPlayer.
	 */
	public MidiMusicPlayer() {
		this.currentMidi = null;
		this.currentPosition = 0;
	}

	// METHODS ---------------------------------------------------
	/**
	 * Starts playing the given Midi from the start.
	 * 
	 * @param newMidi	Given midiMusic-object.
	 * @param loopCount	How many times the song loops. If loopCount is <0, the
	 * loop will be continuous.
	 */
	public void playMidiMusic(MidiMusic newMidi, int loopCount) {
		if (this.currentMidi == null) {
			this.currentMidi = newMidi;
		} else {
			this.currentMidi.stopMusic();
			this.currentMidi = newMidi;
		}
		this.currentMidi.setLoopCount(loopCount);
		try {
			this.currentMidi.startMusic(0);
		} catch (MidiUnavailableException e) {
			System.err.println("Midi was unavailable!");
			e.printStackTrace();
		}
	}

	/**
	 * Starts playing the given Midi from the start. It also sets the loops
	 * start and end.
	 * 
	 * @param newMidi	Given midiMusic-object.
	 * @param loopCount	How many times the song loops. If loopCount is <0, the
	 * loop will be continuous.
	 * @param loopStartPoint	 Where the loop starts.
	 * @param loopEndPoint	Where the loop ends.
	 */
	public void playMidiMusic(MidiMusic newMidi, int loopCount,
			long loopStartPoint, long loopEndPoint) {
		this.playMidiMusic(newMidi, loopCount);
		this.setLoopStartPoint(loopStartPoint);
		this.setLoopEndPoint(loopEndPoint);

	}

	/**
	 * Stops playing currentMidi if it exists.
	 */
	public void stopMidiMusic() {
		if (this.currentMidi != null) {
			// Stops and closes the sequencer
			this.currentMidi.stopMusic();
			// Removes the current midi
			this.currentMidi = null;
		} else {
			System.out.println("There isn't a midi to play!");
		}
	}

	/**
	 * Pauses music and records the tick-position, where music was paused.
	 * 
	 */
	public void pauseMidiMusic() {
		if (this.currentMidi != null) {
			this.currentPosition = this.currentMidi.pauseMusic();
		}
	}

	/**
	 * Starts playing the currentMidi, if it exists, from the last currentPoint
	 * made.
	 */
	public void continueMidiMusic() {
		if (this.currentMidi != null) {
			try {
				this.currentMidi.startMusic(currentPosition);
			} catch (MidiUnavailableException e) {
				System.err.println("Midi was unavailable!");
				e.printStackTrace();
			}
		}
	}

	/**
	 * Sets a starting point for looping the music.
	 * 
	 * @param loopStartPoint	Loop's starting tick-position.
	 */
	public void setLoopStartPoint(long loopStartPoint) {
		if (this.currentMidi != null) {
			this.currentMidi.setLoopStart(loopStartPoint);
		} else {
			System.out.println("There isn't a midi to play!");
		}
	}

	/**
	 * Sets an ending point for looping the music.
	 * 
	 * @param loopEndPoint	Loop's ending tick-position.
	 */
	public void setLoopEndPoint(long loopEndPoint) {
		if (this.currentMidi != null) {
			this.currentMidi.setLoopEnd(loopEndPoint);
		} else {
			System.out.println("There isn't a midi to play!");
		}
	}
	
	/**Swaps the current Midi to the new given Midi, and starts playing it
	 * from the current tick-position, if the position is within the new songs
	 * bounds.
	 * 
	 * @param newMidi	New MidiMusic-file
	 * @param loopCount	How many times the song loops. If loopCount is <0, the
	 * loop will be continuous.
	 */
	public void swapMidiMusic(MidiMusic newMidi, int loopCount){
		//Let's pause the current midi, so we can save our currentPosition
		this.pauseMidiMusic();
		this.stopMidiMusic();
		//Then we'll set up the new Midi-song
		this.currentMidi = newMidi;
		this.currentMidi.setLoopCount(loopCount);
		//Let's make sure our continuePosition is not longer than the new sequence
		long continuePosition = currentPosition%currentMidi.getSequenceLength();
		try {
			//And then we'll try to play
			this.currentMidi.startMusic(continuePosition);
		} catch (MidiUnavailableException e) {
			System.err.println("Midi was unavailable!");
			e.printStackTrace();
		}
	}
	
	
	/**Swaps the current Midi to the new given Midi, and starts playing it
	 * from the current tick-position, if the position is within the new songs
	 * bounds.
	 * 
	 * @param newMidi	New MidiMusic-file
	 * @param loopCount	How many times the song loops. If loopCount is <0, the
	 * loop will be continuous.
	 * @param loopStartPoint	 Where the loop starts.
	 * @param loopEndPoint	Where the loop ends.
	 */
	public void swapMidiMusic(MidiMusic newMidi, int loopCount,
			long loopStartPoint, long loopEndPoint){
		this.swapMidiMusic(newMidi, loopCount);
		this.setLoopStartPoint(loopStartPoint);
		this.setLoopEndPoint(loopEndPoint);
	}

}
