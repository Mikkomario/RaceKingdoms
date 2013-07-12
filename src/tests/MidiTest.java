package tests;

import listeners.KeyListener;
import music.MidiMusicPlayer;
import handlers.ActorHandler;
import handlers.DrawableHandler;
import processing.core.PApplet;
import processing.core.PConstants;

public class MidiTest extends AbstractTest implements KeyListener{
	
	// ATTRIBUTES	------------------------------------------------------
	
	private TestMidiMusicBank testBank;
	private MidiMusicPlayer midiPlayer;
	private boolean paused;
	private boolean isActive;
	private boolean isAlive;
	
	// CONSTRUCTOR	------------------------------------------------------

	/**
	 * Creates a new MidiTest with the required information
	 * 
	 * @param actorhandler The handler that handles created actors
	 * @param drawer The drawer that draws created drawables
	 * @param keylistenerhandler The KeyListenerHandler that informs created listeners
	 * @param mouselistenerhandler The MouseListenerHandler that informs created listeners
	 * @param applet The main applet
	 */
	public MidiTest(ActorHandler actorhandler, DrawableHandler drawer,
			handlers.KeyListenerHandler keylistenerhandler,
			handlers.MouseListenerHandler mouselistenerhandler, PApplet applet) {
		super(actorhandler, drawer, keylistenerhandler, mouselistenerhandler, applet);
		
		//Let's start setting up our test
		this.testBank = new TestMidiMusicBank();
		this.midiPlayer = new MidiMusicPlayer();
		this.paused = false;
		this.isActive = false;
		this.isAlive = true;
		
	}

	// IMPLEMENTED METHOD	---------------------------------------------
	@Override
	public void test() {
		//Let's start the test
		this.activate();
		
	}

	@Override
	public boolean isActive() {
		return this.isActive;
	}

	@Override
	public boolean activate() {
		this.isActive = true;
		return true;
	}

	@Override
	public boolean inactivate() {
		this.isActive = false;
		return true;
	}

	@Override
	public boolean isDead() {
		return !this.isAlive;
	}

	@Override
	public boolean kill() {
		this.isAlive = false;
		return true;
	}

	@Override
	public void onKeyDown(int key, int keyCode, boolean coded) {
		// Not needed
		
	}

	@Override
	public void onKeyPressed(int key, int keyCode, boolean coded) {
		if(!coded){
			if(keyCode == PConstants.ENTER){
				//Starts playing the midi
				this.midiPlayer.playMidiMusic(this.testBank.getMidi("test"), 0);
			}
			else if (key == 'p'){
				//Pauses and continues playing music.
				if(this.paused){
					this.midiPlayer.continueMidiMusic();
					this.paused = false;
				}else{
					this.midiPlayer.pauseMidiMusic();
					this.paused = true;
				}
			}
			else if (key == 's'){
				//Stops playing the music
				this.midiPlayer.stopMidiMusic();
			}
		}
		
	}

	@Override
	public void onKeyReleased(int key, int keyCode, boolean coded) {
		// Not needed
		
	}

}
