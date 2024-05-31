plugins {
	kotlin("jvm")
}

kotlin {
	jvmToolchain(17)
}

sourceSets {
	main {
		java.srcDir("src")
	}
}

dependencies {
	val rxJavaVersion: String? by project
    implementation("io.reactivex.rxjava3:rxjava:$rxJavaVersion")
}