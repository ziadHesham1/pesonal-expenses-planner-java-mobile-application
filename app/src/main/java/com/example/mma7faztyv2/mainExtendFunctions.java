package com.example.mma7faztyv2;

import java.util.ArrayList;

public class mainExtendFunctions {

    public static int budgetcount(ArrayList<budgetData> therecyclerDataArray)
    {
        int c=0;
        for (budgetData i:therecyclerDataArray)
            if (i.getTitle().contains("new budget")) c++;
        return c;
    }

}
