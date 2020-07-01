# Android笔记

​				——IO流

#### IO流：SharedPreferences

##### 本地存储数据

```java
		//SharedPreferences接口主要负责读取Preferences数据
        // getSharedPreferences(【文件名】, 【读写模式】)
        // 1.Context.MODE_PRIVATE 只能被本APP读写
        // 2.Context.MODE_WORLD_READABLE 能被其他APP读，不能写
        // 3.Context.MODE_WORLD_WRITEABLE 能被其他APP读写
        // 23两种参数以不被官方推荐使用
        SharedPreferences preferences=getSharedPreferences("user",MODE_PRIVATE);
        // 获取读写数据的能力
        SharedPreferences.Editor editor=preferences.edit();
        //添加数据
        editor.putInt("flag",1);
        //清空所有数据
        editor.clear();
        //删除指定key的数据
        editor.remove("flag");
        //提交数据
        editor.commit();
```

##### 本地读取数据

```java
		SharedPreferences preferences=getSharedPreferences("user",MODE_PRIVATE);
        //获取指定key-value,getXx(【key】,【如key不存在返回的默认值】);
        preferences.getInt("flag",1);
        //用来判断是否包含指定key的数据，返回boolean
        preferences.contains("flag");
        //获取全部的key-value，返回一个Map<String, ?>
        preferences.getAll();
```

###### 注：1.查找本地存储位置（打开SDK-AS右下角Device File Explorer-data-data-自己的项目名称-shard_prefs）。

###### 		2.本地存储的变量是唯一的，如果存在，下次储存会被替换。