// IMusicPlayer.aidl
package com.example.andy.player.aidl;
import com.example.andy.player.aidl.MusicPlayListner;
import com.example.andy.player.aidl.SongBean;
// Declare any non-default types here with import statements

interface IMusicPlayer {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
   void action(in int actioncode,in SongBean bean);
   void registListner(in MusicPlayListner listner);
   void unregistListner(in MusicPlayListner listner );
}
