# Blockies Icons Android

![Blockies Icon](https://github.com/nikhiljainlive/blockies_android/assets/47408125/5076a5a7-5f79-407b-bb3a-e52b04d884b5)

This project is inspired by [ethereum/blockies](https://github.com/ethereum/blockies).

## Modules

### 1. blockiesview

The `blockiesview` module offers a custom view for displaying blockies icons. You can adjust the icon's size and appearance based on the view's width and height, as well as the pixel size.

#### Usage

```xml
<!-- app:color, app:bgColor, app:spotColor are optional -->

<com.nikhiljain.blockiesview.BlockiesIconView
    android:layout_width="200dp"
    android:layout_height="200dp"
    app:seed="0xfb6916095ca1df60bb79ce92ce3ea74c37c5d359"
    app:size="10"
    app:color="@android:color/holo_green_light"
    app:bgColor="@android:color/holo_blue_light"
    app:spotColor="@android:color/holo_orange_dark" />
```

### 2. blockiesgenerator

The `blockiesgenerator` module is responsible for generating blockies icon bitmaps. You can control the pixel size and image scale to produce custom icons.

#### Usage

```kotlin
val imageView = ImageView(this)
imageView.layoutParams = ViewGroup.LayoutParams(100, 100)

// Create a BlockiesIconGenerator instance
val iconGenerator = BlockiesIconGenerator(
    seed = "0xfb6916095ca1df60bb79ce92ce3ea74c37c5d359",
    size = 10,
    scale = 10,
    color = ContextCompat.getColor(context, android.R.color.holo_green_light),       // color will be generated as per the seed if not passed
    bgColor = ContextCompat.getColor(context, android.R.color.holo_blue_bright),     // bgColor will be generated as per the seed if not passed
    spotColor = ContextCompat.getColor(context, android.R.color.holo_orange_dark)    // spotColor will be generated as per the seed if not passed
)

// Generate the blockies icon bitmap
val blockiesIconBitmap = iconGenerator.generateIconBitmap()

// Set the generated bitmap to an ImageView
imageView.setImageBitmap(blockiesIconBitmap)
```

### 3. blockiesglide

The `blockiesglide` module requires [Glide](https://github.com/bumptech/glide) library as dependency. `BlockiesGlideModule` library Glide module is responsible for registering the necessary components and loads the bitmap image from the `BlockiesIconData` object.

For this, you need to add a class which extends AppGlideModule in your application module. See more here - [Glide - Configuration](https://bumptech.github.io/glide/doc/configuration.html#applications)

```kotlin
@GlideModule
class YourAppModule : AppGlideModule()
```

#### Usage

```kotlin
val imageView = ImageView(this)
imageView.layoutParams = ViewGroup.LayoutParams(100, 100)
Glide.with(imageView)
    .load(
        BlockiesIconData(
            seed = "0xfb6916095ca1df60bb79ce92ce3ea74c37c5d359",
            size = 10,
            color = android.R.color.holo_green_light,        // color will be generated as per the seed if not passed
            bgColor = android.R.color.holo_blue_bright,      // bgColor will be generated as per the seed if not passed
            spotColor = android.R.color.holo_orange_dark     // spotColor will be generated as per the seed if not passed
        )
    )
    .placeholder(R.drawable.ic_launcher_foreground)
    .circleCrop()
    .into(imageView)

```

The above code sample loads the bitmap from the BlockiesIconData object with the help of Glide library.

## Getting Started

To use this library in your Android project, you need to include it as a dependency in your app module `build.gradle` file. Make sure you have JitPack as a repository in your project's `build.gradle` file.

```gradle
dependencies {
    // BlockiesView dependency
    implementation 'com.github.nikhiljainlive.blockies_android:blockiesview:v0.2.0'

    // BlockiesGenerator dependency
    implementation 'com.github.nikhiljainlive.blockies_android:blockiesgenerator:v0.2.0'

    // BlockiesGlide dependency
    implementation 'com.github.nikhiljainlive.blockies_android:blockiesglide:v0.2.0'
}
```

And, in root project directory's `build.gradle` file:

```gradle
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
      mavenCentral()
      maven { url 'https://jitpack.io' }
    }
}
```

For more information and advanced usage, refer to the project's samples.

## License

This project is licensed under the MIT License - see the [LICENSE](https://github.com/nikhiljainlive/blockies_android/blob/main/LICENSE) file for details.

## Support or Contact

If you have any questions or need assistance, please feel free to [create an issue](https://github.com/nikhiljainlive/blockies_android/issues) in this repository.
