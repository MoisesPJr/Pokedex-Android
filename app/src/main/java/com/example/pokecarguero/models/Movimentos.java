package com.example.pokecarguero.models;

import java.io.Serializable;
import java.util.List;

public class Movimentos implements Serializable {

   private Move move;
   private List<VersionGroupDetails> version_group_details;


   public Move getMove() {
      return move;
   }

   public void setMove(Move move) {
      this.move = move;
   }

   public List<VersionGroupDetails> getVersion_group_details() {
      return version_group_details;
   }

   public void setVersion_group_details(List<VersionGroupDetails> version_group_details) {
      this.version_group_details = version_group_details;
   }

   public class Move {

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

