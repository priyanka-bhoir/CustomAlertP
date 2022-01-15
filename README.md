# CustomAlertP

How to
To get a Git project into your build:

## Progess

![Record_2022-01-10-22-36-35](https://user-images.githubusercontent.com/58426635/149612119-a36bb2a4-4bca-4f58-abb8-9697090c03b6.gif)

![VID_20220113192358](https://user-images.githubusercontent.com/58426635/149612121-175018db-2bc2-4c75-b504-69afe247d97f.gif)

![VID_20220113192451](https://user-images.githubusercontent.com/58426635/149612123-9f7aaec6-e1c3-41b2-8cdf-c8052a81969c.gif)

![VID_20220113192538](https://user-images.githubusercontent.com/58426635/149612124-1b8668c7-c231-4a88-8b2c-d9fa9d01bcf2.gif)

![VID_20220113192626](https://user-images.githubusercontent.com/58426635/149612125-1f67fa33-c08d-404b-a6eb-cfe7397254e3.gif)





Step 1. Add the JitPack repository to your build file

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
 
