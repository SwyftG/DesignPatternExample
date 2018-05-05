> 阅读本篇大概需要 11 分钟。  

首先，惯例，先说正事儿：
### 每日一皮克啪
假期楼里有人家装修，听到电钻声的皮克啪，飞机耳立刻竖起来了。

![每日皮克啪之飞机耳](https://mmbiz.qpic.cn/mmbiz_jpg/jA4Qc7C9IZQO6quJmE93JGSK0yJYGp4GnmaiaricOiaMCricqIWp8cGCqZD1PfKCJcOLWnOkH6QNSNylhUzfApNXoA/0?wx_fmt=jpeg)

正事儿说完，咱们先来扯几句相关话题：
所有的设计模式源代码，我均已上传到了***GitHub***上，欢迎***Star***，么么哒：

*https://github.com/SwyftG/DesignPatternExample*

接下来，咱们来聊聊 **命令模式**。  
命令模式（`Command Pattern`）。Wikipedia解释如下：

"The **command pattern** is a behavioral design pattern in which an object is used to *encapsulate all information needed to perform an action or trigger an event at a later time*. This information includes the method name, the object that owns the method and values for the method parameters. Four terms always associated with the command pattern are *command*, *receiver*, *invoker* and *client*."

大致意思是：命令模式是一种***行为型模式***，*通过封装多个类在一起，来处理一个动作*。这些封住哪个的信息一般包括方法名称，方法的拥有者和方法的变量。命令模式由四部分组成：*命令*，*命令的接收者*，*命令的请求者*和*客户端*。

##### 命令模式的UML图如下：

![命令模式UML图](https://mmbiz.qpic.cn/mmbiz_jpg/jA4Qc7C9IZQO6quJmE93JGSK0yJYGp4GU18JN2NgFKUOb0G79fwEcamYJwT7sADRlicobQLP9P6bMw0zKup4Ribg/0?wx_fmt=jpeg)

这其中有几个部分：
- **Receiver**: 命令的接收者，是具体执行逻辑的角色。
- **Command**：命令接口，负责定义命令的抽象接口。
- **ConcreteCommand**：具体的命令角色。在execute()方法中调用命令接收者的相关方法。
- **Invoker**：命令的请求者。

命令模式其实就是讲一个请求封装成一个对象。请求以命令的形式包裹在对象中，并传给调用对象。调用对象寻找可以处理该命令的合适的对象，并把该命令传给相应的对象，该对象执行命令。由此来看，命令模式的结构很清晰，耦合性很低。

### 操作实例

命令模式结构简单，思路清晰，那么我们就以`PeekPa`为例子。假设`PeekPa`前进的时候有两种状态，***正常走路***和***跳着走***，且两种方式都可以***前进***和***倒退***（*相当于undo操作*）。那么我们用命令模式来实现这个例子就很清晰。

抽象命令接口`ICommand`：
```JAVA
// 抽象命令，两个方法，go()和back()方法
public interface ICommand {
    int go(int type);
    int back(int type);
}
```
具体命令实施类`MoveCommand`：
```JAVA
// 命令具体类MoveCommand
public class MoveCommand implements ICommand {
    // 对哪些些Receiver进行操作
    private IMoveReceiver jumpMoveReceiver;
    private IMoveReceiver walkMoveReceiver;

    public void setJumpMoveReceiver(IMoveReceiver jumpMoveReceiver) {
        this.jumpMoveReceiver = jumpMoveReceiver;
    }

    public void setWalkMoveReceiver(IMoveReceiver walkMoveReceiver) {
        this.walkMoveReceiver = walkMoveReceiver;
    }

    // 具体的实现命令go()
    @Override
    public int go(int type) {
        if (type == 0) {
            return jumpMoveReceiver.forward();
        } else {
            return walkMoveReceiver.forward();
        }
    }

    // 具体的实现命令back()
    @Override
    public int back(int type) {
        if (type == 0) {
            return jumpMoveReceiver.backward();
        } else {
            return walkMoveReceiver.backward();
        }
    }
}
```
命令的接受者接口：
```JAVA
// 命令接受者IMoveReceiver
public interface IMoveReceiver {
    int forward();
    int backward();
}
```
具体命令的接受者，`JumpMoveReceiver`和`WalkMoveReceiver`：
```JAVA
// 具体的命令接受者JumpMoveReceiver
public class JumpMoveReceiver implements IMoveReceiver {
    private final int FORWARD_DISTANCE = 2;
    private final int BACKWARD_DISTANCE = 2;

    @Override
    public int forward() {
        System.out.println("Jump forward 2m.");
        return FORWARD_DISTANCE;
    }

    @Override
    public int backward() {
        System.out.println("Jump backward 2m.");
        return BACKWARD_DISTANCE;
    }
}

// 具体的命令接受者WalkMoveReceiver
public class WalkMoveReceiver implements IMoveReceiver {
    private final int FORWARD_DISTANCE = 1;
    private final int BACKWARD_DISTANCE = 1;

    @Override
    public int forward() {
        System.out.println("Walk forward 1m.");
        return FORWARD_DISTANCE;
    }

    @Override
    public int backward() {
        System.out.println("Walk backward 1m.");
        return BACKWARD_DISTANCE;
    }
}
```
下面是关键的`Invoker`：
```JAVA
// 命令的调用者PeekPaInvoker
public class PeekPaInvoker {
    // 通过一个List来记录操作，实现撤销操作
    private List<Integer> forwardList = new ArrayList<>();
    private ICommand moveCommand;
    private int distance = 0;

    public void setMoveCommand(ICommand command) {
        this.moveCommand = command;
    }
    
    // 前进命令执行
    public void go(int type){
        distance += moveCommand.go(type);
        forwardList.add(type);
        System.out.println("Distance: " + distance);
    }

    // 后退命令执行，也可以叫撤销操作
    public void back() {
        if (forwardList.size() > 0) {
            int type = forwardList.get(forwardList.size() - 1);
            forwardList.remove(forwardList.size() - 1);
            distance -= moveCommand.back(type);
        }
        System.out.println("Distance: " + distance);
    }
}
```
OK，命令模式到这里就布置完成，那么下面我们来看命令模式在客户端是如何被调用的：
```JAVA
    // 声明Invoker
    PeekPaInvoker peekPaInvoker = new PeekPaInvoker();
    // 声明命令
    MoveCommand peekPaCommand = new MoveCommand();
    peekPaInvoker.setMoveCommand(peekPaCommand);
    // 声明Receiver
    IMoveReceiver jumpReceiver = new JumpMoveReceiver();
    IMoveReceiver walkReceiver = new WalkMoveReceiver();
    peekPaCommand.setJumpMoveReceiver(jumpReceiver);
    peekPaCommand.setWalkMoveReceiver(walkReceiver);

    // 调用Invoker的方法
    peekPaInvoker.go(0); // Jump forward 2m.
                         // Distance: 2
    peekPaInvoker.go(1); // Walk forward 1m.
                         // Distance: 3
    peekPaInvoker.go(0); // Jump forward 2m.
                         // Distance: 5
    peekPaInvoker.go(1); // Walk forward 1m.
                         // Distance: 6
    peekPaInvoker.go(0); // Jump forward 2m.
                         // Distance: 8
    
    // 调用Invoker的撤销方法
    peekPaInvoker.back(); // Jump backward 2m.
                          // Distance: 6
    peekPaInvoker.back(); // Walk backward 1m.
                          // Distance: 5
    peekPaInvoker.back(); // Jump backward 2m.
                          // Distance: 3
    peekPaInvoker.back(); // Walk backward 1m.
                          // Distance: 2
    peekPaInvoker.back(); // Jump backward 2m.
                          // Distance: 0
```
代码是有点长，类是有点多，但是可以看到，最后调用的时候，还是很方便的。最后不但能够“前进”，还可以执行“撤销方法”倒退。可以看到，最后在调用的时候，好多依赖关系我们是通过***set***方法来设置的。这样在一定程度上*保证了开闭原则，即修改关闭，扩展开放*。使得类和类之间，尽量保持解耦。

### 总结一下
命令模式的***结构清晰，扩展性好***。可用之处很多，比如**请求日志，命令队列，struts 1 中的 action 核心控制器 ActionServlet 只有一个，相当于 Invoker，而模型层的类会随着不同的应用有不同的模型类，相当于具体的 Command**等。

`优点：`*降低了系统耦合度， 结构清晰，方便扩展，新的命令可以很容易添加到系统中去*。  
`缺点：`*使用命令模式可能会导致某些系统有过多的具体命令类*。


#### 推荐阅读：
[设计模式之零八：“排排坐吃果果的”责任链模式](https://mp.weixin.qq.com/s?__biz=MzI2ODYwNjE5NQ==&mid=2247483714&idx=1&sn=1e56583063bce25fa1dfcd536bb9cac1&chksm=eaec4ededd9bc7c8b1efa081e1a848db628eb0ffecae4e83b2d327cc1d698eb35909b15f0094&scene=0&key=9ba149027f2a1592126715f8521e35de8d8c98d7dbb035d235c296da3583b2f0b35697a921a908b869dab2a3c29515091f2c22f91a8ef1b00f5404de7890d789d677256325c921d8a735526db7d10619&ascene=0&uin=MjAyMDI0NzY2MA%3D%3D&devicetype=iMac+MacBookPro11%2C4+OSX+OSX+10.13.3+build(17D47)&version=12020610&nettype=WIFI&lang=ja&fontScale=100&pass_ticket=IPyVmJSiUz4CXg%2F6SaFbTf%2FbIfiJ7%2FEyWW1BhJKNEsk2T1c7BvdtawaxJWITHmVo)  
[设计模式之零九：“一是一二是二的”解释器模式](https://mp.weixin.qq.com/s?__biz=MzI2ODYwNjE5NQ==&mid=2247483724&idx=1&sn=e8a8ceaf1f9e9c17dc876a3991390a3e&chksm=eaec4ed0dd9bc7c6f40464c254ccaa5521dc1afaf2616c47c7728feccf896b9f5df4bb705e54#rd)

每天分享代码骚操作和养猫心得
![](https://mmbiz.qpic.cn/mmbiz_jpg/jA4Qc7C9IZS5CU8Eicxw9K4kIY8BibzDJX6QiahNQ0wDC2HLheXWp6CpITXBWcxt6E4SRlxHJyrxNO6v6TlKMgeUg/0?wx_fmt=jpeg)

