package com.driver;

import java.util.*;

import org.springframework.stereotype.Repository;

@Repository
public class SpotifyRepository {
    public HashMap<Artist, List<Album>> artistAlbumMap;//created
    public HashMap<Album, List<Song>> albumSongMap;//created
    public HashMap<Playlist, List<Song>> playlistSongMap;//created
    public HashMap<Playlist, List<User>> playlistListenerMap;//
    public HashMap<User, Playlist> creatorPlaylistMap;//created
    public HashMap<User, List<Playlist>> userPlaylistMap;
    public HashMap<Song, List<User>> songLikeMap;

    public List<User> users;
    public List<Song> songs;
    public List<Playlist> playlists;
    public List<Album> albums;
    public List<Artist> artists;

    public SpotifyRepository() {
        //To avoid hitting apis multiple times, initialize all the hashmaps here with some dummy data
        artistAlbumMap = new HashMap<>();
        albumSongMap = new HashMap<>();
        playlistSongMap = new HashMap<>();
        playlistListenerMap = new HashMap<>();
        creatorPlaylistMap = new HashMap<>();
        userPlaylistMap = new HashMap<>();
        songLikeMap = new HashMap<>();

        users = new ArrayList<>();
        songs = new ArrayList<>();
        playlists = new ArrayList<>();
        albums = new ArrayList<>();
        artists = new ArrayList<>();
    }

    public User createUser(String name, String mobile) {
        User u = new User(name, mobile);
        users.add(u);
        return u;
    }

    public Artist createArtist(String name) {
        Artist artist = new Artist(name);
        artists.add(artist);
        return artist;
    }

    public Album createAlbum(String title, String artistName) {
        boolean artistExists = false;
        for (Artist artist : artists) {
            if (artist.getName().equals(artistName)) {
                artistExists = true;
                break;
            }
        }
        Artist artist = new Artist(artistName);
        if (!artistExists) {
            artists.add(artist);
        }
        Album album = new Album(title);
        albums.add(album);
        artistExists = false;

        List<Album> albumList;
        for (Artist artst : artistAlbumMap.keySet()) {
            if (artst.getName().equals(artistName)) {
                artistExists = true;
                artistAlbumMap.get(artst).add(album);
                break;
            }
        }
        if (!artistExists) {
            albumList = new ArrayList<>();
            albumList.add(album);
            artistAlbumMap.put(artist, albumList);
        }
        return album;
    }

    public Song createSong(String title, String albumName, int length) throws Exception {
        Album album = null;
        for (Album alboom : albums) {
            if (alboom.getTitle().equals(albumName)) {
                album = alboom;
                break;
            }
        }
        if (album == null) {
            throw new Exception("Album does not exist");
        }
        Song song = new Song(title, length);
        songs.add(song);
        boolean albumExists = false;
        List<Song> songList;
        for (Album alboom : albumSongMap.keySet()) {
            if (alboom.getTitle().equals(albumName)) {
                albumExists = true;
                albumSongMap.get(alboom).add(song);
                break;
            }
        }
        if (!albumExists) {
            songList = new ArrayList<>();
            songList.add(song);
            albumSongMap.put(album, songList);
        }
        return song;
    }

    public Playlist createPlaylistOnLength(String mobile, String title, int length) throws Exception {
        User currUser = null;
        for (User user : users) {
            if (user.getMobile().equals(mobile)) {
                currUser = user;
                break;
            }
        }
        if (currUser == null) {
            throw new Exception("User does not exist");
        }
        Playlist playlist = new Playlist(title);
        playlists.add(playlist);
        List<Song> sameLengthSongs = new ArrayList<>();
        for (Song song : songs) {
            if (song.getLength() == length) {
                sameLengthSongs.add(song);
            }
        }
        playlistSongMap.put(playlist,sameLengthSongs);
        creatorPlaylistMap.put(currUser,playlist);
        List<User> userList=new ArrayList<>();
        userList.add(currUser);
        playlistListenerMap.put(playlist,userList);
        return playlist;
    }

//    public Playlist createPlaylistOnName(String mobile, String title, List<String> songTitles) throws Exception {
//
//    }
//
//    public Playlist findPlaylist(String mobile, String playlistTitle) throws Exception {
//
//    }
//
//    public Song likeSong(String mobile, String songTitle) throws Exception {
//
//    }
//
//    public String mostPopularArtist() {
//    }
//
//    public String mostPopularSong() {
//    }
}
