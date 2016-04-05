# build.gradle 配置greenDao 
# 在build.gradle(project)中配置如下：
dependencies 		                                              
classpath 'com.android.tools.build:gradle:1.3.1'		
        //greenDao generator		
        classpath 'de.greenrobot:greendao-generator:2.1.0'		
}		
		
//greenDao Generate Task		
task daoGenerate << {		
    // 包名		
    def pack = 'cn.chenzhongjin.greendao.sample'		
    Schema schema = new Schema(3, "${pack}.entity");//foucuse version for update database		
    schema.defaultJavaPackageDao = "${pack}.database"		
    schema.hasKeepSectionsByDefault = true		
    schema.useActiveEntitiesByDefault = true;		
    Entity user = schema.addEntity("User");		
    user.addIdProperty();		
    user.addStringProperty("name");		
    user.addStringProperty("sex");		
    user.addLongProperty("phoneNumber");		
    user.addLongProperty("updateTime");			
    new DaoGenerator().generateAll(schema, "app/src/main/java", null, null);		
}		
