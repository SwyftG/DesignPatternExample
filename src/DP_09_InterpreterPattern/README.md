> 阅读本篇大概需要 10 分钟。  

首先，惯例，先说正事儿：
### 每日一皮克啪
今儿给洗了澡，结果一脸嫌弃猫

![每日皮克啪](https://mmbiz.qpic.cn/mmbiz_jpg/jA4Qc7C9IZS5CU8Eicxw9K4kIY8BibzDJXhyUlK05mKWibGSs6l3ibstDcias1Tq6DvtwvMSHSI4gII1ahy8Wv4tWicw/0?wx_fmt=jpeg)

正事儿说完，咱们先来扯几句相关话题：
所有的设计模式源代码，我均已上传到了***GitHub***上，欢迎***Star***，么么哒：

*https://github.com/SwyftG/DesignPatternExample*

接下来，咱们来聊聊 **解释器模式**。  
责任链模式（`Interpreter Pattern`）。Wikipedia解释如下：

"The **interpreter pattern** is a design pattern that specifies how to evaluate sentences in a language. The basic idea is to have a class for each symbol (terminal or nonterminal) in a specialized computer language. "  

大致意思是：解析器模式一般情况是***用来解释语言的语法或者表达式的***。最基本的实现就是为每一种符号（*终结符和非终结符*）编写一种类，然后解析他们。

#### 那么解析器模式的UML图如下：

![解析器UML](https://mmbiz.qpic.cn/mmbiz_jpg/jA4Qc7C9IZS5CU8Eicxw9K4kIY8BibzDJXIVZicSGDR1ksWic05QYQIIKc5bABbia4XIia0eIyJ169QlVg9hEAm7g6CA/0?wx_fmt=jpeg)

可以看到这里面有几部分：
- **AbstractExpression**: 抽象表达类，声明一个抽象的解释操作父类，定义抽象解释方法。
- **TerminalExpression**：终结符表达类，实现文法中的与终结符相关的解释操作。
- **NoterminalExpression**: 非终结符表达类，实现文法中与非终结符相关的解释操作。

可能到这里，大家还是有很多疑惑，这到底是***啥玩意儿***，什么非终结符终结符的。莫慌，目前这种懵逼的状态，是正常的。因为，解析器模式的应用场景并不多，而且平时很少能够见到这种使用，再加上本身概念就有些抽象，晕晕乎乎是正常的。且听我慢慢拨云见日。

解析器模式的应用场景相当广，只不过我们见到的不多而已，*一般可以用来对一些固定文法构建一个解释句子的解释器。使用方法就是通过构建语法树，定义终结符和非终结符，来解析语句就可以*。

### 操作实例
既然解析器模式是用来解析语法的，那么我们可以通过这种模式，来解析`PeekPa`的猫叫。

假设`PeekPa`有两种叫声，`Ao` 和 `Miao`。`Ao` 和 `Miao` 是可以连着叫的，通过 `+` 号连接。`PeekPa`每次连续叫四次，通过 `Ao` 和 `Miao` 的个数来区分PeekPa到底想要干什么：
 - 四个 `Ao` 表示：生气
 - 四个 `Miao` 表示：高兴
 - 一个 `Ao` 三个 `Miao` 表示：要吃饭
 - 其余情况：未知，还未破解
 
那么这种情形之下，我们就可以利用解析器模式来解读`PeekPa`的用意了。
首先构建抽象表达类`AbstractExpression`：
```JAVA
// 抽象表达类AbstractExpression
abstract class AbstractExpression {
    public abstract String interpret();
}
```
接着构建具体表达类，我们这里有三种，`AoExpression`, `MiaoExpression`和`AddExpression`:
```JAVA
// 具体表达类AoExpression
public class AoExpression extends AbstractExpression{
    private final String INTERPRETIVE_RESULT = "A";

    //将"Ao"解析并返回"A"
    @Override
    public String interpret() {
        return INTERPRETIVE_RESULT;
    }
}

// 具体表达类MiaoExpression
public class MiaoExpression extends AbstractExpression {
    private final String INTERPRETIVE_RESULT = "M";
    
    //将"Miao"解析并返回"M"
    @Override
    public String interpret() {
        return INTERPRETIVE_RESULT;
    }
}

// 具体表达类AddExpression的父类OperationExpression，这个完全是为了扩展
public class OperationExpression extends AbstractExpression{
    protected AbstractExpression exp1;
    protected AbstractExpression exp2;
    public OperationExpression(AbstractExpression exp1, AbstractExpression exp2) {
        this.exp1 = exp1;
        this.exp2 = exp2;
    }

    @Override
    public String interpret() {
        return null;
    }
}
// 具体表达类AddExpression
public class AddExpression extends OperationExpression {

    public AddExpression(AbstractExpression exp1, AbstractExpression exp2) {
        super(exp1, exp2);
    }

    //"+"直接解析返回两个字母相加
    @Override
    public String interpret() {
        return exp1.interpret() + exp2.interpret();
    }
}
```
这些符号的解析类构建好了，我们下一步需要利用这些类来做解析的任务：

解析业务相关类`PeekPaInterpreter`：
```JAVA
public class PeekPaInterpreter {
    private final String MIAO = "Miao";
    private final String AO = "Ao";
    private final String ADD_OPERATION = "+";

    // 利用栈来做缓存结构
    private Stack<AbstractExpression> mStack = new Stack<>();

    // 针对输入字符串是否合法的判断，这里没有做，此处只是简单的展示解释器模式的使用
    public void setExpression(String expression) {
        if (mStack != null) {
            mStack.clear();
        }
        AbstractExpression exp1;
        AbstractExpression exp2;

        String[] elements = expression.split(" ");
    
        // 关键解析代码
        for (int i = 0; i < elements.length; i++) {
            switch (elements[i]) {
                case MIAO:
                    mStack.push(new MiaoExpression());
                    break;
                case AO:
                    mStack.push(new AoExpression());
                    break;
                case ADD_OPERATION:
                    exp1 = mStack.pop();
                    // 需要边界判断
                    String nextElement = elements[++i];
                    if (nextElement.equals(MIAO)) {
                        exp2 = new MiaoExpression();
                    } else {
                        exp2 = new AoExpression();
                    }
                    mStack.push(new AddExpression(exp1, exp2));
                    break;
                default:
                    mStack.push(new TerminalExpression(elements[i]));
            }
        }
    }

    // 从栈里面pop()出来的元素调用interpret()方法得到的结果
    // 并不符合我们的需求，这里做一个字母的统计，最后输出符合的结果
    public String parse(){
        String tempResult = mStack.pop().interpret();
        int miaoCount = 0;
        int aoCount = 0;
        for (int i =0; i < tempResult.length(); i++) {
            if (tempResult.charAt(i) == 'M') {
                miaoCount++;
            } else {
                aoCount++;
            }
        }
        return String.valueOf(aoCount) + "A" + String.valueOf(miaoCount) + "M";
    }

}
```
那么我们来调用一下，看看解析出来的结果是否正确：
```JAVA
    // 声明4中声音
    String voice1 = "Miao + Miao + Miao + Miao";
    String voice2 = "Ao + Ao + Ao";
    String voice3 = "Miao + Ao + Miao + Miao";
    String voice4 = "Miao + Ao + Ao + Miao";
    // 声明解析器
    PeekPaInterpreter interpreter = new PeekPaInterpreter();
    //解析voice1
    interpreter.setExpression(voice1);
    System.out.println(interpreter.parse()); // 0A4M
    //解析voice2
    interpreter.setExpression(voice2);
    System.out.println(interpreter.parse()); // 3A0M
    //解析voice3
    interpreter.setExpression(voice3);
    System.out.println(interpreter.parse()); // 1A3M
    //解析voice4
    interpreter.setExpression(voice4);
    System.out.println(interpreter.parse()); // 2A2M
```
可以看到，最后的解析结果格式是`"xAxM"`的格式，而且结果正确，若是这时候要得到`PeekPa`的情绪，只需要再写个方法就可以。可以看到，解释器模式还是很好用的。

### 总结一下
解释器模式，灵活性很高，而且用途也很广，比如*Andorid中的PackageServiceManager*，以及*针对AndroidManifest.xml的解析*，均使用的解释其模式。这种模式看似抽象，其实如果掌握了，还是挺不错的，用起来很顺手，感觉功力有加深了一层。

`优点：` *结构清晰，扩展性好*。  

`缺点：` *解析必须构建语法树，针对每一条文法都至少对应一个解析器，所以会产生大量的类。如果文法复杂，解析树的构造会异常复杂*。

#### 推荐阅读：
[设计模式之零七：“随性切换的”状态模式](https://mp.weixin.qq.com/s?__biz=MzI2ODYwNjE5NQ==&mid=2247483709&idx=1&sn=6ecb0205637d0f39b50aa098f5ae0c63&chksm=eaec4ea1dd9bc7b7cb58170ca837a449dfdfec8386fb5dea577ed00301854e8303940db73c3e&scene=0&key=ea4ee8510c130743f9a92b3a57437ec1be276e2fc80f5f0901677d33234d513e69a100528bd2b0241a99c2edc444a5ded3798b65431cdfcd0514d46e560a73626e95bc63aa830e8189588d53f1b911fd&ascene=0&uin=MjAyMDI0NzY2MA%3D%3D&devicetype=iMac+MacBookPro11%2C4+OSX+OSX+10.13.3+build(17D47)&version=12020610&nettype=WIFI&lang=ja&fontScale=100&pass_ticket=IPyVmJSiUz4CXg%2F6SaFbTf%2FbIfiJ7%2FEyWW1BhJKNEsk2T1c7BvdtawaxJWITHmVo)
[设计模式之零八：“排排坐吃果果的”责任链模式](https://mp.weixin.qq.com/s?__biz=MzI2ODYwNjE5NQ==&mid=2247483714&idx=1&sn=1e56583063bce25fa1dfcd536bb9cac1&chksm=eaec4ededd9bc7c8b1efa081e1a848db628eb0ffecae4e83b2d327cc1d698eb35909b15f0094&scene=0&key=9ba149027f2a1592126715f8521e35de8d8c98d7dbb035d235c296da3583b2f0b35697a921a908b869dab2a3c29515091f2c22f91a8ef1b00f5404de7890d789d677256325c921d8a735526db7d10619&ascene=0&uin=MjAyMDI0NzY2MA%3D%3D&devicetype=iMac+MacBookPro11%2C4+OSX+OSX+10.13.3+build(17D47)&version=12020610&nettype=WIFI&lang=ja&fontScale=100&pass_ticket=IPyVmJSiUz4CXg%2F6SaFbTf%2FbIfiJ7%2FEyWW1BhJKNEsk2T1c7BvdtawaxJWITHmVo)

每天分享代码结构和养猫心得
![](https://mmbiz.qpic.cn/mmbiz_jpg/jA4Qc7C9IZS5CU8Eicxw9K4kIY8BibzDJX6QiahNQ0wDC2HLheXWp6CpITXBWcxt6E4SRlxHJyrxNO6v6TlKMgeUg/0?wx_fmt=jpeg)

