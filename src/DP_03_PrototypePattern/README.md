首先，惯例，先说正事儿：
### 每日一皮克啪

这是我准备工作前的场景。皮克啪眼神里透露出来的情绪很复杂。。。

正事儿说完，咱们来聊聊原型模式。  
所谓 **原型模式**，wikipedia解释如下：  

“The **prototype pattern** is a creational design pattern in software development. It is used when the type of objects to create is determined by a prototypical instance, which is cloned to produce new objects. This pattern is used to:  
- avoid subclasses of an object creator in the client application, like the factory method pattern does.
- avoid the inherent cost of creating a new object in the standard way (e.g., using the 'new' keyword) when it is prohibitively expensive for a given application.”

简单翻译一下就是：    

*原型模式*是一种*创造类型*的模式。原型表明应该是有一个*模板实例（prototype）*，用户从这个实例中*复制*一个内部属性一致的对象，这就是原型模式。说白了，其实就是“克隆”。用原型模式创建一个新的对象，其实就是通过只不过在这个“克隆”的过程中，是可以定制的。  
一般来说，**原型模式多用于创建复杂的或者构造耗时的实例。**

原型模式使用场景：
- 类初始化消耗过多资源，这些资源包括数据，硬件资源等，通过原型拷贝可以避免这些消耗。
- 通过new产生一个对象需要非常繁琐的数据准备或者访问权限的时候，这时候可以使用原型模式。
- 一个对象需要提供给其他对象访问，而且各个调用者都可能修改其内部值，这时候通过原型模式拷贝一个新的对象供其他调用，保护性拷贝。

所以，原型模式是和拷贝看来是分不开的。那么这里就会产生出来一个新的问题，就是拷贝的问题。这里，就有两个概念：**浅拷贝** 和 **深拷贝**。  
**浅拷贝：**  
被复制对象的所有变量都含有与原来的对象相同的值，而所有的对其他对象的引用仍然指向原来的对象。换言之，浅复制仅仅复制所拷贝的对象，而不复制它所引用的对象。  
**深拷贝：**  
被复制对象的所有变量都含有与原来的对象相同的值，除去那些引用其他对象的变量。那些引用其他对象的变量将指向被复制过的新对象，而不再是原有的那些被引用的对象。换言之，深复制把要复制的对象所引用的对象都复制了一遍，*地址都变了*。  

下面的代码可以距离说明浅拷贝和深拷贝的区别：
```JAVA
// Java中用“==”来判断两个变量的地址是否相同
// 用equals()方法来判断对象的内容是否相同（equals方法可以重写）
// 浅拷贝
Integer integer1 = 666;
Integer integer2 = integer1;
System.out.println(integer1); // 666
System.out.println(integer2); // 666
System.out.println(integer1==integer2); // true
System.out.println(integer1.equals(integer2)); // true

// 深拷贝
Integer integer3 = 888;
Integer integer4 = new Integer(integer3);
System.out.println(integer3); // 888
System.out.println(integer4); // 888
System.out.println(integer3==integer4); // false
System.out.println(integer3.equals(integer4)); // true

//Java基础数据类型
int int5 = 100;
int int6 = int5;
System.out.println(int5 == int6); // true
```
如果变量是Java的基础数据类型，直接用`=`即可完成拷贝，若是非基础数据类型，用`=`只能实现浅拷贝，若要深拷贝，得需要`new`一个新的对象才行，或者用其他方法。

