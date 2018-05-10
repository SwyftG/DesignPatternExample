首先，惯例，先说正事儿：
### 每日一皮克啪
皮克啪已经快窝不会来了
![每日皮克啪](https://mmbiz.qpic.cn/mmbiz_jpg/jA4Qc7C9IZQrA2EarYn6djg59SZMD6WMRqibt2EfbA4E05LNY19PmGKnslQeK57WhWXP6QEsWcdWZMUicJcbW7nA/0?wx_fmt=jpeg)

正事儿说完，咱们先来扯几句相关话题：
文章中所有的设计模式源代码，我均已上传到了网上，获取方法：*关注“皮克啪的铲屎官”公众号，回复“设计模式”，即可获得下载地址*。

接下来，咱们来聊聊 **观察者模式**。  
观察者模式（`Observer Pattern`）。Wikipedia解释如下：

"The **observer pattern** is a software design pattern in which an object, called the *subject, maintains a list of its dependents, called observers*, and notifies them automatically of any state changes, usually by calling one of their methods."

大致意思就是：观察者模式是一个**被观察者（Subject）**，有好多个**观察者（observer）**，当被观察者发生变化的时候，会通知观察者。

##### 接下来是观察者模式的UML图：

![观察者模式UML图](https://mmbiz.qpic.cn/mmbiz_jpg/jA4Qc7C9IZQrA2EarYn6djg59SZMD6WM3JxOkuRuI67Pnlhs7iaSRyOhM2QMXpssIarHMyDdMQlbey3oZpQhpFw/0?wx_fmt=jpeg)

这里就有这么几个角色：
 - **Subject** ***抽象主题***，也是被观察者。提供接口。
 - **ConcreteSubject** ***具体的主题类***，会将所有的观察者都保存在一个集合里，如果状态发生变化，会通知他们。
 - **Observer** ***抽象观察者***，定义一个更新接口，在得到被观察者通知的时候调用。
 - **ConcreteObserver** ***具体的观察者实现类***。实现更新方法。
 
这里可能会有很多人会觉得绕，什么观察者被观察者的，到底这俩有什么仇什么怨，非要纠缠在一起。我当时第一次听这两个名词的时候，也觉得很绕，但是***不要慌***，听我举个例子慢慢道来，保准能够帮你打通任督二脉。

举一个简单的“观察者模式”的例子：*微信用户订阅“皮克啪的铲屎官”公众号*。这其中，“***皮克啪的铲屎官***”这个公众号，就是***主题***，也是***被观察者***；***微信用户***，就是***观察者***。**每一个粉丝，都是观察者，观察公众号是否发布新的文章。当公众号的状态发生改变，比如，发文章了，那么所有关注“皮克啪的铲屎官”的微信用户，都能够收到来自公众号的通知，此时，每一个单独的用户，都可以点开文章进行阅读。这就是观察者模式最好的例子。所以，在观察者模式中，被观察者，就理解成公众号，观察者，就是粉丝。**就是这么简单。

### 操作实例
Java为了实现观察者模式，提供了内置的两个类`Observer`和`Obvervable`。`Observer`是抽象观察者角色；`Obervable`是抽象主题。观察者只需要继承Observer类，然后实现里面的`update()`方法，主题如果需要通知观察者，需要调用`notifyObervers()`方法或者其他相对于的方法即可。

这种方法是Java内部提供的实现方法，足以见得观察者模式的地位。我们这里是要洞悉原理，所以这种实现未必是能最好的体验观察者模式。那么我们就自己来按照观察者模式的原理来实现一下代码。

观察者模式的写法，可以分为两种：***一种是push模式***，*即当主题发生变化时，核心业务是由主题通知给观察者*；***另一种是pull模式***，*即当主题发生变化时，核心业务可以由观察者自己主动获取，而不是被动承受*。

针对观察者模式，我们以`PeekPa`和铲屎官们的关系为例子。`PeekPa`是观察者模式中的***主题，即被观察者***，在茫茫人海中，有几位年轻的优秀的***幸运的人***，能够有机会为`PeekPa`铲屎，成为了***铲屎官***。***那么这些铲屎官就是观察者***。*每当`PeekPa`的状态发生变化的时候，铲屎官就会收到通知，然后执行自己的方法*。

下面我们还先写***push模式***，首先是抽象主题类`IPeekPaSubject`:
```JAVA
public class IPeekPaSubject {
    // 通过一个List来存储观察者对象
    private List<IServantObserver> servants = new ArrayList<>();

    // 注册观察者
    public void recruitServant(IServantObserver servant) {
        servants.add(servant);
    }

    // 解绑观察者
    public void fireServant(IServantObserver servant) {
        servants.remove(servant);
    }
    // 通知观察者的方法，同时传入自己的状态
    protected void notifyPoo(String state) {
        for (IServantObserver servent : servants) {
            servent.cleanUp(state);
        }
    }
    // 通知观察者的方法，同时传入自己的状态
    protected void notifyFeed(String state) {
        for (IServantObserver servent : servants) {
            servent.feed(state);
        }
    }
}
```
具体主题类`PeekPa`:
```JAVA
public class PeekPa extends IPeekPaSubject{
    // 记录状态发生改变
    private String state;

    public PeekPa() {
        this.state = "";
    }

    public void changeState(int type) {
        if(type == 0) {
            state += "pop;";
        } else if (type == 1) {
            state += "hungry:";
        }
    }

    public void printState() {
        System.out.println(this.state);
    }
    
    public String getState() {
        return this.state;
    }
    // 状态发生改变，调用通知方法
    public void poo() {
        changeState(0);
        System.out.println("PeekPa poo~~~~~~");
        notifyPoo(this.state);
    }
    
    // 状态发生改变，调用通知方法
    public void hungry() {
        changeState(1);
        System.out.println("PeekPa is hungry~~~~~");
        notifyFeed(this.state);
    }
}
```
观察者接口`IServantObserver`:
```JAVA
public interface IServantObserver {
    void feed(String state);
    void cleanUp(String state);
}
```
具体的观察者`Servant`:
```JAVA
public class Servant implements IServantObserver{
    private String servantName;

    public Servant(String servantName) {
        this.servantName = servantName;
    }

    // 当主题发生变化时，会接到通知来调用次方法
    @Override
    public void feed(String state) {
        this.peekPaState = state;
        System.out.println(this.servantName + " gives some cat food to Lord.");
    }

    // 当主题发生变化时，会接到通知来调用次方法
    @Override
    public void cleanUp(String state) {
        this.peekPaState = state;
        System.out.println(this.servantName + " is ready.");
    }
    
    public void printState() {
        System.out.println(this.servantName + " has state is : " + this.peekPaState);
    }
}
```
好的，到这里观察者模式的代码就已经构建完毕，我们来看一下使用效果：
```JAVA
    // 声明被观察者
    PeekPa peekPa = new PeekPa();
    // 声明观察者
    Servant liLei = new Servant("LiLei");
    Servant hanMeimei = new Servant("HanMeimei");
    // 将观察者注册到被观察者上面
    peekPa.recruitServant(liLei);
    peekPa.recruitServant(hanMeimei);
    // 调用被观察者的方法，看观察者是否被调用
    peekPa.poo();   // PeekPa poo~~~~~~
                    // LiLei is ready.
                    // HanMeimei is ready.

    peekPa.hungry();    // PeekPa is hungry~~~~~
                        // LiLei gives some cat food to Lord.
                        // HanMeimei gives some cat food to Lord.

    // 打印被观察者的状态历史记录
    peekPa.printState();    // "pop;hungry:"
    liLei.printState();     // "LiLei has state is : pop;hungry:"
    hanMeimei.printState();     // "HanMeimei has state is : pop;hungry:"
```
我们看到，我们只需要调用被观察者的方法，就可以执行观察者中的方法。而且客户端调用起来非常方便。上面的写法是***push模式***，***pull模式***则是在*被观察者的通知方法里面，传入的不是要更新的内容，而是将被观察者自己作为对象传过去。然后观察持有被观察者的引用，再调用里面的方法即可。*至于Java中，如何用`Observer`和`Observable`类来实现观察者模式，这个大家自己稍微的网上搜一下即可，炒鸡简单。

### 总结一下
观察者模式的主要作用就是解耦，能够将观察者和被观察者完全解耦，十分灵活，用起来炒鸡方便。扩展性很棒棒。*现在很火的RxJava就是运用观察者模式*。

`优点：`*观察者和被观察者结构，能够灵活应对业务；增强系统灵活性和可扩展性。*  
`缺点：`*开发效率需要注意，程序中一旦观察者和被观察者多了起来，那样维护起来会特别复杂，同时，一旦一个观察者卡顿，将会影响执行效率。异步可破。*

#### 推荐阅读：
[【Python实战】用代码来访问1024网站，送福利(重磅推荐此文)](https://mp.weixin.qq.com/s?__biz=MzI2ODYwNjE5NQ==&mid=2247483753&idx=1&sn=8df6c2a190201826f6f860659ad4af9e&chksm=eaec4ef5dd9bc7e39e8d48134795f6c0173c4614c615d0dcaaa38d937f4394aee77a978d70b1#rd)  
[设计模式之零九：“一是一二是二的”解释器模式](https://mp.weixin.qq.com/s?__biz=MzI2ODYwNjE5NQ==&mid=2247483724&idx=1&sn=e8a8ceaf1f9e9c17dc876a3991390a3e&chksm=eaec4ed0dd9bc7c6f40464c254ccaa5521dc1afaf2616c47c7728feccf896b9f5df4bb705e54#rd)  
[设计模式之十零：“一层又一层封装的”命令模式](https://mp.weixin.qq.com/s?__biz=MzI2ODYwNjE5NQ==&mid=2247483729&idx=1&sn=12bad345e7632452a45bb0e08af20266&chksm=eaec4ecddd9bc7db4520f803e7f8a8a96d433db80f6a526a0472c0835594add886bee453062a#rd)  

每天分享代码骚操作和养猫心得
![](https://mmbiz.qpic.cn/mmbiz_jpg/jA4Qc7C9IZS5CU8Eicxw9K4kIY8BibzDJX6QiahNQ0wDC2HLheXWp6CpITXBWcxt6E4SRlxHJyrxNO6v6TlKMgeUg/0?wx_fmt=jpeg)

