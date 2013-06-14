package graphic;


import java.io.FileNotFoundException;
import java.util.HashMap;

import processing.core.PApplet;

/**
 * This class creates a group of sprites used in a project and gives them to the 
 * objects using them as needed.
 *
 * @author Gandalf.
 *         Created 7.12.2012.
 */
public abstract class SpriteBank
{
    // ATTRIBUTES	---------------------------------------------------------

    private HashMap<String, Sprite> sprites;
    private PApplet applet;


    // CONSTRUCTOR	---------------------------------------------------------

    /**
     * Creates a new spritebank and loads all the sprites needed in it
     * 
     * @param applet The applet with which the images are loaded
     */
    public SpriteBank(PApplet applet)
    {
        this.applet = applet;
        this.sprites = new HashMap<String, Sprite>();
        initializeSprites();
    }
    
    
    // ABSTRACT METHODS	-----------------------------------------------------
    
    /**
     * Here, sprites are created using the createsprite method
     * @throws FileNotFoundException If all of the sprites could not be loaded
     */
    public abstract void createSprites() throws FileNotFoundException;


    // OTHER METHODS	-----------------------------------------------------

    private void initializeSprites()
    {
        try
        {
            createSprites();
        }
        catch(FileNotFoundException fnfe)
        {
            System.err.println("All of the sprites could not be loaded!");
        }
    }

    /**
     * Returns a sprite from the spritebank
     *
     * @param spriteName The name of the sprite looked
     * @return The sprite with the given name
     */
    public Sprite getSprite(String spriteName)
    {
        if (this.sprites.containsKey(spriteName))
            return this.sprites.get(spriteName);
        else
        {
        	System.out.println("Sprite " + spriteName + "does not exist in the bank");
            return null;
        }
    }
    
    /**
     * Loads and creates a sprite and adds it to the bank
     *
     * @param filename The filename of the sprite
     * @param imgnumber How many images does the sprite contain
     * @param originx What is the position of the sprite's origin on the x-axis (Pxl)
     * @param originy What is the position of the sprite's origin on the y-axis (Pxl)
     * @param name What is the name of the new sprite
     * @return The sprite that was created
     * @throws FileNotFoundException If the image file could not be loaded
     */
    protected Sprite createSprite(String filename, int imgnumber, int originx, 
    		int originy, String name) throws FileNotFoundException
    {
    	Sprite newsprite = new Sprite(filename, imgnumber, originx, originy, 
                name, this.applet);
        this.sprites.put(newsprite.getName(), newsprite);
        
        return newsprite;
    }
}
