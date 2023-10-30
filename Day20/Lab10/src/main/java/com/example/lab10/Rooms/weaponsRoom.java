
//this room was made by Phillip

package com.example.lab10.Rooms;
import com.example.lab10.Game.Adventure;
import com.example.lab10.Items.Item;

public class weaponsRoom extends Room {

    // Constructor
    public weaponsRoom() {
        super( "Weapons Room", "A weapons room full of swords." );
        //adding TA
        Item TA = new Item( "An MSD TA", "They are full of helpful info");
        items_.add( TA );
    }

    @Override
    public void playerEntered() {
        System.out.println( "YOU HERE RAPID FOOD STEPS APPROACHING FROM THE REAR!!!" );
        System.out.println( "LOOK AROUND QUICKLY!" );
    }

    public boolean handleCommand( String[] subcommands ) {

        if( subcommands.length <= 1 ) {
            return false;
        }
        String cmd  = subcommands[0];
        String attr = subcommands[1];

        // unlock, use
        if( cmd.equals( "use" ) && attr.equals( "Sword") ) {

            boolean hasSword = false;
            for( Item item : Adventure.inventory ) {
                if( item.getName().equals( "Sword" ) ) {
                    hasSword = true;
                    break;
                }
            }
            if( hasSword ) {
                System.out.println( "You quickly turn around, slaying the demon just before his claws enter your chest");
            }
            else {
                System.out.println( "You don't have a sword!! What are you doing!!" );
            }
            return true;
        }
        return false;
    }

}
