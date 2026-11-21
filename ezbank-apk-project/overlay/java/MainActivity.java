package com.ezbank.app;

import android.os.Bundle;
import com.getcapacitor.BridgeActivity;

public class MainActivity extends BridgeActivity {
  @Override
  public void onCreate(Bundle savedInstanceState) {
    registerPlugin(EzWidgetPlugin.class);
    super.onCreate(savedInstanceState);
  }
}
