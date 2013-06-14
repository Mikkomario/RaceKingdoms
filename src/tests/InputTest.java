package tests;

import handlers.ActorHandler;
import handlers.DrawableHandler;
import handlers.KeyListenerHandler;
import handlers.MouseListenerHandler;

/**
 * This class is used to test input from the user.
 * 
 * @author Unto
 * 			14.6.2013
 */
public class InputTest extends AbstractTest implements listeners.KeyListener{

	/**
	 * Creates a new test with the required information
	 * 
	 * @param actorhandler The handler that handles created actors
	 * @param drawer The drawer that draws created drawables
	 * @param KeyListenerHandler The KeyListenerHandler that informs created listeners
	 * @param MouseListenerHandler The MouseListenerHandler that informs created listeners
	 * 
	 */
	public InputTest(ActorHandler actorhandler, DrawableHandler drawer,
			KeyListenerHandler KeyListenerHandler, MouseListenerHandler MouseListenerHandler) 
	{
		super(actorhandler, drawer, KeyListenerHandler, MouseListenerHandler);
		
	}

	@Override
	public void test() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isActive() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean activate() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean inActivate() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isDead() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean kill() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onKeyDown(int key, int keyCode, boolean coded) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onKeyPressed(int key, int keyCode, boolean coded) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onKeyReleased(int key, int keyCode, boolean coded) {
		// TODO Auto-generated method stub
		
	}
}
