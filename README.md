# Pending Intent Demo

## Pending Intent
The primary purpose of a `PendingIntent` is to grant permission to a foreign application to use the contained `Intent`.

## Implement Pending Intent
```
val resultIntent = Intent(this, MainActivity::class.java)
val resultPendingIntent = PendingIntent.getActivity(this, 1, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT)
```

## Types of Pending Intent Flag
### FLAG_UPDATE_CURRENT
Flag indicating that if the described `PendingIntent` already exists, then keep it but replace its extra data with what is in this new Intent.
### FLAG_CANCEL_CURRENT
Flag indicating that if the described `PendingIntent` already exists, the current one should be canceled before generating a new one.
### FLAG_IMMUTABLE
Flag indicating that the `PendingIntent` is immutable. This means that the additional intent argument will be ignored.
### FLAG_ONE_SHOT
Flag indicating that this `PendingIntent` can be used only once.
### FLAG_NO_CREATE
Flag indicating that if the described `PendingIntent` does not already exist, then simply return null instead of creating it.

## Types of Notification Visibility
### VISIBILITY_PRIVATE
Show this notification on all lockscreens, but conceal sensitive or private information on secure lockscreens.
### VISIBILITY_PUBLIC
Show this notification in its entirety on all lockscreens.
### VISIBILITY_SECRET
Do not reveal any part of this notification on a secure lockscreen.

## Preventing Activity Creation on Every Notification Tapped
Add `launchMode` inside `<activity>` tag on `AndroidManifest.xml`
```
<activity
    android:name=".MainActivity"
    android:launchMode="singleTop">
    
    ...
    
</activity>
```

## Demo
### Simple Notification `simple-notification`
<img src="https://gyazo.com/e86130ec44df2b9e25cd1668ea892430.gif" width="450px" height="800px" />

### Notification with Button & Color `buttons-and-color`
<img src="https://i.gyazo.com/3147445d2fa83b19aa29b10f6e97a9dd.gif" width="450px" height="800px" />

### Notification with Long Text & Thumbnail `big-text-inbox-style`
<img src="https://i.gyazo.com/3d85d856ecd9564b8a707ccad6382413.gif" width="450px" height="800px" />

### Notification with Big Picture `big-picture-media-style`
<img src="https://gyazo.com/896dd347a2bed601e64e1e4ce36b4e25.gif" width="450px" height="800px" />

### Direct Reply from Notification `messaging-style-direct-reply`
<img src="https://i.gyazo.com/50269c86c0f909f031e1f241c98c4e3b.gif" width="450px" height="800px" />

### Notification with Progress Bar `progress-bar-notification`
<img src="https://gyazo.com/5f4e8c9769b84038bb7087a8f919163b.gif" width="450px" height="800px" /><img src="https://i.imgur.com/610pnFb.png" width="450px" height="800px" />

### Notification Group `notification-groups`
<img src="https://i.gyazo.com/9799a06f3286fa742fba8f3f0e51d3ff.gif" width="450px" height="800px" />
