package com.samsidx.ismappbeta.data;

import com.parse.ParseClassName;
import com.parse.ParseObject;

/**
 * Created by samsidx(Majeed Siddiqui) on 14/1/15.
 *
 */

@ParseClassName("Contact")
public class Contact extends ParseObject {

    // BaaS keys for local address
    public static final String KEY_HOSTEL_NAME = "hostelName";
    public static final String KEY_ROOM_NO = "roomNumber";

    // Emails and Phone Numbers are stored as array
    public static final String EMAILS_ARRAY = "emails";  // emails attached to contact info
    public static final String PHONE_NO_ARRAY = "phoneNumbers"; // phone numbers attached to contact info

    // public or private qualifiers
    public static final String KEY_IS_ADDRESS_PUBLIC = "isAddressPublic";
    public static final String KEY_IS_PHONE_PUBLIC = "isPhonePublic";
    public static final String KEY_IS_EMAIL_PUBLIC = "isEmailPublic";

    public String getHostelName() {
        return getString(KEY_HOSTEL_NAME);
    }

    public void setHostelName(String hostelName) {
        put(KEY_HOSTEL_NAME, hostelName);
    }

    public String getRoomNo() {
        return getString(KEY_ROOM_NO);
    }

    public void setRoomNo(String roomNumber) {
        put(KEY_ROOM_NO, roomNumber);
    }

    // TODO: implement emails, phone no. getters and setters
}
