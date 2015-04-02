package com.exercise.together.fragment;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.BarChart.Type;
import org.achartengine.model.CategorySeries;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.renderer.SimpleSeriesRenderer;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint.Align;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.exercise.together.R;
import com.exercise.together.util.Callback;

public class SportsMainFragment extends Fragment {
	
	Context mContext;
	
	Callback mCallback;
	
	public final static String TAG = "BadmintonFragment";
	
	ProgressDialog mProDiag = null;
	
	public SportsMainFragment(){}
	
	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		mContext = activity;
		mCallback = (Callback)activity;
	}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.fragment_sports_main, container, false);
         
        return rootView;
    }

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		
		Log.v(TAG, "onActivityCreated");
		
		Button btn = (Button)getActivity().findViewById(R.id.info_btn2);
		btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				mCallback.onBtnClick(SportsMainFragment.this);	
			}
		});
		
		Bundle bd = getArguments();
		int number = bd.getInt("number");
		TextView tvTitle = (TextView)getActivity().findViewById(R.id.info_title_tv);
		tvTitle.setText("배드민턴 친구 정보");
		TextView tv1 = (TextView)getActivity().findViewById(R.id.info_body_tv1);
		tv1.setText("총 인원 : 10명");
		TextView tv2 = (TextView)getActivity().findViewById(R.id.info_body_tv2);
		tv2.setText("장소별 인원 : 10명");
		TextView tv3 = (TextView)getActivity().findViewById(R.id.info_body_tv2_1);
		tv3.setText("서울  10명\n 경기 20명\n 충남 10명");
		Button btn2 = (Button)getActivity().findViewById(R.id.info_btn2);
		btn2.setText("친구보러 가기");
		
		//그래프
		List<double[]> values = new ArrayList<double[]>();
		values.add(new double[] {number, 20, 7, 4, 15});
		XYMultipleSeriesRenderer renderer = new XYMultipleSeriesRenderer();
		
		renderer.setChartTitle("인원현황");
		renderer.setChartTitleTextSize(30);
		
		String[] titles = new String[]{"서울"};
		int[] colors = new int[]{Color.RED};
		
		//renderer.setLegendTextSize(22);
		int length = colors.length;
		for(int i=0; i<length; i++){
			SimpleSeriesRenderer r = new SimpleSeriesRenderer();
			r.setColor(colors[i]);
			renderer.addSeriesRenderer(r);
		}
		
		//axis 타이틀 == label
		renderer.setXTitle("지역");
		renderer.setYTitle("인원수");
		renderer.setAxisTitleTextSize(22);
		renderer.setLabelsColor(Color.BLACK);
		renderer.setLabelsTextSize(20);
		//axis 범위
		renderer.setXAxisMin(0.5);
		renderer.setXAxisMax(5.5);
		renderer.setYAxisMin(0);
		renderer.setYAxisMax(30);
		renderer.setBackgroundColor(Color.WHITE);
		renderer.addXTextLabel(1, "서울");
		renderer.addXTextLabel(2, "강서");
		renderer.addXTextLabel(3, "강남");
		renderer.addXTextLabel(4, "강북");
		renderer.addXTextLabel(5, "강동");
		renderer.getSeriesRendererAt(0).setDisplayChartValues(true);
		renderer.getSeriesRendererAt(0).setChartValuesTextAlign(Align.RIGHT);
		renderer.getSeriesRendererAt(0).setChartValuesTextSize(30);
		//renderer.getSeriesRendererAt(0).setGradientEnabled(true);	
		//renderer.getSeriesRendererAt(0).setGradientStart(0, Color.parseColor("#e71d73"));	
		//renderer.getSeriesRendererAt(0).setGradientStop(10, Color.parseColor("#ffffff"));
		
		renderer.setAxesColor(Color.DKGRAY);
		renderer.setXLabels(5);
		renderer.setYLabels(5);
		
		renderer.setXLabelsAlign(Align.LEFT);
		renderer.setYLabelsAlign(Align.LEFT);
		
		renderer.setPanEnabled(false, false);
		renderer.setZoomEnabled(false, false);
		
		renderer.setMarginsColor(Color.parseColor("#FFFFFF"));	
		renderer.setShowLegend(false);	
		
		renderer.setZoomRate(1.0f);
		renderer.setBarSpacing(0.5f);
		renderer.setMargins(new int[] {50, 50, 50, 50});
		renderer.setXLabels(0);
		renderer.setXLabelsColor(Color.BLUE);
		renderer.setYLabelsColor(0, Color.DKGRAY);
		renderer.setXLabelsPadding(10);
		
		
		XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
		for(int i=0; i<titles.length; i++){
			CategorySeries series = new CategorySeries(titles[i]);
			double[] v = values.get(i);
			int seriesLength = v.length;
			for(int k=0; k<seriesLength; k++){
				series.add(v[k]);
			}
			dataset.addSeries(series.toXYSeries());
		}
		
		GraphicalView gv = ChartFactory.getBarChartView(mContext, dataset, renderer, Type.STACKED);
		//gv.setBackgroundColor(Color.BLACK);
		LinearLayout llChart = (LinearLayout)getActivity().findViewById(R.id.info_chart);
		llChart.addView(gv);
	}


	
	
}
