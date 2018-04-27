> 阅读本篇大概需要 5 分钟。  


单例模式是使用频率很高的模式。在日常开发中进场能通常使用的类是需要消耗较多资源或者没有多个实例的情况。可以说，单例是很常见的，它结构很简单，而且一般面试的时候也会被问到。可是想要写好一个单例，并没与那么简单。  

实现单例模式主要有如下几个关键点：   
（1）构造函数一般为private，不对外开放  
（2）通过一个静态方法或者枚举返回单例对象  
（3）确保单例类的对象有且只有一个，尤其是在多线程、高并发的环境下  
（4）确保单例类对象在反序列化时不会重新构建对象

许多单例模式的实现都是在单线程下的，多线程下的单例模式就需要加上同步机制，而当需要把对象刻到磁盘上存放时，就会牵扯到反序列化的问题。**因此单例模式在（3）和（4）的应用情况下需要特别处理。下面介绍几种实现单例模式的方式，他们有些是线程不安全的，有些是线程安全的；对于反序列化重构对象，只有枚举可以防止。**


### 懒汉模式
声明一个静态对象，在调用getInstance()的时候，用 *synchronized* 字段来控制。
```JAVA
public class XxxManager{
    private static XxxManager instance;
    //构造方法
    private XxxManger() {
        
    }
    
    public static synchronized XxxManager getInstance() {
        if (instance == null) {
            instance = new XxxManager();
        }
        return instance;
    }
}
```
**缺点：** 由于使用了synchronized关键字，所以在每次调用getInstance()方法的时候都会进行同步，造成不必要的资源消耗。**不建议使用.**

### Double Check Lock 实现单例
DCL就是在调用getInstance()的时候，Double Check了一遍，而且用到了*Synchorized*关键字
```JAVA
public class XxxManager{
    private static XxxManager instance;
    private XxxManager() {
        
    }
    public static XxxManager getInstance() {
        if (instance == null) {
            synchroized(XxxManager.class) {
                if (instance == null) {
                    instance = new XxxManager();
                }
            }
        }
        return isntance;
    }
}
```
DCL会有小概率发生问题，原因在于Double Check的时候，如果instance被分配了地址，他就不再会为null，但是这个时候刚巧一个Thread调用了getInstance()方法，这样就会出问题。在JDK1.5之后，引入了volatile关键字。将intance定义改成`private volatile static XxxManager instance = null`就行了。  
**优点：** 资源利用率高，第一次执行getInstance()的时候才会初始化。
**缺点：** 第一次加载慢，而且由于Java内存模型的问题，可能在高并发的情况下出现问题，概率很小。

### 静态内部类实现单例
这个方法推荐使用：
```JAVA
public class XxxManager{
    private XxxManager() {
        
    }
    
    public static XxxManager getInstance() {
        return XxxManagerHolder.instance;
    }
    
    private static class XxxManagerHolder{
        private static final XxxManager instance = new XxxManager();
    }
}
```
第一次加载XxxManager类的时候并不初始化isntance，只有调用getInstance()方法的时候，才会初始化，而且是线程安全，单例对象唯一，延迟了单例的实例化，**推荐使用这种方法**。
### 枚举单例
这个是《effective java》作者推荐的写法。
```JAVA
public class XxxManager{
    private XxxManager(){
        
    }
    private static XxxManager getInstance() {
        return XxxManagerEnum.ENUM_INSTANCE.getInstance();
    }
    
    private static enum XxxManagerEnum{
        ENUM_INSTANCE;
        private XxxManager instance;
        private XxxManagerEnum(){
            instance = new XxxManager();
        }
        public XxxManager getInstance() {
            return instance;
        }
    }
}
```
此方法线程安全，而且任何情况下只有一个单例存在。若是要杜绝在反序列化之后被重生对象，加入以下方法：
```JAVA
private Object readResolve() throws ObjectStreamException {
    return instance;
}
```

还有什么用容器来做单例的，其实感觉就是存放单例的地方，这个就不考虑了。
后续还会继续更新其他设计模式，还有其他的有意思的教程。

#### 请大家关注一下我的公众号：**皮克啪的铲屎官**
![qr_code](https://github.com/SwyftG/DesignPatternExample/blob/master/src/img/qr_code.png)
#### 是一个日更的微信公众号，每天都有新知识，大家一起交流进步。