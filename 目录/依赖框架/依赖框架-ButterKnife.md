# Android笔记

​				——ButterKnife

#### 依赖框架：ButterKnife

##### 一：优势及作用

1. 强大的View绑定和Click事件处理功能，简化代码，提升开发效率
2. 方便的处理Adapter里的ViewHolder绑定问题
3. 运行时不会影响APP效率，使用配置方便
4. 代码清晰，可读性强

##### 二：ButterKnife的配置

1. 在Project的 build.gradle 中添加如下代码（下载框架）

   ```java
   buildscript {
       repositories {
           jcenter()
       }
       dependencies {
           classpath 'com.android.tools.build:gradle:2.3.3'
           classpath 'com.jakewharton:butterknife-gradle-plugin:8.8.1'  //添加这一行
       }
   }
   ```

2. 在App的 build.gradle 中添加如下代码（导入框架）

   ```java
   apply plugin: 'com.jakewharton.butterknife'//有时不需要添加
   ```

3. dependencies中添加（注入依赖）

   ```java
   compile 'com.jakewharton:butterknife:8.8.1'
   annotationProcessor 'com.jakewharton:butterknife-compiler:8.8.1'
   ```

##### 三:使用

