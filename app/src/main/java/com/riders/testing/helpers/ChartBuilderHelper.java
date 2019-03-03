package com.riders.testing.helpers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class ChartBuilderHelper {


    public List createAreaSplineData() {
        return new ArrayList();
    }

    public List createStackBarData() {
        return new ArrayList();

    }

    public ArrayList<HashMap<String, Object>> createPieData(List<Object> elements) {

        ArrayList<HashMap<String, Object>> pieMapListData = new ArrayList<>();

        for (Object element : elements) {

            HashMap<String, Object> dataMap = new HashMap<>();

            dataMap.put("name", element);
            dataMap.put("y", new Random().nextInt(100));

            pieMapListData.add(dataMap);
        }

        return pieMapListData;

    }
}
