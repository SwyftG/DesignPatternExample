> 阅读本篇大概需要 7 分钟。  

首先，惯例，先说正事儿：
### 每日一皮克啪
皮克啪最近主打***瘦身长图***题材，各种高挑身材一览无余。皮克啪身体展开是防盗门的宽度。


正事儿说完，咱们来聊聊**抽象工厂模式**。  
抽象工厂模式，应该是算是工厂模式里面的一种，是一种创造类型的模式。Wikipedia解释如下：  
“The **abstract factory pattern** provides a way to encapsulate a group of individual factories that have a common theme without specifying their concrete classes.”  
大致意思就是，抽象工厂模式为***创建一组相关或者是相互依赖的对象提供一个接口，而不是指定他们的具体类***。  
所以，一般来说，抽象工厂模式，会有多个具体的工厂类，而且每个工厂类负责产生自己对应的产品。这些具体工厂类都是继承自一个抽象工厂类，而且他们的生产的产品，也是继承自一个抽象产品类。  
抽象工厂的UML图如下：  

【image】 

可以看到，抽象工厂模式还是那四个角色，***抽象工厂***，***具体工厂***，***抽象产品***，***具体产品***。只不过，这里具体工厂不在是只有一个，而是可以有**好几个**，工厂类里面的创造方法应该是包含**所有产品**的。是哪个产品的创建工厂，就实现对应产品的创造方法就可以。

一般来说，抽象工厂方法使用场景不是很多，当出现一个对象族有相同的约束时，可以使用抽象工厂模式。

### 操作实例
假设，`PeekPa`有个堂妹，叫`PeekTata`，`PeekTata`的妈妈是`PeekPa`的姑姑，叫`PeekAunt`，那么我们在算上`PeekMom`，就有四个类，归整一下，`PeekPa`和`PeekTata`算是产品，而`PeekAunt`和`PeekMom`生了两个小崽子，所以，这两个应该算是工厂。产品的抽象类叫`IPeekCat`，工厂的抽象类叫`IPeekFactory`。那么利用抽象工厂模式，他们的结构大致如下：  

抽象工厂类：
```JAVA
// 抽象工厂类
public abstract class IPeekFactory {
    // 生产PeekPa的抽象方法
    abstract IPeekCat bornPeekPa();
    // 生产PeekTata的抽象方法
    abstract IPeekCat bornPeekTata();
}
```
具体工厂类***PeekMom***:
```JAVA
// 负责生产PeekPa的具体工厂类
public class PeekMom extends IPeekFactory {
    public String name;

    public PeekMom() {
        this.name = this.getClass().getSimpleName();
    }
    // PeekMom只负责生产PeekPa，所以这里只实现ornPeekPa()
    @Override
    IPeekCat bornPeekPa() {
        IPeekCat peekPa = new PeekPa(this.name);
        // 调用产品内部方法
        peekPa.init();
        return peekPa;
    }
    // 空实现
    @Override
    IPeekCat bornPeekTata() {
        return null;
    }
}
```
具体工厂类***PeekAunt***:
```JAVA
// 负责生产PeekTata的具体工厂类
public class PeekAunt extends IPeekFactory {
    public String name;

    public PeekAunt() {
        this.name = this.getClass().getSimpleName();
    }
    // 空实现
    @Override
    IPeekCat bornPeekPa() {
        return null;
    }
    // PeekAunt只负责生产PeekTata，所以这里只实现bornPeekTata()方法
    @Override
    IPeekCat bornPeekTata() {
        IPeekCat peekTata = new PeekTata(this.name);
        // 调用产品内部方法
        peekTata.init();
        return peekTata;
    }
}
```
接下来是产品类别，产品抽象类***IPeekCat***：
```JAVA
// 抽象产品类IPeekCat
abstract class IPeekCat {
    public String name;
    public int gender;
    public String mother;
    abstract void init();
    abstract void showInfo();
}
```
下面是具体产品类***PeekPa***：
```JAVA
// 具体产品类PeekPa
public class PeekPa extends IPeekCat {

    public PeekPa(String mother) {
        this.mother = mother;
    }

    @Override
    void init() {
        this.name = this.getClass().getSimpleName();
        this.gender = 1;
    }

    @Override
    void showInfo() {
        System.out.println("This is: " + this.name + "\ngender: " + (this.gender == 0 ? "Female" : "Male") + "\nMother is: " + this.mother);
    }
}

```
下面是具体产品类***PeekTata***：
```JAVA
// 具体产品类PeekTata
public class PeekTata extends IPeekCat {

    public PeekTata(String mother) {
        this.mother = mother;
    }

    @Override
    void init() {
        this.name = this.getClass().getSimpleName();
        this.gender = 0;
    }

    @Override
    void showInfo() {
        System.out.println("This is: " + this.name + "\ngender: " +  (this.gender == 0 ? "Female" : "Male") + "\nMother is: " + this.mother);
    }
}

```
啊，这项我们就有了几个类，抽象工厂类`IPeekFactory`, 具体工厂类`PeekMom`和`PeekAunt`，抽象产品类`IPeekCat`，具体两个产品`PeekPa`和`PeekTata`。那么我们实战调用工厂方法，创建两个对象，并且检验调用结果的代码如下：
```JAVA
    // 创建具体工厂对象
    IPeekFactory peekMom = new PeekMom();
    IPeekFactory peekAunt = new PeekAunt();
    // 调用工厂对象，来创建产品实例
    IPeekCat peekPa = peekMom.bornPeekPa();
    IPeekCat peekTata = peekAunt.bornPeekTata();
    // 调用内部方法验证
    peekPa.showInfo(); // This is: PeekPa
                       // gender: Male 
                       // Mother is: PeekMom
    peekTata.showInfo(); // This is: PeekTata
                         // gender: Female
                         // Mother is: PeekAunt
```
看到打印出来的结果，没有问题，那么我们看到，客户端只是简单的调用工厂的方法，就可以拿到产品的实例对象。而且客户端是看不到产品的构建方法的，耦合性低。
### 总结一下
抽象工厂模式是一种很方便，结构清晰的设计模式。有好处也有坏处。  

`优点：`结构简单清晰，对问题高度抽象，当一个产品族中的多个对象被设计成一起工作时，它能保证客户端始终只使用同一个产品族中的对象。  

`缺点：`由于有很多具体工厂类的存在，使得项目的类的数量会过于庞大，扩展性不是很好，如果要添加新的产品，需要改动所有工厂类。  

所以，使用起来，依情况而定。***啦啦啦啦啦啦~***