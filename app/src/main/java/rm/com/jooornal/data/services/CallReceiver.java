package rm.com.jooornal.data.services;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.telephony.TelephonyManager;
import javax.inject.Inject;
import rm.com.jooornal.JooornalApplication;
import rm.com.jooornal.data.entity.Call;
import rm.com.jooornal.data.entity.Phone;
import rm.com.jooornal.data.provider.PhoneProvider;
import rm.com.jooornal.data.provider.ProviderListener;
import rm.com.jooornal.util.Intents;

/**
 * фоновый сервис, отвечающий за отслеживание входящих вызовов
 */
public final class CallReceiver extends BroadcastReceiver implements ProviderListener<Phone> {

  @Inject PhoneProvider provider;

  /**
   * получено новое событие входящего вызова
   *
   * @param context контейнер информации приложения
   * @param intent объект события
   */
  @Override public void onReceive(Context context, Intent intent) {
    ((JooornalApplication) context.getApplicationContext()).injector().inject(this);

    if (!Intents.checkIntent(intent, TelephonyManager.ACTION_PHONE_STATE_CHANGED)) {
      return;
    }

    final String state = intent.getExtras().getString(TelephonyManager.EXTRA_STATE);
    final String number = intent.getExtras().getString(TelephonyManager.EXTRA_INCOMING_NUMBER);

    if (TelephonyManager.EXTRA_STATE_RINGING.equalsIgnoreCase(state)) {
      provider.provide(number, this);
    }
  }

  /**
   * результат запроса провайдера, который должен был найти, есть ли в базе студент с номером
   * телефона, звонок с которого только что произошёл
   *
   * получив телефон, в базу данных добавляется новый звонок
   *
   * @param payload сам результат, телефон студента
   */
  @Override public void onProviderResult(@NonNull Phone payload) {
    final Call call = new Call();

    call.student = payload.student;
    call.phone = payload;
    call.time = System.currentTimeMillis();
    call.save();
  }
}
