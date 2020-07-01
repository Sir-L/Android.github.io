# Android笔记

​				——Circleimageview

#### 依赖框架：Circleimageview

##### 一：优势及作用

1. 圆形图片

##### 二：Circleimageview的配置

1. dependencies中添加（注入依赖）

   ```java
   implementation 'de.hdodenhof:circleimageview:3.0.0'
   ```

##### 三:使用

```java
//XML文件中
<de.hdodenhof.circleimageview.CircleImageView
android:id="@+id/civ"
android:layout_width="60dp"
android:layout_height="60dp"
android:src="@mipmap/picture" />
```

