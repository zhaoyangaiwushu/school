# Git 结构

> 本地库->历史版本
>
> 暂存区->临时存储->git commit 本地库
>
> 工作区->写代码->git add->暂存区

# 本地库和远程库

> 团队内部协作
>
> ![](http://ahzy.sooman.cn/团队内部模式.png)
>
> 跨团队协作
>
> ![](http://ahzy.sooman.cn/跨团队协作.png)



# 本地库初始化

$ git init

注意：.git 目录中存放的是本地库相关的子目录和文件，不要删除，也不要胡乱修改。

# 设置签名

区分不同开发人员的身份

> - 项目级别/仓库级别：仅在当前本地库范围内有效。
>   - git config user.name tom_pro
>   - git config user.email goodMorning_pro@atguigu.com
> - 系统用户级别：登录当前操作系统的用户范围
>   - git config --global user.name tom_glb
>   - git config --global goodMorning_pro@atguigu.com
> - 查看
>   - $ cat .git/config
> - 级别优先级
>   - 就近原则：项目级别优先于系统用户级别，二者都有时采用项目级别的签名
>   - 如果只有系统用户级别的签名，就以系统用户级别的签名为准
>   - 二者都没有不允许

# 状态查看

查看工作区、暂存区状态 git status 

# 在Idea中使用Git

