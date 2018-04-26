> 阅读本篇大概需要 9 分钟。  

首先，惯例，先说正事儿：
### 每日一皮克啪
今日展示的是皮克啪的美腿，你看着消魂的姿势，唉呀妈呀~
![peekpa](https://github.com/SwyftG/DesignPatternExample/blob/dev/src/DP_06_StrategyPattern/img/06_peekpa.jpeg)

正事儿说完，咱们先来扯几句相关话题：
所有的设计模式源代码，我均已上传到了***GitHub***上，欢迎***Star***，么么哒：

*https://github.com/SwyftG/DesignPatternExample*

接下来，咱们来聊聊**策略模式**。  
策略模式（`Strategy Pattern`），其实是一种挺有意思的模式。Wikipedia解释如下：  
“The **strategy pattern** (also known as the policy pattern) is a behavioral software design pattern that enables selecting an algorithm at runtime. Instead of implementing a single algorithm directly, code receives run-time instructions as to which in a family of algorithms to use.”  

大致意思是，策略模式是一种***行为型模式***。他的工作方式是***使得一个类的行为或其算法可以在运行时更改***。相比于单一的实现一种运算方法，这种策略模式会使得结构更加灵活。

策略模式的UML图如下：
  
![image](https://github.com/SwyftG/DesignPatternExample/blob/dev/src/DP_06_StrategyPattern/img/strategy_pattern.jpg)

这个图里，有这么几个部分：
- **Context** 用来操作策略的上下文环境
- **Strategy** 策略的接口
- **Strategy** 策略的具体实现类

看到Context***不负责***实现算法，而是将算法的具体实现抽成了具体的算法类来做。这样，Context通过调用Strategy接口，使用不同的策略算法，就能够得到不同的结果。可以说这种做法完美的符合OOP的“***开闭原则***”（Open/close principle，即：对于扩展是开放的，对于修改是关闭的）。从而用起来非常舒服，结构简单易懂。


### 操作实例
策略模式的一般使用场景是，当一个***系统有许多许多种情况，我们将每一种情况的算法封装成一个类，然后在这个系统里，只要让他在处理事务的时候，调用不同的算法就可以了***。这样可以避免出现大量if..else的场景。  

我们还是以我们的主子来作为例子。  

假设，`PeekPa`的生活很有规律，我们就以计算`PeekPa`每天喝水来作为我们研究的问题。这里发现，`PeekPa`是一只很有规律的猫，喝水的量和吃猫粮的多少有关。我们以每6个小时为一个单位时间，这里我们发现，一种情况是：在睡觉6个小时的时间里，如果吃`30克`猫粮，就会喝掉`100毫升`的水；另一种情况则是：在家里活动6个小时里，每吃掉`30克`猫粮，就会喝掉`300毫升`水。   

那么这里我们就产生出两种算法，**30克猫粮需要100毫升**和**30克猫粮消耗300毫升**。这种情况完全可以用策略模式来写。  

我们需要计算出`PeekPa`一天的饮水量，如果按照策略模式，我们的代码大致应该如下：

抽象策略接口：
```JAVA
// 策略抽象接口
public interface ICalculateWater {
    int calculateWater(int wight);
}

```
具体策略类`SleepMode`:
```JAVA
// 策略的具体实现类SleepMode
public class SleepMode implements ICalculateWater {
    @Override
    public int calculateWater(int wight) {
        // 吃30克猫粮需要100ml水
        return (int)wight * 100 / 30;
    }
}
```
具体策略类`ExerciseMode`:
```JAVA
// 策略的具体实现类ExerciseMode
public class ExeciseMode implements ICalculateWater {
    @Override
    public int calculateWater(int wight) {
        // 吃30克猫粮需要300ml水
        return (int)wight * 300 / 30;
    }
}
```
策略具体的调用者`PeekPa`:
```JAVA
// 策略的具体调用类
public class PeekPa {
    private ICalculateWater strategy;
    private String date;
    private List<Integer> waters;

    public PeekPa(String date) {
        waters = new ArrayList<>();
        this.date = date;
    }

    public void addWater(int weight, ICalculateWater mode){
        this.strategy = mode;
        // 调用不同策略来获得不同结果
        waters.add(this.strategy.calculateWater(weight));
    }

    public void printSumWater() {
        int sumWater = 0;
        for (Integer item : waters) {
            sumWater += item;
        }
        System.out.println("PeekPa needs " + sumWater + " ml water on " + this.date + ".");
    }
}
```
好了，代码结构已经完成，那么我们需要计算`PeekPa`在周一和周日两天各需要多少水。星期一`PeekPa`的作息情是：**睡觉6小时，吃了克猫粮**；**运动6小时，吃了克猫粮**；**运动6小时，吃了克猫粮**；**睡觉6小时，吃了克猫粮**。由于星期六`PeekPa`玩的着凉了，所以星期天`PeekPa`睡了一天，作息情况是：**睡觉6个小时，吃了克猫粮**；**睡觉6个小时，吃了克猫粮**；**睡觉6个小时，吃了克猫粮**；**睡觉6个小时，吃了克猫粮**。那么我们的代码就应该写成一下样子：
```JAVA
    // 创建策略
    ICalculateWater sleepMode = new SleepMode();
    ICalculateWater execiseMode = new ExeciseMode();

    // 创建策略的调用类，并且传入不同策略和不同数值
    PeekPa mondayPeekPa = new PeekPa("Monday");
    mondayPeekPa.addWater(50, sleepMode);
    mondayPeekPa.addWater(100, execiseMode);
    mondayPeekPa.addWater(150, execiseMode);
    mondayPeekPa.addWater(300, sleepMode);

    PeekPa sundayPeekPa = new PeekPa("Sunday");
    sundayPeekPa.addWater(30, sleepMode);
    sundayPeekPa.addWater(20, sleepMode);
    sundayPeekPa.addWater(10, sleepMode);
    sundayPeekPa.addWater(50, sleepMode);
    
    //检验结果
    mondayPeekPa.printSumWater(); // "PeekPa needs: 3666 ml water on Monday."
    sundayPeekPa.printSumWater(); // "PeekPa needs: 365 ml water on Sunday."
```
可以看到，最后打印出了结果。结果正确。这样的代码模式，使得扩展性非常好，比如，我们现在再添加一个模式，`PeekPa`在在孤独的想念他的铲屎官的6个小时里，吃`10克`猫粮只会喝`10毫升`水，那这种新的模式，我只需要实现抽象策略接口，然后就可以直接提供给策略调用者（`PeekPa`）使用了。

### 总结一下
策略模式主要用来***分离算法***，在相同行为抽象下有***不同具体的实现策略类***，通过***调用***不同的策略来实现方法。这种模式完全符合“开闭原则”，结构简单，定义抽象，注入不同的实现，易于扩展。在Android中，动画的插值器(Interpolator)就是运用这种模式。  

`优点：` *结构清晰，使用简单明了*；*耦合度低，易于扩展*；*封装彻底，数据更为安全*。  
`缺点：` *所有的策略都必须暴露出来，策略类也略多*。  

今天给`PeekPa`买了新的*饮水机*，**效果很不错**。这样`PeekPa`就不会再渴了，主子水管够，我这下又有新的猫砂得铲了。干活好有动力，感觉胸前的红领巾更加***鲜艳***了。我去翻翻猫砂。。。。
