package com.example.timefighter.findworkoutactivity2;

import java.util.ArrayList;
import java.util.List;

public class WorkoutExpert {
    List<String> getWorkouts(String workouttypes)
    {
        List <String> workout=new ArrayList<String>();
        if(workouttypes.equals("Chest"))
        {
            workout.add("Benchpress");
            workout.add("cable flys");
        }
        else if(workouttypes.equals("Triceps"))
        {
            workout.add("tricep ext");
            workout.add("tricep pushdown");


        }
        else if(workouttypes.equals("Shoulders"))
        {
            workout.add("shoulder press");
          }
        else if(workouttypes.equals("Biceps"))
        {
            workout.add("curls");
        }
        return workout;
    }


}
