// MusicPlayListner.aidl
package com.example.andy.player.aidl;

// Declare any non-default types here with import statements

interface MusicPlayListner {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
     void action(in int actioncode,in Message message);
}
