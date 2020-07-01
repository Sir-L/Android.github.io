# Android笔记

​				——JSON解析与网络请求

#### JSON解析与网络请求：JSON and HTTP

##### HTTP请求：

###### HTTP请求就是从客户端向服务器端发送一条HTTP请求，服务端收到请求后返回一些数据给客户端，客户端再对这些数据进行解析处理，用的时候可以通过自定义的工具类直接使用

1.新建一个类，命名为HttpTool

2.ALT+Enter重写HttpCallBack类

```java
package com.example.sirl.practice2.HttpTool;

import android.os.Handler;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpTool {
    public  static  void Tool(final Handler handler,final String path,final String json,final HttpCallBack callBack){
        new Thread(){
            @Override
            public void run() {
                try {
                    //建立新的请求协议
                    URL url=new URL(path);
                    //根据请求协议生成HttpURLConnection类
                    HttpURLConnection conn=(HttpURLConnection)url.openConnection();
                    //向conn输出，默认false
                    conn.setDoInput(true);
                    //向conn读入，默认true
                    conn.setDoOutput(true);
                    //POST请求不能使用缓存
                    conn.setUseCaches(false);
                    //设置请求的方法为"POST",默认为"GET"
                    conn.setRequestMethod("POST");
                    //设定传送的内容类型是可序列化的java对象
                    conn.setRequestProperty("Content-Type","application/json");

                    PrintWriter writer=new PrintWriter(conn.getOutputStream());
                    writer.print(json);
                    writer.close();
                    BufferedReader reader=new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    //创建可变字符串
                    final StringBuilder builder=new StringBuilder();
                    String line;
                    while ((line=reader.readLine())!=null){
                        //连接line-->builder.toString(String结果)
                        builder.append(line);
                    }
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            callBack.Onub(builder.toString());
                        }
                    });
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }.start();
    }
}
```

```java
package com.example.sirl.practice2.HttpTool;

public abstract class HttpCallBack {
    public abstract void Onub(String json);
}
```

##### JSON解析：

解析前需要先在AndroidManifest/manifest里添加权限

```java
<uses-permission android:name="android.permission.INTERNET" />//允许程序打开网络套接字
```



```java
HttpTool.tool(new Handler(Looper.getMainLooper()), "http://140.143.167.47:8080/json/GetSUserInfo.do", "", new HttpCallBack() {
            @Override
            public void Onub(String json) {
                try {
                    //将json解开
                    JSONObject object=new JSONObject(json);
                    //获取json里的student
                    String class1=object.getString("student");
                    //将class1解开
                    JSONArray array=new JSONArray(class1);
                    for (int i = 0; i < array.length(); i++) {
                        //获取array里的每一个学生
                        String student=array.getString(i);
                        //将学生解开
                        JSONObject object1=new JSONObject(student);
                        //获取学生姓名
                        String name=object1.getString("name");
                    }
                    
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
```

###### 说明：https://blog.csdn.net/qq_39659876/article/details/81909451

##### 注：高版本api可能无法请求成功，解决方法：

1.新建xml文件夹，创建xml文件，取名为network_security_config.xml

2.添加代码

```java
<?xml version="1.0" encoding="utf-8"?>
<network-security-config>
    <base-config cleartextTrafficPermitted="true"/>
</network-security-config>
```

3.在AndroidManifest/application里添加

```java
android:networkSecurityConfig="@xml/network_security_config"
```

