# Android笔记

​				——高级控件之GridView

#### GridView

##### 一：使用

1. ​	在Activity布局文件中放置GridView控件

   ```java
   <?xml version="1.0" encoding="utf-8"?>
   <LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
       xmlns:app="http://schemas.android.com/apk/res-auto"
       xmlns:tools="http://schemas.android.com/tools"
       android:layout_width="match_parent"
       android:layout_height="match_parent"
       android:orientation="vertical"
       tools:context=".MainActivity">
   
   
       <GridView
           android:layout_width="match_parent"
           android:layout_height="match_parent"
           android:columnWidth//设置列的宽度
           android:horizontalSpacing="200dp"//设置各元素之间的水平间距
           android:numColumns="3"//设置列数
           android:verticalSpacing="300dp"//设置各元素之间的垂直间距
           android:stretchMode>//设置拉伸模式（https://www.cnblogs.com/rainboy2010/p/4525856.html）
   
       </GridView>
   </LinearLayout>
   ```

2. 制作GridViewAdapter适配器

   1. 为适配器布局

      ```java
      <?xml version="1.0" encoding="utf-8"?>
      <LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
          android:layout_width="match_parent"
          android:layout_height="100dp"
          android:orientation="horizontal"
          android:weightSum="2">
      
          <TextView
              android:id="@+id/gva1_txt1"
              android:layout_width="0dp"
              android:layout_height="match_parent"
              android:layout_weight="1" />
      
          <TextView
              android:id="@+id/gva1_txt2"
              android:layout_width="0dp"
              android:layout_height="match_parent"
              android:layout_weight="1" />
      </LinearLayout>
      ```

   2. 制作适配器

      ```java
      package com.example.sirl.practice2;
      
      import android.content.Context;
      import android.view.LayoutInflater;
      import android.view.View;
      import android.view.ViewGroup;
      import android.widget.BaseAdapter;
      import android.widget.TextView;
      
      import java.util.ArrayList;
      import java.util.List;
      
      public class GridViewAdapter extends BaseAdapter {
          List<user> list;
          LayoutInflater inflater;
      
          //重写构造方法
          public GridViewAdapter(List<user> list, Context context) {
              this.list = list;
              inflater = LayoutInflater.from(context);
          }
      
          //获取集合长度
          @Override
          public int getCount() {
              return list.size();
          }
      
          //获取集合里的元素
          @Override
          public Object getItem(int i) {
              return list.get(i);
          }
      
          //获取集合里的元素的序号
          @Override
          public long getItemId(int i) {
              return i;
          }
      
          //将数据绑定到ListViewAdapter的视图上（参数一：集合里元素的序号；参数二：该适配器的单个元素视图；参数三：该适配器的元素视图集）
          @Override
          public View getView(int i, View view, ViewGroup viewGroup) {
              //将布局绑定到该适配器上
              view = inflater.inflate(R.layout.gva1, null);
              //绑定该适配器的控件
              TextView txt1 = view.findViewById(R.id.gva1_txt1);
              TextView txt2 = view.findViewById(R.id.gva1_txt2);
              //将数据绑定到控件上
              txt1.setText(list.get(i).getName());
              txt2.setText(list.get(i).getPwd());
              //返回元素的视图
              return view;
          }
      }
      ```

   3. 完成适配器的初始化并绑定到控件上

      ```java
      package com.example.sirl.practice2;
      
      import android.support.v7.app.AppCompatActivity;
      import android.os.Bundle;
      import android.widget.GridView;
      
      import java.util.ArrayList;
      import java.util.List;
      
      public class MainActivity extends AppCompatActivity {
          GridViewAdapter gva;
          List<user> list;
          GridView gv;
      
          @Override
          protected void onCreate(Bundle savedInstanceState) {
              super.onCreate(savedInstanceState);
              setContentView(R.layout.activity_main);
      
              //绑定控件
              gv = findViewById(R.id.gv);
              //制作集合
              list = new ArrayList<>();
              //制作对象并添加到集合里
              for (int i = 0; i < 5; i++) {
                  user user = new user();
                  user.setName("user1");
                  user.setPwd("123456");
                  list.add(user);
              }
              //将集合和上下文对象用于制作GridView适配器
              gva = new GridViewAdapter(list, getApplicationContext());
              //设置适配器的实时更新
      		gv.notifyDataSetChanged();
              //将适配器绑定到控件上
              gv.setAdapter(gva);
          }
      }
      ```

      

##### 二：高级方法

###### 1.点击事件

```java
// 列表项的点击事件监听器
   gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
   		// 点击列表项执行的操作
   		@Override
   		public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
          Log.e("TAG","id"+l+"位置"+i+"被点击了");
   		}
   });
```

###### 2.选中事件

```java
// 列表项选中事件监听器
   gv.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
       // 选中列表项执行的操作
       @Override
       public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
          Log.e("TAG","id"+l+"位置"+i+"被选中了");
       }
       // 未选中列表项执行的操作
       @Override
       public void onNothingSelected(AdapterView<?> adapterView) {
          Log.e("TAG","为选中列表项");
       }
   });
```

