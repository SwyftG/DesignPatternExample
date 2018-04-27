最前面先吐槽几句，首先我是个铲屎官，其次是个程序员。所以，以后在每篇文章的最开始，都会有“每日一皮克啪”板块。

### 每日一皮克啪
![PeekPa](https://github.com/SwyftG/DesignPatternExample/blob/master/src/DP_02_BuilderPattern/img/02_peekpa.jpeg)
啪完之后，接着说咱们的事儿了。
Builder模式也是最常见的一种模式之一。在实际开发中，会遇到很多，比如Android中的Volley，Gilde，再比如百度地图sdk中，很多对象也是通过Builder模式创建的。这些最明显的标志位就是创建一个类的时候，会通过这个类的Builder来创建，而且一般将代码写成链式的样子，这样看上去很简洁易懂，非常舒服。通常，需要采用Builder这种模式的场景大概如下：  
（1）类里面参数过多，关系复杂，设置不同的顺序会产生不同的结果。  
（2）类里面的参数过多，且设置不同的参数，会产生不同的效果。  
（3）初始化一个对象特别复杂，比如参数过多，而且很多参数有初始值。

Builder模式的**原生**UML类图如下：
![uml](https://github.com/SwyftG/DesignPatternExample/blob/master/src/DP_02_BuilderPattern/img/builder_pattern.jpg)
- Product产品的抽象类
- Builder抽象Builder类
- ConcreteBuilder具体的Builder类
- Director统一组装过程    

但是具体实际生产中，上图的Director一般情况会省略掉，这样使得结构更加简单，而且在一定程度上节约了内存。

### 操作实例
既然我是铲屎官，那么例子自然要和主子有关系。假设我们要新建一个主子类，类里面有以下属性：`name`, `furColor`, `gender`, `age`, `catSpecies`, `favouiteFood`, `healthCondition`。就先有这个7个参数吧。如果这个主子类的构造方法有好多种，那么代码的长相大概如下：
```JAVA
public class Cat {
    private String name;
    private String furColor;
    private int gender;
    private int age;
    private String catSpecies;
    private String[] favouriteFoods;
    private String healthCondition;

    public Cat(String name) {
        this.name = name;
    }

    public Cat(String name, int gender, int age) {
        this.name = name;
        this.gender = gender;
        this.age = age;
    }

    public Cat(String name, String furColor, int gender, int age, String catSpecies) {
        this.name = name;
        this.furColor = furColor;
        this.gender = gender;
        this.age = age;
        this.catSpecies = catSpecies;
    }

    public Cat(String name, String furColor, int gender, int age, String catSpecies, String[] favouriteFoods, String healthCondition) {
        this.name = name;
        this.furColor = furColor;
        this.gender = gender;
        this.age = age;
        this.catSpecies = catSpecies;
        this.favouriteFoods = favouriteFoods;
        this.healthCondition = healthCondition;
    }
    
    //下面忽略set()，get()方法
}
```
上面的写法，可读性不错，而且易于维护。但是这时候就会有一个比较蛋疼的问题：  
如果主子类参数过多，会在新建类的时候，调用很多set()方法。或重载不同参数组合的构造函数，这样容易错乱，及其容易出错。  
但是Builder就解决了这个问题。步骤有如下几步：
 1. **不直接生成想要的对象，而是让客户端利用所有必要的参数调用构造器（或者静态工厂），得到一个builder对象。**
 2. **然后让客户端在builder对象上调用类似的setter方法来设置每个相关的可选参数。**
 3. **最后，客户端调用无参的builder方法来生成不可变的对象。这个builder是它构建的静态成员类。**  

上面的写法，如果加入Builder模式，大致代码如下：
```JAVA
public class Cat {
    private String name;
    private String furColor;
    private int gender;
    private int age;
    private String catSpecies;
    private String[] favouriteFoods;
    private String healthCondition;

    //Cat的构造函数改为了private
    private Cat(CatBuilder builder) {
        this.name = builder.name;
        this.furColor = builder.furColor;
        this.gender = builder.gender;
        this.age = builder.age;
        this.catSpecies = builder.catSpecies;
        this.favouriteFoods = builder.favouriteFoods;
        this.healthCondition = builder.healthCondition;
    }

    //关键代码----start
    //
    public static class CatBuilder {
        private final String name;
        private String furColor;
        private int gender;
        private int age;
        private String catSpecies;
        private String[] favouriteFoods;
        private String healthCondition;

        public CatBuilder(String name) {
            this.name = name;
        }

        public CatBuilder furColor(String color) {
            this.furColor = color;
            return this;
        }

        public CatBuilder gender(int gender) {
            this.gender = gender;
            return this;
        }

        public CatBuilder age(int age){
            this.age = age;
            return this;
        }

        public CatBuilder catSpecies(String species) {
            this.catSpecies = species;
            return this;
        }

        public CatBuilder facouriteFoods(String[] foods) {
            this.favouriteFoods = foods;
            return this;
        }

        public CatBuilder healthCondition(String condition) {
            this.healthCondition = condition;
            return this;
        }

        public Cat create() {
            return new Cat(this);
        }
    }
    //关键代码----end
    
    //一下省略set(), get()方法
}
```
这样的话，如果要在代码里面新建一个主子类的话，就可以直接这么写了：
```JAVA
Cat MyLord = new Cat.CatBuilder("PeekPa")
                            .age(666)
                            .furColor("Blue")
                            .gender(1)
                            .create();
```
这样看上去就小清新了许多。
### 总结一下
严格意义上讲的Builder模式，是由UML图中的几个角色共同组成的。我这里举得例子，事不过是最常见的Builder，相比于UML，少了Director角色。这样做的好处是：使得整个结构更加简单，链式编写，易懂，同时也对Product对象的组装控制的更加精细。  
**优点：** 良好的封装性，客户端不必知道产品内部的组成细节；建造者是独立的，容易扩展  
**缺点：** 需要新建多余的Builder对象，或者Director对象，占用内存。

以上就是这期的内容，这里我想说一下简单的未来规划：  
首先，感谢大家的支持，这个设计模式系列会继续下去，一方面自己学习，另一方面把知识分享给大家，大家一起进步。  
其次，近期可能会更新一次Python的实战内容，是一次从生活中实际遇到的问题出发，写代码，来让问题解决，提升自己效率的故事。同时我还在构思，可能以后也会把Python整理归纳一下吧，方便大家。
最后，脑子里还有很多板块可以搞，慢慢来呗，反正我敢保证，这个公众号绝对精彩，各种骚操作，各种五花八门的玩法，不说了，主子叫我铲屎去了。。。

#### 请大家关注一下我的公众号：**皮克啪的铲屎官**
![qr_code](https://github.com/SwyftG/DesignPatternExample/blob/master/src/img/qr_code.png)
#### 是一个日更的微信公众号，每天都有新知识，大家一起交流进步。