# Axari
<p align="center"><img src="https://i.ibb.co/r3dDvW9/MGR-logo.png" alt="MGR-logo" border="0"></p>

Axari is a lightweight app aiming at easing the life of players of games with gacha mechanics by allowing them do perform **fast rerolls instead of a standard reroll**. <br>
**Fast reroll is rerolling without having to uninstall and redownload all game files.**

If your game of choice isn't there, don't panic !
It is faster to add support here than doing standard reroll anyway :sweat_smile:


# Adding support for a new game
Adding support for an app is fairly simple, the framework take care of many things for you :thumbsup: <br>
**1.** You first need to add a default app name in the string.xml file:
``` xml
<string name="com.package.name">Exemple name<string/>
```
**2.** Then you need to subclass the abstract `BaseRerollableApp` like so:
```java
public class DocumentationApp extends BaseRerollableApp {  
	public DocumentationApp(Context ctx) {  
		super(ctx, "com.package.name", R.string.com_package_name);  
	}  

	@Override  
	public boolean reroll(){
		//IMPLEMENT REROLL LOGIC
		return false; //Whether the reroll succeded or not.  
	}  
}
```
You need to implement at least the `reroll()` method.
Most of the time, you just need to know which files/folders to delete. <br>
**Note:** `reroll()` is run in a separate thread. Do NOT perform any method affecting the UI !

**3.** Finally, you need to add a line in the `RerollableAppList.java`:
```java
rerollableAppList.add(new DocumentationApp(ctx));
```

That's it !


# Methods index

## Custom reroll behavior
If you need full control on differents steps of the reroll process, additionnal functions are available to **@Override**.
<details> <summary>Reroll related functions</summary>

```java
public void onPreReroll()
```
Called before  `reroll()` on the UI Thread
Use it to prepare the reroll or fix pottential issues that could occur during the reroll if not taken care of.
The default behavior kills the app about to be rerolled.

---
```java
public boolean reroll()
```
The reroll method itself, running on a separate Thread from the UI Thread.
**You have to implement the reroll custom logic here.**

**@return**: Whether or not the reroll succeeded.

---
```java
public boolean onPostReroll(boolean success)
```
Called after `reroll()` on the UI Thread.
**@success**: Whether or not `reroll()` succeeded

---
```java
public void onRerollSuccess()
```
Called if the the reroll succeeded, on the UI Thread.

---
```java
public void onRerollFail()
```
Called if the reroll failed, on the UI Thread

</details>

## Convenicence logic functions
To help you provide custom reroll logic faster, convenience functions are provided from `BaseRerollableApp` and `Commands`

<details><summary>BaseRerollableApp functions</summary>

```java
final public String getPrivateDataDir(int flag = 0)
```
**@return:** The app private data directory *(Traditionally /data/data/com.package.name)*

Since by default you get the root of an app directory, you are provided several flags to access the ususal directories inside it:
```java
DIR_SHARED_PREF, DIR_FILES, DIR_CACHE, DIR_DATABASES, DIR_APP_WEBVIEW
```

---
```java
final public String getPublicDataDir(int flag = 0)
```
**@return:** The app public data dir *(Traditionally /sdcard/Android/com.package.name)*


---
```java
final public String getAppName()
```
**@return:** The app name, either the default one or the official one if the app is available.

---
```java
final public String getAppPackageName()
```
**@return:** The app package name.

</details>

---
The `Command.java`  final class provides pre-formatted UNIX functions for ease of use.
<details> <summary>Command utility functions</summary>

---
```java
final public static boolean removeFile(String filePath)
```
Force remove the file at the provided path
**@return:** Whether the command succeeded

---
```java
final public static boolean removeFolder(String folderPath)
```
Force remove the folder at the provided path, regardless of its content.
**@return:** Whether the command succeeded

---
```java
final public static boolean execute(String command)
```
Execute the given UNIX command as a Super-User.
**@return:** Whether the command succeeded
</details>
