import os, shutil, re
from PIL import Image
A = "android/app/src/main"
pkg = os.path.join(A, "java/com/ezbank/app"); os.makedirs(pkg, exist_ok=True)
for f in os.listdir("overlay/java"): shutil.copy(os.path.join("overlay/java", f), os.path.join(pkg, f))
for d in ("layout", "xml", "drawable"):
    os.makedirs(os.path.join(A, "res", d), exist_ok=True)
    for f in os.listdir(f"overlay/res/{d}"): shutil.copy(os.path.join(f"overlay/res/{d}", f), os.path.join(A, "res", d, f))
mf = os.path.join(A, "AndroidManifest.xml"); s = open(mf, encoding="utf-8").read()
recv = '''<receiver android:name=".EzWidgetProvider" android:exported="true"><intent-filter><action android:name="android.appwidget.action.APPWIDGET_UPDATE"/></intent-filter><meta-data android:name="android.appwidget.provider" android:resource="@xml/ez_widget_info"/></receiver>
    </application>'''
perms = '''<uses-permission android:name="android.permission.CAMERA"/>
    <uses-permission android:name="android.permission.RECORD_AUDIO"/>
    <uses-permission android:name="android.permission.MODIFY_AUDIO_SETTINGS"/>
    <uses-feature android:name="android.hardware.camera" android:required="false"/>
    <application'''
assert "</application>" in s and "<application" in s
s = s.replace("</application>", recv, 1); s = s.replace("<application", perms, 1)
open(mf, "w", encoding="utf-8").write(s)
res = os.path.join(A, "res"); shutil.rmtree(os.path.join(res, "mipmap-anydpi-v26"), ignore_errors=True)
logo = Image.open("www/icons/icon-512.png").convert("RGB")
for d, n in (("mdpi", 48), ("hdpi", 72), ("xhdpi", 96), ("xxhdpi", 144), ("xxxhdpi", 192)):
    p = os.path.join(res, f"mipmap-{d}"); os.makedirs(p, exist_ok=True)
    for f in ("ic_launcher.png", "ic_launcher_round.png"): logo.resize((n, n), Image.LANCZOS).save(os.path.join(p, f))
    fg = os.path.join(p, "ic_launcher_foreground.png")
    if os.path.exists(fg): os.remove(fg)
print("parche aplicado")
