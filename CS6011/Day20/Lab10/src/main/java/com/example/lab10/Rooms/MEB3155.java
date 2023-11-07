//made by Samantha Pope on 10.30.2023

package com.example.lab10.Rooms;
import com.example.lab10.Game.Adventure;
import com.example.lab10.Items.Item;
public class MEB3155 extends Room{

    private boolean codeisbugged_ = true;

    public MEB3155() {
        super("MEB3155", "A place where MSD students rot" );
        //adding in the sword item which is usable in phillips room
        Item sword = new Item( "Sword", "A golden sword.");
        items_.add( sword );
        Item plaque = new Item("plaque", "tells you about room");
        items_.add(plaque);
    }

    @Override
    public Room goThroughDoor(int doorNum) {

        if(codeisbugged_) {
            System.out.println( "You can't leave, your code has bugs!" );
            return null;
        }
        else {
            return super.goThroughDoor( doorNum );
        }
    }

    @Override
    public void playerEntered() {
        if(codeisbugged_) {
            System.out.println( "You hear the sounds of typing students..." );
        }
    }

    @Override
    public boolean handleCommand( String[] subcommands ) {

        if( subcommands.length <= 1 ) {
            return false;
        }
        String cmd  = subcommands[0];
        String attr = subcommands[1];


        if( cmd.equals( "read" ) && attr.equals( "plaque") ){
            boolean hasPlaque = false;
            for( Item item : Adventure.inventory ) {
                if( item.getName().equals( "plaque" )) {
                    hasPlaque = true;
                    break;
                }
            }
            if( hasPlaque ) {
                System.out.println( "This is the classroom of the MSD students. You will find them here until the wee hours of the night sobbing over their broken code.");
            }

        }



        // unlock, use
        if( cmd.equals( "unlock" ) && attr.equals( "door") ) {

            boolean hasDebugger = false;
            for( Item item : Adventure.inventory ) {
                if( item.getName().equals( "debugger" ) || item.getName().equals("TA")) {
                    hasDebugger = true;
                    break;
                }
            }
            if( hasDebugger ) {
                System.out.println( "You unlock the door.");
                codeisbugged_ = false;
            }
            else {
                System.out.println( "Your code still has bugs. You cannot leave." );
            }
            return true;
        }
        return false;
    }
}





