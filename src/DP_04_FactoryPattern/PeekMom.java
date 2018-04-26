package DP_04_FactoryPattern;

public class PeekMom extends PeekFactory{
    @Override
    public IPeekCat bornPeekCat() {
        return null;
    }

    /*
     * 通过传入Name来区分创建类的对象
     *
    @Override
    public <T extends IPeekCat> T bornPeekCat(Class<T> clz) {
        IPeekCat result = null;
        try {
            result = (IPeekCat) Class.forName(clz.getName()).newInstance();
            result.init();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return (T)result;
    }
    */

    /*
     * 通过传入Name来区分创建类的对象
     *
    public static final String PEEKPA = "PeekPa";
    public static final String PEEKBA = "PeekBa";
    @Override
    public IPeekCat bornPeekCat(String name) {
        switch (name) {
            case PEEKPA:
                IPeekCat peekPa = new PeekPa();
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
    */

}
