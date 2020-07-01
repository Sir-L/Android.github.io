# Android笔记

​				——SQLite数据库

#### SQLite数据库：SQLite

###### SQLite数据库是一款轻量级的数据库，它的运算速度特别快，占用资源特别少，通常指需要几百KB的内存就足够了。因而特别适合在移动设备上使用。

##### 特点：

1. 轻量级
2. 不需要“安装”
3. 单一文件
4. 跨平台/可移植性
5. 开源

##### 数据类型：

1. INTEGER：整数
2. REAL：浮点数
3. CHAR(n)：长度固定为n的字串，n不能超过 254
4. VARCHAR(n)：长度不固定且其最大长度为 n 的字串，n不能超过 4000
5. TEXT： 文本字符串
6. BLOB：二进制数据块
7. Date：年份、月份、日期
8. Time：小时、分钟、秒
9. timestamp： 年、月、日、时、分、秒、毫秒

##### 创建数据库：

1. 创建一个继承自SQLiteOpenHelper的工具类：

   ```java
   public class SqlUtile extends SQLiteOpenHelper {
       
       // 重写构造方法
       public SqlUtile(Context context, String name, int version) {
           // 【上下文】、【数据库名称.db】、【默认工厂】、【版本号】
           super(context, name, null, version);
       }
       
       // 重新写onCreate()方法
       /*
       * 第一次创建数据库时回调该方法
       * 可以生成表结构，并添加一些初始化数据
       * */
       @Override
       public void onCreate(SQLiteDatabase db) {
           // 初始化建表
           db.execSQL(【建表语句】);
           // 添加初始数据
           db.execSQL(【添加语句】);
       }
       
       // 当数据库版本更新时回调该方法
       @Override
       public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
       }
   }
   ```

2. 举例

   ```java
   package com.example.sirl.demo0711.work3;
   
   import android.content.Context;
   import android.database.sqlite.SQLiteDatabase;
   import android.database.sqlite.SQLiteOpenHelper;
   import android.support.annotation.Nullable;
   
   public class SQLite extends SQLiteOpenHelper {
       public SQLite(Context context) {
           super(context, "user", null, 1);
       }
   
       @Override
       public void onCreate(SQLiteDatabase sqLiteDatabase) {
           sqLiteDatabase.execSQL("Create table if not exists table1(id Integer primary key,user varchar(20),pwd varchar(20))");
       }
   
       @Override
       public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
   
       }
   }
   ```

   ##### 使用：

   1. 新建数据库

      ```java
      			//创建数据库对象，若不存在则新建，若不存在则复用
                  SQLite sqLite = new SQLite(getApplicationContext());
                  // 以何种方式打开数据库
                  // getReadableDatabase()
                  // 以读写的方式打开数据库对应的SQLiteDatabase对象,如果数据库磁盘满了，则只能读不能写，并且抛出异常
                  // getWritableDatabase()
                  // 以写的方式打开数据库对应的SQLiteDatabase对象，如果数据库磁盘满了，则返回打开失败，继而以只读方式打开数据库
                  SQLiteDatabase db = sqLite.getWritableDatabase();
      ```

   2. 增

      ```java
      	//第一种方式
          ContentValues cv = new ContentValues();
          cv.put("user", "bbb");
          cv.put("pwd", "123456");
          db.insert("user", null, cv);
          //第二种方式
          db.execSQL("insert into user(user,pwd) VALUES(?,?)", new Object[]{"bbb", "123456"});
      ```

      

   3. 删

      ```java
      	//第一种方式
          String whereClause2="user=?";
          String[] whereArgs2={String.valueOf("bbb")};
          //当多个条件时
      	String whereClause2="user=? and pwd=?";
      	String[] whereArgs2={String.valueOf("bbb"),String.valueOf("123456")};
          
      	db.delete("user",whereClause2,whereArgs2);
          //第二种方式
          db.execSQL("delete from user where user='bbb' and pwd='123456'");
      ```

      

   4. 改

      ```java
      	//第一种方式
          ContentValues cv = new ContentValues();
          cv.put("user","bbb");
          String whereClause="user=?";
          String[] whereArgs={"aaa"};
          //当多个条件时
          cv.put("user","bbb");
      	String whereClause="user=? and pwd=?";
      	String[] whereArgs={"aaa","123456"};
      
          db.update("user",cv,whereClause,whereArgs);
          //第二种方式
          db.execSQL("update user set user=? where user='aaa' and pwd='123456'",new Object[]{"bbb"});
      ```

      

   5. 查

      ```java
      	//第一种方式
      	Cursor cursor=db.query("user",new String[]{"user","pwd"},null,null,null,null,null,null);
      	//第二种方式
      	Cursor cursor1=db.rawQuery("select user,pwd from user",null);
      
          if (cursor.moveToFirst()){
              do{
                Log.e("user",""+cursor.getString(cursor.getColumnIndex("user")));
                }while (cursor.moveToNext());
             }
      ```

      ###### 注：1.查找数据库位置（打开SDK-AS右下角Device File Explorer-data-data-自己的项目名称-databases），打开可以用SQLiteSpy打开。

      ###### 	    2.SQLite为独立的数据库，有自己的语法和规范，但也符合通用规范