package io.ricobot.theater.client.core.helper;

import io.ricobot.theater.client.app.model.Cinema;
import io.ricobot.theater.client.app.model.Genre;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Helper {

    public static Map<String, Object> mapper(Map<String, Object> params, List<String> param1)
    {
        ArrayList<Cinema> cinemas = new ArrayList<>();
        for (String cinema : param1){
            cinemas.add(new Cinema(Long.valueOf(cinema)));
        }
        params.put("cinemas", cinemas);
        params.put("genre", new Genre(Long.valueOf(params.get("genre").toString())));
        params.put("language", new Genre(Long.valueOf(params.get("language").toString())));

        String trailer = params.get("trailer").toString();
        if(trailer.length() > 0){
            if(trailer.indexOf("embed/") > 0){
                params.put("trailer", "https://www.youtube.com/embed/" + trailer.substring(trailer.indexOf("embed/") + 6));
            } else {
                params.put("trailer", "https://www.youtube.com/embed/" + trailer.substring(trailer.indexOf("?v=") + 3));
            }
        }
        return params;
    }
}