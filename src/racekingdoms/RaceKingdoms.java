package racekingdoms;


import handlers.ActorHandler;
import handlers.DrawableHandler;
import handlers.KeyListenerHandler;
import handlers.MainKeyListenerHandler;
import handlers.MainMouseListenerHandler;
import handlers.MouseListenerHandler;
import handlers.StepHandler;
import processing.core.PApplet;
import tests.GraphicTest;
import tests.InputTest;
import tests.MouseTest;
import tests.SpriteTest;

/**
 * This class starts the program and creates the necessary elements of the game. 
 * This class also informs listeners about keystrokes and the mouse
 *
 * @author Gandalf.
 *         Created 13.6.2013.
 */
public class RaceKingdoms extends PApplet
{
	// ATTRIBUTES	-----------------------------------------------------
	
	private static final long serialVersionUID = 1L;
	
	private MainKeyListenerHandler mainkeyhandler;
	private MainMouseListenerHandler mainmousehandler;
	private StepHandler stephandler;
	private DrawableHandler drawer;
	private KeyListenerHandler keylistenerhandler;
	private MouseListenerHandler mouselistenerhandler;
	private ActorHandler listeneractorhandler;
	
	private ActorHandler testactorhandler;
	private KeyListenerHandler testkeylistenerhandler;
	private MouseListenerHandler testmouselistenerhandler;
	
	private boolean needsUpdating; 
	
	
	// IMPLEMENTED METHODS	---------------------------------------------

	@Override
	public void setup()
	{
		// Sets the screen
		size(1000, 550);
		noFill();
		
		// Initializes the handlers
		//		(step -> mainmouse &  mainkey -> mouse & key -> testmouse & testkey)
		this.stephandler = new StepHandler(16, this);
		
		this.listeneractorhandler = new ActorHandler(false, this.stephandler);
		this.mainkeyhandler = new MainKeyListenerHandler(this.listeneractorhandler);
		this.mainmousehandler = new MainMouseListenerHandler(this.listeneractorhandler);
		
		this.drawer = new DrawableHandler(false, null);
		this.keylistenerhandler = new KeyListenerHandler(false, null);
		this.mouselistenerhandler = new MouseListenerHandler(false, 
				this.listeneractorhandler, null);
		
		this.testactorhandler = new ActorHandler(true, this.stephandler);
		this.testkeylistenerhandler = new KeyListenerHandler(false, this.keylistenerhandler);
		this.testmouselistenerhandler = new MouseListenerHandler(false, 
				this.testactorhandler, this.mouselistenerhandler);
		
		this.mainkeyhandler.addListener(this.testkeylistenerhandler);
		this.mainmousehandler.addMouseListener(this.testmouselistenerhandler);
		
		// Updates missing handling information
		this.mainkeyhandler.addListener(this.keylistenerhandler);
		this.mainmousehandler.addMouseListener(this.mouselistenerhandler);
		
		// Initializes other attributes
		this.needsUpdating = true;
		
		// Inactivates the testhandlers
		this.testactorhandler.inActivate();
		this.testkeylistenerhandler.inActivate();
		this.testmouselistenerhandler.inActivate();
		
		// Starts the game
		new Thread(this.stephandler).start();
		
		// Tests the system
		test();
	}

	@Override
	public void draw()
	{
		if (this.needsUpdating)
		{
			this.drawer.drawSelf(this);
			this.needsUpdating = false;
		}
	}
	
	@Override
	public void keyPressed()
	{
		this.mainkeyhandler.onKeyPressed(this.key, this.keyCode, this.key == CODED);
	}
	
	@Override
	public void keyReleased()
	{
		this.mainkeyhandler.onKeyReleased(this.key, this.keyCode, this.key == CODED);
	}
	
	@Override
	public void mousePressed()
	{
		this.mainmousehandler.setMouseStatus(this.mouseX, 
				this.mouseY, true, this.mouseButton);
	}
	
	@Override
	public void mouseReleased()
	{
		this.mainmousehandler.setMouseStatus(this.mouseX, 
				this.mouseY, false, this.mouseButton);
	}
	
	
	// OTHER METHODS	--------------------------------------------------
	
	/**
	 * This method should be called when the screen needs redrawing
	 */
	public void callScreenUpdate()
	{
		this.needsUpdating = true;
	}
	
	/**
	 * This method updates the mouse's position in the game
	 */
	public void callMousePositionUpdate()
	{
		this.mainmousehandler.setMousePosition(this.mouseX, this.mouseY);
	}
	
	private void test()
	{
		// Activates the handlers
		this.testactorhandler.activate();
		this.testkeylistenerhandler.activate();
		this.testmouselistenerhandler.activate();
		
		// Runs tests
		new InputTest(this.stephandler, this.drawer, 
				this.testkeylistenerhandler, this.testmouselistenerhandler, 
				this).test();
		new GraphicTest(this.stephandler, this.drawer, 
				this.testkeylistenerhandler, this.testmouselistenerhandler, 
				this).test();
		new SpriteTest(this.stephandler, this.drawer, 
				this.testkeylistenerhandler, this.testmouselistenerhandler, 
				this).test();
		new MouseTest(this.stephandler, this.drawer, 
				this.testkeylistenerhandler, this.testmouselistenerhandler, 
				this).test();
	}
}
