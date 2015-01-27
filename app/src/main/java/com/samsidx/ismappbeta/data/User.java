package com.samsidx.ismappbeta.data;

import android.net.Uri;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseRelation;
import com.parse.ParseUser;

/**
 * Created by samsidx(Majeed Siddiqui) on 14/1/15.
 */

@ParseClassName("_User")
public class User extends ParseUser {

    public static final String KEY_PROFILE_PIC = "profilePic"; // user's profile pic

    // BaaS keys for Name
    public static final String KEY_FIRST_NAME = "firstName";
    public static final String KEY_LAST_NAME = "lastName";

    public static final String GENDER_MALE = "male"; // constant for male gender
    public static final String GENDER_FEMALE = "female"; // constant for female gender

    public static final String KEY_GENDER = "gender"; // user gender male/female

    // BaaS keys for academic
    public static final String KEY_BATCH_OF = "batchOf";
    public static final String KEY_BRANCH_NAME = "branchName";
    public static final String KEY_DEGREE_NAME = "degree";

    // BaaS keys for place
    public static final String KEY_CITY = "city";
    public static final String KEY_STATE = "state";

    public static final String KEY_ROLE = "role"; // role defines student or prof

    // constants for role
    public static final String ROLE_STUDENT = "student";
    public static final String ROLE_PROFESSOR = "professor";

    public static final String KEY_CONTACT = "contact"; // user contact

    public static final String SUBSCRIBED_CLUBS_RELATION = "subscribedClubs"; // clubs user subscribed

    public User() {
    }

    public Uri getProfilePicUri() {
        ParseFile pic = getParseFile(KEY_PROFILE_PIC);

        // check if profile pic is set
        // if not return null
        if (pic == null) {
            return null;
        }

        // if set return url as uri
        return Uri.parse(pic.getUrl());
    }

    public void setProfilePic(ParseFile profilePic) {
        put(KEY_PROFILE_PIC, profilePic);
    }

    public String getFirstName() {
        return getString(KEY_FIRST_NAME);
    }

    public void setFirstName(String firstName) {
        put(KEY_FIRST_NAME, firstName);
    }

    public String getLastName() {
        return getString(KEY_LAST_NAME);
    }

    public void setLastName(String lastName) {
        put(KEY_LAST_NAME, lastName);
    }

    public String getFullName() {
        String fullName = getString(KEY_FIRST_NAME);
        String lastName = getString(KEY_LAST_NAME);

        if (lastName != null && !lastName.isEmpty()) {
            fullName += " " + lastName;
        }

        return fullName;
    }

    public String getGender() {
        return getString(KEY_GENDER);
    }

    public void setGender(String gender) {
        put(KEY_GENDER, gender);
    }

    public String getCity() {
        return getString(KEY_CITY);
    }

    public void setCity(String city) {
        put(KEY_CITY, city);
    }

    public String getState() {
        return getString(KEY_STATE);
    }

    public void setState(String state) {
        put(KEY_STATE, state);
    }

    public Contact getContact() {
        return (Contact) get(KEY_CONTACT);
    }

    public void setContact(Contact contact) {
        put(KEY_CONTACT, contact);
    }

    public ParseRelation<Club> getSubscribedClubsRelation() {
        return getRelation(SUBSCRIBED_CLUBS_RELATION);
    }

    public String getBatchOf() {
        return getString(KEY_BATCH_OF);
    }

    public void setBatchOf(int year) {
        put(KEY_BATCH_OF, Integer.toString(year));
    }

    public String getBranchName() {
        return getString(KEY_BRANCH_NAME);
    }

    public void setBranch(String branchName) {
        put(KEY_BRANCH_NAME, branchName);
    }

    public String getDegreeName() {
        return getString(KEY_DEGREE_NAME);
    }

    public void setDegreeName(String degreeName) {
        put(KEY_DEGREE_NAME, degreeName);
    }

    public String getRole() {
        return getString(KEY_ROLE);
    }

    public void setRole(String typeOfRole) {
        if (typeOfRole.equals(ROLE_STUDENT)) {
            put(KEY_ROLE, typeOfRole);
        }
        else if (typeOfRole.equals(ROLE_PROFESSOR)) {
            put(KEY_ROLE, typeOfRole);
        }
        else {
            put(KEY_ROLE, "UNKNOWN");
        }
    }
}
