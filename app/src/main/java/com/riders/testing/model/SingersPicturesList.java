package com.riders.testing.model;

/**
 * Created by michael on 08/03/2016.
 */
public enum SingersPicturesList {

    KANYE_WEST("http://mybandnews.com/wp-content/uploads/2016/02/Kanye-West-album-Tidal.png"),
    JAY_Z("http://lesaviezvous.net/wp-content/uploads/2015/02/jay-z.jpg"),
    EMINEM("http://static.booska-p.com/images/artistes/eminem_hd.jpg"),
    KENDRICK_LAMAR("http://cdn3.pitchfork.com/news/59067/193edb5c.jpg"),
    DRAKE("http://www.clique.tv/wp-content/uploads/2016/01/drake-cover-650.jpg"),
    THE_GAME("http://generations.fr/media/news/_src/the-game-zimmerman-karen-civil.jpg"),
    DR_DRE("http://hiphop4life.fr/chroniques/wp-content/uploads/2015/08/Dr.-Dre-2015.jpg"),
    MISSY_ELLIOTT("http://www.wired.com/wp-content/uploads/2015/11/MissyElliott.jpg"),
    FIFTY_CENT("http://generations.fr/media/news/_src/50-cent-50-cent.jpg"),
    RIHANNA("http://www.journaldugeek.com/wp-content/blogs.dir/1/files/2015/05/Rihanna-Hairstyle.jpg"),
    BEYONCE("http://actu.last-video.com/wp-content/uploads/2014/04/beyonce-fait-virer-une-vendeuse-dune-boutique-de-luxe-a-new-york.jpg"),
    CHRIS_BROWN("http://groovevolt.com/wp-content/uploads/2015/12/brown-chris-4fee657fd8498.jpg"),
    LIL_WAYNE("http://img0.gtsstatic.com/wallpapers/098752586fc15415279451792e03eea8_large.jpeg"),
    FUTURE("http://brandnew-hiphop.com/wp-content/uploads/2015/07/Future-2.jpg"),
    DEJ_LOAF("http://static1.squarespace.com/static/55664553e4b0e48846329dc0/t/5699363269492ecf0c8287fa/1452881459778/");


    public final String singersPicUrl;

    SingersPicturesList(String url){
        this.singersPicUrl = url;
    }

    public String getUrl(){
        return singersPicUrl;
    }
}
