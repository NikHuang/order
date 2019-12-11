package com.huang.order.dao;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.huang.order.domain.OrderDetail;
import com.huang.order.domain.OrderMaster;
import com.huang.order.domain.ProductCategory;
import com.huang.order.domain.ProductInfo;
import com.huang.order.dto.PageInfoDto;
import com.huang.order.enums.ProductStatusEnum;
import com.huang.order.framework.utils.CommonUtil;
import com.huang.order.service.OrderMasterService;
import com.huang.order.service.ProductCategoryService;
import com.huang.order.service.ProductInfoService;
import com.huang.order.test.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.RecursiveTask;
import java.util.function.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.LongStream;
import java.util.stream.Stream;

import static java.util.Comparator.comparing;
import static java.util.Comparator.comparingInt;
import static org.junit.Assert.*;

/**
 * Created by Administrator on 2019/11/14.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class ProductCategoryDaoTest {

    @Autowired
    ProductCategoryService productCategoryService;

    @Autowired
    ProductInfoService productInfoService;

    @Autowired
    ProductInfoDao productInfoDao;

    @Autowired
    OrderMasterDao orderMasterDao;

    @Autowired
    OrderMasterService orderMasterService;

    @Autowired
    OrderDetailDao orderDetailDao;

    @Test
    public void findOneTest() {
        ProductCategory productCategory = productCategoryService.findOne(1);
        Assert.assertNotEquals(null, productCategory);
    }

    @Test
    @Transactional
    public void saveTest() {
        ProductCategory productCategory = new ProductCategory();
        productCategory.setCategoryName("测试3");
        productCategory.setCategoryType(3);
        productCategoryService.save(productCategory);
    }

    @Test
    @Transactional
    public void updateTest() {
        ProductCategory productCategory = new ProductCategory();
        productCategory.setCategoryId(2);
        productCategory.setCategoryName("儿童专享");
        productCategory.setCategoryType(2);
        productCategoryService.update(productCategory);

    }

    @Test
    public void findListTest() {
        List<Integer> typeList = Arrays.asList(1, 2, 3);
        List<ProductCategory> list = productCategoryService.findList(typeList);
        Assert.assertNotEquals(0, list.size());
    }

    @Test
    public void saveProductInfo() {
        ProductInfo productInfo = new ProductInfo();
        productInfo.setProductId(CommonUtil.generateObjectId(productInfo));
        productInfo.setProductName("M豆");
        productInfo.setProductPrice(BigDecimal.valueOf(5.0));
        productInfo.setProductStock(100);
        productInfo.setProductDescription("高钙牛奶");
        productInfo.setProductIcon("http://zzzzz.jpg");
        productInfo.setProductStatus(0);
        productInfo.setCategoryType(2);
        productInfoDao.save(productInfo);
    }

    @Test
    public void findProductInfoByStatus() {
        List<ProductInfo> list = productInfoDao.findByStatus(ProductStatusEnum.ONSALE.getCode());
//        list.forEach(item -> System.out.println(item.toString()));
        Collections.sort(list, (item1, item2) -> {
            if (item1.getCategoryType() < item2.getCategoryType()) {
                return -1;
            } else if (item1.getCategoryType() > item2.getCategoryType()) {
                return 1;
            } else {
                return item2.getProductPrice().compareTo(item1.getProductPrice());
            }
        });
        Assert.assertNotEquals(0, list.size());
    }

    @Test
    public void findOne() {
        List<ProductInfo> list = productInfoDao.findAll();
        Assert.assertNotEquals(0, list.size());
        List<ProductInfo> list1 = CommonFilterService.doFilterMethod(list, (item) -> (item.getCategoryType() == 2));
        Assert.assertNotEquals(0, list1.size());
        //排序 先比CategoryType 再比price
        Collections.sort(list, (item1, item2) -> {
            if (!(item1.getCategoryType() == item2.getCategoryType())) {
                return item1.getCategoryType().compareTo(item2.getCategoryType());
            } else {
                return item1.getProductPrice().compareTo(item2.getProductPrice());
            }
        });
        System.out.println("==================");
        String resultStr = MyFunctionService.stringConvert("abc", (s) -> s.toUpperCase());
        System.out.println("resultStr = " + resultStr);
        System.out.println("==================");
        long result = MyFunctionService.doTestMethod(1L, 33L, (x, y) -> x + y);
        System.out.println("result = " + result);
        long result1 = MyFunctionService.doTestMethod(2L, 33L, (x, y) -> x * y);
        System.out.println("result1 = " + result1);
        //java8 有四个核心内置函数式接口
        //1.Consumer<T> accept 消费型接口    void
        //2.Supplier<T> get 供给型接口       T
        //1.Function<T,R> apply 函数型接口   R
        //1.Predicate<T> test 断言型接口     boolean
        MyFunctionService.doConsumerTest("123", System.out::println);
        List<Integer> list2 = MyFunctionService.doSupplierTest(10, () -> (int) (Math.random() * 100));
        System.out.println(list2.toString());
        Integer fstr = MyFunctionService.doFunctionTest("bbb", s -> s.length());
        System.out.println(fstr);
        boolean predicate = MyFunctionService.doPredicateTest("abc", (x) -> x.length() == 3);
        List<String> list3 = Arrays.asList("aaa", "bbb", "333", "4444", "55555", "www");
        List<String> list4 = MyFunctionService.doPredicateList(list3, (x) -> x.length() == 3);
        System.out.println("list4.toString() = " + list4.toString());
        List<ProductInfo> list5 = MyFunctionService.filterByT(list, (e) -> e.getCategoryType() == 1);
        System.out.println(list5.toString());

    }

    @Test
    public void findAll() {
        PageInfo<ProductInfo> item = productInfoService.getPageInfoWithPage(new PageInfoDto(3, 2));

        Assert.assertNotEquals(null, item);
    }

    @Test
    public void testSth() {
        List<Integer> list = Arrays.asList(1, 3, 2, 4, 5);
        Collections.sort(list);
        list.forEach(a -> System.out.println(a));
        System.out.println("==================");
        int a = 0;
        Runnable r = () -> System.out.println("a:" + a);
        Consumer<String> con = t -> System.out.println(t);
        //只有一条语句的话 大括号和return 都可以省略
        Comparator<Integer> comparator = (x, y) -> 1;
        //1.8类型推断有所升级
        //lambada 表达式需要函数式接口的支持
        //若接口中只有一个抽象方法的借口，该接口就是函数式接口
        //函数式接口 可以用 @FunctionalInterface

    }

    @Test
    public void lambdaTest() {
        ProductCategory productCategory = productCategoryService.findOne(1);

        Supplier<Integer> supplier = productCategory::getCategoryId;
//        Function<ProductCategory,Integer> function = (x)->x.doTest();
//        BiFunction<ProductCategory,Integer,String> function = ProductCategory::hasId;1
//        System.out.println("productCategory = " + function.apply(productCategory,1));
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5);
        list.forEach((e) -> {
            if (e == supplier.get()) {
                System.out.println("get it");
            } else {
                System.out.println("don't get it");
            }
        });
        //对象调用实例方法 返回值一致 参数类型一致
        //类调用静态方法 返回值一致，参数类型一致
        //类调用实例方法 双参数 前一致 后一致 （x,y）->x.equals(y)
    }


    @Test
    public void streamTest() {
        List<ProductInfo> list = productInfoDao.findAll();
        List<ProductInfo> list1 = new ArrayList<>();
        List<ProductInfo> list2 = new ArrayList<>();
        list.stream().filter((item) -> item.getCategoryType() == 1).forEach(x -> list1.add(x));
        list.stream().filter((item) -> item.getCategoryType() == 2).forEach(x -> list2.add(x));
        //1.创建stream
        //四种方式创建 （1）Collection 的stream 串行流 或 parallelStream 并行流
        Stream<ProductInfo> stream = list.stream();

        //（2）Arrays 的静态stream
        Integer[] integers = {1, 2, 3, 4, 5};
        Stream<Integer> stream1 = Arrays.stream(integers);

        //（3）Stream类的静态方法 of获取
        Stream<String> stringStream = Stream.of("aa", "bb", "cc");

        //(4)无限流
        //迭代
        Stream<Integer> stream2 = Stream.iterate(1, x -> x + 1);
        //limit 限制数量
        stream2.limit(10);
        //生产
        Stream<Double> stream3 = Stream.generate(() -> Math.random()).limit(15);
//        stringStream.forEach(System.out::println);

        //2.中间操作
        //(1)筛选与切片
        //1) filter
        list.stream().filter(item -> item.getCategoryType() == 2).forEach(System.out::println);
        //2) limit 截断流
        //3)skip跳过流
        list.stream().skip(1).limit(2).forEach(System.out::println);
        //4)distinct 去重 需要重新hashcode和equals 暂略

        //(2) 映射
        //1）map
        Stream<String> stringStream1 = list.stream().map(ProductInfo::getProductDescription);

        //2)flatmap
        //试着把list1 list2放进同一个Stream
        List<ProductInfo> list3 = new ArrayList<>();
        List<List<ProductInfo>> list4 = new ArrayList<>();
        list4.add(list1);
        list4.add(list2);

        Stream<Stream<ProductInfo>> streamStream = list4.stream().map(x -> x.stream());
        streamStream.forEach(x -> x.forEach(
                y -> list3.add(y)
        ));
        System.out.println("list3 = " + list3);
        List<ProductInfo> list5 = new ArrayList<>();
        list4.stream().flatMap(
                x -> x.stream()
        ).forEach(
                y -> list5.add(y)
        );
        System.out.println("list5 = " + list5);
        List<String> stringList1 = Arrays.asList("111", "222", "333", "444");
        List<String> stringLista = new ArrayList<>(stringList1);
        List<String> stringList2 = Arrays.asList("aaa", "bbb", "ccc", "ddd");
        List<String> stringListb = new ArrayList<>(stringList2);
        List<List<String>> lists = new ArrayList<>();
        lists.add(stringLista);
        lists.add(stringListb);

        Stream<Stream<String>> streamStream1 = lists.stream().map(x -> x.stream());
        streamStream1.forEach(x -> x.forEach(System.out::println));

        Stream<String> stringStream2 = lists.stream().flatMap(x -> x.stream());
        stringStream2.forEach(System.out::println);


    }

    @Test
    public void testStreamSort() {
        List<Integer> list = Arrays.asList(3, 1, 4, 2, 5);
        list.stream().sorted().forEach(System.out::println);
        System.out.println(list);
        List<ProductInfo> productInfoList = productInfoDao.findAll();

        productInfoList.stream().sorted((x, y) -> x.getProductPrice().compareTo(y.getProductPrice())).sorted((x, y) -> x.getCategoryType() - y.getCategoryType()).
                forEach(System.out::println);

    }

    @Test
    public void testStreamFinal() {
        List<Integer> list = Arrays.asList(3, 1, 4, 2, 5);
        list.stream().sorted().forEach(System.out::println);
        System.out.println(list);
        List<ProductInfo> productInfoList = productInfoDao.findAll();
        //是否完全匹配 allMatch
        boolean a = productInfoList.stream().allMatch(e -> e.getCategoryType() == 1);
        System.out.println(a);
        //是否有一个匹配 anyMatch 略
        //无匹配 noneMatch 略

        //findFirst 返回第一个 返回的是Optional
        Optional<ProductInfo> op =productInfoList.stream().sorted((e1,e2)->e1.getProductPrice().compareTo(e2.getProductPrice())).
        findFirst();
        //findAny返回随机一个 返回的是Optional
        productInfoList.stream().sorted((e1,e2)->e1.getProductPrice().compareTo(e2.getProductPrice())).
                findAny();
        //count 返回数量 略
        //max,min 返回Optional
        productInfoList.stream().max((e1,e2)->e1.getProductPrice().compareTo(e2.getProductPrice()));
        productInfoList.stream().min((e1,e2)->e1.getProductPrice().compareTo(e2.getProductPrice()));
        productInfoList.stream().map(ProductInfo::getProductPrice).min((e1, e2)->e1.compareTo(e2));

        //归约 reduce
        List<Integer> list1 = Arrays.asList(1,2,3,4,5);
        Integer ss =list1.stream().reduce(0,(x,y)->{
            if (y < 3){
                return x+y;
            }else {
                return x*y;
            }
        });
        System.out.println(ss);
        Optional<Integer> o1p = list1.stream().reduce((x,y)->{
            if (y < 3){
                return x+y;
            }else {
                return x*y;
            }
        });
        System.out.println(o1p.get());

        BigDecimal decimal = productInfoList.stream().map(ProductInfo::getProductPrice).reduce(new BigDecimal(0), BigDecimal::add);
        System.out.println("decimal = " + decimal);
        //map-reduce模式 十分重要
    }

    @Test
    public void testStreamCollectors() {
        List<ProductInfo> productInfoList = productInfoDao.findAll();
        List<String> l = productInfoList.stream().map(ProductInfo::getProductName).collect(Collectors.toList());
        System.out.println("l = " + l);
        //可以 toList toSet toMap  其他的 比如HashMap可以 Collectors.toCollection(()->new HashMap)

        //求总数
        long count = productInfoList.stream().collect(Collectors.counting());

        //平均值 无需map
        Double avg = productInfoList.stream().collect(Collectors.averagingDouble(e->e.getProductPrice().doubleValue()));

        //总和
        Double sum = productInfoList.stream().collect(Collectors.summingDouble(e->e.getProductPrice().doubleValue()));

        //最大值
         Optional<ProductInfo> optional =productInfoList.stream().max((e1,e2)->e1.getProductPrice().compareTo(e2.getProductPrice()));

        //最小值  略
        //分组
       Map<Integer,List<ProductInfo>> map = productInfoList.stream().collect(Collectors.groupingBy(ProductInfo::getCategoryType));
        //多级分组 暂略 造数据测试
        //分区 满足条件一个区 不满足另一个区
        Map<Boolean,List<ProductInfo>> map1 = productInfoList.stream().collect(Collectors.partitioningBy(e1->e1.getCategoryType() == 1));

        //summary
        DoubleSummaryStatistics d = productInfoList.stream().collect(Collectors.summarizingDouble(e1->e1.getProductPrice().doubleValue()));
        d.getAverage();
        d.getMax();
        //joining连接字符串



    }
    @Test
    public void testStreamExam() {
       List<Integer> integerList = Arrays.asList(1,2,3,4,5);
        System.out.println(integerList);

       List<Integer> integerList1 = integerList.stream().map(e->e*e).collect(Collectors.toList());
        List<ProductInfo> productInfoList = productInfoDao.findAll();

        productInfoList.stream().map(e->1).reduce(Integer::sum);

        Trader zs = new Trader("张三","北京");
        Trader ls = new Trader("李四","北京");
        Trader ww = new Trader("王五","上海");
        Trader zl = new Trader("赵六","上海");

        List<Trader> traderList = Arrays.asList(zs,ls,ww,zl);
        List<Transaction> transactionList = Arrays.asList(new Transaction(zs,2011,300),
                new Transaction(ls,2011,400),
                new Transaction(ls,2012,350),
                new Transaction(ww,2012,600),
                new Transaction(zl,2011,200),
                new Transaction(zl,2012,510));
        List<Transaction> transactionList1 =transactionList.stream().filter( e-> e.getYear() == 2011)
                .sorted(comparing(Transaction::getValue)).collect(Collectors.toList());
        System.out.println("transactionList1 = " + transactionList1);
        List<String> cityList = transactionList.stream().map(e -> e.getTrader().getCity()).distinct().collect(Collectors.toList());
        System.out.println("cityList = " + cityList);
        List<Trader> nameListFromCertainCity = transactionList.stream().filter(e->e.getTrader().getCity().equals("北京")  ).
                map(Transaction::getTrader).
                sorted(comparing(Trader::getName)).
                distinct().collect(Collectors.toList());
        System.out.println("nameListFromCertainCity = " + nameListFromCertainCity);
        List<String> traderNameList = transactionList.stream().filter(e->e.getTrader().getCity().equals("北京")).
                map(e->e.getTrader().getName()).
                sorted(String::compareTo).
                distinct().collect(Collectors.toList());
        System.out.println("traderNameList = " + traderNameList);
        boolean anyCityMatch = transactionList.stream().anyMatch(e->e.getTrader().getCity().equals("上海"));
        System.out.println("anyCityMatch = " + anyCityMatch);

    }
    @Test
    public void testStreamAgain() {
        List<Integer> integerList = Arrays.asList(1,2,3,4,5);
        List<Integer> integerList1 = integerList.stream().map(e->e*e).collect(Collectors.toList());
        System.out.println("integerList1 = " + integerList1);
        List<ProductInfo> productInfoList = productInfoDao.findAll();
        long count = productInfoList.stream().map(e->1).reduce(0,Integer::sum);
        System.out.println("count = " + count);

        Trader zs = new Trader("张三","北京");
        Trader ls = new Trader("李四","北京");
        Trader ww = new Trader("王五","上海");
        Trader zl = new Trader("赵六","上海");

        List<Trader> traderList = Arrays.asList(zs,ls,ww,zl);
        List<Transaction> transactionList = Arrays.asList(new Transaction(zs,2011,300),
                new Transaction(ls,2011,400),
                new Transaction(ls,2012,350),
                new Transaction(ww,2012,600),
                new Transaction(zl,2011,200),
                new Transaction(zl,2012,510));
        List<Transaction> vl = transactionList.stream().filter(e->e.getYear() == 2011).sorted(comparingInt(Transaction::getValue)).collect(Collectors.toList());
        System.out.println("vl = " + vl);
        List<String> cityList = transactionList.stream().map(e->e.getTrader().getCity()).distinct().collect(Collectors.toList());
        System.out.println("cityList = " + cityList);
        List<Trader> traderList1 = transactionList.stream().map(Transaction::getTrader).distinct().sorted(comparing(Trader::getName)).collect(Collectors.toList());
        System.out.println("traderList1 = " + traderList1);
        //4 略
        boolean wib = traderList.stream().allMatch(e->e.getCity().equals("北京"));
        System.out.println(wib);

        Map<String,Map<String,Integer>> mapMap =
        transactionList.stream().collect(Collectors.groupingBy(e->e.getTrader().getCity(),Collectors.groupingBy(e->e.getTrader().getName(),Collectors.summingInt((x)->x.getValue()))));
        System.out.println("mapMap = " + mapMap);
        Integer sum = transactionList.stream().filter(e->e.getTrader().getCity().equals("北京")).collect(Collectors.summingInt(e->e.getValue()));
        System.out.println("sum = " + sum);
        Optional<Integer> maxValue = transactionList.stream().map(e->e.getValue()).collect(Collectors.maxBy(Integer::compareTo));
        System.out.println("maxValue = " + maxValue);
        Optional<Transaction> minValueTran = transactionList.stream().collect(Collectors.minBy((x,y)->x.getValue().compareTo(y.getValue())));
        System.out.println("minValueTran.get() = " + minValueTran.get());
    }
    @Test
    public void testBx(){
        //fork join 先分叉再合并
        //fork join 框架
        //java8 并行流
        //.parallel即可 仅大量任务时适用 少量任务时效率不如 串行流


        //optional
        //.of
        List<ProductInfo> productInfos = new ArrayList<>();
        List<ProductInfo> productInfoList = productInfoDao.findAll();
        Optional<List<ProductInfo>> optional = Optional.of(productInfos);
        System.out.println("==============");
        System.out.println(optional.get());
        System.out.println("================");
        //如果 .of(null) 会报错 空指针
//        Optional<List<ProductInfo>> optional1 = Optional.of(null);
        //.empty() 如果get回 报错 NoSuchELEMENT异常
        Optional<List<ProductInfo>> optional2 = Optional.empty();
//        optional2.get();
        //Optional.ofNullable 如果不为null则 of ，如果为null则 empty
        Optional<List<ProductInfo>> optional3 = Optional.ofNullable(null);
        //结合 .isPresent 判断是否包含值 返回boolean
        System.out.println(optional3.isPresent());
        //.orElse  如果为空 则返回参数 否则 返回该值
        System.out.println(optional3.orElse(productInfoList));
        //.orElseGet 结合 ofNullable使用 可大致理解为使用lambda方式orElse 但是与orElse不同 在lambda {}块中可以进行一些操作 而不单单是返回一个值
        System.out.println(optional3.orElseGet(()->{
            System.out.println("+++++++++"); //可进行操作
            return productInfoList;
        }));
        ProductInfo productInfo = new ProductInfo();
        Optional<ProductInfo> infoOptional = Optional.ofNullable(productInfo);
        //.map获取optional 如果值为null则返回Optional.empty()
        Optional<String> stringOptional =infoOptional.map(e->e.getProductDescription());





    }

    @Test
    public void testOrderMaster(){


        PageInfo<OrderMaster> orderMasterList = orderMasterService.findOrderMasterByOpenidWithPage(new PageInfoDto(1,2),"aaa");
        System.out.println(orderMasterList);


    }

    @Test
    public void testOrderDetail(){
       productInfoDao.addStock(1,"aabdd9bab9254d363344567eec0395a8");
    }

}