# Android笔记

​				——高级控件之ExpandableListView

#### ExpandableListView

##### 一：使用【方法一】

1. 在要显示列表的Activity布局文件中添加ExpandableListView控件

   ```java
   <?xml version="1.0" encoding="utf-8"?>
   <LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
       xmlns:app="http://schemas.android.com/apk/res-auto"
       xmlns:tools="http://schemas.android.com/tools"
       android:layout_width="match_parent"
       android:layout_height="match_parent"
       android:orientation="vertical"
       tools:context=".MainActivity">
   
   
       <ExpandableListView
           android:id="@+id/elv"
           android:layout_width="match_parent"
           android:layout_height="match_parent">
   
       </ExpandableListView>
   </LinearLayout>
   ```

2. 创建分组、子元素的布局文件

   1. 父级

      ```java
      <?xml version="1.0" encoding="utf-8"?>
      <LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
          android:layout_width="match_parent"
          android:layout_height="100dp"
          android:orientation="vertical">
      
          <TextView
              android:id="@+id/txt_elv_father"
              android:layout_width="match_parent"
              android:layout_height="match_parent"
              android:textColor="#000"
              android:textSize="60sp" />
      </LinearLayout>
      ```

   2. 子级

      ```java
      <?xml version="1.0" encoding="utf-8"?>
      <LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
          android:layout_width="match_parent"
          android:layout_height="50dp"
          android:orientation="vertical">
      
          <TextView
              android:id="@+id/txt_elv_child"
              android:layout_width="match_parent"
              android:layout_height="match_parent"
              android:textColor="#000"
              android:textSize="40sp" />
      </LinearLayout>
      ```

3. 继承BaseExpandableListAdapter创建适配器

   ```java
   package com.example.sirl.practice2;
   
   import android.content.Context;
   import android.view.LayoutInflater;
   import android.view.View;
   import android.view.ViewGroup;
   import android.widget.BaseExpandableListAdapter;
   import android.widget.TextView;
   
   import java.util.List;
   
   public class ExpandableListViewAdapter extends BaseExpandableListAdapter {
       //上下文
       Context context;
       //父级元素集
       List<String> group;
       //子集元素集
       List<String[]> child;
   
       //构造方法
       public ExpandableListViewAdapter(Context context, List<String> group, List<String[]> child) {
           this.context = context;
           this.group = group;
           this.child = child;
       }
   
       //父级元素个数
       @Override
       public int getGroupCount() {
           return group.size();
       }
   
       //子集元素个数
       @Override
       public int getChildrenCount(int i) {
           String[] a=child.get(i);
           return a.length;
       }
   
       //获取父级元素内容
       @Override
       public Object getGroup(int i) {
           return group.get(i);
       }
   
       //获取子集元素内容
       @Override
       public Object getChild(int i, int i1) {
           String[] a=child.get(i);
           return a[i1];
       }
   
       //获取指定分组的ID，ID必须是唯一的
       @Override
       public long getGroupId(int i) {
           return 0;
       }
   
       //获取子选项的ID，ID必须是唯一的
       @Override
       public long getChildId(int i, int i1) {
           return 0;
       }
   
       //分组和子选项是否持有稳定的ID，指底层数据的改变是否会影响到
       @Override
       public boolean hasStableIds() {
           return false;
       }
   
       //父级样式
       /*
        * i 分组位置
        * b 是否展开
        * view 重用已有的视图
        * viewGroup 返回的视图对象始终依附于的视图组
        */
       @Override
       public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
           viewGroup=(ViewGroup)LayoutInflater.from(context).inflate(R.layout.elv_father,viewGroup,false);
           TextView txt1=viewGroup.findViewById(R.id.txt_elv_father);
           txt1.setText(group.get(i));
           return viewGroup;
       }
   
       /*
        * 获取一个视图对象，用来显示指定的子元素
        * i 分组位置
        * i1 子元素位置
        * b 是否处于分组中的最后一个
        * view 重用已有视图对象
        * viewGroup 返回的视图对象始终依附于的视图组
        */
       @Override
       public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
           viewGroup=(ViewGroup)LayoutInflater.from(context).inflate(R.layout.elv_child,viewGroup,false);
           TextView txt1=viewGroup.findViewById(R.id.txt_elv_child);
           txt1.setText(child.get(i)[i1]);
           return viewGroup;
       }
   
       //指定位置上的元素是否可选中
       @Override
       public boolean isChildSelectable(int i, int i1) {
           return true;
       }
   }
   ```

