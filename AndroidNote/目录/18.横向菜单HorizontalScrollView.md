# Android笔记

​				——横向菜单

#### 横向菜单：HorizontalScrollView

##### 一：使用

1. 布局

   ```java
   <HorizontalScrollView
           android:id="@+id/hsv"
           android:layout_width="match_parent"
           android:layout_height="match_parent">
   
           <LinearLayout
               android:layout_width="match_parent"
               android:layout_height="match_parent">
   
               <GridView
                   android:id="@+id/gv"
                   android:layout_width="match_parent"
                   android:layout_height="match_parent"
                   android:gravity="center"
                   android:horizontalSpacing="-3dp"
                   android:numColumns="auto_fit"/>
   
           </LinearLayout>
   
   </HorizontalScrollView>
   ```

   ###### HorizontalScrollView中必须有且仅有一个可以滚动的控件，LinearLayout作为GridView的父容器方便使用

2. 主代码

   ```java
   private GridView gv;
       private GridViewAdapter adapter;
       private List<String> list = new ArrayList<>();
   
       @Override
       protected void onCreate(Bundle savedInstanceState) {
           super.onCreate(savedInstanceState);
           setContentView(R.layout.activity_main3);
   
           gv = findViewById(R.id.gv);
   
           for (int i = 0; i < 30; i++) {
               list.add("站点" + i);
           }
   
           // GridView子项宽度
           int itemWidth = 200;
           // 子项宽度*数据总数=GridView的宽度
           int gridviewWidth = list.size() * itemWidth;
   
           // 动态控制LinearLayout
           //创建布局参数实体类
           LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(gridviewWidth, LinearLayout.LayoutParams.MATCH_PARENT);
           //设置GridView的布局参数
           gv.setLayoutParams(params);
           //设置子项宽度
           gv.setColumnWidth(itemWidth);
           //设置拉伸模式
           gv.setStretchMode(GridView.NO_STRETCH);
           //设置子项个数
           gv.setNumColumns(list.size());
   
   
           adapter = new GridViewAdapter(list, getApplicationContext());
           gv.setAdapter(adapter);
       }
   ```

   