package tests;

import listeners.KeyListener;
import music.MidiMusicPlayer;
import handlers.ActorHandler;
import handlers.DrawableHandler;
import processing.core.PApplet;
import processing.core.PConstants;

/**Tests whether midis can be started, paused, continued and stopped
 * 
 * @author Unto
 *         Created 12.7.2013.
 */
public class MidiTest extends AbstractTest implements KeyListener{
	
	// ATTRIBUTES	------------------------------------------------------
	
	private TestMidiMusicBank testBank;
	private MidiMusicPlayer midiPlayer;
	private boolean paused;
	private boolean isActive;
	private boolean isDead;
	private String currentSong;
	
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
		keylistenerhandler.addKeyListener(this);
		this.testBank = new TestMidiMusicBank();
		this.midiPlayer = new MidiMusicPlayer();
		this.paused = false;
		this.isActive = false;
		this.isDead = false;
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
		return this.isDead;
	}

	@Override
	public boolean kill() {
		this.isDead = true;
		return true;
	}

	@Override
	public void onKeyDown(int key, int keyCode, boolean coded) {
		// Not needed
		
	}

	@Override
	public void onKeyPressed(int key, int keyCode, boolean coded) {
		if(!coded){
			if(key == PConstants.ENTER){
				//Starts playing the midi
				this.midiPlayer.playMidiMusic(this.testBank.getMidi("test"), 0);
				this.currentSong = "test";
				//When you start playing a new song, it isn't paused right from the get-go.
				this.paused = false;
				System.out.println("You pressed ENTER, so the music should start!");
			}
			else if (key == 'p'){
				//Pauses and continues playing music.
				if(this.paused){
					this.midiPlayer.continueMidiMusic();
					this.paused = false;
					System.out.println("You pressed p, so music should continue.");
				}else{
					this.midiPlayer.pauseMidiMusic();
					this.paused = true;
					System.out.println("You pressed p, so music should pause.");
				}
			}
			else if (key == 's'){
				//Stops playing the music
				this.midiPlayer.stopMidiMusic();
				System.out.println("You pressed s, so everything should stop.");
			}
			else if (key == 'c'){
				//Changes the music to a new Midi
				if(currentSong == "test"){
					this.midiPlayer.swapMidiMusic(this.testBank.getMidi("test2"),0);
					this.currentSong = "test2";
				}else if(currentSong == "test2"){
					this.midiPlayer.swapMidiMusic(this.testBank.getMidi("test"),0);
					this.currentSong = "test";
				}
				System.out.println("You pressed c, so music should change.");
			}
		}
		
	}

	@Override
	public void onKeyReleased(int key, int keyCode, boolean coded) {
		// Not needed
		
	}

}
