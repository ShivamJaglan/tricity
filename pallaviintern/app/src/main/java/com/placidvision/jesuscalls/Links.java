package com.placidvision.jesuscalls;

public class Links {
    public static final String HTTP = "http://";
    public static final String IP = "34.93.159.27";
    public static final String VIDEO_CONF = HTTP + IP + "/PlacidVision/app/conference_details.php?fetch_video_con";

    public static String getCategoryJSONURL(String name, int id) {
        return HTTP + IP + "/PlacidVision/app/Category.php?userLoggedIn=" + name + "&fetch_element_for_category=" + id;
    }

    public static String getLanguagesListJSONURL(String name) {
        return HTTP + IP + "/PlacidVision/app/manage_profile.php?userLoggedIn=" + name;
    }

    public static String getMainPageJSONURL(String name, int id) {
        return HTTP + IP + "/PlacidVision/app/Main_page.php?fetch_main_page&userLoggedIn=" + name + "&Lang_id=" + id;
    }

    public static String getMainPageSlideShowJSONURL(String name) {
        return HTTP + IP + "/PlacidVision/app/Main_page.php?fetch_promo&userLoggedIn=" + name;
    }

    public static String getLiveTVJSONURL(String name, int id) {
        return HTTP + IP + "/PlacidVision/app/Main_page.php?fetch_Live&userLoggedIn=" + name + "&Lang_id=" + id;
    }

    public static String getSearchResultsJSONURL(String term, String name) {
        return HTTP + IP + "/PlacidVision/ajax/getAppSearchResults.php" + "?term=" + term + "&username=" + name;
    }

    public static String getLoginJSONURL(String username, String password) {
        return HTTP + IP + "/PlacidVision/login_app.php?submitButton&username=" + username + "&password=" + password;
    }

    public static String getRegisterJSONURL(String firstName, String lastName, String userName, String email, String cEmail, String password, String cPassword) {
        return HTTP + IP + "/PlacidVision/register_app.php?submitButton&firstName=" + firstName
                + "&lastName=" + lastName
                + "&username=" + userName
                + "&email=" + email
                + "&email2=" + cEmail
                + "&password=" + password
                + "&password2=" + cPassword;
    }
}