
package com.example.niyati.demoxmljson.Entity;
import java.util.List;

public class RootBean {

    private String name;
    private List<Fans> fans;
    public void setName(String name) {
         this.name = name;
     }
     public String getName() {
         return name;
     }

    public void setFans(List<Fans> fans) {
         this.fans = fans;
     }
     public List<Fans> getFans() {
         return fans;
     }

}