4. 在Activity的onCreate()方法中通过Id绑定控件，设置分组、子元素的数据集合，实例化适配器并传值

   ```java
   package com.example.sirl.practice2;
   
   import android.support.v7.app.AppCompatActivity;
   import android.os.Bundle;
   import android.widget.ExpandableListView;
   
   import java.util.ArrayList;
   import java.util.List;
   
   public class MainActivity extends AppCompatActivity {
       ExpandableListView expandableListView;
       ExpandableListViewAdapter elva;
   
       @Override
       protected void onCreate(Bundle savedInstanceState) {
           super.onCreate(savedInstanceState);
           setContentView(R.layout.activity_main);
   
           //绑定控件
           expandableListView=findViewById(R.id.elv);
           //建立父级与子级集合
           List<String> groups=new ArrayList<>();
           List<String[]> childs=new ArrayList<>();
           for (int i = 0; i < 5; i++) {
               groups.add("菜单"+String.valueOf(i+1));
               String[] a=new String[3];
               for (int j = 0; j <3 ; j++) {
                   a[j]="子选项"+String.valueOf(j+1);
               }
               childs.add(a);
           }
           //实例化适配器
           elva=new ExpandableListViewAdapter(getApplicationContext(),groups,childs);
           //设置适配器的实时更新
           elva.notifyDataSetChanged();
           //将适配器绑定控件
           expandableListView.setAdapter(elva);
       }
   }
   ```

   

##### 一：使用【方法二】

1. 在要显示列表的Activity布局文件中添加ExpandableListView控件

   ```java
   <?xml version="1.0" encoding="utf-8"?>
   <LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
       xmlns:app="http://schemas.android.com/apk/res-auto"
       xmlns:tools="http://schemas.android.com/tools"
       android:layout_width="match_parent"
       android:layout_height="match_parent"
       android:orientation="vertical"
       tools:context=".MainActivity">
   
   
       <ExpandableListView
           android:id="@+id/elv"
           android:layout_width="match_parent"
           android:layout_height="match_parent">
   
       </ExpandableListView>
   </LinearLayout>
   ```

2. 创建分组、子元素的布局文件

   1. 父级

      ```java
      <?xml version="1.0" encoding="utf-8"?>
      <LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
          android:layout_width="match_parent"
          android:layout_height="100dp"
          android:orientation="vertical">
      
          <TextView
              android:id="@+id/txt_elv_father"
              android:layout_width="match_parent"
              android:layout_height="match_parent"
              android:textColor="#000"
              android:textSize="60sp" />
      </LinearLayout>
      ```

   2. 子级

      ```java
      <?xml version="1.0" encoding="utf-8"?>
      <LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
          android:layout_width="match_parent"
          android:layout_height="50dp"
          android:orientation="vertical">
      
          <TextView
              android:id="@+id/txt_elv_child"
              android:layout_width="match_parent"
              android:layout_height="match_parent"
              android:textColor="#000"
              android:textSize="40sp" />
      </LinearLayout>
      ```

3. 创建菜单栏类

   ```java
   package com.example.sirl.practice2;
   
   public class Menu {
       private String father;
       private String[] child;
   
       public String getFather() {
           return father;
       }
   
       public void setFather(String father) {
           this.father = father;
       }
   
       public String[] getChild() {
           return child;
       }
   
       public void setChild(String[] child) {
           this.child = child;
       }
   }
   ```

