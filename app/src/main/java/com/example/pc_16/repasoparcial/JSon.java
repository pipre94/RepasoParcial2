package com.example.pc_16.repasoparcial;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pc-16 on 25/04/17.
 */

public class JSon {

    //clase para parsear el JSON

    public static List<Post> getData(String content) throws Exception{

        JSONArray myArray = new JSONArray(content);
        List<Post> myPost = new ArrayList<>();

        for (int i=0; i<myArray.length(); i++){
            JSONObject item = myArray.getJSONObject(i);

            Post post = new Post();
            post.setId(item.getInt("id"));
            post.setTitle(item.getString("title"));
            post.setBody(item.getString("body"));

            myPost.add(post);
        }

        return myPost;
    }

}
