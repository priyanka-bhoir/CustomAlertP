# CustomAlertP

How to
To get a Git project into your build:

Step 1. Add the JitPack repository to your build file

* Loading


https://user-images.githubusercontent.com/58426635/149611681-551c522b-27aa-435a-9882-ab51ec946bb6.mp4



https://user-images.githubusercontent.com/58426635/149611683-bccf24ae-7cdd-4ceb-ae62-c92e85f92640.mp4



https://user-images.githubusercontent.com/58426635/149611685-fea5e789-f683-4f4d-a7bb-70c9b5415da4.mp4



https://user-images.githubusercontent.com/58426635/149611686-5d611c80-d225-45fd-aa32-92f6e80bd1d2.mp4



https://user-images.githubusercontent.com/58426635/149611687-252dea6c-7ea1-4668-9f26-58c2801ea42a.mp4




gradle
maven
sbt
leiningen
Add it in your root build.gradle at the end of repositories:

	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
Step 2. Add the dependency

	dependencies {
	        implementation 'com.github.priyanka-bhoir:CustomAlertP:Tag'
	}
 
