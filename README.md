# RollNumberView

---

![preview][1]

一个可以滚动的数字View
========
- 把项目的RollNumberTextView和RollNumberView两个类复制到你项目去，然后就可以用啦。
- 项目里面使用了个字体 DINPro-Bold.otf，你们不用的自行去掉就OK了，在RollNumberView里面。

使用
========

### 布局

>记得改这个前缀(com.xxx.xxx.xxx)，改成你自己目录下的路径

 ```java
    <com.xxx.xxx.xxx.RollNumberTextView
        android:id="@+id/roll_number_text_view"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content" />
```

>startAnimator即可起飞
```java
        RollNumberTextView rollNumberTextView = findViewById(R.id.roll_number_text_view);
        rollNumberTextView.startAnimator(5555555,6666666);
 ```


  [1]: https://github.com/CPT-CJS/RollNumberView/blob/master/preview.gif
