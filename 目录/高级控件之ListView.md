# Android笔记

​				——高级控件之ListView

#### ListView

##### 一：使用

1. ​	在Activity布局文件中放置ListView控件

   ```java
   <?xml version="1.0" encoding="utf-8"?>
   <LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
       xmlns:app="http://schemas.android.com/apk/res-auto"
       xmlns:tools="http://schemas.android.com/tools"
       android:layout_width="match_parent"
       android:layout_height="match_parent"
       android:orientation="vertical"
       tools:context=".MainActivity">
   
   
       <ListView
           android:id="@+id/lv"
           android:layout_width="match_parent"
           android:layout_height="match_parent">
   
       </ListView>
   </LinearLayout>
   ```

2. 制作ListViewAdapter适配器（适配器是用于自定义控件）

   1. 为适配器布局

      ```java
      <?xml version="1.0" encoding="utf-8"?>
      <LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
          android:layout_width="match_parent"
          android:layout_height="100dp"
          android:orientation="horizontal"
          android:weightSum="2">
      
          <TextView
              android:id="@+id/lva1_txt1"
              android:layout_width="0dp"
              android:layout_height="match_parent"
              android:layout_weight="1" />
      
          <TextView
              android:id="@+id/lva1_txt2"
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
      
      public class ListViewAdapter extends BaseAdapter {
          List<user> list;
          LayoutInflater inflater;
      
          //重写构造方法
          public ListViewAdapter(List<user> list, Context context) {
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
              view = inflater.inflate(R.layout.lva1, null);
              //绑定该适配器的控件
              TextView txt1 = view.findViewById(R.id.lva1_txt1);
              TextView txt2 = view.findViewById(R.id.lva1_txt2);
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
      import android.widget.ListView;
      import java.util.ArrayList;
      import java.util.List;
      
      public class MainActivity extends AppCompatActivity {
          ListViewAdapter lva;
          List<user> list;
          ListView lv;
      
          @Override
          protected void onCreate(Bundle savedInstanceState) {
              super.onCreate(savedInstanceState);
              setContentView(R.layout.activity_main);
      
              //绑定控件
              lv=findViewById(R.id.lv);
              //制作集合
              list=new ArrayList<>();
              //制作对象并添加到集合里
              user user=new user();
              user.setName("user1");
              user.setPwd("123456");
              list.add(user);
              //将集合和上下文对象用于制作ListView适配器
              lva=new ListViewAdapter(list,getApplicationContext());
              //设置适配器的实时更新
      		lv.notifyDataSetChanged();
              //将适配器绑定到控件上
              lv.setAdapter(lva);
          }
      }
      ```

      

##### 二：高级方法

###### 1.点击事件

```java
// 列表项的点击事件监听器
lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
    // 点击列表项执行的操作
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Log.e(TAG, "id"+id+"位置"+position+"被点击了");
    }
});
```

###### 2.选中事件

```java
// 列表项选中事件监听器
lv.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
    // 选中列表项执行的操作
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Log.e(TAG, "id"+id+"位置"+position+"被选中了");
    }
    // 未选中列表项执行的操作
    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        Log.e(TAG, "未选中列表项" );
    }
});
```

##### 三：动态更新列表

###### 方法一：

1. 直接更改添加到listview里list的值
2. adapter.notifyDataSetChanged();//通知适配器数值已经更改

###### 方法二：

1. listview.setAdapter(null);//清空listview里的适配器
2. 用新数据重新制作适配器
3. listview.setAdapter(新适配器)

##### 四：添加表头

```java
//在主代码中
View view=LayoutInflater.from(getApplicationContext()).inflate(R.layout.lva1,null);
TextView txt1=view.findViewById(R.id.lva1_txt1);
TextView txt2=view.findViewById(R.id.lva1_txt2);
txt1.setText("用户名");
txt2.setText("密码");
listView.addHeaderView(view);
```

##### 五：在主代码中获取控件

```java
listView.getChildAt(i)--返回item的View
TextView txt=listView.getChildAt(i).findViewById(R.id.txt);
```

##### 六：回调接口处理item控件触发

1. 制作接口（在适配器中）

   ```java
   	//制作接口
   	public interface Finish{
           void Click(int position);
       }
   	//实例化接口
       public Finish finish;
   	//提供方法供主代码访问到适配器中控件使用的接口
       public void demo_Click(Finish finish){
           this.finish=finish;
       }
   ```

2. 在适配中绑定

   ```java
   @Override
       public View getView(final int i, View view, ViewGroup viewGroup) {
           view=inflater.inflate(R.layout.demo_lva,null);
           TextView txt1=view.findViewById(R.id.demo_lva_txt);
           txt1.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   //为适配器中控件绑定接口（触发）
                   finish.Click(i);
               }
           });
           return view;
       }
   ```

3. 在主代码中提供触发的具体功能

   ```java
   Demo_lva lva=new Demo_lva(list,getApplicationContext())
   lva.demo_Click(new Demo_lva.Finish() {
       @Override
       public void Click(int position) {
           Toast.makeText(Demo_main.this, "我被点击了", Toast.LENGTH_SHORT).show();
       }
   });
   ```

   