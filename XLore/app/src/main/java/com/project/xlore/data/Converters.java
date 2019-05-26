package com.project.xlore.data;

import java.util.ArrayList;

import androidx.room.TypeConverter;

public class Converters
{
    @TypeConverter
    public String convertFromStringArray(ArrayList<String> stringArray)
    {
        String stringConcatenated = "";
        for(String item : stringArray)
            stringConcatenated += (item + ",");
        return stringConcatenated;
    }

    @TypeConverter
    public ArrayList<String> convertToStringArray(String stringConcatenated)
    {
        ArrayList<String> stringArray = new ArrayList<>();
        for(String item : stringConcatenated.split(","))
            stringArray.add(item);
        return stringArray;
    }

    @TypeConverter
    public String convertFromIntegerArray(ArrayList<Integer> integerArray)
    {
        String stringConcatenated = "";
        for(int item : integerArray)
            stringConcatenated += (item + ",");
        return convertFromStringArray(convertToStringArray(stringConcatenated));
    }

    @TypeConverter
    public ArrayList<Integer> convertToIntegerArray(String stringConcatenated)
    {
        ArrayList<Integer> integerArray = new ArrayList<>();
        for(String item : convertToStringArray(stringConcatenated))
            integerArray.add(Integer.parseInt(item));
        return integerArray;
    }
}
