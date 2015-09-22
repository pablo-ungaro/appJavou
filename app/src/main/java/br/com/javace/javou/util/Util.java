package br.com.javace.javou.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import java.nio.channels.NoConnectionPendingException;

import br.com.javace.javou.R;

/**
 * Created by Rudsonlive on 10/07/15.
 */
public class Util {
    public static int[] shirtSize = new int[] {
            R.string.shirt_size_p, R.string.shirt_size_m, R.string.shirt_size_g, R.string.shirt_size_gg, R.string.shirt_size_eg};

    public static int[] shirtSizeColor = new int[] {
            R.color.shirtSizePColor, R.color.shirtSizeMColor, R.color.shirtSizeGColor, R.color.shirtSizeGGColor, R.color.shirtSizeEGColor};

    public static boolean checkConnection(Context context) throws NoConnectionPendingException {

        ConnectivityManager connectivity = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connectivity == null) {
            return false;
        } else {

            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null) {
                for (NetworkInfo anInfo : info) {
                    if (anInfo.getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public abstract static class Mask {
        public static String unmask(String s) {
            return s.replaceAll("[.]", "").replaceAll("[-]", "")
                    .replaceAll("[/]", "").replaceAll("[(]", "")
                    .replaceAll("[)]", "");
        }

        public static TextWatcher insert(final String mask, final EditText ediTxt) {
            return new TextWatcher() {
                boolean isUpdating;
                String old = "";

                public void onTextChanged(CharSequence s, int start,
                                          int before, int count) {
                    String str = Mask.unmask(s.toString());
                    String mascara = "";

                    if (isUpdating) {
                        old = str;
                        isUpdating = false;
                        return;
                    }

                    int i = 0;
                    for (char m : mask.toCharArray()) {
                        if (m != '#' && str.length() > old.length()) {
                            mascara += m;
                            continue;
                        }
                        try {
                            mascara += str.charAt(i);
                        } catch (Exception e) {
                            break;
                        }
                        i++;
                    }

                    isUpdating = true;
                    ediTxt.setText(mascara);
                    ediTxt.setSelection(mascara.length());
                }

                public void beforeTextChanged(CharSequence s, int start,
                                              int count, int after) {
                }

                public void afterTextChanged(Editable s) {
                }
            };
        }
    }
}
