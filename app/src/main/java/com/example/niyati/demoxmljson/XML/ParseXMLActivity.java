package com.example.niyati.demoxmljson.XML;

import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.Xml;
import android.view.View;
import android.widget.TextView;

import com.example.niyati.demoxmljson.R;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

public class ParseXMLActivity extends AppCompatActivity {
    private TextView dataText;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xml);
        dataText = findViewById(R.id.xml_data);
        //Pull方式解析
        findViewById(R.id.pull).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xml_pull();
            }
        });
        //SAX方式解析
        findViewById(R.id.sax).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xml_sax();
            }
        });
        //DOM方式解析
        findViewById(R.id.dom).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xml_dom();
            }
        });
    }

    /**
     * SAX方式解析：需要新建类继承自DefaultHandler类，并重写弗雷德5个方法，如下所示
     */
    class ContentHandler extends DefaultHandler{

        private String nodeName;

        private StringBuilder id;

        private StringBuilder name;

        private StringBuilder version;

        private StringBuilder text;

        //在开始XML解析的时候调用
        @Override
        public void startDocument() throws SAXException {
            id = new StringBuilder();
            name = new StringBuilder();
            version = new StringBuilder();
            text = new StringBuilder("This is parsed by SAX" + "\n");
        }

        //在解析某个节点的时候调用
        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
            nodeName = localName;
        }

        //在获取节点内容时调用
        //注意：获取内容时，该方法可能会被调用多次，同时换行符也会被当作内容解析出来
        @Override
        public void characters(char[] ch, int start, int length) throws SAXException {
            if ("id".equals(nodeName)){
                id.append(ch, start, length);
            }else if ("name".equals(nodeName)){
                name.append(ch, start, length);
            }else if ("version".equals(nodeName)){
                version.append(ch, start, length);
            }
        }

        //在对某个节点的解析完成时调用
        @Override
        public void endElement(String uri, String localName, String qName) throws SAXException {
            if ("app".equals(localName)){
                text.append("id is " + id.toString() + "\n");
                text.append("name" + name.toString() + "\n");
                text.append("version is " + version.toString() + "\n");
                id.setLength(0);
                name.setLength(0);
                version.setLength(0);
            }
        }

        //整个XML解析完成的时候调用
        @Override
        public void endDocument() throws SAXException {
            dataText.setText(text);
            super.endDocument();

        }
    }

    private void xml_sax(){
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser parser = factory.newSAXParser();
            ContentHandler mHandler = new ContentHandler();
            //打开assets文件夹下的xml示例文件到输入流
            InputStream in = getAssets().open("example.xml");
            parser.parse(in,mHandler);

        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    private void xml_pull(){
        /*
        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser parser = factory.newPullParser();
            parser.setInput(new StringReader(xmlData));
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }*/


        StringBuilder builder = new StringBuilder("This is parsed by PULL" + "\n");
        XmlPullParser parser = Xml.newPullParser();

        try {
            //打开assets文件夹下的xml示例文件到输入流
            InputStream in = getAssets().open("example.xml");
            parser.setInput(in,"UTF-8");
            while (parser.getEventType() != XmlPullParser.END_DOCUMENT){

                String nodeName = parser.getName();
                int eventType = parser.getEventType();
                switch (eventType){
                    case XmlPullParser.START_DOCUMENT:
                        break;
                    case XmlPullParser.START_TAG:
                        if ("id".equals(nodeName)){
                            builder.append("id is : " + parser.nextText() + "\n");
                        }else if ("name".equals(nodeName)){
                            builder.append("name is : "+ parser.nextText() + "\n");
                        }else if ("version".equals(nodeName)){
                            builder.append("version is : "+ parser.nextText() + "\n");
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        break;
                }
                parser.next();
            }
            dataText.setText(builder.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void xml_dom(){
        StringBuilder data = new StringBuilder("This is parsed by DOM" + "\n");
        try {
            //打开assets文件夹下的xml示例文件到输入流
            InputStream in = getAssets().open("example.xml");
            //得到Document Builder Factory对象
            DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
            //得到Document Builder对象
            DocumentBuilder builder = builderFactory.newDocumentBuilder();
            //得到Document存放整个xml的Document对象数据
            Document document = builder.parse(in);
            //得到xml数据的根节点
            Element rootElement = document.getDocumentElement();
            //得到根节点下所有app节点
            NodeList list = rootElement.getElementsByTagName("app");
            //遍历所有节点
            for (int i = 0;i < list.getLength(); i++){
                //获取app节点的所有子元素
                Element app = (Element) list.item(i);
                //获取app节点的子元素id，name，version，并添加到StringBuilder中
                data.append("id is " + app.getElementsByTagName("id").item(0).getTextContent() + "\n");
                data.append("name is " + app.getElementsByTagName("name").item(0).getTextContent() + "\n");
                data.append("version is " + app.getElementsByTagName("version").item(0).getTextContent() + "\n");
            }
            //更新UI
            dataText.setText(data);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
