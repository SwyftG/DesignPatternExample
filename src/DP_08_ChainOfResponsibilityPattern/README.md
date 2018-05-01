> 阅读本篇大概需要 12 分钟。  

首先，惯例，先说正事儿：
### 每日一皮克啪
这就是过 ***五一*** 的皮克啪↓↓↓↓↓
![五一的皮克啪](https://mmbiz.qpic.cn/mmbiz_jpg/jA4Qc7C9IZQ91ibo2uDuiaWFVCD1qtnFgV6icibUcuK76hJj9cV0bFVVgu6xiaX9Zu8kLficZrCtxI5JPnVk3aIDc88Q/0?wx_fmt=jpeg)



正事儿说完，咱们先来扯几句相关话题：
所有的设计模式源代码，我均已上传到了***GitHub***上，欢迎***Star***，么么哒：

*https://github.com/SwyftG/DesignPatternExample*

接下来，咱们来聊聊 **责任链模式**。  
责任链模式（`Chain-of-responsibility Pattern`）。Wikipedia解释如下：  

"The **chain-of-responsibility pattern** is a design pattern consisting of a source of command objects and a series of processing objects.Each processing object contains logic that defines the types of command objects that it can handle; the rest are passed to the next processing object in the chain. A mechanism also exists for adding new processing objects to the end of this chain."

大致意思是：责任链模是***由一系列处理事物的类组成的***。每一个具体处理事物的类，都有相同的处理方法，只不过他们之间是有***级别***之分的，当前处理不了，就会***交给下一个处理***，知道最后一个。

责任链模式，是有那种一个需要处理的方法，当责任链全部遍历之后，都处理不了的情况的。

##### 责任链的**UML**图如下：

![责任链UML](https://mmbiz.qpic.cn/mmbiz_jpg/jA4Qc7C9IZQ91ibo2uDuiaWFVCD1qtnFgVKo3icicl0NzzqztLZczFWnEAicKicK6rK3IjpB7cJjjPN5S2OE5ldlOy6w/0?wx_fmt=jpeg)

可以看到，这个里面主要包含这么几个角色：
- **Handler**：这个是处理者的抽象类，声明一个处理方法
- **Receiver**：是具体的处理类，他们实现共同的方法，如果当前对象不能处理，则会将请求交给下一个对象。

这么来看，其实责任链模式其实不难，就是一个抽象的处理类，定义处理方法的接口，然后所有具体处理类再依次连城一个链，尝试着挨个处理就好。但是这里需要注意：*链表避免首尾相连，造成死循环*。

### 操作实例
假设`PeekPa`是一只健康的蓝猫，他的消化食物的能力和吃进去食物的硬度有关系。比如：
- 喝*水*(这里姑且算食物)，硬度为`0.0`
- 吃*肉罐头*，硬度为`2.5`
- 吃*猫粮*，一粒一粒的，硬度为`5.0`
- 吃*风干牛肉干*，硬度为`9.9`

`PeekPa`的消化系统处理时间应该是这样的：
- 硬度`[0.0,  2.0)`之间，需要*4个小时*
- 硬度`[2.0, 6.0)`之间，需要*8个小时*
- 硬度`[6.0, 9.0)`之间，需要*12个小时*
- 硬度`[9.0, 10.0)`之间，需要*16个小时*

在这样的场景里，我们完全可以使用责任链模式来编写代码。

首先来先建立吃的：
```JAVA
// Food抽象基类
abstract class Food {
    // 食物的硬度
    public double solidity;
    // 食物的名字
    public String name;

    public double getSolidity() {
        return solidity;
    }
}

// 具体食物--水，硬度--0.0
public class Water extends Food{
    private final Double WATER_SOLIDITY = 0.0;

    public Water() {
        this.name = this.getClass().getSimpleName();
        this.solidity = WATER_SOLIDITY;
    }
}

// 具体食物--猫罐头，硬度--2.5
public class CatCan extends Food{
    private final Double MEETCAN_SOLIDITY = 2.5;

    public CatCan() {
        this.name = this.getClass().getSimpleName();
        this.solidity = MEETCAN_SOLIDITY;
    }
}

// 具体食物--猫粮，硬度--5.0
public class CatFood extends Food{
    private final Double CATFOOD_SOLIDITY = 5.0;

    public CatFood() {
        this.name = this.getClass().getSimpleName();
        this.solidity = CATFOOD_SOLIDITY;
    }
}

// 具体食物--风干牛肉干，硬度--9.9
public class BeefJerky extends Food{
    private final Double BEEFJERKY_SOLIDITY = 9.9;

    public BeefJerky() {
        this.name = this.getClass().getSimpleName();
        this.solidity = BEEFJERKY_SOLIDITY;
    }
}

// 具体食物--砖头，硬度--100.0
// 这个是用来检测责任链不能处理的话，会是什么情况
public class Brick extends Food{
    private final Double BRICK_SOLIDITY = 100.0;

    public Brick() {
        this.name = this.getClass().getSimpleName();
        this.solidity = BRICK_SOLIDITY;
    }
}
```
吃的弄完，下面我们来新建一下`PeekPa`的消化处理类：
处理事物的基类`DigestionTime`：
```JAVA
abstract class DigestionTime {
    // 下一个处理事物的处理类
    protected DigestionTime nextHandler;

    protected abstract double getSoftHandleSolidity();
    protected abstract double getHardHandleSolidity();
    // 计算消化时间
    protected abstract void calculateTime(Food food);
    // *关键方法*
    // 当前节点理不了的时候，会交给责任链下一个节点处理
    public final void handleFood(Food food){
        if (food.getSolidity() >= getSoftHandleSolidity() && food.getSolidity() < getHardHandleSolidity()) {
            this.calculateTime(food);
        } else {
            if (nextHandler != null) {
                nextHandler.handleFood(food);
            } else {
                System.out.println("PeekPa can't digest: " + food.name);
            }
        }
    }
}
```
下面是责任链处理类的具体类：
```JAVA
// Part I，消化[0.0, 2.0)的食物，需要 4 个小时
public class PartI extends DigestionTime{
    private final int PART_I_DIGEST_TIME = 4;
    @Override
    protected double getSoftHandleSolidity() {
        return 0.0;
    }

    @Override
    protected double getHardHandleSolidity() {
        return 2.0;
    }

    @Override
    protected void calculateTime(Food food) {
        System.out.println("This is PartI\n PeekPa needs: " + PART_I_DIGEST_TIME + " hours to digestive " + food.name);
    }
}

// Part II，消化[2.0, 5.0)的食物，需要 8 个小时
public class PartII extends DigestionTime{
    private final int PART_II_DIGEST_TIME = 8;
    @Override
    protected double getSoftHandleSolidity() {
        return 2.0;
    }

    @Override
    protected double getHardHandleSolidity() {
        return 5.0;
    }

    @Override
    protected void calculateTime(Food food) {
        System.out.println("This is PartII\n PeekPa needs: " + PART_II_DIGEST_TIME + " hours to digestive " + food.name);
    }
}

// Part III，消化[5.0, 9.0)的食物，需要 12 个小时
public class PartIII extends DigestionTime{
    private final int PART_III_DIGEST_TIME = 12;
    @Override
    protected double getSoftHandleSolidity() {
        return 5.0;
    }

    @Override
    protected double getHardHandleSolidity() {
        return 9.0;
    }

    @Override
    protected void calculateTime(Food food) {
        System.out.println("This is PartIII\n PeekPa needs: " + PART_III_DIGEST_TIME + " hours to digestive " + food.name);
    }
}

// Part IV，消化[9.0, 10.0)的食物，需要 16 个小时
public class PartIV extends DigestionTime{
    private final int PART_IV_DIGEST_TIME = 16;
    @Override
    protected double getSoftHandleSolidity() {
        return 9.0;
    }

    @Override
    protected double getHardHandleSolidity() {
        return 10.0;
    }

    @Override
    protected void calculateTime(Food food) {
        System.out.println("This is PartIV\n PeekPa needs: " + PART_IV_DIGEST_TIME + " hours to digestive " + food.name);
    }
}
```
万事具备，下面我们来封一下PeekPa消化的“责任链” `PeekPaDigestiveSystem`：  

```JAVA
public class PeekPaDigestiveSystem {
    // 责任链中的四个处理节点
    private DigestionTime partI;
    private DigestionTime partII;
    private DigestionTime partIII;
    private DigestionTime partIV;

    public PeekPaDigestiveSystem() {
        partI = new PartI();
        partII = new PartII();
        partIII = new PartIII();
        partIV = new PartIV();

        // 建立节点之间的关系
        partI.nextHandler = partII;
        partII.nextHandler = partIII;
        partIII.nextHandler = partIV;
    }

    public void calculateDigestiveTime(Food food){
        partI.handleFood(food);
    }
}
```

OK，现在我们来实践一下，看看我们的责任链好不好用：

```JAVA
    // 声明食物
    Food water = new Water();
    Food catCan = new CatCan();
    Food catFood = new CatFood();
    Food beefJerky = new BeefJerky();
    Food brick = new Brick();
    // 声明责任链
    PeekPaDigestiveSystem system = new PeekPaDigestiveSystem();

    // 调用处理方法
    system.calculateDigestiveTime(water); // "This is PartI"
                                          // "PeekPa needs: 4 hours to digestive Water"
    system.calculateDigestiveTime(catCan); // "This is PartII"
                                           // "PeekPa needs: 8 hours to digestive CatCan"
    system.calculateDigestiveTime(catFood); // "This is PartIII"
                                            // "PeekPa needs: 12 hours to digestive CatFood"
    system.calculateDigestiveTime(beefJerky); // "This is PartIV"
                                              // "PeekPa needs: 16 hours to digestive BeefJerky"
    system.calculateDigestiveTime(brick); // "PeekPa can't digest: Brick"
```
我们看到，这个计算结果没有问题，当一个食物送到责任链里时，一个一个的节点按照顺序来处理事物。

责任链模式可以应用在：*Log系统，JAVA WEB 中 Apache Tomcat 对 Encoding 的处理，Struts2 的拦截器，jsp servlet 的 Filter等*。

### 总结一下
责任链比较简单，将具体处理类通过单链表的形式串联起来。但是由于他的结构比较特殊，在递归调用的时候，需要格外注意。

`优点：` *请求者和处理者解耦，结构清晰，代码灵活*。  
`缺点：` *处理类特别多，当一个请求复杂的时候，需要遍历责任链，影响性能*。

#### 请大家关注一下我的公众号：**皮克啪的铲屎官**
![qr_code](https://github.com/SwyftG/DesignPatternExample/blob/master/src/img/qr_code.png)
#### 是一个日更的微信公众号，每天都有新知识，大家一起交流进步。

