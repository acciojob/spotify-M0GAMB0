package com.driver;

import java.util.*;

import org.springframework.stereotype.Repository;

@Repository
public class SpotifyRepository {
    public HashMap<Artist, List<Album>> artistAlbumMap;
    public HashMap<Album, List<Song>> albumSongMap;
    public HashMap<Playlist, List<Song>> playlistSongMap;
    public HashMap<Playlist, List<User>> playlistListenerMap;
    public HashMap<User, Playlist> creatorPlaylistMap;
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
                albumList = artistAlbumMap.get(artst);
                albumList.add(album);
                artistAlbumMap.put(artst, albumList);
                break;
            }
        }
        if (!artistExists) {
            albumList = new ArrayList<>();
            albumList.add(album);
            artistAlbumMap.put(artist, albumList);
        }
        System.out.println(artistAlbumMap);
        System.out.println(albums);
        return album;
    }

    public Song createSong(String title, String albumName, int length) throws Exception {
        boolean albumExists = false;
        Album album = null;
        for (Album alboom : albums) {
            if (alboom.getTitle().equals(albumName)) {
                albumExists = true;
                album = alboom;
                break;
            }
        }
        if (!albumExists) {
            throw new Exception("Album does not exist");
        }
        Song song = new Song(title, length);
        songs.add(song);
        albumExists = false;
        List<Song> songList;
        for (Album alboom : albumSongMap.keySet()) {
            if (alboom.getTitle().equals(albumName)) {
                albumExists = true;
                songList = albumSongMap.get(alboom);
                songList.add(song);
                albumSongMap.put(alboom, songList);
                break;
            }
        }
        if (!albumExists) {
            songList = new ArrayList<>();
            songList.add(song);
            albumSongMap.put(album, songList);
        }
        System.out.println(albumSongMap);
        return song;
    }

    public Playlist createPlaylistOnLength(String mobile, String title, int length) throws Exception {

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
