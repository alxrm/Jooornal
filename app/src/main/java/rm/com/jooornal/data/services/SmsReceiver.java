package rm.com.jooornal.data.services;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.provider.Telephony;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.telephony.SmsMessage;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import rm.com.jooornal.JooornalApplication;
import rm.com.jooornal.data.entity.Phone;
import rm.com.jooornal.data.entity.Sms;
import rm.com.jooornal.data.provider.PhoneProvider;
import rm.com.jooornal.data.provider.ProviderListener;
import rm.com.jooornal.util.Collections;
import rm.com.jooornal.util.Collections.Predicate;

/**
 * Created by alex
 */

public final class SmsReceiver extends BroadcastReceiver implements ProviderListener<Phone> {
  private static final String KEY_PDU_CHUNKS = "pdus";

  @Inject PhoneProvider provider;

  private List<SmsMessage> receivedMessages = new ArrayList<>();

  @Override public void onReceive(Context context, Intent intent) {
    ((JooornalApplication) context.getApplicationContext()).injector().inject(this);

    if (!checkIntent(intent, Telephony.Sms.Intents.SMS_RECEIVED_ACTION)) {
      return;
    }

    receivedMessages = unwrapMessages(intent);

    for (SmsMessage message : receivedMessages) {
      final String from = message.getOriginatingAddress();
      provider.provide(from, this);
    }
  }

  @Override public void onProvide(@NonNull final Phone payload) {
    final SmsMessage messageToSave = findSmsByNumber(receivedMessages, payload.phoneNumber);

    if (messageToSave != null) {
      saveMessage(messageToSave, payload);
    }
  }

  private void saveMessage(@NonNull SmsMessage message, @NonNull Phone phone) {
    final long time = message.getTimestampMillis();
    final String text = message.getMessageBody();
    final Sms sms = new Sms();

    sms.phone = phone;
    sms.student = phone.student;
    sms.text = text;
    sms.time = time;

    sms.save();
  }

  @Nullable private SmsMessage findSmsByNumber(@NonNull List<SmsMessage> messages,
      @NonNull final String number) {
    return Collections.first(messages, new Predicate<SmsMessage>() {
      @Override public boolean test(SmsMessage item) {
        return item.getOriginatingAddress().equals(number);
      }
    });
  }

  @NonNull private List<SmsMessage> unwrapMessages(@NonNull Intent intent) {
    final List<Object> pduChunks =
        Collections.listOfArray((Object[]) intent.getExtras().get(KEY_PDU_CHUNKS));

    return Collections.map(pduChunks, new Collections.Transformer<Object, SmsMessage>() {
      @Override public SmsMessage invoke(Object item) {
        //noinspection deprecation
        return SmsMessage.createFromPdu((byte[]) item);
      }
    });
  }

  private static boolean checkIntent(@Nullable Intent intent, @NonNull String action) {
    return intent != null
        && action.equalsIgnoreCase(intent.getAction())
        && intent.getExtras() != null;
  }
}
