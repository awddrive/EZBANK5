package com.ezbank.app;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.widget.RemoteViews;
import java.util.Locale;

public class EzWidgetProvider extends AppWidgetProvider {
  @Override public void onUpdate(Context c, AppWidgetManager m, int[] ids) { for (int id : ids) draw(c, m, id); }

  static void refreshAll(Context c) {
    AppWidgetManager m = AppWidgetManager.getInstance(c);
    int[] ids = m.getAppWidgetIds(new ComponentName(c, EzWidgetProvider.class));
    for (int id : ids) draw(c, m, id);
  }

  static void draw(Context c, AppWidgetManager m, int id) {
    SharedPreferences p = c.getSharedPreferences("ezbank", 0);
    boolean has = p.getBoolean("has", false);
    long spent = p.getLong("spent", 0), allow = p.getLong("allow", 0);
    float pct = allow > 0 ? (float) spent / allow : (spent > 0 ? 2f : 0f);
    int color = pct > 1f ? 0xFFE5484D : pct > .75f ? 0xFFE0A210 : 0xFF17A35A;
    RemoteViews v = new RemoteViews(c.getPackageName(), R.layout.ez_widget);
    v.setImageViewBitmap(R.id.gauge, gauge(pct, color));
    v.setTextViewText(R.id.pct, has ? Math.round(pct * 100) + "%" : "—");
    v.setTextViewText(R.id.spent, has ? money(spent) + " hoy" : "Abre Ezbank");
    v.setTextViewText(R.id.sub, has ? (spent > allow ? "Excedido " + money(spent - allow) : "Quedan " + money(allow - spent)) : "para configurar");
    Intent i = c.getPackageManager().getLaunchIntentForPackage(c.getPackageName());
    v.setOnClickPendingIntent(R.id.root, PendingIntent.getActivity(c, 0, i, PendingIntent.FLAG_IMMUTABLE | PendingIntent.FLAG_UPDATE_CURRENT));
    m.updateAppWidget(id, v);
  }

  static Bitmap gauge(float pct, int color) {
    Bitmap b = Bitmap.createBitmap(400, 220, Bitmap.Config.ARGB_8888);
    Canvas cv = new Canvas(b);
    Paint p = new Paint(Paint.ANTI_ALIAS_FLAG);
    p.setStyle(Paint.Style.STROKE); p.setStrokeWidth(34); p.setStrokeCap(Paint.Cap.ROUND);
    RectF r = new RectF(40, 40, 360, 360);
    p.setColor(0x33FFFFFF); cv.drawArc(r, 180, 180, false, p);
    if (pct > 0) { p.setColor(color); cv.drawArc(r, 180, 180 * Math.min(pct, 1f), false, p); }
    return b;
  }

  static String money(long cents) { return String.format(new Locale("es", "MX"), "$%,.0f", cents / 100.0); }
}
