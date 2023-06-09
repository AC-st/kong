#### 项目介绍
该项目主要运用了Spring+Vue 前后端分离的开发模式设计，该系统分为超级管理员、普通管理员、管理员等角色，在各个角色间的项目协作下将影视上传到阿里云服务器，然后将视频显示到门户网站中，用户可以通过不同的影视分类，选择不同的影视，使用阿里云的vod点播技术进行点播；
#### 项目环境
Windows+IDEA+MySQL5.7+JDK1.8
#### 项目安装
  1.先将数据库文件导入
  2.将项目导入IDEA中，导入Maven依赖(IDEA中配置Maven环境)
  3.修改application-dev.yml中相关信息
  4.运行gec-service-system->SystemApp.java中启动类
  5.在浏览器中输入localhost:8085:/doc.html使用Swagger进行测试
#### 使用说明
  1.如果没有前端，只能通过Swagger文档进行测试
  2.使用Swagger进行测试时，首先需要登录，生成token，进行其他请求时，必须将token传入，否侧会返回错误
  3.修改数据库密码
#### 项目功能
  1.影视管理：管理员可以通过阿里云的OSS与VOD技术将影视的介绍图以及视频资源上传至阿里云存储平台进行存储，当前端需要播放的时候，可以通过上传视频的key进行点播
  2.权限管理：管理员可以根据不同的角色分配不同的权限，当用户登录时通过SpringSecurity进行权限 认证，如果管理员没有某个功能的操作权限时，提醒当前管理员
  3.角色管理：管理员可以根据不同的用户分配不同的角色，每个用户只能拥有一个角色
  4.菜单管理：管理员可以给该平台添加不同的菜单，将菜单以树形的方式进行展示
  5.用户认证：通过用户输入的账号使用Jwt进行认证，如果认证成功，将用户ID与账号封装成Token返回给前端，前端每次访问时，携带token进行访问
  6.分类管理：管理员可以通过分类管理可以对门户网站的导航进行管理以及影视的分类
  7.日志管理：当管理员登录或者执行每次操作时，都将记录在日志当中，超级管理员可以通过日志管理进行查询
