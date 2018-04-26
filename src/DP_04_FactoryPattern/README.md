> 阅读本篇大概需要 15 分钟。  

首先，惯例，先说正事儿：
### 每日一皮克啪
今日更新皮克啪高清无码大图，绝对震撼！！！
![peekpa](https://github.com/SwyftG/DesignPatternExample/blob/dev/src/DP_04_FactoryPattern/img/04_peekpa.jpeg)

正事儿说完，咱们来聊聊工厂模式。  
工厂模式，应该算是应用最广的模式之一，Wikipedia解释如下：  

"The **factory method** pattern is a creational pattern that uses factory methods to deal with the problem of creating objects without having to specify the exact class of the object that will be created. This is done by creating objects by calling a factory method—*either specified in an interface and implemented by child classes, or implemented in a base class and optionally overridden by derived classes*—rather than by calling a constructor. "  

大概意思是，工厂模式是一种**创造类型**的模式，他是通过一个工厂方法来创建特定对象，这样的做法就免去了直接调用特定对象的构造函数创建。*一般是通过定义一个接口，并且实现这个接口的方法，或者是重写父类的一个方法，来实现一个工厂方法，创建对象*，而不是直接调用对象的构造方法来创建对象。

工厂模式的UML图如下：

![image](https://github.com/SwyftG/DesignPatternExample/blob/dev/src/DP_04_FactoryPattern/img/factory_patter.jpg)

这几个有几个角色：
- **抽象工厂**，是工厂方法模式核心，里面定义工厂类的实现方法。
- **具体工厂**，实现具体的业务逻辑。
- **抽象产品**，是工厂方法所创建的产品的父类。
- **具体产品**，实现抽象产品的具体产品的对象。  

所以工厂模式中，我们在创建对象时**不会对客户端暴露创建逻辑**，并且是通过**使用一个共同的接口**来指向新创建的对象。

### 操作实例
实例场景是这样的：假设PeekPa的妈妈`PeekMom`，生了两个~~小猫崽子~~（不对，是**主子**，自我掌嘴），`PeekPa`，和他的哥哥`PeekBa`。那么可以抽象成PeekMom就是这里的工厂类，PeekPa和PeekBa是具体的产品类。这里定义抽象工厂类叫`PeekFactory`,抽象产品叫`IPeekCat`。那么他们的结构大致如下：  

产品抽象类：
```JAVA
//产品抽象类
public abstract class IPeekCat {
    public String name;
    public abstract void init();
    public abstract void showInfo();
}
```
具体产品类PeekBa：
```JAVA
// 具体产品类：PeekBa
public class PeekBa extends IPeekCat {

    private String FISH = "fish";

    public PeekBa() {
        this.name = PeekBa.class.getSimpleName();
    }

    @Override
    public void init() {
        this.favouriteFood = FISH;
    }

    @Override
    public void showInfo() {
        System.out.println("This is: " + this.name + " Favourite: " + this.favouriteFood);
    }
}
```
具体产品类PeekPa：
```JAVA
// 具体产品类：PeekPa
public class PeekPa extends IPeekCat {

    private String BEEF = "beef";

    public PeekPa() {
        this.name = PeekPa.class.getSimpleName();
    }

    @Override
    public void init() {
        this.favouriteFood = BEEF;
    }

    @Override
    public void showInfo() {
        System.out.println("This is: " + this.name + " Favourite: " + this.favouriteFood);
    }
}
```

接下来我们来说工厂的代码，这里就有一些玄机了。  
工厂模式分为***简单工厂***，***多工厂模式***，以及***抽象工厂***（抽象工厂下次更新）。既然都到这步了，分叉了，所以咱们就简单拾到拾到这几个东西。  

#### 简单工厂  
简单工厂一般有一个抽象工厂类，里面有抽象工厂方法，然后一个具体的工厂类去实现这个抽象方法。创建不同对象，通过传入的参数来去返回不同的对象。  

传入参数的抽象工厂类：
```JAVA
// 抽象工厂类
public abstract class PeekFactory {
    // 传入参数创建对象
    public abstract IPeekCat bornPeekCat(String name);
}
```
具体工厂类PeekMom（`通过传入参数来新建对象`）：
```JAVA
// 具体工厂实现类PeekMom
public class PeekMom extends PeekFactory{
    public static final String PEEKPA = "PeekPa";
    public static final String PEEKBA = "PeekBa";
    
    // 通过传入参数，来区分新建对象的种类
    @Override
    public IPeekCat bornPeekCat(String name) {
        switch (name) {
            case PEEKPA:
                IPeekCat peekPa = new PeekPa();
                // 这里可以调用产品类的的内部方法
                peekPa.init();
                return peekPa;
            case PEEKBA:
                IPeekCat peekBa = new PeekBa();
                peekBa.init();
                return peekBa;
            default:
                return null;
        }
    }
}
```
下面是外部函数调用验证：
```JAVA
    // 工厂类
    PeekFactory peekMom = new PeekMom();
    // 通过 *传入不同参数* 工厂方法来创建对象
    IPeekCat peekPa = peekMom.bornPeekCat(PeekMom.PEEKPA);
        IPeekCat peekBa = peekMom.bornPeekCat(PeekMom.PEEKBA);
    // 检测创建对象结果
    peekPa.showInfo(); // "This is: PeekPa Favourite: beef"
    peekBa.showInfo(); // "This is: PeekBa Favourite: fish"
```
可以看到上面的输出结果，和我们预期的一样。我们通过传入**不同的参数**，调用Factory的`bornPeekCat()`方法来创建不同的产品类。

上面的工厂具体实现类是`通过传入参数`，来做判断，这样的实现很简单，但是*扩展性比较差*，如果需要添加一个新的产品的话，需要改动工厂类代码。  

还有一种工厂类的实现，是通过**JAVA的反射机制**来创建类的。通过传入正确的类名，来创建相应的对象。  

抽象工厂类（`传入ClassName`）:
```JAVA
// 抽象工厂类
public abstract class PeekFactory {
    // 传入ClassName创建对象
    public abstract <T extends IPeekCat> T bornPeekCat(Class<T> clz);
}
```
具体工厂类PeekMom（`通过传入ClassName来新建对象`）：
```JAVA
// 工厂具体实现类
public class PeekMom extends PeekFactory{
    // 通过传入ClassName创建对象
    @Override
    public <T extends IPeekCat> T bornPeekCat(Class<T> clz) {
        IPeekCat result = null;
        try {
            result = (IPeekCat) Class.forName(clz.getName()).newInstance();
            // 此处可以调用产品类的内部方法
            result.init();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return (T)result;
    }
}

```
外部调用的时候，验证代码如下：
```JAVA
    // 工厂类
    PeekFactory peekMom = new PeekMom();
    // 通过传入 *ClassName* 来创建对象
    IPeekCat peekPa = peekMom.bornPeekCat(PeekPa.class);
    IPeekCat peekBa = peekMom.bornPeekCat(PeekBa.class);
    // 检测创建对象结果
    peekPa.showInfo(); // "This is: PeekPa Favourite: beef"
    peekBa.showInfo(); // "This is: PeekBa Favourite: fish"
```
看到上面打印的结果和传入参数创建对象的方法结果是一样的。耶耶耶耶耶耶耶，这样就可以利用***Java的泛型，传入ClassName，通过反射方法***来创建对象。需要哪个类，就传入那个类，简介，方便，动态。

这样的`好处`是，**如果新添加新的产品，不太需要修改工厂类的代码**，但是也有`缺点`，就是**Java反射的效率问题**。

以上两种工厂方法的实现，是简单工厂的例子，还有一种工厂方法是：**多工厂模式**。阅读到这里，这个多工厂模式就很简单了，其实就是把`PeekMom`拆成两个，一个是`PeekPaMon`，另一个是`PeekBaMom`，针对一些特定的产品群，来建立一个具体工厂类来创建产品。  

抽象工厂类（`多工厂方法`）：
```JAVA
// 抽象工厂类
public abstract class PeekFactory {
    // 多工厂方法
    public abstract IPeekCat bornPeekCat();
}

```
具体工厂类PeekBaMom：
```JAVA
// 具体工厂类PeekBaMom
public class PeekBaMom extends PeekFactory{
    @Override
    public IPeekCat bornPeekCat() {
        IPeekCat peekBa = new PeekBa();
        // 可以调用创造类的方法
        peekBa.init();
        return peekBa;
    }
}
```
具体工厂类PeekPaMom：
```JAVA
//具体工厂类PeekPaMom
public class PeekPaMom extends PeekFactory {
    @Override
    public IPeekCat bornPeekCat() {
        IPeekCat peekPa = new PeekPa();
        // 可以调用创造类的方法
        peekPa.init();
        return peekPa;
    }
}
```
外部创建实例代码，以及验证方法:
```JAVA
    // 创建多工厂实例
    PeekFactory peekBaMom = new PeekBaMom();
    PeekFactory peekPaMom = new PeekPaMom();
    // 创建产品实例
    IPeekCat peekBa = peekBaMom.bornPeekCat();
    IPeekCat peekPa = peekPaMom.bornPeekCat();
    // 检测创建对象结果
    peekPa.showInfo(); // "This is: PeekPa Favourite: beef"
    peekBa.showInfo(); // "This is: PeekBa Favourite: fish"
```
看到`showInfo()`出来的信息是完全一致的。说明此方法也是可行的。  

那这种多工厂模式的`优点`，***简洁明了***，可以针对每一个产品，工厂内部可以添加一些定制的元素。`缺点`，***由于工厂体系过于多，会是的代码结构过于庞大***，若是要添加一个新的不同类型的产品，还需要编写与其对应的具体工厂类。

### 总结一下
工厂模式是最常见的模式，他总共有四个模块：
- **抽象工厂类**
- **具体工厂类**
- **抽象产品类**
- **具体产品类**

实现起来，也有***简单工厂***，***多工厂***和***抽象工厂***（下期）这几种方式。  
- **简单工厂**
   - 通过给具体工厂类传入**参数**来创建具体产品实例
   - 通过给具体工厂类传入**产品类的ClassName**来创建产品类
- **多工厂**
   - 针对每一个或者几个产品创建一个具体工厂类，从而会产生很多具体工厂类。
 
总体而言，工厂模式是一种创造的模式，他将系统解耦，通过封装，是的对象类的创建方法不会暴露类客户端，客户端只需要调用统一接口就可以创建实例，很方便。  

最后闲扯几句，通过这几天的学习和整理，我发现，写文章是一个很好的过程，而且，我发现我这几篇文章，越写越多。但是感谢你能读到这里。***非常感谢你的支持，我会继续努力滴。***

#### 请大家关注一下我的公众号：**皮克啪的铲屎官**
![qr_code](https://github.com/SwyftG/DesignPatternExample/blob/dev/src/img/qr_code.png)
#### 是一个日更的微信公众号，每天都有新知识，大家一起交流进步。  



