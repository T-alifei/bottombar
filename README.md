# bottombar
基于FlycoTabLayout简单的实现了一下底部导航栏

**导入依赖**

步骤1.将JitPack存储库添加到您的构建文件中

	//在root build.gradle中
	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' } //添加此段
		}
	}
步骤2.添加依赖项

	//在模块的 build.gradle中添加
	dependencies {
		implementation 'com.github.T-alifei:bottombar:1.1.3'
	}
