### Springboot Admin + SpringSecurity实现安全监控应用

### 依赖版本信息:
 > spring-boot-starter-parent 2.1.6.RELEASE<br>
 > spring-boot-admin-starter-server 2.1.6<br>
 > spring-boot-admin-starter-client 2.1.6<br>

### 项目结构:
```
  ├─springboot-admin-server   Admin监控的服务端模块
  ├─springboot-admin-client   监控的客户端模块(被监控者)
  ├─.gitignore                .gitignore文件
  ├─README.md                 README.md文件
  └─pom.xml                   父模块pom文件
 ```

### 如何运行:
  1. 进入springboot-admin-server模块下启动监控服务端;
  
  2. 浏览器访问`http://localhost:9999`进入网页监控控制台:<br>
     用户名: admin
     
     密码:  123456
     
  3. 进入springboot-admin-client客户端模块, 启动一个客户端实例;
  
  4. 回到网页监控控制台即可查看被监控的应用信息;
  
  