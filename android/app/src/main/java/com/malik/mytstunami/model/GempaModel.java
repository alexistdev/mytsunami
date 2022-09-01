package com.malik.mytstunami.model;

import com.google.gson.annotations.SerializedName;

public class GempaModel {
   @SerializedName("status")
   private final Boolean status;

   @SerializedName("message")
   private final String message;

   @SerializedName("lat")
   private final String latitude;

   @SerializedName("long")
   private final String longitude;

   @SerializedName("kedalaman")
   private final String kedalaman;

   @SerializedName("magnitudo")
   private final String magnitudo;

   public GempaModel(Boolean status, String message, String latitude, String longitude, String kedalaman, String magnitudo) {
      this.status = status;
      this.message = message;
      this.latitude = latitude;
      this.longitude = longitude;
      this.kedalaman = kedalaman;
      this.magnitudo = magnitudo;
   }

   public Boolean getStatus() {
      return status;
   }

   public String getMessage() {
      return message;
   }

   public String getLatitude() {
      return latitude;
   }

   public String getLongitude() {
      return longitude;
   }

   public String getKedalaman() {
      return kedalaman;
   }

   public String getMagnitudo() {
      return magnitudo;
   }
}
