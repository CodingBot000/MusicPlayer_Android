<h1 align="center">My Music Player </h1>

<p align="center">  
 This ToyProject make modern Android development with Compose, Dagger-Hilt, Coroutines, Jetpack based on MVVM architecture.
</br>

[![Video Label](http://img.youtube.com/vi/36fbyUBBeXE/0.jpg)](https://youtu.be/36fbyUBBeXE)
- Click to view as a 'YouTube' video.

## Tech stack
- [Kotlin] based, [Coroutines] + [Flow]  for asynchronous.
- Compose
- ExoPlayer
- [Hilt] for dependency injection.
- Jetpack
- MVVMArchitecture

## Online, offline
- When `Constants.ONLINE` is set to `false`, changing it to `true` will fetch files from the server.
- If it cannot be used due to server restrictions, an `AlertDialog` is displayed, and local data from the assets is read and used.
- Currently, the default is set to read and use local data from the assets.
- Looping and repeating the same data due to insufficient sample data.


## Features

<ul>
 <li> Music sample data from my private server 
 <li> Background playback using a foreground service
 <li> Controls the playback state with actions
<ul>

 ## Project Structure
![Alt text](https://github.com/CodingBot000/MusicPlayer_Android/blob/main/MusicPlayerSample.drawio.png)