### 操作实例
在了解了浅拷贝深拷贝的概念之后，那么我们可以看看原型模式是怎么实现和使用的了。
实现原型模式，有两种方式：
1. 让类实现Cloneable接口，重写clone()方法即可。在clone()方法里面，可以适当的自定义一些东西，这里比较灵活，可以做一些文章。
2. 让类实现Serializable接口，在复制方法里面，先将对象序列化，写到流里，然后再从流里读出来即可。
假设我这里有个类，就是皮克啪，下面先用第一种方法Clonable实现一下:
```JAVA
// Cloneable方法
public class PeekPa implements Cloneable {
    public String name;
    public String furColor;
    public int weight;
    public List<String> favoriteFoods;

    public PeekPa(String name, String furColor, int weight, List<String> favoriteFoods) {
        this.name = name;
        this.furColor = furColor;
        this.weight = weight;
        this.favoriteFoods = favoriteFoods;
    }

    @Override
    protected PeekPa clone() {
        try {
            PeekPa peekPa = (PeekPa) super.clone();
            peekPa.name = new String(this.name);
            peekPa.furColor = new String(this.furColor);
            peekPa.weight = this.weight;
            // 下面的代码是对List<String>的深度拷贝，不光要新建一个List
            // 而且针对List里面的每一个item，如果不是JAVA的基本类型，都需要new一个新的出来
            List<String> newFavouriteFoods = new ArrayList<>();
            for (String food : this.favoriteFoods) {
                String newFood = new String(food);
                newFavouriteFoods.add(newFood);
            }
            peekPa.favoriteFoods = newFavouriteFoods;
            return peekPa;
        } catch (Exception e) {
            System.out.println(e.getStackTrace());
        }
        return null;
    }
    //下面省略setter 和 getter 方法。
}
```
以上是PeekPa的类，这个时候，如果出现了PeekPa的`二重身`，那么peekPaDoppelganger里面的属性应该是和PeekPa的属性值是一样的，但是，二重身和本体是两个完全不一样的东西。所以，下面的代码就证明了，上面PeekPa中的clone()方法是是否完全实现了深拷贝：
```JAVA
String name = "PeekPa";
String furColor = "Blue";
int weight = 11;
List<String> favouriteFoods = new ArrayList<>();
favouriteFoods.add("meet");
favouriteFoods.add("fish");
favouriteFoods.add("milk");

// 声明peekPa
PeekPa peekPa = new PeekPa(name, furColor, weight, favouriteFoods);

// peekPa的二重身声明
PeekPa peekPaDoppelganger = peekPa.clone();

// 验证 peekPa的二重身和本体之前是否是一样的
System.out.println(peekPa.name == peekPaDoppelganger.name);// false
System.out.println(peekPa.favoriteFoods == peekPaDoppelganger.favoriteFoods); // false
System.out.println(peekPa.favoriteFoods.get(0)); // "meet"
System.out.println(peekPaDoppelganger.favoriteFoods.get(0)); // "meet"
System.out.println(peekPa.favoriteFoods.get(0) == peekPaDoppelganger.favoriteFoods.get(0)); // false
```
上面的一连串`false`表明，clone()方法里面的拷贝确实是深拷贝，这样就简单的实现了原型模式。
若是使用Serializable实现，代码如下：
```JAVA
// Serializable方法
public class PeekPa implements Serializable {
    public String name;
    public String furColor;
    public int weight;
    public List<String> favoriteFoods;

    public PeekPa(String name, String furColor, int weight, List<String> favoriteFoods) {
        this.name = name;
        this.furColor = furColor;
        this.weight = weight;
        this.favoriteFoods = favoriteFoods;
    }

    public Object deepCopy() {
        try {
            // 将对象写到流里
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(this);
            // 从流里读出来
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
            ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
            return (objectInputStream.readObject());
        } catch (Exception e) {
            System.out.println(e.getStackTrace());
        }
        return null;
    }
}
```
继续调用peekPaDoppelganger，结果如下：
```JAVA
    // peekPa 的初始值和上面的一养
    PeekPa peekPa = new PeekPa(name, furColor, weight, favouriteFoods);
    
    // 调用PeekPa的deepCopy()方法
    PeekPa peekPaDoppelganger = (PeekPa) peekPa.deepCopy();
    
    // 验证 peekPa的二重身和本体之前是否是一样的
    System.out.println(peekPa.name == peekPaDoppelganger.name);// false
    System.out.println(peekPa.favoriteFoods == peekPaDoppelganger.favoriteFoods); // false
    System.out.println(peekPa.favoriteFoods.get(0)); // "meet"
    System.out.println(peekPaDoppelganger.favoriteFoods.get(0)); // "meet"
    System.out.println(peekPa.favoriteFoods.get(0) == peekPaDoppelganger.favoriteFoods.get(0)); // false
```
看来Serializable的实现方式和Cloneable的实现结果是一样的。哈哈哈哈哈哈。。。。

### 总结一下
原型模式是一种和拷贝相关的模式，他的使用主要在新建对象的场景里面。如果要用Cloneable方法，一般推荐对对象内部的所有变量都实行深拷贝，绝对的深度拷贝，一层一层的拷贝。**否则就会出现对非Java基本数据类型变量的浅拷贝**。  
`优点：` 原型模式是内存中二进制流的拷贝，要比new一个对象好很多。  
`缺点：` 原型模式的拷贝，不能够调用类本身的构造方法，所以如果在构造方法中有些特殊处理的地方，还是需要在拷贝的时候多注意一下。

收尾呼应一下，皮克啪完事儿了，我该上场干活了。。。

#### 请大家关注一下我的公众号：**皮克啪的铲屎官**
![qr_code](https://github.com/SwyftG/DesignPatternExample/blob/dev/src/img/qr_code.png)
#### 是一个日更的微信公众号，每天都有新知识，大家一起交流进步。