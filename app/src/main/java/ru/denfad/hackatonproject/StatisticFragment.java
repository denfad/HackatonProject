package ru.denfad.hackatonproject;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.LegendRenderer;
import com.jjoe64.graphview.helper.GraphViewXML;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.HashMap;
import java.util.Map;

import ru.denfad.hackatonproject.db.DbService;

public class StatisticFragment extends Fragment {

    private DbService dbService;
    public StatisticFragment(Context context){
        dbService = new DbService(context);
    }

    public static StatisticFragment newInstance(Context context){
        return new StatisticFragment(context);
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.statistic_fragment,container,false);

        GraphView graph = (GraphView) rootView.findViewById(R.id.graph);
        graph.setTitle("Колличество целей по типам");
        graph.setTitleTextSize(50.0f);
        graph.getLegendRenderer().setVisible(true);
        graph.getLegendRenderer().setAlign(LegendRenderer.LegendAlign.TOP);

        graph.getViewport().setXAxisBoundsManual(true);
        graph.getViewport().setMinX(0);
        graph.getViewport().setMaxX(getResources().getStringArray(R.array.types).length+1);

// set manual Y bounds
        graph.getViewport().setYAxisBoundsManual(true);
        graph.getViewport().setMinY(0);
        graph.getViewport().setMaxY(dbService.getMaxCountAll(getResources().getStringArray(R.array.types))*1.5);

        addNewSeriesByTypeCount(graph,getResources().getStringArray(R.array.types));
        return rootView;
    }

    private void addNewSeriesByTypeCount(GraphView graphView, String[] arr){
        for(int i=1; i<=arr.length;i++){
            String type = arr[i-1];
            Log.e("count",dbService.getCountOfTasksByType(type)+" ");
            BarGraphSeries<DataPoint> series = new BarGraphSeries<>(new DataPoint[]{new DataPoint(i,dbService.getCountOfTasksByType(type))});
            series.setTitle(type);
            series.setColor(getTypeColor(type));
            graphView.addSeries(series);

            series.setSpacing(10);

// draw values on top
            series.setDrawValuesOnTop(true);
            series.setValuesOnTopColor(Color.RED);
        }
    }

    private int getTypeColor(String type){
        Map<String, Integer> colors = new HashMap<>();
        colors.put("Спорт",Color.GREEN);
        colors.put("Самообразование",Color.YELLOW);
        if (colors.get(type)==null) return R.color.nothing_color;
        else return colors.get(type);
    }
}
