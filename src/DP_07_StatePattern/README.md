> 阅读本篇大概需要 9 分钟。  

首先，惯例，先说正事儿：
### 每日一皮克啪
瑜伽猫
![PeekPa](https://github.com/SwyftG/DesignPatternExample/blob/master/src/DP_07_StatePattern/img/07_peekpa.jpeg)

正事儿说完，咱们先来扯几句相关话题：
所有的设计模式源代码，我均已上传到了***GitHub***上，欢迎***Star***，么么哒：

*https://github.com/SwyftG/DesignPatternExample*

接下来，咱们来聊聊 **状态模式**。  
状态模式（`State Pattern`），这个模式和上一期的策略模式比较像。Wikipedia解释如下：  

“The **state pattern** is a **behavioral software design pattern** that implements a state machine in an object-oriented way. With the state pattern, a state machine is implemented by implementing each individual state as a derived class of the state pattern interface, and implementing state transitions by invoking methods defined by the pattern's superclass.”  

大致意思是：状态模式是一种行为设计模式。行为完全是由状态来决定的，不同的状态下有不同的行为。比如有些方法在状态A下面才有效，有些方法在状态B下面才有效。在状态模式里，一个实例对象通过调用不同具体状态类的接口，来实现方法的改变。

状态模式的UML图如下：

![uml](https://github.com/SwyftG/DesignPatternExample/blob/master/src/DP_07_StatePattern/img/state_pattern.jpg)  

可以看到这里面的结构和策略模式很像，
- **Context** 上下文，定义感兴趣的接口，维护一个State子类的实例
- **State** 抽象状态类或者接口
- **ConcreteState** 具体状态类

一般情况，当一个对象的***行为***决定了他的***状态***，而且运行时，***状态***会***改变行为***的时候；或者代码中含有大量与对象状态判断相关的语句，这种时候用状态模式比较好。这样做使得对象可以独立于他的状态变化。状态模式能够完美的解耦，是的结构清晰，易于扩展。

### 操作实例
既然状态模式和策略模式相似，那么我们还是以生活作息都很规律的`PeekPa`来做例子。

假设`PeekPa`有`高兴`的时候，和`不高兴`的时候。`高兴`的时候，`PeekPa`会满家`疯跑`，同时可以吃`很多猫粮`，`口渴`，会喝`很多水`，叫声也是“`嗷嗷`”的；当`PeekPa` `不高兴`的时候，就只会`睡觉`，不疯了，而且只吃`一点点猫粮`，喝`一点点`水。叫声没有，改成了`呼噜`。

在这个例子中，`PeekPa`就有两种状态，`高兴`和`不高兴`，行为方法总共有：在家里疯跑，吃饭，喝水和发出的声音。那么首先状态模式的结构代码如下。

抽象状态接口：
```JAVA
// 状态接口
public interface IModeState {
    // 叫声
    String voiceState();
    // 吃东西
    int eatState(int weight);
    // 运动状态
    String moveState();
    // 喝水
    int calculateWater(int foodWeight);
}
```
具体状态类`HappyMode`:
```JAVA
// 具体状态类HappyMode
public class HappyMode implements IModeState {
    private final String VOICE_STATE = "Ao~ Ao~ Ao~";
    private final String MOVE_STATE = "Mad";

    @Override
    public String voiceState() {
        return VOICE_STATE;
    }

    @Override
    public int eatState(int weight) {
        return weight;
    }

    @Override
    public String moveState() {
        return MOVE_STATE;
    }

    @Override
    public int calculateWater(int foodWeight) {
        // 吃30克猫粮要喝300ml水
        return (int)foodWeight * 300 / 30;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
```
具体状态类`DepressionMode`:
```JAVA
// 具体状态类DepressionMode
public class DepressionMode implements IModeState {
    private final String VOICE_STATE = "HuluHulu";
    private final String MOVE_STATE = "Sleep";
    @Override
    public String voiceState() {
        return VOICE_STATE;
    }

    @Override
    public int eatState(int weight) {
        return weight;
    }

    @Override
    public String moveState() {
        return MOVE_STATE;
    }

    @Override
    public int calculateWater(int foodWeight) {
        // 吃30克猫粮需要喝 100ml 水。
        return (int)foodWeight * 100 / 30;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
```
具体调用状态类`PeekPa`：
```JAVA
// 状态调用类PeekPa
public class PeekPa {
    private IModeState modeState;
    private IModeState happyMode;
    private IModeState depressionMode;
    private String voiceState;
    private String moveState;
    private List<Integer> drintWaterList;

    public PeekPa() {
        drintWaterList = new ArrayList<>();
        // 初始化状态类
        this.happyMode = new HappyMode();
        this.depressionMode = new DepressionMode();
        setMode(happyMode);
    }

    // 改变状态方法，高兴模式
    public void inHappyMode() {
        setMode(this.happyMode);
    }

    // 改变状态方法，消极模式
    public void inDepressionMode() {
        setMode(this.depressionMode);
    }

    private void setMode(IModeState mode) {
        this.modeState = mode;
    }

    // 调用状态类的 voice() 方法
    public void voice() {
        this.voiceState = this.modeState.voiceState();
        System.out.println("PeekPa's is In : " + this.modeState + ". Voice is : " + this.voiceState);
    }

    // 调用状态类的 eat() 方法
    public void eat(int weight) {
        int foodWeight = this.modeState.eatState(weight);
        int drinkWater = calculateWater(this.modeState, foodWeight);
        drintWaterList.add(drinkWater);
        System.out.println("PeekPa eat: " + foodWeight + ". Drink: " + drinkWater + "ml water.");
    }

    // 调用状态类的 calculateWater() 方法
    private int calculateWater(IModeState modeState, int foodWeight) {
        return modeState.calculateWater(foodWeight);
    }

    // 调用状态类的 moveState() 方法
    public void move() {
        this.moveState = this.modeState.moveState();
        System.out.println("PeekPa's is In : " + this.modeState + ". MoveState is : " + this.moveState);
    }

    public void showInfo() {
        int waterSum = 0;
        for (Integer water : drintWaterList) {
            waterSum += water;
        }
        System.out.println("Water sum is : " + waterSum + "ml.");
    }
}
```
好的，状态模式的代码我们已经构建完成，那么下一步我们就是来调用检测代码的运行结果了。
```JAVA
    // 初始化状态调用类
    PeekPa peekPa = new PeekPa();
    // PeekPa在设置成高兴的状态
    peekPa.inHappyMode();
    // PeekPa在高兴的状态下吃了20克猫粮
    peekPa.eat(20); // "PeekPa eat: 20. Drink: 200 ml water."
    // PeekPa在高兴的状态下叫声 
    peekPa.voice(); // "PeekPa's is In : HappyMode. Voice is : AoAoAo"
    // PeekPa在高兴状态下运动状态
    peekPa.move();
    // PeekPa设置成了悲伤的状态
    peekPa.inDepressionMode();
    // PeekPa在悲伤状态下的叫声
    peekPa.voice();
    // PeekPa在悲伤状态下的运动状态
    peekPa.move();
    // PeekPa在悲伤状态下吃了20克猫粮
    peekPa.eat(20);
    // PeekPa总共需要多少水
    peekPa.showInfo();
```
这里就可以看到，调用者`PeekPa`在不同状态下，调用同一个方法，会运行处不同的结果，这就是状态模式的实现。  

考虑一下状态模式的扩展性，如果我要添加一个新的Mode，比如`PeekPa`在每天早晨起床，有`起床气`的情况下，他的这些方法表现是什么样子的。那么我们只需要：***1. 新建一个具体状态类，实现接口里面的方法***; ***2.在状态调用者内部声明出这个状态类，并且写好切换状态的方法就可以了***。  

*状态模式*和*策略模式*最大的不同：策略模式的***策略是暴露给客户端的***，用什么策略，是客户端构建出来，并安插给使用类的；而状态模式，***所有的状态都是在状态调用类内部初始化声明的***，针对客户端，只暴露一个设置状态的方法而已。

### 总结一下
状态模式是将调用类的所有不同状态都放到自己的类中，然后在不同状态下，会有不同的行为。他和策略模式很像。  

`优点：` *结构简单，封装性好，易于扩展。各种状态不暴露给客户端，能够较好的保护数据*。   
`缺点：` *状态模式需要编写很多具状态类，会增加代码数量*。

#### 请大家关注一下我的公众号：**皮克啪的铲屎官**
![qr_code](https://github.com/SwyftG/DesignPatternExample/blob/master/src/img/qr_code.png)
#### 是一个日更的微信公众号，每天都有新知识，大家一起交流进步。

