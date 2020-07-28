package com.example.timefighter.coffeebuzz;

public class Drinks {
    private String name;
    private String desc;
    private int imageID;
    public static final Drinks[] drinks={
      new Drinks("Latte","this is a description for latte",R.drawable.latte),
      new Drinks("Cappuccino","this is a description for cappuccino",R.drawable.othercoffee),
      new Drinks("Filter","this is a description for Filter",R.drawable.latte)

    };
    private Drinks(String name,String desc,int imageID)
        {
            this.name= name;
            this.desc=desc;
            this.imageID=imageID;
        }
        public String getDesc(){
        return desc;
        }
        public String getName()
        {
            return name;
        }
        public int getIamgeID(){
        return imageID;
        }
        public String toString(){
        return this.name; 
        }
}
