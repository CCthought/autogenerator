# 概述
该开源项目功能类似于Mybatis plus generator 或 Mybatis generator，均是通过表结构，自动生成代码，提高效率，不过该项目更加简单，易用。

# 注意
因为MYSQL数据库没有Boolean类型，而是由tinyint代替，但是在Java中，为了显示语义，我们最好还是用Boolean类型进行字段定义。

因此为了将普通的Boolean字段和真正的tinyint字段区分，我们需要遵循阿里巴巴Java编程规范。

表达是与否概念的字段，必须使用 is_xxx 的方式命名，数据类型是 unsigned  

tinyint（1 表示是，0 表示否）



只有Boolean值的字段才会以` is_`开头，主键不能以`is_`开头




# Summary
this open source project functions like Mybatis plus generator or Mybatis generator,it can generate code automatically and improve efficiency through table structure,but the project is simpler and easier to use