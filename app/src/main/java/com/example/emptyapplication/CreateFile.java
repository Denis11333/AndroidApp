package com.example.emptyapplication;

import com.google.api.services.sheets.v4.model.ValueRange;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CreateFile {

    public static void createFile(File file){

        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);   // outputStream create the file

            FileInputStream fileInputStream = new FileInputStream(file);

            FileWriter fileWriter = new FileWriter(file);

            String str = "{\n" +
                    "  \"type\": \"service_account\",\n" +
                    "  \"project_id\": \"august-bond-354607\",\n" +
                    "  \"private_key_id\": \"df7674f0599b1b1c4ee9e7fb743fa899800d28dd\",\n" +
                    "  \"private_key\": \"-----BEGIN PRIVATE KEY-----\\nMIIEuwIBADANBgkqhkiG9w0BAQEFAASCBKUwggShAgEAAoIBAQCPixNZtanBxeq3\\n4a6OjlNfFzm1R6T+DfgIWTwdcbkuAz3X2hWD6pHm84tnZPqRtWWLpImdzge9Z7N4\\nhTFeLqWKV4jgGpmDmxSRDFgeBBa3Z9/6NosTISiKL2X14YGHXl03XkH+YQpwemmh\\nqrr9ndbasCWJWC3dEEB4aPmxDZkmA52CvrPK1kGDOIesjDaJ4w71aqMYP2yBE7kH\\nxFZ/08t42CDCg+xGX/g89ou4h2l+phWZG86P3wobrO1DKUXGTi9CNv+3UrINki8Z\\nAUvqvow34oMvIKMK01Tf+qwy4SvlscoPGDiIrsFMm5KoO/coVx6ArH2ikX0WiDZE\\n0/KQpWApAgMBAAECgf9u3DkwhsnLvg4ceJfl/v4+ZInxECBKJXgHvSsBHM1NITZW\\nlyNqmUrqO091VCKSbeFKtFRcLpO5ykKVpUiTz4n4XGKOaned49xMizCSRbdpgJP3\\nRcoHNcQOCbO2sVhXKItTJyhis0qY42KQRO5CF/CeXkDxMh2gMSQUfqKMV6tF1zZG\\nRJherp917ZuhBMx0s7daCj5QGqm2RBMnF2dnO6UJsEQz7l1WryrpxL1azVhk41Un\\nHrhLR191/ffr8AVlAvRwPX2BupQF70m9Qpla1OzHs0Omg0IOH5lEl1/pRGjp1gZG\\nKhAbu9PzVvzvAPncQq0g4SFKGcvW57UTA0XYxBECgYEAxnJWhHfM9tkZMBFyqqmS\\nBaV7nqaSDUCXcOynOFm/YbPYviyuXC1rrvhmvimQoKd8dEn2y7JC5OPI7SSty6qe\\nJT59A/Z74Dr7TeJKBg2BcpjjR4ucpohEJ2b1J//9O6HjS9HNSrXmJKKOnxiV1dXG\\njW5gaC2z7DCW1rRic6W8D9ECgYEAuSxwdYFG3lNbY9u6KySLd7ozw6RxWClUX7Lm\\nleDxGbEk7bcor3bVrqJQ8zaeMd9cYJZ9oTn+v3EF/wAyH5sV3m6NqGlRnIHFZMJu\\n0YVISZWXEVZCWUwuvIazdkbJ9rL7oDVK2u3NyhzKp0R73LS76F/UGOpBwZUF5Li3\\nvfYyeNkCgYBwhhrKyEd/XQCsQeh8HuS+sOPUxOyh+RMmI/p5uGINm3X1983yVIrv\\nzsgrYdQxVGb8ZYg8XlsSAJtI5HhruNYtli47FgON1MP0mLCSmAvjmA+f3Ok5gKDs\\nmrO+N6g2rQf+UPuQNm5GLaqjScSEWNBaF9EwoWGZNsABBI2it8JYUQKBgHJQ8ABq\\n1eBtH49vV+hIgF1is5BFVb/yi+ckn9vJaa7NwJmMqG5ic5qDFGyBvHnXhcUPQvGk\\nxxNS6Du72DXQ3VhbcdKVak+vgbgpnJZ2rODYm9GVKhLMyFGU6vfKzeYiKtuF6vdr\\nNc9fpPBMhBcF8LeNaXgULpIeuOux6aU5d4FJAoGBAJcCYbmicD/II2d+KrtxfvzW\\nno9QJfKMsSBY9TfRTMW2hwxJ1i/FSv0ycqFPrXSv2nf4O2TQcGv0AhTm6sMdsMaz\\nMxpppG3SD9Gb94HdfT993fl1nmeEporcALOdHK4h2YECVN3DfoB6lG+Ixlq5KwjW\\nPhzQVMdFMXmxtMIv+XWx\\n-----END PRIVATE KEY-----\\n\",\n" +
                    "  \"client_email\": \"denis-165@august-bond-354607.iam.gserviceaccount.com\",\n" +
                    "  \"client_id\": \"100751590564862813441\",\n" +
                    "  \"auth_uri\": \"https://accounts.google.com/o/oauth2/auth\",\n" +
                    "  \"token_uri\": \"https://oauth2.googleapis.com/token\",\n" +
                    "  \"auth_provider_x509_cert_url\": \"https://www.googleapis.com/oauth2/v1/certs\",\n" +
                    "  \"client_x509_cert_url\": \"https://www.googleapis.com/robot/v1/metadata/x509/denis-165%40august-bond-354607.iam.gserviceaccount.com\"\n" +
                    "}\n";

            fileWriter.write(str);

            fileWriter.close();

            System.out.println(file.exists());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
