# dreidroid
Shared Repository for Android projects

# Local Development inside Android Project
* Clone repo into `/SOME-DIR/dreidroid` (outside of your app repository)
* In `local.properties` add:
```groovy
dreidroid.dir=/SOME-DIR/dreidroid
```
* In your `settings.gradle` dynamically include the project if the setting exists
```groovy
Properties properties = new Properties()
properties.load(new File(rootProject.projectDir.absolutePath + '/local.properties').newDataInputStream())
def dreidroidDir = properties.getProperty('dreidroid.dir')
if (dreidroidDir != null) {
    include ':dreidroid'
    project(':dreidroid').projectDir = new File(dreidroidDir)
}
```
* In your `build.gradle` add repository url and credentials for JitPack
```groovy
maven {
    url 'https://jitpack.io'
    credentials { username githubAuthToken }
}
```
* In your `$HOME/.gradle/gradle.properties` add a newly generated github access token
```groovy
githubAuthToken=AUTH_TOKEN
```
* In your `app/build.gradle` add the dependency if the local property is set otherwise fetch from JitPack
```groovy
dependencies {

    Properties properties = new Properties()
    properties.load(new File(rootProject.projectDir.absolutePath + '/local.properties').newDataInputStream())
    def dreidroidDir = properties.getProperty('dreidroid.dir')
    if (dreidroidDir != null) {
        implementation project (":dreidroid")
    } else {
        // if the dreidroid local directory is not we will fetch from github
        // TODO: make sure the jitpack works with private repo
        // implementation 'com.github.dreipol:dreidroid:develop-SNAPSHOT'
    }
}
```
* Sync your gradle project and you should be able to import packages from dreidroid
* Enjoy