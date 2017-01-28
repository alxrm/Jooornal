package rm.com.jooornal.data.services;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.provider.Telephony;
import android.support.annotation.NonNull;
import android.telephony.SmsMessage;
import java.util.List;
import javax.inject.Inject;
import rm.com.jooornal.JooornalApplication;
import rm.com.jooornal.data.entity.Phone;
import rm.com.jooornal.data.entity.Sms;
import rm.com.jooornal.data.provider.PhoneProvider;
import rm.com.jooornal.data.provider.ProviderListener;
import rm.com.jooornal.util.Intents;
import rm.com.jooornal.util.Lists.Accumulator;
import rm.com.jooornal.util.Lists.Transformer;

import static rm.com.jooornal.util.Lists.listOfArray;
import static rm.com.jooornal.util.Lists.map;
import static rm.com.jooornal.util.Lists.reduce;

@SuppressWarnings("deprecation") public final class SmsReceiver extends BroadcastReceiver
    implements ProviderListener<Phone> {

  private static final String KEY_PDU_CHUNKS = "pdus";

  @Inject PhoneProvider provider;

  private String fullMessageText = "";

  @Override public void onReceive(Context context, Intent intent) {
    ((JooornalApplication) context.getApplicationContext()).injector().inject(this);

    if (!Intents.checkIntent(intent, Telephony.Sms.Intents.SMS_RECEIVED_ACTION)) {
      return;
    }

    final List<SmsMessage> receivedChunks = unwrapMessage(intent);
    final String from = receivedChunks.get(0).getOriginatingAddress();

    fullMessageText = unwrapMessageText(receivedChunks);
    provider.provide(from, this);
  }

  @Override public void onProvide(@NonNull final Phone payload) {
    saveMessage(fullMessageText, payload);
  }

  private void saveMessage(@NonNull String msgText, @NonNull Phone phone) {
    final Sms sms = new Sms();

    sms.phone = phone;
    sms.student = phone.student;
    sms.text = msgText;
    sms.time = System.currentTimeMillis();

    sms.save();
  }

  @NonNull private List<SmsMessage> unwrapMessage(@NonNull Intent intent) {
    final List<Object> pduChunks = listOfArray((Object[]) intent.getExtras().get(KEY_PDU_CHUNKS));

    return map(pduChunks, new Transformer<Object, SmsMessage>() {
      @Override public SmsMessage invoke(Object item) {
        return SmsMessage.createFromPdu((byte[]) item);
      }
    });
  }

  @NonNull private String unwrapMessageText(@NonNull List<SmsMessage> receivedChunks) {
    return reduce(receivedChunks, "", new Accumulator<SmsMessage, String>() {
      @Override public String collect(String result, SmsMessage item) {
        return result + item.getMessageBody();
      }
    });
  }
}
