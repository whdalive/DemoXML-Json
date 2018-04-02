package com.example.niyati.demoxmljson.JSON;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import com.alibaba.fastjson.JSON;
import com.example.niyati.demoxmljson.Entity.Fans;
import com.example.niyati.demoxmljson.Entity.RootBean;
import com.example.niyati.demoxmljson.R;
import com.google.gson.Gson;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ParseJsonActivity extends AppCompatActivity implements View.OnClickListener{
    private TextView jsonText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_json);
        jsonText = findViewById(R.id.json_data);
        //四种解析方式
        findViewById(R.id.json).setOnClickListener(this);
        findViewById(R.id.Gson).setOnClickListener(this);
        findViewById(R.id.Jackson).setOnClickListener(this);
        findViewById(R.id.FastJSON).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.json:
                parse_json();
                break;
            case R.id.Gson:
                parse_gson();
                break;
            case R.id.Jackson:
                parse_jackson();
                break;
            case R.id.FastJSON:
                parse_fastjson();
                break;
        }
    }

    //将json数据转化为字符串
    private String get_json(String url){

        StringBuilder jsonData = null;
        try {
            //从assets获取json文件
            InputStream in = getAssets().open(url);
            //字节流转字符流
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            String line;
            jsonData = new StringBuilder();
            while ((line = reader.readLine())!= null){
                jsonData.append(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //返回字符串
        return String.valueOf(jsonData);
    }
    private void parse_json(){
        try {

            StringBuilder textShow = new StringBuilder("This is parsed by org.json" + "\n");
            //获取根节点对象
            JSONObject rootObject = new JSONObject(get_json("complex.json"));
            //获取根节点对象的属性name
            textShow.append("star name is : "+ rootObject.get("name") + "\n");
            //获取根节点对象的内部数组Fans[]
            JSONArray jsonArray =  rootObject.getJSONArray("fans");
            //遍历数组Fans[[]
            for (int i = 0; i < jsonArray.length(); i++){
                //获取数组Fans中的每一个对象fans
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                //获取fans的属性
                textShow.append("fans name is + " + jsonObject.getString("name") + "\n");
                textShow.append("fans age is + " + jsonObject.getString("age") + "\n");
            }
            jsonText.setText(textShow);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void parse_gson(){
        try {
            StringBuilder textShow = new StringBuilder("This is parsed by GSON" + "\n");
            //获取Gson对象
            Gson gson = new Gson();
            //将JSON数据转换为JavaBean类实体
            RootBean json = gson.fromJson(get_json("complex.json"),RootBean.class);
            //获取RootBean对象的熟悉
            textShow.append("star name is :"+json.getName()+"\n");
            //获取RootBean对象中的Fans[]数组
            for (Fans fans:json.getFans()) {
                textShow.append("fans name is :" + fans.getName() + "\n");
                textShow.append("fans age is :" + fans.getAge() + "\n");
            }
            jsonText.setText(textShow);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    private void parse_jackson(){
        try {
            StringBuilder textShow = new StringBuilder("this is parsed by jackson" + "\n");
            //获取ObjectMapper对象
            ObjectMapper mapper = new ObjectMapper();
            //Json数据转化为类实体，以下同Gson用法相似
            RootBean root = mapper.readValue(get_json("complex.json"),RootBean.class);
            textShow.append(root.getName()+ "\n");
            for (Fans fans: root.getFans()){
                textShow.append(fans.getName()+ "\n");
                textShow.append(fans.getAge()+ "\n");
            }
            jsonText.setText(textShow);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void parse_fastjson(){
        try {
            StringBuilder textShow = new StringBuilder("This is parsed by fastjson"+"\n");
            //转化Json数据到类实体
            RootBean root = JSON.parseObject(get_json("complex.json"),RootBean.class);
            //以下类似Gson
            textShow.append(root.getName()+ "\n");
            for (Fans fans:root.getFans()){
                textShow.append(fans.getName()+ "\n");
                textShow.append(fans.getAge()+ "\n");
            }
            jsonText.setText(textShow);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
