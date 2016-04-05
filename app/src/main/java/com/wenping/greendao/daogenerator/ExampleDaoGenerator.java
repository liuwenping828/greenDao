package com.wenping.greendao.daogenerator;


import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Property;
import de.greenrobot.daogenerator.Schema;
import de.greenrobot.daogenerator.ToMany;

/**
 *  添加依赖库
 *  compile 'de.greenrobot:greendao:2.1.0'
 *  compile 'de.greenrobot:greendao-generator:2.1.0'
 *
 */
public class ExampleDaoGenerator {

    public static void main(String[] args) throws Exception {
        // 创建了一个用于添加实体（Entity）的模式（Schema）对象。
        // 两个参数分别代表：数据库版本号与自动生成代码的包路径
        Schema schema = new Schema(1, "com.wenping.greendao");
        // 分别指定生成的 Bean 与 DAO 类所在的目录
//        Schema schema1 = new Schema(2,"com.wenping.greendao.bean");
//        schema1.setDefaultJavaPackageDao("com.wenping.greendao.dao");

        addNote(schema);
        addCustomerOrder(schema);
        // ../greenDao/app/src/src-gen/
        // 输出目录的路径  ../ 表示当前工程目录下
        new DaoGenerator().generateAll(schema, "../greenDao/app/src/src-gen/");
    }

    private static void addNote(Schema schema) {
        Entity note = schema.addEntity("Note");
        note.addIdProperty();
        note.addStringProperty("text").notNull();
        note.addStringProperty("comment");

        note.addDateProperty("date");
    }

    private static void addCustomerOrder(Schema schema) {
        Entity customer = schema.addEntity("Customer");
        customer.addIdProperty();
        customer.addStringProperty("name").notNull();

        Entity order = schema.addEntity("Order");
        // 重新设置数据表名称
        order.setTableName("ORDERS"); // "ORDER" is a reserved keyword
        order.addIdProperty();
        Property orderDate = order.addDateProperty("date").getProperty();
        Property customerId = order.addLongProperty("customerId").notNull().getProperty();
        //一对一映射关系 (一个订单对应一个Customer)
        order.addToOne(customer, customerId);
        //一对多映射关系 (一个 Customer 对应 多个 订单)
        ToMany customerToOrders = customer.addToMany(order, customerId);
        customerToOrders.setName("orders");
        customerToOrders.orderAsc(orderDate);

        // 多对多
        Entity treeEntity = schema.addEntity("TreeEntity");
        treeEntity.addIdProperty();
        Property parentIdProperty = treeEntity.addLongProperty("parentId").getProperty();
        treeEntity.addToOne(treeEntity, parentIdProperty).setName("parent");
        treeEntity.addToMany(treeEntity, parentIdProperty).setName("children");

    }
}