4. 继承BaseExpandableListAdapter创建适配器

   ```java
   package com.example.sirl.practice2;
   
   import android.content.Context;
   import android.view.LayoutInflater;
   import android.view.View;
   import android.view.ViewGroup;
   import android.widget.BaseExpandableListAdapter;
   import android.widget.TextView;
   
   import java.util.List;
   
   public class ExpandableListViewAdapter extends BaseExpandableListAdapter {
       //上下文
       Context context;
       //菜单栏子集集合
       List<Menu> list;
   
       //构造方法
       public ExpandableListViewAdapter(Context context, List<Menu> list) {
           this.context = context;
           this.list = list;
       }
   
       //父级元素个数
       @Override
       public int getGroupCount() {
           return list.size();
       }
   
       //子集元素个数
       @Override
       public int getChildrenCount(int i) {
           return list.get(i).getChild().length;
       }
   
       //获取父级元素内容
       @Override
       public Object getGroup(int i) {
           return list.get(i).getFather();
       }
   
       //获取子集元素内容
       @Override
       public Object getChild(int i, int i1) {
           return list.get(i).getChild()[i1];
       }
   
       //获取指定分组的ID，ID必须是唯一的
       @Override
       public long getGroupId(int i) {
           return 0;
       }
   
       //获取子选项的ID，ID必须是唯一的
       @Override
       public long getChildId(int i, int i1) {
           return 0;
       }
   
       //分组和子选项是否持有稳定的ID，指底层数据的改变是否会影响到
       @Override
       public boolean hasStableIds() {
           return false;
       }
   
       //父级样式
       /*
        * i 分组位置
        * b 是否展开
        * view 重用已有的视图
        * viewGroup 返回的视图对象始终依附于的视图组
        */
       @Override
       public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
           view=LayoutInflater.from(context).inflate(R.layout.elv_father,null);
           TextView txt1=view.findViewById(R.id.txt_elv_father);
           txt1.setText(list.get(i).getFather());
           return view;
       }
   
       /*
        * 获取一个视图对象，用来显示指定的子元素
        * i 分组位置
        * i1 子元素位置
        * b 是否处于分组中的最后一个
        * view 重用已有视图对象
        * viewGroup 返回的视图对象始终依附于的视图组
        */
       @Override
       public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
           view=LayoutInflater.from(context).inflate(R.layout.elv_child,null);
           TextView txt1=view.findViewById(R.id.txt_elv_child);
           txt1.setText(list.get(i).getChild()[i1]);
           return view;
       }
   
       //指定位置上的元素是否可选中
       @Override
       public boolean isChildSelectable(int i, int i1) {
           return true;
       }
   }
   ```

5. 在Activity的onCreate()方法中通过Id绑定控件，设置分组、子元素的数据集合，实例化适配器并传值

   ```java
   package com.example.sirl.practice2;
   
   import android.support.v7.app.AppCompatActivity;
   import android.os.Bundle;
   import android.widget.ExpandableListView;
   
   import java.util.ArrayList;
   import java.util.List;
   
   public class MainActivity extends AppCompatActivity {
       ExpandableListView expandableListView;
       ExpandableListViewAdapter elva;
   
       @Override
       protected void onCreate(Bundle savedInstanceState) {
           super.onCreate(savedInstanceState);
           setContentView(R.layout.activity_main);
   
           //绑定控件
           expandableListView=findViewById(R.id.elv);
           //建立菜单栏对象集合
           List<Menu> list=new ArrayList<>();
           for (int i = 0; i < 5; i++) {
               Menu menu=new Menu();
               menu.setFather("菜单"+String.valueOf(i+1));
               String[] a=new String[3];
               for (int j = 0; j <3 ; j++) {
                   a[j]="子选项"+String.valueOf(j+1);
               }
               menu.setChild(a);
               list.add(menu);
           }
           //实例化适配器
           elva=new ExpandableListViewAdapter(getApplicationContext(),list);
           //设置适配器的实时更新
           elva.notifyDataSetChanged();
           //将适配器绑定控件
           expandableListView.setAdapter(elva);
       }
   }
   ```

##### 二：高级样式：取消左边的小箭头

```java
expandableListView.setGroupIndicator(null);
```

##### 三：高级方法

1. 分组点击事件

   ```java
   //分组点击事件
   expandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
               @Override
               public boolean onGroupClick(ExpandableListView expandableListView, View view, int i, long l) {
                   // 从Activity的分组数据集合中获取第几个
                   Log.e("TAG","onGroupClick"+i);
                   return false;
               }
           });
   ```

2. 子元素点击事件

   ```java
   //子元素点击事件
   expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
               @Override
               public boolean onChildClick(ExpandableListView expandableListView, View view, int i, int i1, long l) {
                   // 从Activity的子元素数据集合总获取第几个
                   Log.e("TAG","onChildClick"+i+i1);
                   return false;
               }
           });
   ```

3. 分组收缩事件

   ```java
   //分组收缩事件
   expandableListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {
               @Override
               public void onGroupCollapse(int i) {
                   Log.e("TAG","onGroupCollapse"+i);
               }
           });
   ```

4. 分组展开事件

   ```java
   //分组展开事件
   expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
               @Override
               public void onGroupExpand(int i) {
                   Log.e("TAG","onGroupExpand"+i);
               }
           });
   ```

   