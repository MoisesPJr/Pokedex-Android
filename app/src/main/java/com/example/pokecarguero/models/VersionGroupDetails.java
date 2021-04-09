package com.example.pokecarguero.models;


import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class VersionGroupDetails implements Serializable {

    int level_learned_at;

    VersionGroup version_group;

    public int getLevel_learned_at() {
        return level_learned_at;
    }

    public void setLevel_learned_at(int level_learned_at) {
        this.level_learned_at = level_learned_at;
    }

    public VersionGroup getVersionGroup() {
        return version_group;
    }

    public void setVersionGroup(VersionGroup versionGroup) {
        this.version_group = versionGroup;
    }



    public class VersionGroup implements Serializable {

        private String name;
        private String url;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }

}
