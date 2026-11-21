package com.ezbank.app;

import android.content.Context;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;

@CapacitorPlugin(name = "EzWidget")
public class EzWidgetPlugin extends Plugin {
  @PluginMethod
  public void update(PluginCall call) {
    Context c = getContext();
    long spent = Math.round(call.getDouble("spent", 0.0));
    long allow = Math.round(call.getDouble("allow", 0.0));
    c.getSharedPreferences("ezbank", 0).edit().putLong("spent", spent).putLong("allow", allow).putBoolean("has", true).apply();
    EzWidgetProvider.refreshAll(c);
    call.resolve();
  }
}
