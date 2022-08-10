# KDC-WebSite
口袋村服务器的Web仓库

### **在线更新功能注意事项：**

- 更新之前最好先自己试一下

##### -）更新步骤如下

- help/update接口需包含的更新包

  - **minecraft-Update.zip**

  - **NsisoLauncher.zip**(记得放入最新的version.yaml)

  - **minecraft-Full.zip**(JavaZulu文件夹必须为空)

    ↑上述文件每次更新都需要重新上传↑

  - Jre_Win32.zip

  - Jre_Win64.zip

  - Jre_MACx64.zip

  - Jre_MACarm.zip

- 修改version.yaml文件，记得新旧版本号递进

    version.yaml中必须先删除文件再放入文件，才会有效果